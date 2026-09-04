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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Wallet
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.identitylab.wallet.ui.components.AppToast

private const val SampleDid =
    "did:key:z6Mkk5vVc8nYq7F1gJ2mRwLmNpQWrTzXb3y4A6B9C0D"

private const val SampleDocuments = 2

/**
 * Dashboard harbour screen ported from `app/(tabs)/index.tsx`. Static sample
 * data stands in for the wallet storage layer.
 */
@Composable
fun DashboardScreen(
    onOpenCreateDID: () -> Unit,
) {
    var showDIDQR by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    var toastType by remember { mutableStateOf("info") }
    var toastVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .padding(top = 22.dp, bottom = 40.dp),
        ) {
            // Top header row.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(Color(0xFF2563EB), RoundedCornerShape(26.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "B",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Halo,",
                            fontSize = 13.sp,
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Budi Santoso",
                            fontSize = 18.sp,
                            color = Color(0xFF111827),
                            fontWeight = FontWeight.Black,
                        )
                    }
                }

                // Notification button with dot.
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, RoundedCornerShape(24.dp))
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notifications",
                        tint = Color(0xFF111827),
                        modifier = Modifier.size(24.dp),
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 11.dp, end = 12.dp)
                            .size(8.dp)
                            .background(Color(0xFFF97316), RoundedCornerShape(4.dp)),
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // DID card.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(26.dp))
                    .border(1.dp, Color(0xFFDBEAFE), RoundedCornerShape(26.dp))
                    .padding(20.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(Color(0xFFDBEAFE), RoundedCornerShape(26.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Fingerprint,
                            contentDescription = null,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(28.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Decentralized Identifier",
                            fontSize = 13.sp,
                            color = Color(0xFF6B7280),
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Active DID",
                            fontSize = 19.sp,
                            color = Color(0xFF111827),
                            fontWeight = FontWeight.Black,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFDCFCE7), RoundedCornerShape(999.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                    ) {
                        Text(
                            text = "ACTIVE",
                            color = Color(0xFF166534),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "DID Address",
                    fontSize = 13.sp,
                    color = Color(0xFF6B7280),
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = SampleDid,
                    fontSize = 13.sp,
                    color = Color(0xFF2563EB),
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 20.sp,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Copy DID button.
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color(0xFFEFF6FF), RoundedCornerShape(12.dp))
                            .clickable {
                                toastMessage = "DID Address berhasil disalin"
                                toastType = "success"
                                toastVisible = true
                            }
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = null,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(16.dp),
                        )
                        Text(
                            text = "Copy DID",
                            color = Color(0xFF2563EB),
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(start = 6.dp),
                        )
                    }

                    // Generate QR button.
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color(0xFFF97316), RoundedCornerShape(12.dp))
                            .clickable { showDIDQR = true }
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.QrCode,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp),
                        )
                        Text(
                            text = "Generate QR",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(start = 6.dp),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // My VC Documents section.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(24.dp))
                    .padding(18.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "My VC Documents",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF111827),
                    )
                    Text(
                        text = "View Wallet",
                        color = Color(0xFF2563EB),
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        modifier = Modifier.clickable { onOpenCreateDID() },
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (SampleDocuments == 0) {
                    EmptyCredentialCard()
                } else {
                    CredentialRow(
                        title = "KTP Digital",
                        issuer = "DINDUKCAPIL JAKARTA",
                        label = "VALID",
                        valid = true,
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Scan verification request banner.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2563EB), RoundedCornerShape(24.dp))
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .background(Color(0xFFF97316), RoundedCornerShape(27.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.QrCodeScanner,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp),
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Scan Verification Request",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                    )
                    Text(
                        text = "Pindai QR dari verifikator untuk merespons permintaan verifikasi.",
                        color = Color(0xFFDBEAFE),
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp),
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Security card.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF7ED), RoundedCornerShape(18.dp))
                    .border(1.dp, Color(0xFFFED7AA), RoundedCornerShape(18.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null,
                    tint = Color(0xFFF97316),
                    modifier = Modifier.size(22.dp),
                )
                Text(
                    text = "Wallet dilindungi dengan secure storage, PIN lokal, dan biometrik. Credential disimpan sebagai Verifiable Credential Data Model v2.0.",
                    color = Color(0xFF9A3412),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }
        }

        // DID QR modal.
        if (showDIDQR) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x8C0F172A))
                    .clickable { showDIDQR = false },
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(26.dp))
                        .clickable(enabled = false) { }
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color(0xFFEFF6FF), RoundedCornerShape(32.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.QrCode,
                            contentDescription = null,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(34.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "DID QR Code",
                        fontSize = 20.sp,
                        color = Color(0xFF111827),
                        fontWeight = FontWeight.Black,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "QR ini berisi DID Address wallet kamu.",
                        color = Color(0xFF64748B),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        lineHeight = 19.sp,
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    // QR placeholder.
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(18.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = SampleDid,
                            color = Color(0xFF2563EB),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF2563EB), RoundedCornerShape(14.dp))
                            .clickable { showDIDQR = false }
                            .padding(vertical = 13.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "Tutup",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                        )
                    }
                }
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

@Composable
private fun CredentialRow(
    title: String,
    issuer: String,
    label: String,
    valid: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(20.dp))
            .padding(18.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFFEFF6FF), RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFFDBEAFE), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "ID",
                        color = Color(0xFF2563EB),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF111827),
                        lineHeight = 21.sp,
                    )
                    Text(
                        text = issuer.uppercase(),
                        fontSize = 10.sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.3.sp,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(
                        if (valid) {
                            Color(0xFFDCFCE7)
                        } else {
                            Color(0xFFF8FAFC)
                        },
                        RoundedCornerShape(7.dp),
                    )
                    .border(
                        1.dp,
                        if (valid) {
                            Color(0xFF166534)
                        } else {
                            Color(0xFFCBD5E1)
                        },
                        RoundedCornerShape(7.dp),
                    )
                    .padding(horizontal = 8.dp, vertical = 5.dp),
            ) {
                Text(
                    text = label,
                    color = if (valid) {
                        Color(0xFF166534)
                    } else {
                        Color(0xFF64748B)
                    },
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFE5E7EB)),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Klik untuk melihat detail credential",
                color = Color(0xFF64748B),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

@Composable
private fun EmptyCredentialCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FAFC), RoundedCornerShape(18.dp))
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Filled.Wallet,
            contentDescription = null,
            tint = Color(0xFF9CA3AF),
            modifier = Modifier.size(34.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Belum Ada VC",
            fontSize = 18.sp,
            color = Color(0xFF111827),
            fontWeight = FontWeight.Black,
        )
        Text(
            text = "Buat credential dari halaman Wallet untuk menambahkan credential ke dashboard.",
            color = Color(0xFF6B7280),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}
