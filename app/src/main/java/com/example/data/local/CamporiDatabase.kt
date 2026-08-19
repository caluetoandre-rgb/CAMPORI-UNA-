package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        RegistrationEntity::class,
        ScheduleEntity::class,
        AnnouncementEntity::class,
        BibleBookmarkEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class CamporiDatabase : RoomDatabase() {
    abstract fun registrationDao(): RegistrationDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun announcementDao(): AnnouncementDao
    abstract fun bibleBookmarkDao(): BibleBookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: CamporiDatabase? = null

        fun getInstance(context: Context): CamporiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CamporiDatabase::class.java,
                    "campori_una.db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
