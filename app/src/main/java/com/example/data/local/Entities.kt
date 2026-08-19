package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.Announcement
import com.example.data.model.Registration
import com.example.data.model.ScheduleItem

@Entity(tableName = "registrations")
data class RegistrationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fullName: String,
    val clubName: String,
    val churchName: String,
    val mission: String,
    val region: String,
    val role: String,
    val age: Int,
    val phone: String,
    val bloodType: String,
    val emergencyContact: String,
    val registrationCode: String,
    val registrationDate: Long,
    val status: String,
    val isCheckedIn: Boolean = false,
    val rejectionReason: String = ""
) {
    fun toDomain(): Registration = Registration(
        id = id,
        fullName = fullName,
        clubName = clubName,
        churchName = churchName,
        mission = mission,
        region = region,
        role = role,
        age = age,
        phone = phone,
        bloodType = bloodType,
        emergencyContact = emergencyContact,
        registrationCode = registrationCode,
        registrationDate = registrationDate,
        status = status,
        isCheckedIn = isCheckedIn,
        rejectionReason = rejectionReason
    )

    companion object {
        fun fromDomain(r: Registration): RegistrationEntity = RegistrationEntity(
            id = r.id,
            fullName = r.fullName,
            clubName = r.clubName,
            churchName = r.churchName,
            mission = r.mission,
            region = r.region,
            role = r.role,
            age = r.age,
            phone = r.phone,
            bloodType = r.bloodType,
            emergencyContact = r.emergencyContact,
            registrationCode = r.registrationCode,
            registrationDate = r.registrationDate,
            status = r.status,
            isCheckedIn = r.isCheckedIn,
            rejectionReason = r.rejectionReason
        )
    }
}

@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey val id: Long,
    val dayNumber: Int,
    val dateLabel: String,
    val timeLabel: String,
    val title: String,
    val description: String,
    val location: String,
    val category: String,
    val isFavorite: Boolean,
    val isCompleted: Boolean
) {
    fun toDomain(): ScheduleItem = ScheduleItem(
        id = id,
        dayNumber = dayNumber,
        dateLabel = dateLabel,
        timeLabel = timeLabel,
        title = title,
        description = description,
        location = location,
        category = category,
        isFavorite = isFavorite,
        isCompleted = isCompleted
    )

    companion object {
        fun fromDomain(s: ScheduleItem): ScheduleEntity = ScheduleEntity(
            id = s.id,
            dayNumber = s.dayNumber,
            dateLabel = s.dateLabel,
            timeLabel = s.timeLabel,
            title = s.title,
            description = s.description,
            location = s.location,
            category = s.category,
            isFavorite = s.isFavorite,
            isCompleted = s.isCompleted
        )
    }
}

@Entity(tableName = "announcements")
data class AnnouncementEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val summary: String,
    val body: String,
    val dateLabel: String,
    val priority: String,
    val department: String,
    val isRead: Boolean
) {
    fun toDomain(): Announcement = Announcement(
        id = id,
        title = title,
        summary = summary,
        body = body,
        dateLabel = dateLabel,
        priority = priority,
        department = department,
        isRead = isRead
    )

    companion object {
        fun fromDomain(a: Announcement): AnnouncementEntity = AnnouncementEntity(
            id = a.id,
            title = a.title,
            summary = a.summary,
            body = a.body,
            dateLabel = a.dateLabel,
            priority = a.priority,
            department = a.department,
            isRead = a.isRead
        )
    }
}

@Entity(tableName = "bible_bookmarks")
data class BibleBookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val book: String,
    val chapter: Int,
    val verse: Int,
    val text: String,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
