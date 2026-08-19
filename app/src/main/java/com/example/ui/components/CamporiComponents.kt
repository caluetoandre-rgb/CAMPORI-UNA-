package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.model.CamporiMapPoint
import com.example.data.model.Registration
import com.example.ui.theme.CamporiBlue
import com.example.ui.theme.CamporiNavy
import com.example.ui.theme.CamporiNavyLight
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.MalanjeSand
import com.example.ui.theme.PathfinderRed
import com.example.ui.theme.PathfinderYellow
import com.example.ui.theme.PathfinderYellowDark
import com.example.viewmodel.CountdownState

@Composable
fun CamporiHeroHeader(
    countdown: CountdownState,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CamporiNavy)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.img_campori_banner),
                contentDescription = "Banner do Campori UNA",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x770D2B45),
                                Color(0xDD0D2B45),
                                Color(0xFF0D2B45)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Badge Pill
                Surface(
                    shape = RoundedCornerShape(50),
                    color = PathfinderYellow,
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Text(
                        text = "II CAMPORI DOS DESBRAVADORES UNA",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF331C00),
                            letterSpacing = 1.sp
                        )
                    )
                }

                Text(
                    text = "MAIS QUE UM LENÇO,\nUMA MISSÃO",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 26.sp
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = PathfinderYellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Pungo a Ndongo, Malanje | 28 Dez 2026 - 03 Jan 2027",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFE0E0E0),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Countdown Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CountdownBox(value = countdown.days.toString().padStart(2, '0'), label = "DIAS")
                    CountdownBox(value = countdown.hours.toString().padStart(2, '0'), label = "HORAS")
                    CountdownBox(value = countdown.minutes.toString().padStart(2, '0'), label = "MIN")
                    CountdownBox(value = countdown.seconds.toString().padStart(2, '0'), label = "SEG")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onRegisterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("hero_register_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PathfinderYellow,
                        contentColor = Color(0xFF331C00)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "PORTAL DE INSCRIÇÃO & CRACHÁ",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@Composable
fun CountdownBox(value: String, label: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0x44000000),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x66FFB300)),
        modifier = Modifier.width(68.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Black,
                    color = PathfinderYellow
                )
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun DigitalBadgeCard(
    registration: Registration,
    onClose: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onClose) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
                .shadow(16.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header of Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "UNIÃO NORDESTE DE ANGOLA",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = CamporiNavy
                            )
                        )
                        Text(
                            text = "II CAMPORI DE DESBRAVADORES",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Black,
                                color = PathfinderRed
                            )
                        )
                    }
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Fechar", tint = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Official Card Container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    CamporiNavy,
                                    CamporiBlue,
                                    Color(0xFF0F3A60)
                                )
                            )
                        )
                        .border(2.dp, PathfinderYellow, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Top Emblem Pill
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = PathfinderYellow,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text(
                                text = "CRACHÁ OFICIAL DE PARTICIPANTE",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF331C00),
                                    fontSize = 10.sp
                                )
                            )
                        }

                        // Avatar / Emblem
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(3.dp, PathfinderYellow, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.foundation.Image(
                                painter = painterResource(id = R.drawable.img_app_icon),
                                contentDescription = "Emblema Campori",
                                modifier = Modifier.size(54.dp),
                                contentScale = ContentScale.Fit
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Participant Name
                        Text(
                            text = registration.fullName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        // Role & Blood Type Pill
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = PathfinderRed
                            ) {
                                Text(
                                    text = registration.role.uppercase(),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0x33FFFFFF)
                            ) {
                                Text(
                                    text = "Sangue: ${registration.bloodType}",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = PathfinderYellow,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Club & Mission Details Grid
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x33FFFFFF))
                                .padding(8.dp)
                        ) {
                            BadgeDetailRow(label = "Clube:", value = registration.clubName)
                            BadgeDetailRow(label = "Igreja:", value = registration.churchName)
                            BadgeDetailRow(label = "Missão:", value = registration.mission)
                            BadgeDetailRow(label = "Região:", value = registration.region)
                            BadgeDetailRow(label = "Telefone:", value = registration.phone)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Custom Matrix QR Code Drawing
                        QrCodeSimulation(
                            code = registration.registrationCode,
                            modifier = Modifier.size(110.dp)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "CÓDIGO: ${registration.registrationCode}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PathfinderYellow,
                                letterSpacing = 2.sp
                            )
                        )

                        Text(
                            text = "PUNGO A NDONGO | MALANJE 2026/2027",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xAAFFFFFF),
                                fontSize = 9.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onClose,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Fechar")
                    }
                    Button(
                        onClick = onShare,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("share_badge_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CamporiNavy,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Compartilhar")
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color(0xBBFFFFFF),
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun QrCodeSimulation(code: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = Color.White
    ) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)) {
            val size = size.width
            val gridSize = 11
            val blockSize = size / gridSize

            // Draw pseudo-random reproducible QR pattern based on hash
            val hash = code.hashCode()

            for (r in 0 until gridSize) {
                for (c in 0 until gridSize) {
                    // QR Corners (Position markers)
                    val isCorner = (r < 3 && c < 3) || (r < 3 && c >= gridSize - 3) || (r >= gridSize - 3 && c < 3)
                    val isCenterPoint = (r in 1..1 && c in 1..1) ||
                            (r in 1..1 && c in (gridSize - 2)..(gridSize - 2)) ||
                            (r in (gridSize - 2)..(gridSize - 2) && c in 1..1)

                    val fill = if (isCorner) {
                        if (r == 0 || r == 2 || c == 0 || c == 2 ||
                            r == gridSize - 1 || r == gridSize - 3 || c == gridSize - 1 || c == gridSize - 3 ||
                            isCenterPoint
                        ) {
                            true
                        } else {
                            false
                        }
                    } else {
                        // Deterministic pseudo bits
                        ((hash xor (r * 31 + c * 17)) and (1 shl ((r + c) % 16))) != 0
                    }

                    if (fill) {
                        drawRect(
                            color = Color(0xFF0D2B45),
                            topLeft = Offset(c * blockSize, r * blockSize),
                            size = Size(blockSize - 0.5f, blockSize - 0.5f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InteractiveCampMapCanvas(
    points: List<CamporiMapPoint>,
    selectedPoint: CamporiMapPoint?,
    onPointSelect: (CamporiMapPoint) -> Unit,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(340.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE8ECE2))
            .border(1.dp, Color(0xFFCFD8DC), RoundedCornerShape(16.dp))
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(0.8f, 3.5f)
                    val maxPan = 200f * scale
                    offset = Offset(
                        x = (offset.x + pan.x).coerceIn(-maxPan, maxPan),
                        y = (offset.y + pan.y).coerceIn(-maxPan, maxPan)
                    )
                }
            }
    ) {
        // Map Canvas Drawing
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Background terrain / Savana of Malanje
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFF1EAD9), Color(0xFFE2DCB9), Color(0xFFD3C8A0)),
                    center = Offset(w * 0.5f, h * 0.5f),
                    radius = w * 0.8f
                )
            )

            // Draw River / Rio Lucala tributary curve
            val riverPath = Path().apply {
                moveTo(0f, h * 0.7f)
                cubicTo(w * 0.3f, h * 0.65f, w * 0.6f, h * 0.85f, w, h * 0.75f)
            }
            drawPath(
                path = riverPath,
                color = Color(0xFF81D4FA).copy(alpha = 0.8f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 18f * scale)
            )

            // Draw Pungo a Ndongo Black Rocks Monoliths Graphic Silhouettes
            drawCircle(
                color = Color(0xFF37474F),
                radius = 45f * scale,
                center = Offset(w * 0.80f + offset.x, h * 0.85f + offset.y)
            )
            drawCircle(
                color = Color(0xFF263238),
                radius = 35f * scale,
                center = Offset(w * 0.88f + offset.x, h * 0.80f + offset.y)
            )
            drawCircle(
                color = Color(0xFF455A64),
                radius = 28f * scale,
                center = Offset(w * 0.72f + offset.x, h * 0.90f + offset.y)
            )

            // Draw Campori Central Arena Oval
            drawOval(
                color = Color(0xFFFFD54F).copy(alpha = 0.6f),
                topLeft = Offset(w * 0.35f + offset.x, h * 0.28f + offset.y),
                size = Size(w * 0.30f * scale, h * 0.24f * scale)
            )

            // Draw Subcampo Zones
            drawRoundRect(
                color = Color(0xFF81C784).copy(alpha = 0.4f),
                topLeft = Offset(w * 0.15f + offset.x, h * 0.15f + offset.y),
                size = Size(w * 0.22f * scale, h * 0.20f * scale),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
            )
            drawRoundRect(
                color = Color(0xFFFFB74D).copy(alpha = 0.4f),
                topLeft = Offset(w * 0.65f + offset.x, h * 0.15f + offset.y),
                size = Size(w * 0.22f * scale, h * 0.20f * scale),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
            )

            // Draw Pathways
            val mainRoad = Path().apply {
                moveTo(w * 0.15f + offset.x, h * 0.85f + offset.y)
                lineTo(w * 0.50f + offset.x, h * 0.40f + offset.y)
                lineTo(w * 0.78f + offset.x, h * 0.85f + offset.y)
            }
            drawPath(
                path = mainRoad,
                color = Color(0xFFBCAAA4),
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 6f * scale,
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
                )
            )
        }

        // Overlay Interactive Pins
        points.forEach { point ->
            val isSelected = selectedPoint?.id == point.id
            val pinColor = when (point.zone) {
                "Arena" -> PathfinderYellowDark
                "Subcampo" -> CamporiBlue
                "Serviços" -> PathfinderRed
                "Pioneiria" -> ForestGreen
                else -> Color(0xFF455A64)
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = (point.xPercent * 280).dp,
                        top = (point.yPercent * 260).dp
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { onPointSelect(point) }
                        .testTag("map_pin_${point.id}")
                ) {
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 36.dp else 28.dp)
                            .shadow(6.dp, CircleShape)
                            .clip(CircleShape)
                            .background(pinColor)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (point.iconType) {
                                "stage" -> Icons.Default.Star
                                "tent" -> Icons.Default.Place
                                "medical" -> Icons.Default.LocalHospital
                                "food" -> Icons.Default.Restaurant
                                "water" -> Icons.Default.WaterDrop
                                "flag" -> Icons.Default.LocationOn
                                else -> Icons.Default.Place
                            },
                            contentDescription = point.title,
                            tint = Color.White,
                            modifier = Modifier.size(if (isSelected) 20.dp else 16.dp)
                        )
                    }

                    if (isSelected) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = CamporiNavy,
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Text(
                                text = point.title,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Map Control Buttons (Zoom In, Out, Reset)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .size(36.dp)
                    .clickable { scale = (scale * 1.25f).coerceAtMost(3.5f) }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.ZoomIn, contentDescription = "Zoom In", tint = CamporiNavy)
                }
            }
            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .size(36.dp)
                    .clickable { scale = (scale / 1.25f).coerceAtLeast(0.8f) }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.ZoomOut, contentDescription = "Zoom Out", tint = CamporiNavy)
                }
            }
        }

        // Map Title Tag
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xDD0D2B45),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Place,
                    contentDescription = null,
                    tint = PathfinderYellow,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Mapa do Campo: Pungo a Ndongo",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
