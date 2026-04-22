package com.anthropic.claude.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.concurrent.CancellationException

class ClaudeAppWidgetReceiver : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val action = intent.action
            when {
                action == ACTION_TRIGGER_LAMBDA -> {
                    val actionKey = intent.getStringExtra(EXTRA_ACTION_KEY)
                        ?: throw IllegalStateException("Intent is missing ActionKey extra")
                    val appWidgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID, -1)
                    if (appWidgetId == -1) throw IllegalStateException("Intent is missing AppWidgetId extra")
                    handleLambdaAction(context, actionKey, appWidgetId)
                }
                action == "androidx.glance.appwidget.action.DEBUG_UPDATE" ||
                action == Intent.ACTION_LOCALE_CHANGED -> {
                    val manager = AppWidgetManager.getInstance(context)
                    val component = ComponentName(context.packageName, javaClass.canonicalName
                        ?: throw IllegalStateException("no canonical name"))
                    val ids = if (intent.hasExtra(EXTRA_APPWIDGET_IDS)) {
                        intent.getIntArrayExtra(EXTRA_APPWIDGET_IDS)!!
                    } else {
                        manager.getAppWidgetIds(component)
                    }
                    onUpdate(context, manager, ids)
                }
                else -> super.onReceive(context, intent)
            }
        } catch (e: CancellationException) {
            return
        } catch (e: Exception) {
            Log.e("GlanceAppWidget", "Error in Glance App Widget", e)
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: android.os.Bundle,
    ) {
    }

    private fun handleLambdaAction(context: Context, actionKey: String, appWidgetId: Int) {
    }

    companion object {
        const val ACTION_TRIGGER_LAMBDA = "ACTION_TRIGGER_LAMBDA"
        const val EXTRA_ACTION_KEY = "EXTRA_ACTION_KEY"
        const val EXTRA_APPWIDGET_ID = "EXTRA_APPWIDGET_ID"
        const val EXTRA_APPWIDGET_IDS = "appWidgetIds"
    }
}
