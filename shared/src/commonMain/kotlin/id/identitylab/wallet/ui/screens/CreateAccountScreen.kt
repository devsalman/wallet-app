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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.identitylab.wallet.ui.components.AppButton
import id.identitylab.wallet.ui.components.AppTextField
import id.identitylab.wallet.ui.components.AppToast
import id.identitylab.wallet.ui.theme.Blue500
import id.identitylab.wallet.ui.theme.BorderSubtle
import id.identitylab.wallet.ui.theme.Canvas
import id.identitylab.wallet.ui.theme.LightBlue100
import id.identitylab.wallet.ui.theme.NufidTheme
import id.identitylab.wallet.ui.theme.Orange500
import id.identitylab.wallet.ui.theme.Placeholder
import id.identitylab.wallet.ui.theme.SurfaceWhite
import id.identitylab.wallet.ui.theme.TextPrimary
import id.identitylab.wallet.ui.theme.TextSecondary
import id.identitylab.wallet.ui.theme.WarnBg
import id.identitylab.wallet.ui.theme.WarnBorder
import id.identitylab.wallet.ui.theme.WarnText

/**
 * Create DID account screen ported from `app/auth/create-account.tsx`.
 * Visual-only: the wallet identity / recovery phrase creation is stubbed.
 */
@Composable
fun CreateAccountScreen(
    onCreated: () -> Unit,
) {
    var fullName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf<String?>(null) }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    var toastType by remember { mutableStateOf("info") }
    var toastVisible by remember { mutableStateOf(false) }
    var dateResult by remember { mutableStateOf("") }

    fun showToast(message: String, type: String = "error") {
        toastMessage = message
        toastType = type
        toastVisible = true
    }

    val setDate = { value: String ->
        birthDate = value
        dateResult = value
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Canvas)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .padding(top = 54.dp, bottom = 40.dp),
        ) {
            // Header.
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(LightBlue100, RoundedCornerShape(36.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.PersonAdd,
                        contentDescription = null,
                        tint = Blue500,
                        modifier = Modifier.size(34.dp),
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Buat Akun Wallet",
                    color = TextPrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Lengkapi data akun. DID dan recovery phrase akan otomatis dibuat saat akun berhasil disimpan.",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 21.sp,
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Form card.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceWhite, RoundedCornerShape(26.dp))
                    .border(1.dp, BorderSubtle, RoundedCornerShape(26.dp))
                    .padding(20.dp),
            ) {
                AppTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = "Nama Lengkap",
                    placeholder = "Masukkan nama lengkap",
                    keyboardType = KeyboardType.Text,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.PersonOutline,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Tanggal Lahir",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                // Date field (stub picker: toggles a preset display).
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Canvas, RoundedCornerShape(16.dp))
                        .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
                        .clickable {
                            setDate("01 Januari 2000")
                        }
                        .padding(horizontal = 14.dp)
                        .height(52.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = dateResult.ifEmpty { "Pilih tanggal lahir" },
                        color = if (dateResult.isEmpty()) {
                            Placeholder
                        } else {
                            TextPrimary
                        },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                AppTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    placeholder = "nama@email.com",
                    keyboardType = KeyboardType.Email,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.MailOutline,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                )

                Spacer(modifier = Modifier.height(14.dp))

                AppTextField(
                    value = phone,
                    onValueChange = { value ->
                        phone = value.filter { it.isDigit() }.take(15)
                    },
                    label = "Nomor HP",
                    placeholder = "08xxxxxxxxxx",
                    keyboardType = KeyboardType.Phone,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                )

                Spacer(modifier = Modifier.height(14.dp))

                AppTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = "Alamat",
                    placeholder = "Masukkan alamat lengkap",
                    keyboardType = KeyboardType.Text,
                    singleLine = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                AppButton(
                    text = if (loading) "Membuat Akun & Wallet..." else "Buat Akun",
                    onClick = {
                        when {
                            fullName.trim().isEmpty() ->
                                showToast("Nama lengkap wajib diisi.")
                            dateResult.isEmpty() ->
                                showToast("Tanggal lahir wajib diisi.")
                            email.trim().isEmpty() ->
                                showToast("Email wajib diisi.")
                            !isValidEmail(email.trim()) ->
                                showToast("Format email tidak valid.")
                            phone.trim().isEmpty() ->
                                showToast("Nomor HP wajib diisi.")
                            !isValidPhoneNumber(phone.trim()) ->
                                showToast("Nomor HP harus diawali 08 dan berisi 10 sampai 15 digit.")
                            address.trim().isEmpty() ->
                                showToast("Alamat wajib diisi.")
                            else -> {
                                loading = true
                                onCreated()
                                loading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Blue500,
                    startIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = SurfaceWhite,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Info note.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WarnBg, RoundedCornerShape(20.dp))
                    .border(1.dp, WarnBorder, RoundedCornerShape(20.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = Orange500,
                    modifier = Modifier.size(22.dp),
                )
                Text(
                    text = "DID dibuat dari recovery phrase 12 kata. Simpan phrase tersebut agar wallet identity dapat dipulihkan di masa depan.",
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

private fun isValidEmail(email: String): Boolean {
    return Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+\$").matches(email)
}

private fun isValidPhoneNumber(phone: String): Boolean {
    return Regex("^08[0-9]{8,13}\$").matches(phone)
}

@Composable
@Preview
fun CreateAccountScreenPreview() {
    NufidTheme {
        CreateAccountScreen(onCreated = {})
    }
}