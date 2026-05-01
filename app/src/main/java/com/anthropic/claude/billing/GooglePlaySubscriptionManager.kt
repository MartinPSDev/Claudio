package com.anthropic.claude.billing

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Manages Google Play subscription flows for Claude Pro and Max plans.
 *
 * Product IDs:
 *   - Pro: queried dynamically from product catalog
 *   - Max: `max-20250401-5x` (5x usage), `max-20250401-20x` (20x usage)
 *
 * Billing periods:
 *   - `P1M` (monthly)
 *   - `P1Y` (yearly)
 *
 * Feature flags:
 *   - `mobile_android_billing_is_offer_personalized` — controls personalized pricing disclosure
 */
class GooglePlaySubscriptionManager(
    private val activity: Activity,
    private val verifyPurchaseCallback: suspend (Purchase) -> VerifyResult,
) : PurchasesUpdatedListener {

    companion object {
        private const val TAG = "GooglePlaySubscriptionManager"
        private const val MAX_PLAN_5X = "max-20250401-5x"
        private const val MAX_PLAN_20X = "max-20250401-20x"
        private const val BILLING_PERIOD_MONTHLY = "P1M"
        private const val BILLING_PERIOD_YEARLY = "P1Y"
        private const val FLAG_IS_OFFER_PERSONALIZED = "mobile_android_billing_is_offer_personalized"
    }

    private var billingClient: BillingClient? = null

    private val _state = MutableStateFlow<SubscriptionState>(SubscriptionState.Idle)
    val state: StateFlow<SubscriptionState> = _state.asStateFlow()

    /**
     * Initializes and connects the BillingClient.
     */
    fun connect() {
        billingClient = BillingClient.newBuilder(activity)
            .setListener(this)
            .enablePendingPurchases()
            .build()

        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.i(TAG, "BillingClient connected")
                } else {
                    Log.e(TAG, "BillingClient setup failed: ${result.responseCode}")
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.w(TAG, "BillingClient disconnected")
            }
        })
    }

    /**
     * Queries available subscription products (Pro and Max).
     *
     * @param proProductId Product ID for Pro subscription.
     * @param maxProductId Product ID for Max subscription.
     * @return [SubscriptionProducts] with available plans, or null on error.
     */
    suspend fun queryProducts(
        proProductId: String,
        maxProductId: String,
    ): SubscriptionProducts? {
        val client = billingClient
        if (client == null) {
            Log.e(TAG, "Querying; BillingClient is null.")
            return null
        }

        if (!client.isReady) {
            Log.e(TAG, "Querying; No BillingClient connection.")
            return null
        }

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(proProductId)
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build(),
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(maxProductId)
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build(),
                )
            )
            .build()

        return suspendCancellableCoroutine { cont ->
            client.queryProductDetailsAsync(params) { result, productDetailsList ->
                if (result.responseCode != BillingClient.BillingResponseCode.OK) {
                    Log.e(TAG, "Subscribing; Bad product details code (${result.responseCode}).")
                    cont.resume(null)
                    return@queryProductDetailsAsync
                }

                val proProduct = productDetailsList.find { it.productId == proProductId }
                val maxProduct = productDetailsList.find { it.productId == maxProductId }

                if (proProduct == null) {
                    Log.e(TAG, "Querying; Cannot find product for pro.")
                    cont.resume(null)
                    return@queryProductDetailsAsync
                }

                val proPlans = extractPlans(proProduct)
                if (proPlans.isEmpty()) {
                    Log.e(TAG, "Querying; Pro products empty. Returning error.")
                    cont.resume(null)
                    return@queryProductDetailsAsync
                }

                val maxPlans = if (maxProduct != null) extractMaxPlans(maxProduct) else emptyList()

                cont.resume(SubscriptionProducts(proPlans = proPlans, maxPlans = maxPlans))
            }
        }
    }

    /**
     * Launches the Google Play billing flow for the selected plan.
     *
     * @param productDetails The selected product details.
     * @param offerToken The offer token for the selected plan.
     * @param isOfferPersonalized Whether to show personalized pricing disclosure.
     */
    fun launchBillingFlow(
        productDetails: ProductDetails,
        offerToken: String,
        isOfferPersonalized: Boolean = false,
    ) {
        val client = billingClient
        if (client == null) {
            Log.e(TAG, "Billing; BillingClient is null.")
            return
        }

        val flowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .setOfferToken(offerToken)
                        .build()
                )
            )
            .setIsOfferPersonalized(isOfferPersonalized)
            .build()

        val result = client.launchBillingFlow(activity, flowParams)
        if (result.responseCode != BillingClient.BillingResponseCode.OK) {
            Log.e(TAG, "Billing; Bad billing launch code (${result.responseCode}).")
        }
    }

    /**
     * Queries current active subscriptions.
     */
    suspend fun queryCurrentSubscriptions(): List<Purchase>? {
        val client = billingClient ?: return null

        return suspendCancellableCoroutine { cont ->
            val params = QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS)
                .build()

            client.queryPurchasesAsync(params) { result, purchases ->
                if (result.responseCode != BillingClient.BillingResponseCode.OK) {
                    Log.e(TAG, "querying current subscriptions; Bad BillingResponseCode: ${result.responseCode}")
                    cont.resume(null)
                    return@queryPurchasesAsync
                }

                if (purchases.size > 1) {
                    Log.w(TAG, "querying current subscriptions; Warning: more than one active purchase found.")
                }

                cont.resume(purchases)
            }
        }
    }

    override fun onPurchasesUpdated(result: BillingResult, purchases: List<Purchase>?) {
        when (result.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                purchases?.firstOrNull()?.let { purchase ->
                    _state.value = SubscriptionState.PurchasePending(purchase)
                }
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                _state.value = SubscriptionState.Cancelled
            }
            else -> {
                Log.e(TAG, "Billing; Bad billing purchase code (${result.responseCode})")
                _state.value = SubscriptionState.Error("Billing error: ${result.responseCode}")
            }
        }
    }

    fun disconnect() {
        billingClient?.endConnection()
        billingClient = null
    }

    // ── Private ──────────────────────────────────────────────────────────────

    private fun extractPlans(product: ProductDetails): List<SubscriptionPlan> {
        val offers = product.subscriptionOfferDetails ?: return emptyList()
        return offers.mapNotNull { offer ->
            val pricingPhase = offer.pricingPhases.pricingPhaseList.firstOrNull() ?: return@mapNotNull null
            val period = pricingPhase.billingPeriod
            if (period != BILLING_PERIOD_MONTHLY && period != BILLING_PERIOD_YEARLY) return@mapNotNull null

            SubscriptionPlan(
                productDetails = product,
                offerToken = offer.offerToken,
                billingPeriod = period,
                formattedPrice = pricingPhase.formattedPrice,
                priceMicros = pricingPhase.priceAmountMicros,
                currencyCode = pricingPhase.priceCurrencyCode,
                tier = SubscriptionTier.PRO,
            )
        }
    }

    private fun extractMaxPlans(product: ProductDetails): List<SubscriptionPlan> {
        val offers = product.subscriptionOfferDetails ?: return emptyList()
        return offers.mapNotNull { offer ->
            val basePlanId = offer.basePlanId
            val multiplier = when (basePlanId) {
                MAX_PLAN_5X -> 5
                MAX_PLAN_20X -> 20
                else -> {
                    Log.w(TAG, "Querying; skipping unknown max base plan $basePlanId")
                    return@mapNotNull null
                }
            }

            val pricingPhase = offer.pricingPhases.pricingPhaseList.firstOrNull()
            if (pricingPhase == null) {
                Log.w(TAG, "Querying; missing max offer details.")
                return@mapNotNull null
            }

            if (pricingPhase.priceAmountMicros <= 0) {
                Log.w(TAG, "Querying; skipping max base plan with bad pricing $basePlanId")
                return@mapNotNull null
            }

            SubscriptionPlan(
                productDetails = product,
                offerToken = offer.offerToken,
                billingPeriod = pricingPhase.billingPeriod,
                formattedPrice = pricingPhase.formattedPrice,
                priceMicros = pricingPhase.priceAmountMicros,
                currencyCode = pricingPhase.priceCurrencyCode,
                tier = SubscriptionTier.MAX,
                usageMultiplier = multiplier,
            )
        }
    }
}

// ── Models ───────────────────────────────────────────────────────────────

enum class SubscriptionTier { PRO, MAX }

data class SubscriptionPlan(
    val productDetails: ProductDetails,
    val offerToken: String,
    val billingPeriod: String,
    val formattedPrice: String,
    val priceMicros: Long,
    val currencyCode: String,
    val tier: SubscriptionTier,
    val usageMultiplier: Int? = null,
)

data class SubscriptionProducts(
    val proPlans: List<SubscriptionPlan>,
    val maxPlans: List<SubscriptionPlan>,
)

sealed interface SubscriptionState {
    data object Idle : SubscriptionState
    data class PurchasePending(val purchase: Purchase) : SubscriptionState
    data object Cancelled : SubscriptionState
    data class Error(val message: String) : SubscriptionState
}

sealed interface VerifyResult {
    data object Success : VerifyResult
    data class IdvRequired(val status: String) : VerifyResult
    data class Failed(val message: String) : VerifyResult
}
