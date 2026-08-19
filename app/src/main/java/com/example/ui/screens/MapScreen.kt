package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.CamporiMapPoint
import com.example.ui.components.InteractiveCampMapCanvas
import com.example.ui.theme.CamporiBlue
import com.example.ui.theme.CamporiNavy
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.PathfinderRed
import com.example.ui.theme.PathfinderYellow
import com.example.ui.theme.PathfinderYellowDark
import com.example.viewmodel.CamporiViewModel

@Composable
fun MapScreen(
    viewModel: CamporiViewModel,
    modifier: Modifier = Modifier
) {
    val selectedPoint by viewModel.selectedMapPoint.collectAsStateWithLifecycle()
    val selectedZone by viewModel.selectedMapZone.collectAsStateWithLifecycle()
    val allPoints = viewModel.mapPoints

    val zones = listOf("Todas", "Arena", "Subcampo", "Serviços", "Pioneiria", "Natureza")

    val filteredPoints = if (selectedZone == "Todas") {
        allPoints
    } else {
        allPoints.filter { it.zone.equals(selectedZone, ignoreCase = true) }
    }

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
                        text = "MAPA INTERATIVO DO CAMPORI",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = PathfinderYellow,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "Pungo a Ndongo, Província de Malanje",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Toque nos marcadores para explorar os subcampos, arena e serviços.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFD3C4B4),
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }

        // Zone Filters
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(zones) { zone ->
                    val isSelected = selectedZone.equals(zone, ignoreCase = true)
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setMapZone(zone) },
                        label = { Text(zone, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CamporiNavy,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Interactive Canvas Map
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                InteractiveCampMapCanvas(
                    points = filteredPoints,
                    selectedPoint = selectedPoint,
                    onPointSelect = { viewModel.selectMapPoint(it) }
                )
            }
        }

        // Selected Point Detail Banner
        if (selectedPoint != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("selected_map_point_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(2.dp, PathfinderYellowDark)
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
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = CamporiNavy
                            ) {
                                Text(
                                    text = selectedPoint!!.zone.uppercase(),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = PathfinderYellow,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            IconButton(onClick = { viewModel.selectMapPoint(null) }) {
                                Icon(Icons.Default.Close, contentDescription = "Fechar", tint = Color.Gray)
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = selectedPoint!!.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = CamporiNavy
                            )
                        )
                        Text(
                            text = selectedPoint!!.subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PathfinderRed,
                                fontWeight = FontWeight.Medium
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = selectedPoint!!.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF444444),
                                lineHeight = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Horário: ${selectedPoint!!.openingHours}",
                                style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                            )
                            Text(
                                text = "Responsável: ${selectedPoint!!.coordinator}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = CamporiBlue,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }

        // List of all Points in Zone
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Pontos & Instalações (${filteredPoints.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = CamporiNavy
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        items(filteredPoints, key = { it.id }) { point ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                MapPointListItem(
                    point = point,
                    isSelected = selectedPoint?.id == point.id,
                    onClick = { viewModel.selectMapPoint(point) }
                )
            }
        }
    }
}

@Composable
fun MapPointListItem(
    point: CamporiMapPoint,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("map_item_${point.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isSelected) Color(0xFFFFF9C4) else Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = when (point.zone) {
                    "Arena" -> PathfinderYellowDark
                    "Subcampo" -> CamporiBlue
                    "Serviços" -> PathfinderRed
                    "Pioneiria" -> ForestGreen
                    else -> CamporiNavy
                },
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = when (point.iconType) {
                            "stage" -> Icons.Default.Star
                            "tent" -> Icons.Default.Place
                            "medical" -> Icons.Default.LocalHospital
                            "food" -> Icons.Default.Restaurant
                            "water" -> Icons.Default.WaterDrop
                            else -> Icons.Default.Place
                        },
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = point.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = CamporiNavy
                    )
                )
                Text(
                    text = point.subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray),
                    maxLines = 1
                )
            }

            Icon(
                Icons.Default.Navigation,
                contentDescription = null,
                tint = if (isSelected) PathfinderYellowDark else Color.LightGray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
