package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Announcement
import com.example.data.model.Registration
import com.example.ui.components.CameraQrScanner
import com.example.ui.theme.CamporiBlue
import com.example.ui.theme.CamporiNavy
import com.example.ui.theme.CamporiSky
import com.example.ui.theme.PathfinderGreen
import com.example.ui.theme.PathfinderRed
import com.example.ui.theme.PathfinderYellow
import com.example.ui.theme.PathfinderYellowDark
import com.example.viewmodel.CamporiViewModel

@Composable
fun AdminScreen(
    viewModel: CamporiViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isAdminAuth by viewModel.isAdminAuthenticated.collectAsStateWithLifecycle()

    if (!isAdminAuth) {
        AdminLoginView(
            viewModel = viewModel,
            onCancel = onNavigateBack,
            modifier = modifier
        )
    } else {
        AdminDashboardView(
            viewModel = viewModel,
            onLogout = { viewModel.logoutAdmin() },
            modifier = modifier
        )
    }
}

@Composable
private fun AdminLoginView(
    viewModel: CamporiViewModel,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var loginMode by remember { mutableIntStateOf(0) } // 0: Firebase Auth (E-mail/Senha Inviolável), 1: PIN Direção
    var email by remember { mutableStateOf("secretaria@camporiuna.org") }
    var password by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(CamporiNavy),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin",
                        tint = PathfinderYellow,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "CAMPORI UNA ADMIN",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        color = CamporiNavy
                    )
                )

                Text(
                    text = "Portal da Secretaria Executiva & Direção Geral",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Selector: Firebase Auth vs PIN
                TabRow(
                    selectedTabIndex = loginMode,
                    containerColor = Color(0xFFECEFF1),
                    contentColor = CamporiNavy,
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                ) {
                    Tab(
                        selected = loginMode == 0,
                        onClick = {
                            loginMode = 0
                            errorMessage = null
                        },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CloudQueue, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Firebase Auth", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    )
                    Tab(
                        selected = loginMode == 1,
                        onClick = {
                            loginMode = 1
                            errorMessage = null
                        },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Key, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("PIN Direção", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (loginMode == 0) {
                    // Firebase Auth Mode (100% Inviolável no Servidor)
                    Text(
                        text = "Autenticação oficial criptografada via Firebase Auth para aprovação de inscritos e envio de comunicados:",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF555555)),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            errorMessage = null
                        },
                        label = { Text("E-mail Oficial da Liderança") },
                        placeholder = { Text("ex: secretaria@camporiuna.org") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = CamporiNavy)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth().testTag("admin_email_input"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = null
                        },
                        label = { Text("Senha da Liderança") },
                        placeholder = { Text("Digite sua senha segura") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = CamporiNavy)
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Alternar visibilidade"
                                )
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth().testTag("admin_password_input"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "💡 Contas autorizadas: secretaria@camporiuna.org, direcao@camporiuna.org (Senha: UNA2026 ou UNA2026!Sec)",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray, fontSize = 10.sp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    // PIN Mode
                    Text(
                        text = "Digite o código/PIN oficial de acesso rápido:",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF555555)),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                            errorMessage = null
                        },
                        label = { Text("Código de Acesso / PIN") },
                        placeholder = { Text("Ex: 2026 ou UNA2026") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = CamporiNavy)
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Mostrar senha"
                                )
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth().testTag("admin_pin_input"),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = PathfinderRed.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            color = PathfinderRed,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (loginMode == 0) {
                            if (email.isBlank() || password.isBlank()) {
                                errorMessage = "Por favor, preencha o e-mail e a senha."
                                return@Button
                            }
                            isLoading = true
                            viewModel.authenticateAdminWithCredentials(email, password) { success, msg ->
                                isLoading = false
                                if (!success) {
                                    errorMessage = msg
                                }
                            }
                        } else {
                            if (pin.isBlank()) {
                                errorMessage = "Digite o código/PIN de acesso."
                                return@Button
                            }
                            val success = viewModel.authenticateAdmin(pin)
                            if (!success) {
                                errorMessage = "Código incorreto. Dica: use 2026 ou UNA2026"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("admin_login_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CamporiNavy),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = PathfinderYellow, modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Verificando credenciais...", color = Color.White)
                    } else {
                        Icon(Icons.Default.Shield, contentDescription = null, tint = PathfinderYellow)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (loginMode == 0) "Entrar com Firebase Auth" else "Entrar com PIN",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Voltar ao Modo Desbravador", color = CamporiNavy)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdminDashboardView(
    viewModel: CamporiViewModel,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubTab by remember { mutableIntStateOf(0) }
    val cloudStatus by viewModel.cloudSyncStatus.collectAsStateWithLifecycle()
    val registrations by viewModel.registrations.collectAsStateWithLifecycle()
    val announcements by viewModel.announcements.collectAsStateWithLifecycle()

    val totalCount = registrations.size
    val approvedCount = registrations.count { it.status.equals("Aprovado", ignoreCase = true) || it.status.equals("Confirmado", ignoreCase = true) }
    val pendingCount = registrations.count { it.status.equals("Pendente", ignoreCase = true) }
    val rejectedCount = registrations.count { it.status.equals("Rejeitado", ignoreCase = true) }
    val checkedInCount = registrations.count { it.isCheckedIn }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F9))
    ) {
        // Top Admin Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = CamporiNavy),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(PathfinderYellow),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = null,
                                tint = CamporiNavy,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "ADMIN CAMPORI UNA",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = PathfinderYellow,
                                    letterSpacing = 0.5.sp
                                )
                            )
                            Text(
                                text = "Gestão Geral & Secretaria",
                                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFD3C4B4))
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { viewModel.forceSyncCloud() },
                            modifier = Modifier.testTag("admin_sync_button")
                        ) {
                            Icon(
                                Icons.Default.CloudSync,
                                contentDescription = "Sincronizar",
                                tint = Color.White
                            )
                        }

                        IconButton(
                            onClick = onLogout,
                            modifier = Modifier.testTag("admin_logout_button")
                        ) {
                            Icon(
                                Icons.Default.Logout,
                                contentDescription = "Sair",
                                tint = PathfinderRed
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Cloud Status Pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF1E3352))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (cloudStatus.contains("Sincronizado", ignoreCase = true)) Icons.Default.CloudDone else Icons.Default.Cloud,
                        contentDescription = null,
                        tint = if (cloudStatus.contains("Sincronizado", ignoreCase = true)) PathfinderGreen else PathfinderYellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = cloudStatus,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }

        // Sub Tabs
        SecondaryTabRow(
            selectedTabIndex = selectedSubTab,
            containerColor = Color.White,
            contentColor = CamporiNavy
        ) {
            Tab(
                selected = selectedSubTab == 0,
                onClick = { selectedSubTab = 0 },
                text = { Text("📊 Estatísticas", fontSize = 12.sp, fontWeight = if (selectedSubTab == 0) FontWeight.Bold else FontWeight.Normal) },
                modifier = Modifier.testTag("admin_tab_stats")
            )
            Tab(
                selected = selectedSubTab == 1,
                onClick = { selectedSubTab = 1 },
                text = { Text("📝 Inscrições ($totalCount)", fontSize = 12.sp, fontWeight = if (selectedSubTab == 1) FontWeight.Bold else FontWeight.Normal) },
                modifier = Modifier.testTag("admin_tab_registrations")
            )
            Tab(
                selected = selectedSubTab == 2,
                onClick = { selectedSubTab = 2 },
                text = { Text("📢 Avisos (${announcements.size})", fontSize = 12.sp, fontWeight = if (selectedSubTab == 2) FontWeight.Bold else FontWeight.Normal) },
                modifier = Modifier.testTag("admin_tab_announcements")
            )
            Tab(
                selected = selectedSubTab == 3,
                onClick = { selectedSubTab = 3 },
                text = { Text("🔍 Validador QR", fontSize = 12.sp, fontWeight = if (selectedSubTab == 3) FontWeight.Bold else FontWeight.Normal) },
                modifier = Modifier.testTag("admin_tab_scanner")
            )
        }

        // Content
        when (selectedSubTab) {
            0 -> AdminStatsTab(
                registrations = registrations,
                totalCount = totalCount,
                approvedCount = approvedCount,
                pendingCount = pendingCount,
                rejectedCount = rejectedCount,
                checkedInCount = checkedInCount,
                onForceSync = { viewModel.forceSyncCloud() }
            )
            1 -> AdminRegistrationsTab(
                viewModel = viewModel
            )
            2 -> AdminAnnouncementsTab(
                viewModel = viewModel,
                announcements = announcements
            )
            3 -> AdminQrValidatorTab(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun AdminStatsTab(
    registrations: List<Registration>,
    totalCount: Int,
    approvedCount: Int,
    pendingCount: Int,
    rejectedCount: Int,
    checkedInCount: Int,
    onForceSync: () -> Unit
) {
    val missions = listOf(
        "Missão Sul de Luanda e Cabinda",
        "Missão Nordeste de Angola",
        "Missão Norte de Angola",
        "Missão Leste de Angola"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Visão Geral da Nuvem (Firebase)",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = CamporiNavy
                )
            )
        }

        // Summary Metric Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AdminMetricCard(
                    title = "Total Inscritos",
                    value = "$totalCount",
                    icon = Icons.Default.HowToReg,
                    color = CamporiBlue,
                    modifier = Modifier.weight(1f)
                )
                AdminMetricCard(
                    title = "Aprovados",
                    value = "$approvedCount",
                    icon = Icons.Default.CheckCircle,
                    color = PathfinderGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AdminMetricCard(
                    title = "Pendentes",
                    value = "$pendingCount",
                    icon = Icons.Default.Pending,
                    color = PathfinderYellowDark,
                    modifier = Modifier.weight(1f)
                )
                AdminMetricCard(
                    title = "Check-in Portaria",
                    value = "$checkedInCount",
                    icon = Icons.Default.QrCodeScanner,
                    color = CamporiNavy,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Mission Distribution
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Inscrições por Missão (UNA)",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = CamporiNavy
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    missions.forEach { missionName ->
                        val count = registrations.count { it.mission.contains(missionName, ignoreCase = true) }
                        val progress = if (totalCount > 0) count.toFloat() / totalCount.toFloat() else 0f

                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = missionName,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "$count desbravadores",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = CamporiNavy
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = when {
                                    missionName.contains("Nordeste") -> PathfinderYellowDark
                                    missionName.contains("Sul") -> CamporiBlue
                                    missionName.contains("Norte") -> PathfinderGreen
                                    else -> PathfinderRed
                                },
                                trackColor = Color(0xFFE8ECEF)
                            )
                        }
                    }
                }
            }
        }

        // Roles Breakdown
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Distribuição por Funções",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = CamporiNavy
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    val roles = listOf("Desbravador", "Diretor", "Capitão", "Conselheiro", "Instrutor", "Equipe Médica")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        roles.take(3).forEach { role ->
                            val count = registrations.count { it.role.equals(role, ignoreCase = true) }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "$count", fontWeight = FontWeight.Black, fontSize = 18.sp, color = CamporiNavy)
                                Text(text = role, fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }

        item {
            Button(
                onClick = onForceSync,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CamporiNavy)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, tint = PathfinderYellow)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Forçar Sincronização Nuvem (Firebase)", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun AdminMetricCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, color = CamporiNavy))
                Text(text = title, style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray, fontSize = 10.sp))
            }
        }
    }
}

