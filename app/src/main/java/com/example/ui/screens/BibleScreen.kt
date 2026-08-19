package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.BibleBook
import com.example.data.model.BibleVerse
import com.example.ui.theme.CamporiBlue
import com.example.ui.theme.CamporiNavy
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.PathfinderRed
import com.example.ui.theme.PathfinderYellow
import com.example.ui.theme.PathfinderYellowDark
import com.example.viewmodel.CamporiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleScreen(
    viewModel: CamporiViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var activeTab by remember { mutableIntStateOf(0) } // 0: Leitura, 1: Favoritos, 2: Ano Bíblico

    val selectedBook by viewModel.selectedBibleBook.collectAsStateWithLifecycle()
    val selectedChapter by viewModel.selectedBibleChapter.collectAsStateWithLifecycle()
    val searchQuery by viewModel.bibleSearchQuery.collectAsStateWithLifecycle()
    val fontSize by viewModel.bibleFontSize.collectAsStateWithLifecycle()
    val verses by viewModel.currentVerses.collectAsStateWithLifecycle()
    val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()

    var showBookPicker by remember { mutableStateOf(false) }

    val currentBookObj = viewModel.bibleBooks.find { it.name.equals(selectedBook, ignoreCase = true) }
        ?: viewModel.bibleBooks.first()

    if (showBookPicker) {
        BibleBookPickerDialog(
            books = viewModel.bibleBooks,
            currentBook = selectedBook,
            onSelectBook = { book ->
                viewModel.selectBibleBook(book.name)
                showBookPicker = false
            },
            onDismiss = { showBookPicker = false }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
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
                    text = "BÍBLIA SAGRADA OFFLINE",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = PathfinderYellow,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "A Palavra de Deus no Campori",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // Tabs
        SecondaryTabRow(
            selectedTabIndex = activeTab,
            containerColor = Color.White,
            contentColor = CamporiNavy
        ) {
            Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                text = { Text("Texto Sagrado", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                text = { Text("Favoritos (${bookmarks.size})", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = activeTab == 2,
                onClick = { activeTab = 2 },
                text = { Text("Ano Bíblico", fontWeight = FontWeight.Bold) }
            )
        }

        when (activeTab) {
            0 -> {
                // Bible Reader View
                Column(modifier = Modifier.fillMaxSize()) {
                    // Navigation / Selector Bar
                    Surface(
                        color = Color(0xFFF5F2EB),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Book & Chapter Picker Trigger Button
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = CamporiNavy,
                                modifier = Modifier
                                    .clickable { showBookPicker = true }
                                    .testTag("bible_book_picker_button")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.MenuBook, contentDescription = null, tint = PathfinderYellow, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "$selectedBook $selectedChapter",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }

                            // Font controls
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { viewModel.decreaseBibleFontSize() }) {
                                    Text("A-", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                                }
                                Text("${fontSize}sp", style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
                                IconButton(onClick = { viewModel.increaseBibleFontSize() }) {
                                    Text("A+", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                        }
                    }

                    // Chapter horizontal pills
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items((1..currentBookObj.chaptersCount.coerceAtMost(50)).toList()) { chap ->
                            val isSelected = chap == selectedChapter
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) PathfinderYellowDark else Color.White,
                                border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0)) else null,
                                modifier = Modifier.clickable { viewModel.selectBibleChapter(chap) }
                            ) {
                                Text(
                                    text = "$chap",
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) Color.White else CamporiNavy
                                    )
                                )
                            }
                        }
                    }

                    // Search input inside reader
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { viewModel.setBibleSearchQuery(it) },
                            placeholder = { Text("Pesquisar versículos...") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { viewModel.setBibleSearchQuery("") }) {
                                        Icon(Icons.Default.Close, contentDescription = "Limpar")
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("search_bible_input"),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Verses List
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(verses) { verse ->
                            val isBookmarked = bookmarks.any {
                                it.book == verse.book && it.chapter == verse.chapter && it.verse == verse.verse
                            }
                            VerseItemCard(
                                verse = verse,
                                fontSize = fontSize,
                                isBookmarked = isBookmarked,
                                onToggleBookmark = {
                                    viewModel.toggleBookmark(verse.book, verse.chapter, verse.verse, verse.text)
                                },
                                onCopy = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Versículo Bíblico", "${verse.book} ${verse.chapter}:${verse.verse} - ${verse.text}")
                                    clipboard.setPrimaryClip(clip)
                                }
                            )
                        }
                    }
                }
            }
            1 -> {
                // Bookmarks List
                if (bookmarks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhum versículo salvo nos favoritos ainda.\nToque no ícone de marcador durante a leitura para salvar.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Gray,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(bookmarks, key = { it.id }) { bm ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "${bm.book} ${bm.chapter}:${bm.verse}",
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = CamporiBlue
                                            )
                                        )
                                        IconButton(
                                            onClick = { viewModel.toggleBookmark(bm.book, bm.chapter, bm.verse, bm.text) }
                                        ) {
                                            Icon(Icons.Default.Bookmark, contentDescription = "Remover", tint = PathfinderYellowDark)
                                        }
                                    }
                                    Text(
                                        text = bm.text,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color(0xFF2B2B2B),
                                            lineHeight = 22.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
            2 -> {
                // Ano Bíblico dos Desbravadores
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, PathfinderYellowDark),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "ANO BÍBLICO DOS DESBRAVADORES 2026/2027",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF331C00)
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "A leitura diária da Bíblia é o primeiro preceito da Lei do Desbravador: 'Observar a devoção matinal'.",
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF5D4037))
                                )
                            }
                        }
                    }

                    val readings = listOf(
                        Pair("Dezembro 28", "Salmos 23 & Gênesis 1"),
                        Pair("Dezembro 29", "Gênesis 2-3 & Mateus 1"),
                        Pair("Dezembro 30", "Gênesis 4-5 & Mateus 2"),
                        Pair("Dezembro 31", "Salmos 90 & Mateus 3"),
                        Pair("Janeiro 01", "Gênesis 6-8 & Salmos 1"),
                        Pair("Janeiro 02", "Gênesis 9-11 & Mateus 4"),
                        Pair("Janeiro 03", "Salmos 119:105-120 & Apocalipse 21")
                    )

                    items(readings) { item ->
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = item.first,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = PathfinderRed,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = item.second,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = CamporiNavy
                                        )
                                    )
                                }
                                Icon(Icons.Default.AutoStories, contentDescription = null, tint = CamporiBlue)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VerseItemCard(
    verse: BibleVerse,
    fontSize: Int,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onCopy: () -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = CamporiNavy.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = "${verse.book} ${verse.chapter}:${verse.verse}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CamporiNavy,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Row {
                    IconButton(onClick = onCopy, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copiar", tint = Color.Gray, modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = onToggleBookmark, modifier = Modifier.size(28.dp)) {
                        Icon(
                            if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Favorito",
                            tint = if (isBookmarked) PathfinderYellowDark else Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = verse.text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = fontSize.sp,
                    lineHeight = (fontSize * 1.5).sp,
                    color = Color(0xFF212121)
                )
            )
        }
    }
}

@Composable
fun BibleBookPickerDialog(
    books: List<BibleBook>,
    currentBook: String,
    onSelectBook: (BibleBook) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
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
                        text = "Livros da Bíblia",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = CamporiNavy
                        )
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Fechar")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(380.dp)
                ) {
                    items(books) { book ->
                        val isSelected = book.name.equals(currentBook, ignoreCase = true)
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) PathfinderYellowDark else Color(0xFFF5F5F5),
                            modifier = Modifier.clickable { onSelectBook(book) }
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = book.name,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else CamporiNavy
                                    )
                                )
                                Text(
                                    text = "${book.chaptersCount} capítulos",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        color = if (isSelected) Color(0xFFFFF9C4) else Color.Gray
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
