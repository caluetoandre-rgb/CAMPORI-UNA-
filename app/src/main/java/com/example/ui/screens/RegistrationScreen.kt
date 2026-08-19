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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Registration
import com.example.ui.components.DigitalBadgeCard
import com.example.ui.theme.CamporiBlue
import com.example.ui.theme.CamporiNavy
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.PathfinderRed
import com.example.ui.theme.PathfinderYellow
import com.example.ui.theme.PathfinderYellowDark
import com.example.viewmodel.CamporiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: CamporiViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val registrations by viewModel.registrations.collectAsStateWithLifecycle()
    val lastRegistered by viewModel.lastRegistered.collectAsStateWithLifecycle()

    var activeBadgeForPreview by remember { mutableStateOf<Registration?>(null) }

    // Form inputs state
    var fullName by remember { mutableStateOf("") }
    var clubName by remember { mutableStateOf("") }
    var churchName by remember { mutableStateOf("") }
    var mission by remember { mutableStateOf("Missão Nordeste de Angola") }
    var region by remember { mutableStateOf("1ª Região - Malanje") }
    var role by remember { mutableStateOf("Desbravador") }
    var ageText by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var bloodType by remember { mutableStateOf("O+") }
    var emergencyContact by remember { mutableStateOf("") }

    var formError by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    // Dropdown expanded states
    var missionExpanded by remember { mutableStateOf(false) }
    var roleExpanded by remember { mutableStateOf(false) }
    var bloodExpanded by remember { mutableStateOf(false) }

    val missionOptions = listOf(
        "Missão Nordeste de Angola",
        "Missão Norte de Angola",
        "Missão Centro de Angola",
        "Missão Sul de Angola",
        "Missão Leste de Angola"
    )

    val roleOptions = listOf(
        "Desbravador",
        "Capitão",
        "Conselheiro",
        "Diretor do Clube",
        "Diretor Associado",
        "Instrutor de Classes",
        "Pastor / Capelão",
        "Equipe Médica / Saúde",
        "Cozinha & Alimentação",
        "Segurança / Apoio"
    )

    val bloodOptions = listOf("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-", "Não sabe")

    // Show Badge Dialog if triggered
    val previewItem = activeBadgeForPreview ?: lastRegistered
    if (previewItem != null) {
        DigitalBadgeCard(
            registration = previewItem,
            onClose = {
                activeBadgeForPreview = null
                viewModel.clearLastRegistered()
            },
            onShare = {
                // Share action trigger
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Header
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
                    text = "PORTAL DE INSCRIÇÃO & CREDENCIAMENTO",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = PathfinderYellow,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "II Campori UNA 2026/2027",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Gere seu crachá oficial com QR Code e confirme a vaga do seu Clube.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFFD3C4B4),
                        fontSize = 12.sp
                    )
                )
            }
        }

        // Tabs: 0 -> Nova Inscrição, 1 -> Lista de Inscritos
        SecondaryTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = CamporiNavy
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.HowToReg, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Nova Inscrição", fontWeight = FontWeight.Bold)
                    }
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Inscritos (${registrations.size})", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }

        if (selectedTab == 0) {
            // Registration Form
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                    .padding(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (formError.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFFFEBEE),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PathfinderRed)
                    ) {
                        Text(
                            text = formError,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PathfinderRed,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                // 1. Dados Pessoais
                Text(
                    text = "1. Identificação do Participante",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = CamporiNavy
                    )
                )

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it; formError = "" },
                    label = { Text("Nome Completo *") },
                    placeholder = { Text("Ex: André Calueto") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = CamporiNavy) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_fullname"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CamporiNavy)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = ageText,
                        onValueChange = { ageText = it.filter { char -> char.isDigit() }.take(2) },
                        label = { Text("Idade *") },
                        placeholder = { Text("Ex: 16") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_age"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Blood Type Dropdown
                    ExposedDropdownMenuBox(
                        expanded = bloodExpanded,
                        onExpandedChange = { bloodExpanded = !bloodExpanded },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = bloodType,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Grupo Sang.") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = bloodExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .testTag("select_blood_type"),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = bloodExpanded,
                            onDismissRequest = { bloodExpanded = false }
                        ) {
                            bloodOptions.forEach { opt ->
                                DropdownMenuItem(
                                    text = { Text(opt) },
                                    onClick = {
                                        bloodType = opt
                                        bloodExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // 2. Dados Eclesiásticos & Clube
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "2. Clube, Igreja e Missão da UNA",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = CamporiNavy
                    )
                )

                OutlinedTextField(
                    value = clubName,
                    onValueChange = { clubName = it; formError = "" },
                    label = { Text("Nome do Clube de Desbravadores *") },
                    placeholder = { Text("Ex: Estrela da Savana") },
                    leadingIcon = { Icon(Icons.Default.Group, contentDescription = null, tint = PathfinderYellowDark) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_clubname"),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = churchName,
                    onValueChange = { churchName = it; formError = "" },
                    label = { Text("Igreja Adventista Local *") },
                    placeholder = { Text("Ex: IASD Central de Malanje") },
                    leadingIcon = { Icon(Icons.Default.Church, contentDescription = null, tint = CamporiBlue) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_churchname"),
                    shape = RoundedCornerShape(12.dp)
                )

                // Mission Dropdown
                ExposedDropdownMenuBox(
                    expanded = missionExpanded,
                    onExpandedChange = { missionExpanded = !missionExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = mission,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Missão / Campo *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = missionExpanded) },
                        leadingIcon = { Icon(Icons.Default.Flag, contentDescription = null, tint = ForestGreen) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .testTag("select_mission"),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = missionExpanded,
                        onDismissRequest = { missionExpanded = false }
                    ) {
                        missionOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    mission = opt
                                    missionExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = region,
                    onValueChange = { region = it },
                    label = { Text("Região / Distrito") },
                    placeholder = { Text("Ex: 1ª Região - Malanje / Cacuso") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Role Dropdown
                ExposedDropdownMenuBox(
                    expanded = roleExpanded,
                    onExpandedChange = { roleExpanded = !roleExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = role,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Cargo / Função no Campori *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = roleExpanded) },
                        leadingIcon = { Icon(Icons.Default.Security, contentDescription = null, tint = PathfinderRed) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .testTag("select_role"),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = roleExpanded,
                        onDismissRequest = { roleExpanded = false }
                    ) {
                        roleOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    role = opt
                                    roleExpanded = false
                                }
                            )
                        }
                    }
                }

                // 3. Contatos & Emergência
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "3. Contatos & Segurança",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = CamporiNavy
                    )
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Telefone / WhatsApp *") },
                    placeholder = { Text("Ex: +244 923 000 000") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = CamporiNavy) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_phone"),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = emergencyContact,
                    onValueChange = { emergencyContact = it },
                    label = { Text("Contato de Emergência / Responsável *") },
                    placeholder = { Text("Ex: +244 912 000 000 (Mãe / Diretor)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (fullName.isBlank()) {
                            formError = "Por favor, preencha o Nome Completo."
                            return@Button
                        }
                        if (clubName.isBlank()) {
                            formError = "Por favor, informe o Nome do Clube."
                            return@Button
                        }
                        if (churchName.isBlank()) {
                            formError = "Por favor, informe a Igreja Adventista."
                            return@Button
                        }
                        val age = ageText.toIntOrNull() ?: 15
                        viewModel.submitRegistration(
                            fullName = fullName,
                            clubName = clubName,
                            churchName = churchName,
                            mission = mission,
                            region = region,
                            role = role,
                            age = age,
                            phone = phone.ifBlank { "+244 920 000 000" },
                            bloodType = bloodType,
                            emergencyContact = emergencyContact.ifBlank { "Coordenação do Clube" }
                        )

                        // Clear inputs for next entry
                        fullName = ""
                        clubName = ""
                        churchName = ""
                        ageText = ""
                        phone = ""
                        emergencyContact = ""
                        formError = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("submit_registration_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PathfinderYellow,
                        contentColor = Color(0xFF331C00)
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.HowToReg, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "CONCLUIR INSCRIÇÃO & GERAR CRACHÁ",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                    )
                }
            }
        } else {
            // Registrations List & Search
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Pesquisar por nome, clube ou missão...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Limpar")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_registration_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                val filtered = registrations.filter {
                    if (searchQuery.isBlank()) true
                    else {
                        it.fullName.contains(searchQuery, ignoreCase = true) ||
                        it.clubName.contains(searchQuery, ignoreCase = true) ||
                        it.mission.contains(searchQuery, ignoreCase = true) ||
                        it.registrationCode.contains(searchQuery, ignoreCase = true)
                    }
                }

                if (filtered.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhum participante encontrado.",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filtered, key = { it.id }) { item ->
                            RegistrationItemCard(
                                registration = item,
                                onViewBadge = { activeBadgeForPreview = item },
                                onDelete = { viewModel.deleteRegistration(item) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RegistrationItemCard(
    registration: Registration,
    onViewBadge: () -> Unit,
    onDelete: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onViewBadge)
            .testTag("registered_card_${registration.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = CamporiNavy,
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = registration.fullName.take(2).uppercase(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = PathfinderYellow,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = registration.fullName,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = CamporiNavy
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = "Clube: ${registration.clubName} • ${registration.role}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = PathfinderRed,
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${registration.mission} • ${registration.registrationCode}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.Gray,
                        fontSize = 11.sp
                    ),
                    maxLines = 1
                )
            }

            IconButton(onClick = onViewBadge) {
                Icon(
                    Icons.Default.QrCode,
                    contentDescription = "Ver Crachá",
                    tint = CamporiNavy
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remover",
                    tint = Color.LightGray
                )
            }
        }
    }
}
