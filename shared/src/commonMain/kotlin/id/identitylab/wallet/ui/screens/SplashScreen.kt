package id.identitylab.wallet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.identitylab.wallet.ui.theme.Blue500
import id.identitylab.wallet.ui.theme.LightBlue50
import id.identitylab.wallet.ui.theme.SurfaceWhite
import id.identitylab.wallet.ui.theme.TextPrimary
import id.identitylab.wallet.ui.theme.NufidTheme

/**
 * Splash screen. UI only: shows the placeholder NufID logo mark on a white
 * background with the app name beneath. Navigation logic (auto-advance after
 * a delay) is intentionally left for later.
 *
 * @param onFinished Callback to trigger once the splash flow completes
 * (wired to navigation later).
 */
@Composable
fun SplashScreen(
    onFinished: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWhite),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            NufIdLogo()
            Text(
                text = "NufID Wallet",
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(top = 20.dp),
            )
        }
    }
}

/**
 * Placeholder NufID logo mark.
 *
 * REPLACE THIS with the real logo when it is ready: drop the vector into
 * `shared/src/commonMain/composeResources/drawable/nufid_logo.xml` and swap
 * the body of this composable for:
 *
 *     Image(painterResource(Res.drawable.nufid_logo), contentDescription = "NufID logo")
 *
 * Nothing else in the splash screen needs to change.
 */
@Composable
fun NufIdLogo() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(LightBlue50, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Shield,
            contentDescription = "NufID logo",
            tint = Blue500,
            modifier = Modifier.size(64.dp),
        )
    }
}

@Preview(widthDp = 400, heightDp = 800, showBackground = true)
@Composable
private fun SplashScreenPreview() {
    NufidTheme {
        SplashScreen(onFinished = {})
    }
}
