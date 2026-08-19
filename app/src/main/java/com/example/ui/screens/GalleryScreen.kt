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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.GalleryItem
import com.example.ui.theme.CamporiBlue
import com.example.ui.theme.CamporiNavy
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.PathfinderRed
import com.example.ui.theme.PathfinderYellow
import com.example.ui.theme.PathfinderYellowDark
import com.example.viewmodel.CamporiViewModel

@Composable
fun GalleryScreen(
    viewModel: CamporiViewModel,
    modifier: Modifier = Modifier
) {
    val photos = viewModel.galleryPhotos
    val selectedPhoto by viewModel.selectedGalleryPhoto.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.galleryCategory.collectAsStateWithLifecycle()

    val categories = listOf("Todas", "Pedras Negras", "Kalandula", "Acampamento")

    val filteredPhotos = if (selectedCategory == "Todas") {
        photos
    } else {
        photos.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    if (selectedPhoto != null) {
        PhotoDetailDialog(
            item = selectedPhoto!!,
            onDismiss = { viewModel.selectGalleryPhoto(null) }
        )
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
                        text = "GALERIA DE FOTOS DO LOCAL",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = PathfinderYellow,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "Pungo a Ndongo & Malanje",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Conheça a história e as belezas naturais onde ocorrerá o II Campori UNA.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFD3C4B4),
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory.equals(cat, ignoreCase = true)
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setGalleryCategory(cat) },
                        label = { Text(cat, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CamporiNavy,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Feature Large Hero Photo Card
        item {
            val heroItem = filteredPhotos.firstOrNull()
            if (heroItem != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clickable { viewModel.selectGalleryPhoto(heroItem) }
                        .testTag("gallery_hero_card"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = CamporiNavy)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = heroItem.drawableRes),
                            contentDescription = heroItem.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color(0x99000000),
                                            Color(0xEE0D2B45)
                                        )
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = PathfinderYellow
                            ) {
                                Text(
                                    text = heroItem.category.uppercase(),
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
                                text = heroItem.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = heroItem.location,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFFE0E0E0),
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // Photo Grid Cards
        items(filteredPhotos.drop(1), key = { it.id }) { item ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                GalleryCard(
                    item = item,
                    onClick = { viewModel.selectGalleryPhoto(item) }
                )
            }
        }

        // Historical Section regarding Malanje & Queen Nzinga Mbandi
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F3E9)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD7CCC8))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Landscape, contentDescription = null, tint = CamporiNavy)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "História de Pungo a Ndongo & Malanje",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = CamporiNavy
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "As Pedras Negras de Pungo a Ndongo são gigantescas formações rochosas milenares que se destacam na savana angolana. Segundo a tradição oral e a história de Angola, este local serviu de fortaleza e refúgio para a Rainha Nzinga Mbandi (Njinga) durante as lutas do Reino do Ndongo.\n\nPara o II Campori UNA, este cenário inspira a mensagem bíblica: assim como estas pedras são inabaláveis, a nossa fé está firmada na Rocha Eterna que é Jesus Cristo!",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF4E342E),
                            lineHeight = 20.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun GalleryCard(
    item: GalleryItem,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("gallery_card_${item.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = item.drawableRes),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = CamporiNavy
                        )
                    )
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = CamporiNavy.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = item.category,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = CamporiNavy,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = PathfinderRed,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF555555),
                        lineHeight = 18.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun PhotoDetailDialog(
    item: GalleryItem,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = item.drawableRes),
                        contentDescription = item.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(Color(0x99000000), CircleShape)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Fechar", tint = Color.White)
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = CamporiNavy
                        )
                    )
                    Text(
                        text = item.location,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PathfinderRed,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF333333),
                            lineHeight = 22.sp
                        )
                    )
                }
            }
        }
    }
}
