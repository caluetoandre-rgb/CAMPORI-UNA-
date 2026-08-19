package com.example.viewmodel

import android.app.Application
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BibleBookmarkEntity
import com.example.data.local.CamporiDatabase
import com.example.data.model.Announcement
import com.example.data.model.BibleBook
import com.example.data.model.BibleVerse
import com.example.data.model.CamporiMapPoint
import com.example.data.model.GalleryItem
import com.example.data.model.PathfinderIdeal
import com.example.data.model.Registration
import com.example.data.model.ScheduleItem
import com.example.data.repository.CamporiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone
import kotlin.math.sin

data class CountdownState(
    val days: Long = 0,
    val hours: Long = 0,
    val minutes: Long = 0,
    val seconds: Long = 0,
    val isStarted: Boolean = false
)

data class UiMessage(
    val id: Long = System.currentTimeMillis(),
    val message: String
)

class CamporiViewModel(application: Application) : AndroidViewModel(application) {

    private val database = CamporiDatabase.getInstance(application)
    private val repository = CamporiRepository(
        registrationDao = database.registrationDao(),
        scheduleDao = database.scheduleDao(),
        announcementDao = database.announcementDao(),
        bookmarkDao = database.bibleBookmarkDao()
    )

    // Registrations
    val registrations: StateFlow<List<Registration>> = repository.allRegistrations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val registrationCount: StateFlow<Int> = repository.registrationCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _lastRegistered = MutableStateFlow<Registration?>(null)
    val lastRegistered: StateFlow<Registration?> = _lastRegistered.asStateFlow()

    // Schedules
    val schedules: StateFlow<List<ScheduleItem>> = repository.allSchedules
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedDay = MutableStateFlow(1) // 1 to 7 or 0 for all
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Todas")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _scheduleSearchQuery = MutableStateFlow("")
    val scheduleSearchQuery: StateFlow<String> = _scheduleSearchQuery.asStateFlow()

