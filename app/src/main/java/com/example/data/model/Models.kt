package com.example.data.model

data class Registration(
    val id: Long = 0,
    val fullName: String = "",
    val clubName: String = "",
    val churchName: String = "",
    val mission: String = "",
    val region: String = "",
    val role: String = "Desbravador",
    val age: Int = 0,
    val phone: String = "",
    val bloodType: String = "O+",
    val emergencyContact: String = "",
    val registrationCode: String = "",
    val registrationDate: Long = System.currentTimeMillis(),
    val status: String = "Confirmado", // "Pendente", "Confirmado", "Aprovado", "Rejeitado"
    val isCheckedIn: Boolean = false,
    val rejectionReason: String = ""
)

data class ScheduleItem(
    val id: Long,
    val dayNumber: Int, // 1 to 7 (28 Dez to 03 Jan)
    val dateLabel: String,
    val timeLabel: String,
    val title: String,
    val description: String,
    val location: String,
    val category: String, // Espiritual, Civismo, Pioneiria, Desafios, Cerimônia, Geral
    val isFavorite: Boolean = false,
    val isCompleted: Boolean = false
)

data class Announcement(
    val id: Long,
    val title: String,
    val summary: String,
    val body: String,
    val dateLabel: String,
    val priority: String, // Urgente, Importante, Geral, Notícia
    val department: String, // Direção Geral UNA, Secretaria, Posto Médico, Eventos, Pastoral
    val isRead: Boolean = false
)

data class CamporiMapPoint(
    val id: String,
    val title: String,
    val subtitle: String,
    val zone: String, // Arena, Subcampo, Serviços, Pioneiria, Natureza
    val xPercent: Float, // 0.0 to 1.0
    val yPercent: Float, // 0.0 to 1.0
    val iconType: String, // flag, tent, medical, food, stage, compass, tree, parking, water
    val description: String,
    val openingHours: String = "24h / Permanente",
    val coordinator: String = "Coordenação UNA"
)

data class GalleryItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val drawableRes: Int,
    val category: String, // Pedras Negras, Kalandula, Acampamento, História
    val location: String = "Pungo a Ndongo, Malanje"
)

data class BibleVerse(
    val book: String,
    val chapter: Int,
    val verse: Int,
    val text: String
)

data class BibleBook(
    val name: String,
    val abbreviation: String,
    val testament: String, // Antigo Testamento / Novo Testamento
    val chaptersCount: Int
)

data class PathfinderIdeal(
    val title: String,
    val subtitle: String,
    val content: String,
    val meaning: String
)
