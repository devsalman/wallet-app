package id.identitylab.wallet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import id.identitylab.wallet.ui.screens.CreateAccountScreen
import id.identitylab.wallet.ui.screens.DashboardScreen
import id.identitylab.wallet.ui.screens.SplashScreen
import id.identitylab.wallet.ui.screens.UnlockScreen
import id.identitylab.wallet.ui.theme.NufidTheme

internal enum class AppScreen {
    Splash,
    Unlock,
    CreateAccount,
    Dashboard,
}

/**
 * Root of the VC Wallet UI port. Uses simple state-based navigation between
 * the splash, login (unlock), create-DID-account, and dashboard screens.
 */
@Composable
fun App() {
    NufidTheme {
        var screen by remember { mutableStateOf(AppScreen.Splash) }

        when (screen) {
            AppScreen.Splash -> SplashScreen(
                // Auto-advance/timing logic is deferred; onFinished is wired
                // once the splash flow logic is added.
                onFinished = { screen = AppScreen.Unlock },
            )

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
