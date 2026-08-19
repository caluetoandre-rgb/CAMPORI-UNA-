package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.PathfinderIdeal
import com.example.ui.theme.CamporiBlue
import com.example.ui.theme.CamporiNavy
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.PathfinderRed
import com.example.ui.theme.PathfinderYellow
import com.example.ui.theme.PathfinderYellowDark
import com.example.viewmodel.CamporiViewModel

@Composable
fun IdealsScreen(
    viewModel: CamporiViewModel,
    modifier: Modifier = Modifier
) {
    val ideals = viewModel.ideals
    val hymnLyrics = viewModel.hymnLyrics
    val isPlayingHymn by viewModel.isPlayingHymn.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Header
        item {
            Surface(
                color = CamporiNavy,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Text(
                        text = "IDEAIS & HINO OFICIAL",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = PathfinderYellow,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "Valores & Tradições dos Desbravadores",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        // Hino dos Desbravadores Player Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("hymn_player_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CamporiNavy)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = PathfinderYellow,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "HINO DOS DESBRAVADORES",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF331C00)
                            )
                        )
                    }

                    Text(
                        text = "Letra & Melodia Oficial",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = hymnLyrics,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFFFFF9C4),
                            lineHeight = 22.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.togglePlayHymn() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isPlayingHymn) PathfinderRed else PathfinderYellow,
                            contentColor = if (isPlayingHymn) Color.White else Color(0xFF331C00)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("play_hymn_button")
                    ) {
                        Icon(
                            if (isPlayingHymn) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isPlayingHymn) "Pausar Melodia" else "Tocar Melodia do Hino",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }

        // Section Title: Ideais
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                Text(
                    text = "Ideais dos Desbravadores",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = CamporiNavy
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
        }

        // Ideals List
        items(ideals) { ideal ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                IdealCard(ideal = ideal)
            }
        }

        // Structure of UNA Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F8)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFB0BEC5))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Flag, contentDescription = null, tint = CamporiBlue)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "União Nordeste de Angola (UNA)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = CamporiNavy
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "A União Nordeste de Angola é uma das organizações regionais da Igreja Adventista do Sétimo Dia em Angola, congregando milhares de clubes de desbravadores e aventureiros através de suas missões e campos:\n\n• Missão Nordeste (Sede do Campori - Malanje)\n• Missão Norte de Angola\n• Missão Centro de Angola\n• Missão Sul de Angola\n• Missão Leste de Angola\n\nTodos unidos sob o lema: 'Mais que um Lenço, Uma Missão!'",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF37474F),
                            lineHeight = 20.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun IdealCard(ideal: PathfinderIdeal) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
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
                    text = ideal.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = CamporiNavy
                    )
                )
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = PathfinderYellow.copy(alpha = 0.3f)
                ) {
                    Text(
                        text = ideal.subtitle,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF5D4037),
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFF9F9F9),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = ideal.content,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF1B3B6F),
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp
                    ),
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = ideal.meaning,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF616161),
                    lineHeight = 18.sp
                )
            )
        }
    }
}