    val filteredSchedules: StateFlow<List<ScheduleItem>> = combine(
        schedules,
        _selectedDay,
        _selectedCategory,
        _scheduleSearchQuery
    ) { list, day, category, query ->
        list.filter { item ->
            val matchDay = if (day == 0) true else item.dayNumber == day
            val matchCat = if (category == "Todas") true else item.category.equals(category, ignoreCase = true)
            val matchQuery = if (query.isBlank()) true else {
                item.title.contains(query, ignoreCase = true) ||
                item.description.contains(query, ignoreCase = true) ||
                item.location.contains(query, ignoreCase = true)
            }
            matchDay && matchCat && matchQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Announcements
    val announcements: StateFlow<List<Announcement>> = repository.allAnnouncements
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedAnnouncement = MutableStateFlow<Announcement?>(null)
    val selectedAnnouncement: StateFlow<Announcement?> = _selectedAnnouncement.asStateFlow()

    // Map
    val mapPoints: List<CamporiMapPoint> = repository.getMapPoints()

    private val _selectedMapPoint = MutableStateFlow<CamporiMapPoint?>(null)
    val selectedMapPoint: StateFlow<CamporiMapPoint?> = _selectedMapPoint.asStateFlow()

    private val _selectedMapZone = MutableStateFlow("Todas")
    val selectedMapZone: StateFlow<String> = _selectedMapZone.asStateFlow()

    // Bible
    val bibleBooks: List<BibleBook> = repository.getBibleBooks()

    private val _selectedBibleBook = MutableStateFlow("Salmos")
    val selectedBibleBook: StateFlow<String> = _selectedBibleBook.asStateFlow()

    private val _selectedBibleChapter = MutableStateFlow(23)
    val selectedBibleChapter: StateFlow<Int> = _selectedBibleChapter.asStateFlow()

    private val _bibleSearchQuery = MutableStateFlow("")
    val bibleSearchQuery: StateFlow<String> = _bibleSearchQuery.asStateFlow()

    private val _bibleFontSize = MutableStateFlow(16) // sp
    val bibleFontSize: StateFlow<Int> = _bibleFontSize.asStateFlow()

    val currentVerses: StateFlow<List<BibleVerse>> = combine(
        _selectedBibleBook,
        _selectedBibleChapter,
        _bibleSearchQuery
    ) { book, chapter, query ->
        if (query.isNotBlank()) {
            repository.searchBible(query)
        } else {
            repository.getVersesForBook(book, chapter)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarks: StateFlow<List<BibleBookmarkEntity>> = repository.allBookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Gallery
    val galleryPhotos: List<GalleryItem> = repository.getGalleryPhotos()

    private val _selectedGalleryPhoto = MutableStateFlow<GalleryItem?>(null)
    val selectedGalleryPhoto: StateFlow<GalleryItem?> = _selectedGalleryPhoto.asStateFlow()

    private val _galleryCategory = MutableStateFlow("Todas")
    val galleryCategory: StateFlow<String> = _galleryCategory.asStateFlow()

    // Ideals & Anthem
    val ideals: List<PathfinderIdeal> = repository.getIdeals()
    val hymnLyrics: String = repository.getHymnLyrics()

    private val _isPlayingHymn = MutableStateFlow(false)
    val isPlayingHymn: StateFlow<Boolean> = _isPlayingHymn.asStateFlow()

    // Countdown
    private val _countdown = MutableStateFlow(CountdownState())
    val countdown: StateFlow<CountdownState> = _countdown.asStateFlow()

    // Notifications / Snackbars
    private val _uiMessage = MutableStateFlow<UiMessage?>(null)
    val uiMessage: StateFlow<UiMessage?> = _uiMessage.asStateFlow()

    private var audioTrack: AudioTrack? = null
    private var hymnJob: Job? = null

    init {
        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }
        startCountdownTimer()
    }

    private fun startCountdownTimer() {
        viewModelScope.launch(Dispatchers.Default) {
            val targetCal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Luanda")).apply {
                set(2026, Calendar.DECEMBER, 28, 8, 0, 0)
            }
            val targetMillis = targetCal.timeInMillis

            while (true) {
                val currentMillis = System.currentTimeMillis()
                val diff = targetMillis - currentMillis

                if (diff <= 0) {
                    _countdown.value = CountdownState(0, 0, 0, 0, isStarted = true)
                } else {
                    val days = diff / (1000 * 60 * 60 * 24)
                    val hours = (diff / (1000 * 60 * 60)) % 24
                    val minutes = (diff / (1000 * 60)) % 60
                    val seconds = (diff / 1000) % 60
                    _countdown.value = CountdownState(days, hours, minutes, seconds, isStarted = false)
                }
                delay(1000)
            }
        }
    }

    fun submitRegistration(
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
    ) {
        viewModelScope.launch {
            try {
                val registered = repository.registerParticipant(
                    fullName = fullName,
                    clubName = clubName,
                    churchName = churchName,
                    mission = mission,
                    region = region,
                    role = role,
                    age = age,
                    phone = phone,
                    bloodType = bloodType,
                    emergencyContact = emergencyContact
                )
                _lastRegistered.value = registered
                _uiMessage.value = UiMessage(message = "Inscrição realizada com sucesso para ${registered.fullName}!")
            } catch (e: Exception) {
                _uiMessage.value = UiMessage(message = "Erro ao registrar: ${e.localizedMessage}")
            }
        }
    }

    fun clearLastRegistered() {
        _lastRegistered.value = null
    }

    fun setLastRegisteredForPreview(reg: Registration) {
        _lastRegistered.value = reg
    }

    fun deleteRegistration(registration: Registration) {
        viewModelScope.launch {
            repository.deleteRegistration(registration)
            _uiMessage.value = UiMessage(message = "Inscrição de ${registration.fullName} removida.")
        }
    }

    fun setSelectedDay(day: Int) {
        _selectedDay.value = day
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setScheduleSearchQuery(query: String) {
        _scheduleSearchQuery.value = query
    }

    fun toggleScheduleFavorite(item: ScheduleItem) {
        viewModelScope.launch {
            repository.toggleScheduleFavorite(item)
        }
    }

    fun toggleScheduleCompleted(item: ScheduleItem) {
        viewModelScope.launch {
            repository.toggleScheduleCompleted(item)
        }
    }

    fun selectAnnouncement(announcement: Announcement?) {
        _selectedAnnouncement.value = announcement
        if (announcement != null && !announcement.isRead) {
            viewModelScope.launch {
                repository.markAnnouncementAsRead(announcement.id)
            }
        }
    }

    fun selectMapPoint(point: CamporiMapPoint?) {
        _selectedMapPoint.value = point
    }

    fun setMapZone(zone: String) {
        _selectedMapZone.value = zone
    }

    fun selectBibleBook(bookName: String) {
        _selectedBibleBook.value = bookName
        _selectedBibleChapter.value = 1
        _bibleSearchQuery.value = ""
    }

    fun selectBibleChapter(chapter: Int) {
        _selectedBibleChapter.value = chapter
    }

    fun setBibleSearchQuery(query: String) {
        _bibleSearchQuery.value = query
    }

    fun increaseBibleFontSize() {
        if (_bibleFontSize.value < 26) {
            _bibleFontSize.value += 2
        }
    }

    fun decreaseBibleFontSize() {
        if (_bibleFontSize.value > 12) {
            _bibleFontSize.value -= 2
        }
    }

    fun toggleBookmark(book: String, chapter: Int, verse: Int, text: String) {
        viewModelScope.launch {
            val existing = bookmarks.value.find { it.book == book && it.chapter == chapter && it.verse == verse }
            if (existing != null) {
                repository.removeBookmark(book, chapter, verse)
                _uiMessage.value = UiMessage(message = "Versículo removido dos favoritos.")
            } else {
                repository.addBookmark(book, chapter, verse, text)
                _uiMessage.value = UiMessage(message = "Versículo salvo nos favoritos!")
            }
        }
    }

    fun selectGalleryPhoto(photo: GalleryItem?) {
        _selectedGalleryPhoto.value = photo
    }

    fun setGalleryCategory(cat: String) {
        _galleryCategory.value = cat
    }

    fun clearUiMessage() {
        _uiMessage.value = null
    }

    // Pathfinder Anthem (Hino dos Desbravadores) Audio Tone Player
    fun togglePlayHymn() {
        if (_isPlayingHymn.value) {
            stopHymn()
        } else {
            playHymnMelody()
        }
    }

    private fun stopHymn() {
        _isPlayingHymn.value = false
        hymnJob?.cancel()
        hymnJob = null
        try {
            audioTrack?.pause()
            audioTrack?.flush()
            audioTrack?.release()
            audioTrack = null
        } catch (_: Exception) {}
    }

    private fun playHymnMelody() {
        _isPlayingHymn.value = true
        hymnJob = viewModelScope.launch(Dispatchers.Default) {
            try {
                val sampleRate = 44100
                // Notes in Hz (Hino dos Desbravadores melody in G Major)
                // Sol(392), Si(493), Re(587), Mi(659), Do(523), La(440), etc.
                val notes = listOf(
                    Pair(392.0, 400), Pair(493.88, 400), Pair(587.33, 600), Pair(587.33, 400),
                    Pair(659.25, 400), Pair(587.33, 400), Pair(493.88, 400), Pair(392.0, 600),
                    Pair(440.0, 400), Pair(493.88, 400), Pair(523.25, 400), Pair(493.88, 400),
                    Pair(440.0, 600), Pair(392.0, 800),
                    // Refrão
                    Pair(587.33, 400), Pair(659.25, 400), Pair(587.33, 400), Pair(523.25, 400),
                    Pair(493.88, 600), Pair(392.0, 400), Pair(440.0, 400), Pair(493.88, 400),
                    Pair(392.0, 800)
                )

                val minBufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )

                audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(minBufferSize * 2)
                    .build()

                audioTrack?.play()

                for ((freq, durationMs) in notes) {
                    if (!_isPlayingHymn.value) break
                    val numSamples = (durationMs * sampleRate / 1000)
                    val buffer = ShortArray(numSamples)
                    for (i in 0 until numSamples) {
                        val angle = 2.0 * Math.PI * i / (sampleRate / freq)
                        // Smooth envelope to avoid clicks
                        val env = if (i < 500) i / 500.0 else if (i > numSamples - 500) (numSamples - i) / 500.0 else 1.0
                        buffer[i] = (sin(angle) * Short.MAX_VALUE * 0.4 * env).toInt().toShort()
                    }
                    audioTrack?.write(buffer, 0, buffer.size)
                    delay(durationMs.toLong())
                }
            } catch (_: Exception) {
            } finally {
                _isPlayingHymn.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopHymn()
    }
}
