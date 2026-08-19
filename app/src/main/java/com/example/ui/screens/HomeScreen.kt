package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.Announcement
import com.example.data.model.ScheduleItem
import com.example.ui.components.CamporiHeroHeader
import com.example.ui.theme.CamporiBlue
import com.example.ui.theme.CamporiNavy
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.PathfinderRed
import com.example.ui.theme.PathfinderYellow
import com.example.ui.theme.PathfinderYellowDark
import com.example.viewmodel.CamporiViewModel

@Composable
fun HomeScreen(
    viewModel: CamporiViewModel,
    onNavigateToTab: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val countdown by viewModel.countdown.collectAsStateWithLifecycle()
    val registrationCount by viewModel.registrationCount.collectAsStateWithLifecycle()
    val announcements by viewModel.announcements.collectAsStateWithLifecycle()
    val schedules by viewModel.schedules.collectAsStateWithLifecycle()

    val todayHighlights = schedules.filter { it.dayNumber == 1 }.take(3)
    val urgentAnnouncement = announcements.firstOrNull { it.priority == "Urgente" } ?: announcements.firstOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Hero Header with Campori UNA banner & countdown
        item {
            CamporiHeroHeader(
                countdown = countdown,
                onRegisterClick = { onNavigateToTab(1) } // Navigate to Portal de Inscrição
            )
        }

        // Stats Overview Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    icon = Icons.Default.HowToReg,
                    number = "$registrationCount",
                    label = "Inscritos",
                    tint = PathfinderYellowDark,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToTab(1) }
                )
                StatCard(
                    icon = Icons.Default.DateRange,
                    number = "7",
                    label = "Dias de Atividades",
                    tint = CamporiBlue,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToTab(2) }
                )
                StatCard(
                    icon = Icons.Default.People,
                    number = "15K+",
                    label = "Desbravadores",
                    tint = ForestGreen,
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
            }
        }

        // Urgent Alert Banner if present
        if (urgentAnnouncement != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable {
                            viewModel.selectAnnouncement(urgentAnnouncement)
                            onNavigateToTab(5) // Announcements tab (TAB_ANNOUNCEMENTS)
                        }
                        .testTag("urgent_announcement_banner"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PathfinderYellowDark)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = PathfinderRed,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.Campaign,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = PathfinderRed
                                ) {
                                    Text(
                                        text = urgentAnnouncement.priority.uppercase(),
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = urgentAnnouncement.dateLabel,
                                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                                )
                            }
                            Text(
                                text = urgentAnnouncement.title,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF331C00)
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = urgentAnnouncement.summary,
                                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF5D4037)),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = PathfinderYellowDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Quick Access Features Grid
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Text(
                    text = "Módulos do Campori",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = CamporiNavy
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickModuleCard(
                        title = "Inscrição & Crachá",
                        subtitle = "Credenciamento Oficial",
                        icon = Icons.Default.HowToReg,
                        containerColor = Color(0xFFFFF8E1),
                        accentColor = PathfinderYellowDark,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateToTab(1) }
                    )
                    QuickModuleCard(
                        title = "Cronograma",
                        subtitle = "7 Dias de Atividades",
                        icon = Icons.Default.DateRange,
                        containerColor = Color(0xFFE3F2FD),
                        accentColor = CamporiBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateToTab(2) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickModuleCard(
                        title = "Mapa Interativo",
                        subtitle = "Pungo a Ndongo",
                        icon = Icons.Default.Map,
                        containerColor = Color(0xFFE8F5E9),
                        accentColor = ForestGreen,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateToTab(3) }
                    )
                    QuickModuleCard(
                        title = "Bíblia Sagrada",
                        subtitle = "Offline & Ano Bíblico",
                        icon = Icons.Default.MenuBook,
                        containerColor = Color(0xFFFBE9E7),
                        accentColor = PathfinderRed,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateToTab(4) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickModuleCard(
                        title = "Fotos de Malanje",
                        subtitle = "Pedras Negras & Kalandula",
                        icon = Icons.Default.Collections,
                        containerColor = Color(0xFFEDE7F6),
                        accentColor = Color(0xFF512DA8),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateToTab(6) }
                    )
                    QuickModuleCard(
                        title = "Hino & Ideais",
                        subtitle = "Música & Votos UNA",
                        icon = Icons.Default.MusicNote,
                        containerColor = Color(0xFFFFF3E0),
                        accentColor = Color(0xFFE65100),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateToTab(7) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                QuickModuleCard(
                    title = "Área da Direção & Admin",
                    subtitle = "Validação de Inscrições & Comunicados na Nuvem",
                    icon = Icons.Default.HowToReg,
                    containerColor = Color(0xFFE0F2F1),
                    accentColor = Color(0xFF00796B),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onNavigateToTab(8) } // TAB_ADMIN
                )
            }
        }

        // Pungo Andongo Scenic Highlight Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onNavigateToTab(6) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CamporiNavy)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_pungo_andongo_1),
                        contentDescription = "Pungo Andongo",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = PathfinderYellow
                        ) {
                            Text(
                                text = "LOCAL DO EVENTO",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF331C00),
                                    fontSize = 9.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Pedras Negras de Pungo a Ndongo",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "Explore as fotos e a rica história da savana e cataratas de Malanje.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFFD3C4B4),
                                fontSize = 11.sp
                            ),
                            maxLines = 2
                        )
                    }
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = PathfinderYellow,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Day 1 Schedule Preview
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Destaques da Abertura (28 Dez)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = CamporiNavy
                        )
                    )
                    Text(
                        text = "Ver Todos",
                        modifier = Modifier
                            .clickable { onNavigateToTab(2) }
                            .padding(4.dp),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = CamporiBlue,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                todayHighlights.forEach { item ->
                    ScheduleMiniCard(
                        item = item,
                        onClick = { onNavigateToTab(2) }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
fun StatCard(
    icon: ImageVector,
    number: String,
    label: String,
    tint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .clickable(onClick = onClick)
            .height(82.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = number,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = CamporiNavy
                    )
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun QuickModuleCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    containerColor: Color,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .height(96.dp)
            .clickable(onClick = onClick)
            .testTag("quick_module_${title.replace(" ", "_").lowercase()}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = accentColor,
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = accentColor.copy(alpha = 0.6f), modifier = Modifier.size(16.dp))
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F1B16),
                        fontSize = 13.sp
                    ),
                    maxLines = 1
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(0xFF5D4037),
                        fontSize = 10.sp
                    ),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun ScheduleMiniCard(
    item: ScheduleItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = CamporiBlue.copy(alpha = 0.1f),
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(
                    text = item.timeLabel.split("-").firstOrNull()?.trim() ?: item.timeLabel,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Black,
                        color = CamporiBlue
                    )
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = CamporiNavy
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = item.location,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.Gray,
                            fontSize = 11.sp
                        ),
                        maxLines = 1
                    )
                }
            }
        }
    }
}
