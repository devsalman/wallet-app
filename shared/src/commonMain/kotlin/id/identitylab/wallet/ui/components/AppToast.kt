package id.identitylab.wallet.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.identitylab.wallet.ui.theme.DangerText
import id.identitylab.wallet.ui.theme.SuccessText
import id.identitylab.wallet.ui.theme.SurfaceWhite
import id.identitylab.wallet.ui.theme.TextPrimary

/**
 * A slim toast bar mirroring the VC Wallet `AppToast`. Visual-only: hides
 * itself after a short timeout. Place it inside a Box aligned to the bottom.
 */
@Composable
fun AppToast(
    visible: Boolean,
    message: String,
    type: String = "info",
    onHide: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val background = when (type) {
        "success" -> SuccessText
        "error" -> DangerText
        else -> TextPrimary
    }

    LaunchedEffect(visible, message) {
        if (visible && message.isNotEmpty()) {
            kotlinx.coroutines.delay(2500)
            onHide()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically { it },
        exit = fadeOut() + slideOutVertically { it },
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .background(background, RoundedCornerShape(14.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Text(
                text = message,
                color = SurfaceWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
