package com.example.data.repository

import android.util.Log
import com.example.data.cloud.FirebaseCloudService
import com.example.data.local.AnnouncementDao
import com.example.data.local.AnnouncementEntity
import com.example.data.local.BibleBookmarkDao
import com.example.data.local.BibleBookmarkEntity
import com.example.data.local.RegistrationDao
import com.example.data.local.RegistrationEntity
import com.example.data.local.ScheduleDao
import com.example.data.local.ScheduleEntity
import com.example.data.model.Announcement
import com.example.data.model.BibleBook
import com.example.data.model.BibleVerse
import com.example.data.model.CamporiMapPoint
import com.example.data.model.GalleryItem
import com.example.data.model.PathfinderIdeal
import com.example.data.model.Registration
import com.example.data.model.ScheduleItem
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CamporiRepository(
    private val registrationDao: RegistrationDao,
    private val scheduleDao: ScheduleDao,
    private val announcementDao: AnnouncementDao,
    private val bookmarkDao: BibleBookmarkDao,
    private val cloudService: FirebaseCloudService = FirebaseCloudService()
) {
    private val tag = "CamporiRepository"
    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    private val _cloudSyncStatus = MutableStateFlow("Nuvem Ativa")
    val cloudSyncStatus: StateFlow<String> = _cloudSyncStatus.asStateFlow()

    private var registrationsListener: ListenerRegistration? = null
    private var announcementsListener: ListenerRegistration? = null

    val allRegistrations: Flow<List<Registration>> = registrationDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    val registrationCount: Flow<Int> = registrationDao.getCount()

    val allSchedules: Flow<List<ScheduleItem>> = scheduleDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    val allAnnouncements: Flow<List<Announcement>> = announcementDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    val allBookmarks: Flow<List<BibleBookmarkEntity>> = bookmarkDao.getAll()

    suspend fun seedInitialDataIfEmpty() {
        val existingSchedules = scheduleDao.getAll().firstOrNull()
        if (existingSchedules.isNullOrEmpty()) {
            scheduleDao.insertAll(CamporiInitialData.defaultSchedules.map { ScheduleEntity.fromDomain(it) })
        }

        val existingAnnouncements = announcementDao.getAll().firstOrNull()
        if (existingAnnouncements.isNullOrEmpty()) {
            announcementDao.insertAll(CamporiInitialData.defaultAnnouncements.map { AnnouncementEntity.fromDomain(it) })
        }

        val existingRegistrations = registrationDao.getAll().firstOrNull()
        if (existingRegistrations.isNullOrEmpty()) {
            for (reg in CamporiInitialData.sampleRegistrations) {
                registrationDao.insert(RegistrationEntity.fromDomain(reg))
            }
        }

        // Initialize Realtime Cloud Sync
        startCloudSynchronization()
    }

    fun startCloudSynchronization() {
        repositoryScope.launch {
            try {
                _cloudSyncStatus.value = "Sincronizando..."
                // Try initial pull from Firestore
                val cloudRegsResult = cloudService.fetchRegistrations()
                if (cloudRegsResult.isSuccess) {
                    val cloudRegs = cloudRegsResult.getOrNull()
                    if (!cloudRegs.isNullOrEmpty()) {
                        for (reg in cloudRegs) {
                            registrationDao.insert(RegistrationEntity.fromDomain(reg))
                        }
                    } else {
                        // Push initial local registrations to cloud to seed cloud database
                        val localList = registrationDao.getAll().firstOrNull() ?: emptyList()
                        for (local in localList) {
                            cloudService.saveRegistration(local.toDomain())
                        }
                    }
                    _cloudSyncStatus.value = "Firebase Cloud Sincronizado"
                } else {
                    _cloudSyncStatus.value = "Modo Conectado / Local"
                }

                // Sync announcements
                val cloudAnnResult = cloudService.fetchAnnouncements()
                if (cloudAnnResult.isSuccess) {
                    val cloudAnn = cloudAnnResult.getOrNull()
                    if (!cloudAnn.isNullOrEmpty()) {
                        for (ann in cloudAnn) {
                            announcementDao.insert(AnnouncementEntity.fromDomain(ann))
                        }
                    } else {
                        val localAnn = announcementDao.getAll().firstOrNull() ?: emptyList()
                        for (local in localAnn) {
                            cloudService.publishAnnouncement(local.toDomain())
                        }
                    }
                }

                // Register real-time snapshot listeners
                registrationsListener?.remove()
                registrationsListener = cloudService.listenToRegistrations { updatedList ->
                    repositoryScope.launch {
                        for (item in updatedList) {
                            registrationDao.insert(RegistrationEntity.fromDomain(item))
                        }
                        _cloudSyncStatus.value = "Firebase Cloud Sincronizado"
                    }
                }

                announcementsListener?.remove()
                announcementsListener = cloudService.listenToAnnouncements { updatedAnnouncements ->
                    repositoryScope.launch {
                        for (ann in updatedAnnouncements) {
                            announcementDao.insert(AnnouncementEntity.fromDomain(ann))
                        }
                    }
                }
            } catch (e: Exception) {
                Log.w(tag, "Cloud sync fallback: ${e.message}")
                _cloudSyncStatus.value = "Modo Offline / Local Ativo"
            }
        }
    }

    suspend fun registerParticipant(
        fullName: String,
        clubName: String,
        churchName: String,
        mission: String,
        region: String,
        role: String,
        age: Int,
        phone: String,
        bloodType: String,
        emergencyContact: String
    ): Registration {
        val randomNum = (1000..9999).random()
        val code = "UNA-2026-$randomNum"
        val registration = Registration(
            fullName = fullName.trim(),
            clubName = clubName.trim(),
            churchName = churchName.trim(),
            mission = mission,
            region = region.trim().ifEmpty { "Região Malanje / UNA" },
            role = role,
            age = age,
            phone = phone.trim(),
            bloodType = bloodType,
            emergencyContact = emergencyContact.trim(),
            registrationCode = code,
            registrationDate = System.currentTimeMillis(),
            status = "Confirmado",
            isCheckedIn = false,
            rejectionReason = ""
        )
        val generatedId = registrationDao.insert(RegistrationEntity.fromDomain(registration))
        val finalReg = registration.copy(id = generatedId)

        // Asynchronously push to Cloud
        repositoryScope.launch {
            try {
                cloudService.saveRegistration(finalReg)
            } catch (e: Exception) {
                Log.w(tag, "Failed to push new registration to cloud: ${e.message}")
            }
        }

        return finalReg
    }

    suspend fun approveRegistration(code: String) {
        registrationDao.updateStatus(code, "Aprovado", "")
        repositoryScope.launch {
            try {
                cloudService.updateRegistrationStatus(code, "Aprovado", "")
            } catch (e: Exception) {
                Log.w(tag, "Cloud update error: ${e.message}")
            }
        }
    }

    suspend fun rejectRegistration(code: String, reason: String = "") {
        registrationDao.updateStatus(code, "Rejeitado", reason)
        repositoryScope.launch {
            try {
                cloudService.updateRegistrationStatus(code, "Rejeitado", reason)
            } catch (e: Exception) {
                Log.w(tag, "Cloud update error: ${e.message}")
            }
        }
    }

    suspend fun checkInRegistration(code: String, isCheckedIn: Boolean) {
        registrationDao.updateCheckIn(code, isCheckedIn)
        repositoryScope.launch {
            try {
                cloudService.updateRegistrationCheckIn(code, isCheckedIn)
            } catch (e: Exception) {
                Log.w(tag, "Cloud checkin error: ${e.message}")
            }
        }
    }

    suspend fun deleteRegistration(registration: Registration) {
        registrationDao.delete(RegistrationEntity.fromDomain(registration))
        repositoryScope.launch {
            try {
                cloudService.deleteRegistration(registration.registrationCode)
            } catch (e: Exception) {
                Log.w(tag, "Cloud delete error: ${e.message}")
            }
        }
    }

    suspend fun publishAnnouncement(
        title: String,
        summary: String,
        body: String,
        priority: String,
        department: String
    ): Announcement {
        val id = System.currentTimeMillis()
        val announcement = Announcement(
            id = id,
            title = title.trim(),
            summary = summary.trim(),
            body = body.trim(),
            dateLabel = "Oficial UNA",
            priority = priority,
            department = department,
            isRead = false
        )
        announcementDao.insert(AnnouncementEntity.fromDomain(announcement))

        repositoryScope.launch {
            try {
                cloudService.publishAnnouncement(announcement)
            } catch (e: Exception) {
                Log.w(tag, "Cloud announcement push error: ${e.message}")
            }
        }

        return announcement
    }

    suspend fun deleteAnnouncement(id: Long) {
        announcementDao.deleteById(id)
        repositoryScope.launch {
            try {
                cloudService.deleteAnnouncement(id)
            } catch (e: Exception) {
                Log.w(tag, "Cloud announcement delete error: ${e.message}")
            }
        }
    }

    suspend fun toggleScheduleFavorite(scheduleItem: ScheduleItem) {
        scheduleDao.updateFavorite(scheduleItem.id, !scheduleItem.isFavorite)
    }

    suspend fun toggleScheduleCompleted(scheduleItem: ScheduleItem) {
        scheduleDao.updateCompleted(scheduleItem.id, !scheduleItem.isCompleted)
    }

    suspend fun signInAdmin(email: String, pass: String): Result<String> {
        return cloudService.signInWithEmail(email, pass)
    }

    fun getCurrentAdminEmail(): String? {
        return cloudService.getCurrentUserEmail()
    }

    fun signOutAdmin() {
        cloudService.signOut()
    }

    suspend fun markAnnouncementAsRead(announcementId: Long) {
        announcementDao.markAsRead(announcementId)
    }

    suspend fun addBookmark(book: String, chapter: Int, verse: Int, text: String, note: String = "") {
        bookmarkDao.insert(
            BibleBookmarkEntity(
                book = book,
                chapter = chapter,
                verse = verse,
                text = text,
                note = note
            )
        )
    }

    suspend fun removeBookmark(book: String, chapter: Int, verse: Int) {
        bookmarkDao.deleteByVerse(book, chapter, verse)
    }

    fun getMapPoints(): List<CamporiMapPoint> = CamporiInitialData.mapPoints

    fun getGalleryPhotos(): List<GalleryItem> = CamporiInitialData.galleryItems

    fun getIdeals(): List<PathfinderIdeal> = CamporiInitialData.ideals

    fun getHymnLyrics(): String = CamporiInitialData.hymnLyrics

    fun getBibleBooks(): List<BibleBook> = CamporiInitialData.bibleBooks

    fun getVersesForBook(bookName: String, chapter: Int): List<BibleVerse> {
        val matched = CamporiInitialData.keyBibleVerses.filter {
            it.book.equals(bookName, ignoreCase = true) && (chapter == 0 || it.chapter == chapter)
        }
        if (matched.isNotEmpty()) return matched

        return listOf(
            BibleVerse(bookName, chapter, 1, "No princípio deste capítulo em $bookName, a palavra de Deus instrui os Seus servos para a fidelidade."),
            BibleVerse(bookName, chapter, 2, "Guarda os meus mandamentos e vive; e a minha lei, como a menina dos teus olhos."),
            BibleVerse(bookName, chapter, 3, "Ata-os aos teus dedos, escreve-os na tábua do teu coração."),
            BibleVerse(bookName, chapter, 4, "Porque o mandamento é lâmpada, e a lei é luz; e as repreensões da instrução são o caminho da vida."),
            BibleVerse(bookName, chapter, 5, "Confia no Senhor de todo o teu coração, e Ele guiará os teus passos.")
        )
    }

    fun searchBible(query: String): List<BibleVerse> {
        if (query.isBlank()) return CamporiInitialData.keyBibleVerses
        return CamporiInitialData.keyBibleVerses.filter {
            it.text.contains(query, ignoreCase = true) ||
            it.book.contains(query, ignoreCase = true)
        }
    }
}

