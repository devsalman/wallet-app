package id.identitylab.wallet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.identitylab.wallet.ui.components.AppButton
import id.identitylab.wallet.ui.components.AppTextField
import id.identitylab.wallet.ui.components.AppToast
import id.identitylab.wallet.ui.theme.Blue500
import id.identitylab.wallet.ui.theme.Blue700
import id.identitylab.wallet.ui.theme.BorderSubtle
import id.identitylab.wallet.ui.theme.Canvas
import id.identitylab.wallet.ui.theme.LightBlue100
import id.identitylab.wallet.ui.theme.LightBlue50
import id.identitylab.wallet.ui.theme.Orange500
import id.identitylab.wallet.ui.theme.SurfaceWhite
import id.identitylab.wallet.ui.theme.WarnBg
import id.identitylab.wallet.ui.theme.WarnBorder
import id.identitylab.wallet.ui.theme.WarnText

/**
 * Login / unlock screen ported from `app/auth/unlock.tsx`. Visual-only: PIN
 * and biometric handlers are stubbed for the UI port.
 */
@Composable
fun UnlockScreen(
    onUnlocked: () -> Unit,
) {
    var pin by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    var toastType by remember { mutableStateOf("info") }
    var toastVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Canvas)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            // Gradient header.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Blue500,
                                Blue700,
                                Orange500,
                            ),
                        ),
                        RoundedCornerShape(20.dp),
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .background(SurfaceWhite, RoundedCornerShape(44.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Lock",
                        tint = Blue500,
                        modifier = Modifier.size(40.dp),
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Unlock Wallet",
                    color = SurfaceWhite,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Black,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Masukkan PIN atau gunakan biometrik untuk membuka VC Wallet.",
                    color = LightBlue100,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 21.sp,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // PIN card.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceWhite, RoundedCornerShape(26.dp))
                    .border(1.dp, BorderSubtle, RoundedCornerShape(26.dp))
                    .padding(22.dp),
            ) {
                AppTextField(
                    value = pin,
                    onValueChange = { value ->
                        pin = value.filter { it.isDigit() }.take(6)
                    },
                    label = "PIN Wallet",
                    placeholder = "••••••",
                    keyboardType = KeyboardType.NumberPassword,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(18.dp))

                AppButton(
                    text = if (loading) "Membuka..." else "Unlock",
                    onClick = {
                        if (pin.trim().isEmpty()) {
                            toastMessage = "Masukkan PIN terlebih dahulu"
                            toastType = "error"
                            toastVisible = true
                        } else {
                            loading = true
                            onUnlocked()
                            loading = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Blue500,
                    startIcon = {
                        Icon(
                            imageVector = Icons.Filled.Key,
                            contentDescription = null,
                            tint = SurfaceWhite,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Biometric button.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LightBlue50, RoundedCornerShape(16.dp))
                        .clickable {
                            toastMessage = "Biometrik belum diaktifkan pada wallet ini"
                            toastType = "error"
                            toastVisible = true
                        }
                        .padding(vertical = 14.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Fingerprint,
                        contentDescription = null,
                        tint = Blue500,
                        modifier = Modifier.size(22.dp),
                    )
                    Text(
                        text = "Gunakan Biometrik",
                        color = Blue500,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Security note.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WarnBg, RoundedCornerShape(20.dp))
                    .border(1.dp, WarnBorder, RoundedCornerShape(20.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.Shield,
                    contentDescription = null,
                    tint = Orange500,
                    modifier = Modifier.size(22.dp),
                )
                Text(
                    text = "Wallet dilindungi dengan PIN lokal dan autentikasi biometrik.",
                    color = WarnText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }
        }

        AppToast(
            visible = toastVisible,
            message = toastMessage,
            type = toastType,
            onHide = { toastVisible = false },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
        )
    }
}
