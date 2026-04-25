package com.anthropic.claude.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anthropic.claude.chat.viewmodel.ChatListViewModel
import com.anthropic.claude.chat.viewmodel.ChatViewModel
import com.anthropic.claude.login.viewmodel.LoginViewModel
import com.anthropic.claude.settings.viewmodel.SettingsViewModel
import com.anthropic.claude.ui.screen.chat.ChatScreen
import com.anthropic.claude.ui.screen.chatlist.ChatListScreen
import com.anthropic.claude.ui.screen.login.LoginScreen
import com.anthropic.claude.ui.screen.settings.SettingsScreen

/** Top-level navigation destinations. */
object AppDestinations {
    const val LOGIN       = "login"
    const val CHAT_LIST   = "chat_list"
    const val CHAT        = "chat/{chatId}"
    const val SETTINGS    = "settings"

    fun chatRoute(chatId: String) = "chat/$chatId"
}

/**
 * Root navigation host for the Claude app.
 * Starts on the login screen; switches to chat_list on authentication.
 */
@Composable
fun ClaudeNavHost(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    chatListViewModel: ChatListViewModel,
    chatViewModel: ChatViewModel,
    settingsViewModel: SettingsViewModel,
    startDestination: String = AppDestinations.LOGIN,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(AppDestinations.LOGIN) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(AppDestinations.CHAT_LIST) {
                        popUpTo(AppDestinations.LOGIN) { inclusive = true }
                    }
                },
            )
        }

        composable(AppDestinations.CHAT_LIST) {
            ChatListScreen(
                viewModel = chatListViewModel,
                onOpenChat = { chatId ->
                    navController.navigate(AppDestinations.chatRoute(chatId))
                },
                onNewChat = {
                    navController.navigate(AppDestinations.chatRoute("new"))
                },
            )
        }

        composable(AppDestinations.CHAT) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: return@composable
            ChatScreen(
                viewModel = chatViewModel,
                onNavigateUp = { navController.navigateUp() },
            )
        }

        composable(AppDestinations.SETTINGS) {
            SettingsScreen(
                viewModel = settingsViewModel,
                onNavigateUp = { navController.navigateUp() },
                onNavigateToAccount = { },
                onNavigateToNotifications = { },
                onNavigateToPrivacy = { },
                onSignOut = {
                    navController.navigate(AppDestinations.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }
    }
}
