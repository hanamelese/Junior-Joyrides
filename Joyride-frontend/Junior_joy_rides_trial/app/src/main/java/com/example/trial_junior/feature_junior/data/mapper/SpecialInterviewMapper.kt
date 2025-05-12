package com.example.trial_junior.feature_junior.data.mapper

import com.example.trial_junior.feature_junior.data.local.dto.LocalSpecialInterviewItem
import com.example.trial_junior.feature_junior.data.remote.dto.RemoteSpecialInterviewItem
import com.example.trial_junior.feature_junior.domain.model.SpecialInterviewItem

fun SpecialInterviewItem.toLocalSpecialInterviewItem(id: Int): LocalSpecialInterviewItem {
    return LocalSpecialInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        videoUrl = videoUrl,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = userId
    )
}

fun SpecialInterviewItem.toRemoteSpecialInterviewItem(): RemoteSpecialInterviewItem {
    return RemoteSpecialInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        videoUrl = videoUrl,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = null // userId is set by the server, not sent in the request
    )
}

fun LocalSpecialInterviewItem.toSpecialInterviewItem(): SpecialInterviewItem {
    return SpecialInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        videoUrl = videoUrl,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = userId
    )
}

fun LocalSpecialInterviewItem.toRemoteSpecialInterviewItem(): RemoteSpecialInterviewItem {
    return RemoteSpecialInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        videoUrl = videoUrl,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = null // userId is set by the server, not sent in the request
    )
}

fun RemoteSpecialInterviewItem.toSpecialInterviewItem(): SpecialInterviewItem {
    return SpecialInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        videoUrl = videoUrl,
        upcoming = upcoming,
        approved = approved,
        id = id ?: throw IllegalStateException("Remote special interview missing ID: $this"),
        userId = userId
    )
}

fun RemoteSpecialInterviewItem.toLocalSpecialInterviewItem(): LocalSpecialInterviewItem {
    val serverId = id ?: throw IllegalStateException("Remote special interview missing ID: $this")
    return LocalSpecialInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        videoUrl = videoUrl,
        upcoming = upcoming,
        approved = approved,
        id = serverId,
        userId = userId
    )
}

fun List<SpecialInterviewItem>.toLocalSpecialInterviewItemList(ids: List<Int>): List<LocalSpecialInterviewItem> {
    if (this.size != ids.size) {
        throw IllegalArgumentException("The number of items (${this.size}) must match the number of IDs (${ids.size})")
    }
    return this.mapIndexed { index, item ->
        LocalSpecialInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            videoUrl = item.videoUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = ids[index],
            userId = item.userId
        )
    }
}

fun List<SpecialInterviewItem>.toRemoteSpecialInterviewItemList(): List<RemoteSpecialInterviewItem> {
    return this.map { item ->
        RemoteSpecialInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            videoUrl = item.videoUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id,
            userId = null // userId is set by the server, not sent in the request
        )
    }
}

fun List<LocalSpecialInterviewItem>.toSpecialInterviewItemListFromLocal(): List<SpecialInterviewItem> {
    return this.map { item ->
        SpecialInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            videoUrl = item.videoUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id,
            userId = item.userId
        )
    }
}

fun List<LocalSpecialInterviewItem>.toRemoteSpecialInterviewItemListFromLocal(): List<RemoteSpecialInterviewItem> {
    return this.map { item ->
        RemoteSpecialInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            videoUrl = item.videoUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id,
            userId = null // userId is set by the server, not sent in the request
        )
    }
}

fun List<RemoteSpecialInterviewItem>.toSpecialInterviewItemListFromRemote(): List<SpecialInterviewItem> {
    return this.map { item ->
        SpecialInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            videoUrl = item.videoUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id ?: throw IllegalStateException("Remote special interview missing ID: $item"),
            userId = item.userId
        )
    }
}

fun List<RemoteSpecialInterviewItem>.toLocalSpecialInterviewItemListFromRemote(): List<LocalSpecialInterviewItem> {
    return this.map { item ->
        val serverId = item.id ?: throw IllegalStateException("Remote special interview missing ID: $item")
        LocalSpecialInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            videoUrl = item.videoUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = serverId,
            userId = item.userId
        )
    }
}