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
                .background(Color(0xFFF8FAFC))
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
                                Color(0xFF2563EB),
                                Color(0xFF1D4ED8),
                                Color(0xFFF97316),
                            ),
                        ),
                        RoundedCornerShape(30.dp),
                    )
                    .padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .background(Color.White, RoundedCornerShape(44.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Lock",
                        tint = Color(0xFF2563EB),
                        modifier = Modifier.size(40.dp),
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Unlock Wallet",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Black,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Masukkan PIN atau gunakan biometrik untuk membuka VC Wallet.",
                    color = Color(0xFFDBEAFE),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 21.sp,
                )
            }

            // PIN card.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(26.dp))
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(26.dp))
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
                    backgroundColor = Color(0xFF2563EB),
                    startIcon = {
                        Icon(
                            imageVector = Icons.Filled.Key,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Biometric button.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEFF6FF), RoundedCornerShape(16.dp))
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
                        tint = Color(0xFF2563EB),
                        modifier = Modifier.size(22.dp),
                    )
                    Text(
                        text = "Gunakan Biometrik",
                        color = Color(0xFF2563EB),
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }

            // Security note.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF7ED), RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFFFED7AA), RoundedCornerShape(20.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.Shield,
                    contentDescription = null,
                    tint = Color(0xFFF97316),
                    modifier = Modifier.size(22.dp),
                )
                Text(
                    text = "Wallet dilindungi dengan PIN lokal dan autentikasi biometrik.",
                    color = Color(0xFF9A3412),
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
