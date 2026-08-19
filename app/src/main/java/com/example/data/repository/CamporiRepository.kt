package com.example.data.repository

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class CamporiRepository(
    private val registrationDao: RegistrationDao,
    private val scheduleDao: ScheduleDao,
    private val announcementDao: AnnouncementDao,
    private val bookmarkDao: BibleBookmarkDao
) {

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
            status = "Confirmado"
        )
        val generatedId = registrationDao.insert(RegistrationEntity.fromDomain(registration))
        return registration.copy(id = generatedId)
    }

    suspend fun deleteRegistration(registration: Registration) {
        registrationDao.delete(RegistrationEntity.fromDomain(registration))
    }

    suspend fun toggleScheduleFavorite(scheduleItem: ScheduleItem) {
        scheduleDao.updateFavorite(scheduleItem.id, !scheduleItem.isFavorite)
    }

    suspend fun toggleScheduleCompleted(scheduleItem: ScheduleItem) {
        scheduleDao.updateCompleted(scheduleItem.id, !scheduleItem.isCompleted)
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

        // Return contextual sample verses for the book/chapter to make any book readable
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