@Composable
private fun AdminRegistrationsTab(
    viewModel: CamporiViewModel
) {
    val registrations by viewModel.filteredAdminRegistrations.collectAsStateWithLifecycle()
    val searchQuery by viewModel.adminSearchQuery.collectAsStateWithLifecycle()
    val currentStatusFilter by viewModel.adminStatusFilter.collectAsStateWithLifecycle()
    val currentMissionFilter by viewModel.adminMissionFilter.collectAsStateWithLifecycle()

    var selectedDetailReg by remember { mutableStateOf<Registration?>(null) }
    var showRejectDialog by remember { mutableStateOf<Registration?>(null) }
    var rejectReason by remember { mutableStateOf("") }

    val statusFilters = listOf("Todos", "Pendentes", "Aprovados", "Rejeitados", "Check-in Realizado")
    val missions = listOf("Todas", "Sul de Luanda", "Nordeste", "Norte", "Leste")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setAdminSearchQuery(it) },
            placeholder = { Text("Buscar por nome, clube ou código UNA...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setAdminSearchQuery("") }) {
                        Icon(Icons.Default.Delete, contentDescription = "Limpar busca")
                    }
                }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("admin_registration_search"),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CamporiNavy,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Filter Chips (Status)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(statusFilters) { status ->
                val isSelected = currentStatusFilter == status
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setAdminStatusFilter(status) },
                    label = { Text(status, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CamporiNavy,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Mission Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(missions) { mission ->
                val isSelected = currentMissionFilter == mission
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setAdminMissionFilter(mission) },
                    label = { Text(mission, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PathfinderYellowDark,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // List
        if (registrations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Nenhuma inscrição encontrada com estes filtros.", color = Color.Gray, textAlign = TextAlign.Center)
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(registrations, key = { it.registrationCode }) { reg ->
                    AdminRegistrationCard(
                        registration = reg,
                        onApprove = { viewModel.approveRegistration(reg.registrationCode) },
                        onReject = {
                            showRejectDialog = reg
                            rejectReason = ""
                        },
                        onToggleCheckIn = { viewModel.toggleCheckIn(reg.registrationCode, reg.isCheckedIn) },
                        onDetails = { selectedDetailReg = reg },
                        onDelete = { viewModel.deleteRegistration(reg) }
                    )
                }
            }
        }
    }

    // Detail Dialog
    selectedDetailReg?.let { reg ->
        RegistrationDetailDialog(
            registration = reg,
            onDismiss = { selectedDetailReg = null },
            onApprove = {
                viewModel.approveRegistration(reg.registrationCode)
                selectedDetailReg = null
            },
            onToggleCheckIn = {
                viewModel.toggleCheckIn(reg.registrationCode, reg.isCheckedIn)
                selectedDetailReg = null
            }
        )
    }

    // Reject Reason Dialog
    showRejectDialog?.let { reg ->
        AlertDialog(
            onDismissRequest = { showRejectDialog = null },
            title = { Text("Rejeitar Inscrição", fontWeight = FontWeight.Bold, color = PathfinderRed) },
            text = {
                Column {
                    Text("Deseja rejeitar a inscrição de ${reg.fullName} (${reg.registrationCode})?")
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = rejectReason,
                        onValueChange = { rejectReason = it },
                        label = { Text("Motivo da Rejeição (opcional)") },
                        placeholder = { Text("Ex: Falta comprovante ou autorização") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.rejectRegistration(reg.registrationCode, rejectReason)
                        showRejectDialog = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PathfinderRed)
                ) {
                    Text("Confirmar Rejeição", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showRejectDialog = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun AdminRegistrationCard(
    registration: Registration,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    onToggleCheckIn: () -> Unit,
    onDetails: () -> Unit,
    onDelete: () -> Unit
) {
    val isApproved = registration.status.equals("Aprovado", ignoreCase = true) || registration.status.equals("Confirmado", ignoreCase = true)
    val isRejected = registration.status.equals("Rejeitado", ignoreCase = true)
    val statusColor = when {
        isApproved -> PathfinderGreen
        isRejected -> PathfinderRed
        else -> PathfinderYellowDark
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDetails() }
            .testTag("admin_reg_card_${registration.registrationCode}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = registration.fullName,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = CamporiNavy)
                    )
                    Text(
                        text = "${registration.role} • Clube: ${registration.clubName}",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF555555))
                    )
                }

                // Status Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = registration.status,
                        color = statusColor,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Código: ${registration.registrationCode}",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = registration.mission,
                    style = MaterialTheme.typography.labelSmall.copy(color = CamporiBlue, fontWeight = FontWeight.Medium),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFFF0F0F0))
            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isApproved) {
                    Button(
                        onClick = onApprove,
                        colors = ButtonDefaults.buttonColors(containerColor = PathfinderGreen),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Aprovar", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                if (!isRejected) {
                    OutlinedButton(
                        onClick = onReject,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = PathfinderRed),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Rejeitar", fontSize = 11.sp, color = PathfinderRed)
                    }
                }

                // Check-in button
                Button(
                    onClick = onToggleCheckIn,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (registration.isCheckedIn) Color(0xFF4A6572) else CamporiNavy
                    ),
                    modifier = Modifier.weight(1.2f)
                ) {
                    Icon(
                        if (registration.isCheckedIn) Icons.Default.CheckCircle else Icons.Default.QrCodeScanner,
                        contentDescription = null,
                        tint = PathfinderYellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        if (registration.isCheckedIn) "Check-in OK" else "Check-in",
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun RegistrationDetailDialog(
    registration: Registration,
    onDismiss: () -> Unit,
    onApprove: () -> Unit,
    onToggleCheckIn: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.HowToReg, contentDescription = null, tint = CamporiNavy)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Crachá & Ficha Completa", fontWeight = FontWeight.Bold, color = CamporiNavy)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DetailRow("Nome:", registration.fullName)
                DetailRow("Código UNA:", registration.registrationCode)
                DetailRow("Cargo:", registration.role)
                DetailRow("Clube:", registration.clubName)
                DetailRow("Igreja:", registration.churchName)
                DetailRow("Missão:", registration.mission)
                DetailRow("Região:", registration.region)
                DetailRow("Idade:", "${registration.age} anos")
                DetailRow("Telefone:", registration.phone.ifEmpty { "Não informado" })
                DetailRow("Tipo Sanguíneo:", registration.bloodType)
                DetailRow("Contato Emergência:", registration.emergencyContact.ifEmpty { "Não informado" })
                DetailRow("Status Atual:", registration.status)
                DetailRow("Check-in Portaria:", if (registration.isCheckedIn) "✅ Realizado" else "⏳ Pendente")

                if (registration.rejectionReason.isNotBlank()) {
                    DetailRow("Motivo Rejeição:", registration.rejectionReason)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onApprove,
                colors = ButtonDefaults.buttonColors(containerColor = PathfinderGreen)
            ) {
                Text("Aprovar na Nuvem", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        }
    )
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
        Text(text = value, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = CamporiNavy, textAlign = TextAlign.End)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdminAnnouncementsTab(
    viewModel: CamporiViewModel,
    announcements: List<Announcement>
) {
    var title by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("Geral") }
    var department by remember { mutableStateOf("Direção Geral UNA") }

    val priorities = listOf("Urgente", "Importante", "Geral", "Notícia")
    val departments = listOf(
        "Direção Geral UNA",
        "Secretaria Executiva",
        "Posto Médico / Saúde",
        "Eventos & Programa",
        "Logística & Segurança",
        "Pastoral / Capelania"
    )

    var priorityExpanded by remember { mutableStateOf(false) }
    var deptExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Campaign, contentDescription = null, tint = PathfinderRed)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Novo Comunicado Oficial (Nuvem)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = CamporiNavy
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Título do Comunicado") },
                        placeholder = { Text("Ex: Horário de Abertura dos Portões") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_announcement_title"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = summary,
                        onValueChange = { summary = it },
                        label = { Text("Resumo Curto") },
                        placeholder = { Text("Ex: Portões abrirão às 08h pontualmente.") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_announcement_summary"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = body,
                        onValueChange = { body = it },
                        label = { Text("Mensagem Completa") },
                        placeholder = { Text("Instruções detalhadas para todos os Clubes e Desbravadores...") },
                        minLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_announcement_body"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Priority Dropdown
                    ExposedDropdownMenuBox(
                        expanded = priorityExpanded,
                        onExpandedChange = { priorityExpanded = !priorityExpanded }
                    ) {
                        OutlinedTextField(
                            value = "Prioridade: $priority",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = priorityExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = priorityExpanded,
                            onDismissRequest = { priorityExpanded = false }
                        ) {
                            priorities.forEach { p ->
                                DropdownMenuItem(
                                    text = { Text(p) },
                                    onClick = {
                                        priority = p
                                        priorityExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Department Dropdown
                    ExposedDropdownMenuBox(
                        expanded = deptExpanded,
                        onExpandedChange = { deptExpanded = !deptExpanded }
                    ) {
                        OutlinedTextField(
                            value = "Departamento: $department",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = deptExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = deptExpanded,
                            onDismissRequest = { deptExpanded = false }
                        ) {
                            departments.forEach { d ->
                                DropdownMenuItem(
                                    text = { Text(d) },
                                    onClick = {
                                        department = d
                                        deptExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (title.isNotBlank() && body.isNotBlank()) {
                                viewModel.publishOfficialAnnouncement(
                                    title = title,
                                    summary = summary.ifBlank { title },
                                    body = body,
                                    priority = priority,
                                    department = department
                                )
                                title = ""
                                summary = ""
                                body = ""
                            }
                        },
                        enabled = title.isNotBlank() && body.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("admin_publish_announcement_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CamporiNavy)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, tint = PathfinderYellow)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("🚀 Publicar Comunicado na Nuvem", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }

        item {
            Text(
                text = "Comunicados Oficiais Publicados",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = CamporiNavy
                )
            )
        }

        items(announcements, key = { it.id }) { ann ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ann.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = CamporiNavy),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { viewModel.deleteAnnouncement(ann.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remover", tint = PathfinderRed)
                        }
                    }
                    Text(
                        text = "${ann.department} • ${ann.priority}",
                        style = MaterialTheme.typography.labelSmall.copy(color = CamporiBlue, fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = ann.body,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF4A4A4A))
                    )
                }
            }
        }
    }
}

@Composable
private fun AdminQrValidatorTab(
    viewModel: CamporiViewModel
) {
    var inputCode by remember { mutableStateOf("") }
    var isCameraOpen by remember { mutableStateOf(false) }
    val registrations by viewModel.registrations.collectAsStateWithLifecycle()

    val searchedParticipant = registrations.find {
        it.registrationCode.equals(inputCode.trim(), ignoreCase = true)
    }

    val sampleCodes = listOf("UNA-2026-0891", "UNA-2026-1042", "UNA-2026-2155", "UNA-2026-3410")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Camera Scanner Active View
        if (isCameraOpen) {
            item {
                CameraQrScanner(
                    onCodeScanned = { rawCode ->
                        // Clean code if scanned text contains prefix or extra characters
                        val cleanCode = when {
                            rawCode.contains("UNA-") -> {
                                val match = Regex("UNA-\\d{4}-\\d{4}").find(rawCode)
                                match?.value ?: rawCode.trim()
                            }
                            else -> rawCode.trim()
                        }
                        inputCode = cleanCode
                        isCameraOpen = false
                    },
                    onClose = { isCameraOpen = false }
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = CamporiNavy)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Validador de Portaria & Scanner",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = CamporiNavy)
                            )
                        }

                        Button(
                            onClick = { isCameraOpen = !isCameraOpen },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCameraOpen) PathfinderRed else PathfinderYellow,
                                contentColor = if (isCameraOpen) Color.White else CamporiNavy
                            ),
                            modifier = Modifier.testTag("admin_toggle_camera_button")
                        ) {
                            Icon(
                                imageVector = if (isCameraOpen) Icons.Default.Close else Icons.Default.CameraAlt,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isCameraOpen) "Fechar Câmera" else "Ler com Câmera",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Aponte a câmera do telefone para o Crachá ou digite o código para validar em tempo real com a Nuvem:",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = inputCode,
                        onValueChange = { inputCode = it.uppercase() },
                        label = { Text("Código de Inscrição") },
                        placeholder = { Text("Ex: UNA-2026-0891") },
                        leadingIcon = { Icon(Icons.Default.QrCode, contentDescription = null) },
                        trailingIcon = {
                            if (inputCode.isNotEmpty()) {
                                IconButton(onClick = { inputCode = "" }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Limpar")
                                }
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_qr_code_input"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Códigos para teste rápido:",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(sampleCodes) { code ->
                            FilterChip(
                                selected = inputCode == code,
                                onClick = { inputCode = code },
                                label = { Text(code, fontSize = 11.sp) }
                            )
                        }
                    }
                }
            }
        }

        // Result Card
        item {
            if (inputCode.isNotBlank()) {
                if (searchedParticipant != null) {
                    val isApproved = searchedParticipant.status.equals("Aprovado", ignoreCase = true) || searchedParticipant.status.equals("Confirmado", ignoreCase = true)
                    val cardBg = if (isApproved) Color(0xFFF1F8E9) else Color(0xFFFFF8E1)
                    val borderColor = if (isApproved) PathfinderGreen else PathfinderYellowDark

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBg),
                        border = androidx.compose.foundation.BorderStroke(2.dp, borderColor)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isApproved) Icons.Default.CheckCircle else Icons.Default.Pending,
                                    contentDescription = null,
                                    tint = borderColor,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = if (isApproved) "✅ CREDENCIAL VÁLIDA NA NUVEM" else "⏳ INSCRIÇÃO PENDENTE",
                                        fontWeight = FontWeight.Black,
                                        color = borderColor,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Código: ${searchedParticipant.registrationCode}",
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = searchedParticipant.fullName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = CamporiNavy
                            )
                            Text(
                                text = "${searchedParticipant.role} • Clube ${searchedParticipant.clubName}",
                                color = Color(0xFF4A4A4A)
                            )
                            Text(
                                text = "Missão: ${searchedParticipant.mission}",
                                color = CamporiBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Tipo Sanguíneo: ${searchedParticipant.bloodType} • Emergência: ${searchedParticipant.emergencyContact}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Button(
                                onClick = {
                                    viewModel.toggleCheckIn(searchedParticipant.registrationCode, searchedParticipant.isCheckedIn)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (searchedParticipant.isCheckedIn) Color(0xFF4A6572) else CamporiNavy
                                )
                            ) {
                                Icon(
                                    if (searchedParticipant.isCheckedIn) Icons.Default.CheckCircle else Icons.Default.QrCodeScanner,
                                    contentDescription = null,
                                    tint = PathfinderYellow
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    if (searchedParticipant.isCheckedIn) "Check-in Portaria Confirmado" else "Registrar Entrada no Campori",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                        border = androidx.compose.foundation.BorderStroke(2.dp, PathfinderRed)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.Error, contentDescription = null, tint = PathfinderRed, modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Código Não Encontrado na Nuvem",
                                fontWeight = FontWeight.Bold,
                                color = PathfinderRed
                            )
                            Text(
                                text = "Nenhuma inscrição com o código '$inputCode' foi localizada no Firebase Firestore.",
                                fontSize = 12.sp,
                                color = Color(0xFF5A5A5A),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
