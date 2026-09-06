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
import id.identitylab.wallet.ui.theme.Orange500
import id.identitylab.wallet.ui.theme.Overlay
import id.identitylab.wallet.ui.theme.Placeholder
import id.identitylab.wallet.ui.theme.SkeletonBase
import id.identitylab.wallet.ui.theme.Slate
import id.identitylab.wallet.ui.theme.SuccessBg
import id.identitylab.wallet.ui.theme.SuccessText
import id.identitylab.wallet.ui.theme.SurfaceWhite
import id.identitylab.wallet.ui.theme.TextMuted
import id.identitylab.wallet.ui.theme.TextPrimary
import id.identitylab.wallet.ui.theme.TextSecondary
import id.identitylab.wallet.ui.theme.WarnBg
import id.identitylab.wallet.ui.theme.WarnBorder
import id.identitylab.wallet.ui.theme.WarnText

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
                .background(Canvas)
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
                            .background(Blue500, RoundedCornerShape(26.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "B",
                            color = SurfaceWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Halo,",
                            fontSize = 13.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Budi Santoso",
                            fontSize = 18.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Black,
                        )
                    }
                }

                // Notification button with dot.
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(SurfaceWhite, RoundedCornerShape(24.dp))
                        .border(1.dp, BorderSubtle, RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notifications",
                        tint = TextPrimary,
                        modifier = Modifier.size(24.dp),
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 11.dp, end = 12.dp)
                            .size(8.dp)
                            .background(Orange500, RoundedCornerShape(4.dp)),
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // DID card.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceWhite, RoundedCornerShape(26.dp))
                    .border(1.dp, LightBlue100, RoundedCornerShape(26.dp))
                    .padding(20.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(LightBlue100, RoundedCornerShape(26.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Fingerprint,
                            contentDescription = null,
                            tint = Blue500,
                            modifier = Modifier.size(28.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Decentralized Identifier",
                            fontSize = 13.sp,
                            color = TextMuted,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Active DID",
                            fontSize = 19.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Black,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(SuccessBg, RoundedCornerShape(999.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                    ) {
                        Text(
                            text = "ACTIVE",
                            color = SuccessText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "DID Address",
                    fontSize = 13.sp,
                    color = TextMuted,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = SampleDid,
                    fontSize = 13.sp,
                    color = Blue500,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 20.sp,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Copy DID button.
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .background(LightBlue50, RoundedCornerShape(12.dp))
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
                            tint = Blue500,
                            modifier = Modifier.size(16.dp),
                        )
                        Text(
                            text = "Copy DID",
                            color = Blue500,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(start = 6.dp),
                        )
                    }

                    // Generate QR button.
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .background(Orange500, RoundedCornerShape(12.dp))
                            .clickable { showDIDQR = true }
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.QrCode,
                            contentDescription = null,
                            tint = SurfaceWhite,
                            modifier = Modifier.size(16.dp),
                        )
                        Text(
                            text = "Generate QR",
                            color = SurfaceWhite,
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
                    .background(SurfaceWhite, RoundedCornerShape(24.dp))
                    .border(1.dp, BorderSubtle, RoundedCornerShape(24.dp))
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
                        color = TextPrimary,
                    )
                    Text(
                        text = "View Wallet",
                        color = Blue500,
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
                    .background(Blue500, RoundedCornerShape(24.dp))
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .background(Orange500, RoundedCornerShape(27.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.QrCodeScanner,
                        contentDescription = null,
                        tint = SurfaceWhite,
                        modifier = Modifier.size(26.dp),
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Scan Verification Request",
                        color = SurfaceWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                    )
                    Text(
                        text = "Pindai QR dari verifikator untuk merespons permintaan verifikasi.",
                        color = LightBlue100,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = SurfaceWhite,
                    modifier = Modifier.size(22.dp),
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Security card.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WarnBg, RoundedCornerShape(18.dp))
                    .border(1.dp, WarnBorder, RoundedCornerShape(18.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null,
                    tint = Orange500,
                    modifier = Modifier.size(22.dp),
                )
                Text(
                    text = "Wallet dilindungi dengan secure storage, PIN lokal, dan biometrik. Credential disimpan sebagai Verifiable Credential Data Model v2.0.",
                    color = WarnText,
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
                    .background(Overlay)
                    .clickable { showDIDQR = false },
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .background(SurfaceWhite, RoundedCornerShape(26.dp))
                        .clickable(enabled = false) { }
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(LightBlue50, RoundedCornerShape(32.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.QrCode,
                            contentDescription = null,
                            tint = Blue500,
                            modifier = Modifier.size(34.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "DID QR Code",
                        fontSize = 20.sp,
                        color = TextPrimary,
                        fontWeight = FontWeight.Black,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "QR ini berisi DID Address wallet kamu.",
                        color = TextSecondary,
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
                            .background(Canvas, RoundedCornerShape(18.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = SampleDid,
                            color = Blue500,
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
                            .background(Blue500, RoundedCornerShape(14.dp))
                            .clickable { showDIDQR = false }
                            .padding(vertical = 13.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "Tutup",
                            color = SurfaceWhite,
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
            .background(SurfaceWhite, RoundedCornerShape(20.dp))
            .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
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
                        .background(LightBlue50, RoundedCornerShape(16.dp))
                        .border(1.dp, LightBlue100, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "ID",
                        color = Blue500,
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
                        color = TextPrimary,
                        lineHeight = 21.sp,
                    )
                    Text(
                        text = issuer.uppercase(),
                        fontSize = 10.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.3.sp,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(
                        if (valid) {
                            SuccessBg
                        } else {
                            Canvas
                        },
                        RoundedCornerShape(7.dp),
                    )
                    .border(
                        1.dp,
                        if (valid) {
                            SuccessText
                        } else {
                            SkeletonBase
                        },
                        RoundedCornerShape(7.dp),
                    )
                    .padding(horizontal = 8.dp, vertical = 5.dp),
            ) {
                Text(
                    text = label,
                    color = if (valid) {
                        SuccessText
                    } else {
                        TextSecondary
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
                .background(BorderSubtle),
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
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = Slate,
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
            .background(Canvas, RoundedCornerShape(18.dp))
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Filled.Wallet,
            contentDescription = null,
            tint = Placeholder,
            modifier = Modifier.size(34.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Belum Ada VC",
            fontSize = 18.sp,
            color = TextPrimary,
            fontWeight = FontWeight.Black,
        )
        Text(
            text = "Buat credential dari halaman Wallet untuk menambahkan credential ke dashboard.",
            color = TextMuted,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    NufidTheme {
        DashboardScreen( onOpenCreateDID = {})
    }
}
