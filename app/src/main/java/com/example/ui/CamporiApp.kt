package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.AnnouncementsScreen
import com.example.ui.screens.BibleScreen
import com.example.ui.screens.GalleryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.IdealsScreen
import com.example.ui.screens.MapScreen
import com.example.ui.screens.RegistrationScreen
import com.example.ui.screens.ScheduleScreen
import androidx.compose.material.icons.filled.AdminPanelSettings
import com.example.ui.theme.CamporiBlue
import com.example.ui.theme.CamporiNavy
import com.example.ui.theme.PathfinderRed
import com.example.ui.theme.PathfinderYellow
import com.example.ui.theme.PathfinderYellowDark
import com.example.viewmodel.CamporiViewModel

data class NavTabItem(
    val title: String,
    val icon: ImageVector,
    val testTag: String
)

const val TAB_HOME = 0
const val TAB_REGISTRATION = 1
const val TAB_SCHEDULE = 2
const val TAB_MAP = 3
const val TAB_BIBLE = 4
const val TAB_ANNOUNCEMENTS = 5
const val TAB_GALLERY = 6
const val TAB_IDEALS = 7
const val TAB_ADMIN = 8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CamporiApp(
    viewModel: CamporiViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(TAB_HOME) }
    val snackbarHostState = remember { SnackbarHostState() }
    val uiMessage by viewModel.uiMessage.collectAsStateWithLifecycle()
    val announcements by viewModel.announcements.collectAsStateWithLifecycle()
    val unreadAnnouncements = announcements.count { !it.isRead }

    var moreMenuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(uiMessage) {
        uiMessage?.let {
            snackbarHostState.showSnackbar(it.message)
            viewModel.clearUiMessage()
        }
    }

    val navTabs = listOf(
        NavTabItem("Início", Icons.Default.Home, "nav_home"),
        NavTabItem("Inscrição", Icons.Default.HowToReg, "nav_registration"),
        NavTabItem("Programa", Icons.Default.DateRange, "nav_schedule"),
        NavTabItem("Mapa", Icons.Default.Map, "nav_map"),
        NavTabItem("Bíblia", Icons.Default.MenuBook, "nav_bible")
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.img_app_icon),
                            contentDescription = "Emblema Campori",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "CAMPORI UNA",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = PathfinderYellow,
                                    letterSpacing = 1.sp
                                )
                            )
                            Text(
                                text = "Malanje 2026/2027",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFFD3C4B4),
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CamporiNavy,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    // Avisos Icon with Badge
                    IconButton(
                        onClick = { selectedTab = TAB_ANNOUNCEMENTS },
                        modifier = Modifier.testTag("topbar_announcements_button")
                    ) {
                        if (unreadAnnouncements > 0) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = PathfinderRed,
                                        contentColor = Color.White
                                    ) {
                                        Text("$unreadAnnouncements")
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Campaign, contentDescription = "Avisos")
                            }
                        } else {
                            Icon(Icons.Default.Campaign, contentDescription = "Avisos")
                        }
                    }

                    // More Menu (Fotos & Ideais)
                    Box {
                        IconButton(
                            onClick = { moreMenuExpanded = !moreMenuExpanded },
                            modifier = Modifier.testTag("topbar_more_menu_button")
                        ) {
                            Icon(Icons.Default.MoreHoriz, contentDescription = "Mais opções")
                        }
                        DropdownMenu(
                            expanded = moreMenuExpanded,
                            onDismissRequest = { moreMenuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Galeria de Fotos (Malanje)") },
                                leadingIcon = { Icon(Icons.Default.Collections, contentDescription = null, tint = CamporiBlue) },
                                onClick = {
                                    selectedTab = TAB_GALLERY
                                    moreMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Hino & Ideais dos Desbravadores") },
                                leadingIcon = { Icon(Icons.Default.MusicNote, contentDescription = null, tint = PathfinderYellowDark) },
                                onClick = {
                                    selectedTab = TAB_IDEALS
                                    moreMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Boletim Oficial & Avisos") },
                                leadingIcon = { Icon(Icons.Default.Campaign, contentDescription = null, tint = PathfinderRed) },
                                onClick = {
                                    selectedTab = TAB_ANNOUNCEMENTS
                                    moreMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("🔐 Painel Admin & Validador") },
                                leadingIcon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = CamporiNavy) },
                                onClick = {
                                    selectedTab = TAB_ADMIN
                                    moreMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = CamporiNavy,
                tonalElevation = 8.dp
            ) {
                navTabs.forEachIndexed { index, item ->
                    val isSelected = selectedTab == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(item.icon, contentDescription = item.title)
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF331C00),
                            selectedTextColor = CamporiNavy,
                            indicatorColor = PathfinderYellow,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        ),
                        modifier = Modifier.testTag(item.testTag)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { targetTab ->
                when (targetTab) {
                    TAB_HOME -> HomeScreen(viewModel = viewModel, onNavigateToTab = { selectedTab = it })
                    TAB_REGISTRATION -> RegistrationScreen(viewModel = viewModel)
                    TAB_SCHEDULE -> ScheduleScreen(viewModel = viewModel)
                    TAB_MAP -> MapScreen(viewModel = viewModel)
                    TAB_BIBLE -> BibleScreen(viewModel = viewModel)
                    TAB_ANNOUNCEMENTS -> AnnouncementsScreen(viewModel = viewModel)
                    TAB_GALLERY -> GalleryScreen(viewModel = viewModel)
                    TAB_IDEALS -> IdealsScreen(viewModel = viewModel)
                    TAB_ADMIN -> AdminScreen(viewModel = viewModel, onNavigateBack = { selectedTab = TAB_HOME })
                    else -> HomeScreen(viewModel = viewModel, onNavigateToTab = { selectedTab = it })
                }
            }
        }
    }
}
