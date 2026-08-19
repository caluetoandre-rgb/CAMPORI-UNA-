package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistrationDao {
    @Query("SELECT * FROM registrations ORDER BY id DESC")
    fun getAll(): Flow<List<RegistrationEntity>>

    @Query("SELECT * FROM registrations WHERE id = :id")
    suspend fun getById(id: Long): RegistrationEntity?

    @Query("SELECT * FROM registrations WHERE registrationCode = :code")
    suspend fun getByCode(code: String): RegistrationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(registration: RegistrationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(registrations: List<RegistrationEntity>)

    @Update
    suspend fun update(registration: RegistrationEntity)

    @Query("UPDATE registrations SET status = :newStatus, rejectionReason = :reason WHERE registrationCode = :code")
    suspend fun updateStatus(code: String, newStatus: String, reason: String = "")

    @Query("UPDATE registrations SET isCheckedIn = :isCheckedIn WHERE registrationCode = :code")
    suspend fun updateCheckIn(code: String, isCheckedIn: Boolean)

    @Delete
    suspend fun delete(registration: RegistrationEntity)

    @Query("DELETE FROM registrations WHERE registrationCode = :code")
    suspend fun deleteByCode(code: String)

    @Query("DELETE FROM registrations")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM registrations")
    fun getCount(): Flow<Int>
}

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedules ORDER BY dayNumber ASC, id ASC")
    fun getAll(): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM schedules WHERE dayNumber = :day ORDER BY id ASC")
    fun getByDay(day: Int): Flow<List<ScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ScheduleEntity>)

    @Query("UPDATE schedules SET isFavorite = :isFav WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFav: Boolean)

    @Query("UPDATE schedules SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateCompleted(id: Long, isCompleted: Boolean)
}

@Dao
interface AnnouncementDao {
    @Query("SELECT * FROM announcements ORDER BY id DESC")
    fun getAll(): Flow<List<AnnouncementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<AnnouncementEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AnnouncementEntity)

    @Query("UPDATE announcements SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("DELETE FROM announcements WHERE id = :id")
    suspend fun deleteById(id: Long)
}

@Dao
interface BibleBookmarkDao {
    @Query("SELECT * FROM bible_bookmarks ORDER BY timestamp DESC")
    fun getAll(): Flow<List<BibleBookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: BibleBookmarkEntity)

    @Delete
    suspend fun delete(bookmark: BibleBookmarkEntity)

    @Query("DELETE FROM bible_bookmarks WHERE book = :book AND chapter = :chapter AND verse = :verse")
    suspend fun deleteByVerse(book: String, chapter: Int, verse: Int)
}
