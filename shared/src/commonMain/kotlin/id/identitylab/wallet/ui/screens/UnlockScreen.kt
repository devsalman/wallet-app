package id.identitylab.wallet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.Fingerprint
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.identitylab.wallet.ui.components.AppToast
import id.identitylab.wallet.ui.theme.Blue500
import id.identitylab.wallet.ui.theme.BorderSubtle
import id.identitylab.wallet.ui.theme.Canvas
import id.identitylab.wallet.ui.theme.LightBlue100
import id.identitylab.wallet.ui.theme.LightBlue50
import id.identitylab.wallet.ui.theme.NufidTheme
import id.identitylab.wallet.ui.theme.SurfaceWhite
import id.identitylab.wallet.ui.theme.TextPrimary
import id.identitylab.wallet.ui.theme.TextSecondary

private const val PinLength = 6

/**
 * Banking-style unlock screen: a 6-digit PIN shown as dots, with a numeric
 * keypad (0-9), a biometric button, and backspace. Auto-submits when the PIN
 * reaches [PinLength] digits. Biometric flow is still stubbed for the UI port.
 */
@Composable
fun UnlockScreen(
    onUnlocked: () -> Unit,
) {
    var pin by remember { mutableStateOf("") }
    var toastMessage by remember { mutableStateOf("") }
    var toastType by remember { mutableStateOf("info") }
    var toastVisible by remember { mutableStateOf(false) }

    fun showToast(message: String, type: String = "error") {
        toastMessage = message
        toastType = type
        toastVisible = true
    }

    fun onDigit(digit: Int) {
        if (pin.length >= PinLength) return
        val next = pin + digit
        pin = next
        if (next.length == PinLength) {
            onUnlocked()
        }
    }

    fun onDelete() {
        if (pin.isNotEmpty()) {
            pin = pin.dropLast(1)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Canvas)
                .padding(horizontal = 28.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Logo mark.
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(LightBlue50, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Shield,
                    contentDescription = "NufID logo",
                    tint = Blue500,
                    modifier = Modifier.size(38.dp),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Masukkan PIN",
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Masukkan $PinLength digit PIN untuk membuka wallet",
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // PIN dots.
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(PinLength) { index ->
                    PINDot(filled = index < pin.length)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.weight(1f))

            // Numeric keypad.
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                KeypadRow {
                    listOf(1, 2, 3).forEach { digit ->
                        KeypadKey(
                            label = digit.toString(),
                            onClick = { onDigit(digit) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
                KeypadRow {
                    listOf(4, 5, 6).forEach { digit ->
                        KeypadKey(
                            label = digit.toString(),
                            onClick = { onDigit(digit) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
                KeypadRow {
                    listOf(7, 8, 9).forEach { digit ->
                        KeypadKey(
                            label = digit.toString(),
                            onClick = { onDigit(digit) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
                KeypadRow {
                    // Biometric button.
                    KeypadKey(
                        onClick = {
                            showToast("Biometrik belum diaktifkan pada wallet ini")
                        },
                        modifier = Modifier.weight(1f),
                        content = {
                            Icon(
                                imageVector = Icons.Filled.Fingerprint,
                                contentDescription = "Gunakan Biometrik",
                                tint = Blue500,
                                modifier = Modifier.size(30.dp),
                            )
                        },
                    )

                    // 0 digit.
                    KeypadKey(
                        label = "0",
                        onClick = { onDigit(0) },
                        modifier = Modifier.weight(1f),
                    )

                    // Backspace.
                    KeypadKey(
                        onClick = { onDelete() },
                        modifier = Modifier.weight(1f),
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Backspace,
                                contentDescription = "Hapus",
                                tint = TextPrimary,
                                modifier = Modifier.size(26.dp),
                            )
                        },
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
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

@Composable
private fun KeypadRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        content = content,
    )
}

@Composable
private fun RowScope.KeypadKey(
    modifier: Modifier = Modifier,
    label: String? = null,
    onClick: () -> Unit,
    content: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .height(72.dp)
            .background(SurfaceWhite, CircleShape)
            .border(1.dp, LightBlue100, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        if (content != null) {
            content()
        } else if (label != null) {
            Text(
                text = label,
                color = TextPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun PINDot(filled: Boolean) {
    Box(
        modifier = Modifier
            .size(18.dp)
            .background(
                if (filled) {
                    Blue500
                } else {
                    SurfaceWhite
                },
                CircleShape,
            )
            .border(2.dp, if (filled) Blue500 else BorderSubtle, CircleShape),
    )
}

@Preview
@Composable
fun UnlockScreenPreview() {
    NufidTheme {
        UnlockScreen( onUnlocked = {} )
    }
}
