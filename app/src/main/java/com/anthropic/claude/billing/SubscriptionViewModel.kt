package com.anthropic.claude.billing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the subscription/billing UI.
 *
 * Coordinates between [GooglePlaySubscriptionManager] and the UI layer
 * to display available plans and handle purchase flows.
 */
class SubscriptionViewModel(
    private val subscriptionManager: GooglePlaySubscriptionManager,
    private val verifyPurchase: suspend (Purchase) -> VerifyResult,
) : ViewModel() {

    private val _state = MutableStateFlow<SubscriptionUiState>(SubscriptionUiState.Loading)
    val state: StateFlow<SubscriptionUiState> = _state.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _state.value = SubscriptionUiState.Loading

            val products = subscriptionManager.queryProducts(
                proProductId = "pro_subscription",
                maxProductId = "max_subscription",
            )

            if (products != null) {
                _state.value = SubscriptionUiState.Available(
                    proPlans = products.proPlans,
                    maxPlans = products.maxPlans,
                    selectedPlan = null,
                )
            } else {
                _state.value = SubscriptionUiState.Error("Unable to load subscription plans")
            }
        }
    }

    fun selectPlan(plan: SubscriptionPlan) {
        val current = _state.value
        if (current is SubscriptionUiState.Available) {
            _state.value = current.copy(selectedPlan = plan)
        }
    }

    fun launchPurchase(plan: SubscriptionPlan, isOfferPersonalized: Boolean = false) {
        subscriptionManager.launchBillingFlow(
            productDetails = plan.productDetails,
            offerToken = plan.offerToken,
            isOfferPersonalized = isOfferPersonalized,
        )
    }

    fun verifyPendingPurchase(purchase: Purchase) {
        viewModelScope.launch {
            _state.value = SubscriptionUiState.Verifying

            when (val result = verifyPurchase(purchase)) {
                is VerifyResult.Success -> {
                    _state.value = SubscriptionUiState.Subscribed(
                        activePurchase = purchase,
                    )
                }
                is VerifyResult.IdvRequired -> {
                    _state.value = SubscriptionUiState.IdvRequired(
                        status = result.status,
                        purchase = purchase,
                    )
                }
                is VerifyResult.Failed -> {
                    _state.value = SubscriptionUiState.Error(result.message)
                }
            }
        }
    }

    fun checkCurrentSubscription() {
        viewModelScope.launch {
            val purchases = subscriptionManager.queryCurrentSubscriptions()
            val activePurchase = purchases?.firstOrNull()
            if (activePurchase != null) {
                _state.value = SubscriptionUiState.Subscribed(activePurchase = activePurchase)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptionManager.disconnect()
    }
}

// ── UI State ─────────────────────────────────────────────────────────────

sealed interface SubscriptionUiState {
    data object Loading : SubscriptionUiState

    data class Available(
        val proPlans: List<SubscriptionPlan>,
        val maxPlans: List<SubscriptionPlan>,
        val selectedPlan: SubscriptionPlan?,
    ) : SubscriptionUiState

    data object Verifying : SubscriptionUiState

    data class Subscribed(
        val activePurchase: Purchase,
    ) : SubscriptionUiState

    data class IdvRequired(
        val status: String,
        val purchase: Purchase,
    ) : SubscriptionUiState

    data class Error(val message: String) : SubscriptionUiState
}
