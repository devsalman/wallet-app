package id.identitylab.wallet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import id.identitylab.wallet.ui.screens.CreateAccountScreen
import id.identitylab.wallet.ui.screens.DashboardScreen
import id.identitylab.wallet.ui.screens.UnlockScreen
import id.identitylab.wallet.ui.theme.NufidTheme

internal enum class AppScreen {
    Unlock,
    CreateAccount,
    Dashboard,
}

/**
 * Root of the VC Wallet UI port. Uses simple state-based navigation between
 * the login (unlock), create-DID-account, and dashboard screens.
 */
@Composable
fun App() {
    NufidTheme {
        var screen by remember { mutableStateOf(AppScreen.Unlock) }

        when (screen) {
            AppScreen.Unlock -> UnlockScreen(
                onUnlocked = { screen = AppScreen.Dashboard },
            )

            AppScreen.CreateAccount -> CreateAccountScreen(
                onCreated = { screen = AppScreen.Dashboard },
            )

            AppScreen.Dashboard -> DashboardScreen(
                onOpenCreateDID = { screen = AppScreen.CreateAccount },
            )
        }
    }
}
