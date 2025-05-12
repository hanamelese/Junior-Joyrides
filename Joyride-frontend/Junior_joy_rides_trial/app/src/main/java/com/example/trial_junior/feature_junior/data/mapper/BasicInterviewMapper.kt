package com.example.trial_junior.feature_junior.data.mapper

import com.example.trial_junior.feature_junior.data.local.dto.LocalBasicInterviewItem
import com.example.trial_junior.feature_junior.data.remote.dto.RemoteBasicInterviewItem
import com.example.trial_junior.feature_junior.domain.model.BasicInterviewItem

fun BasicInterviewItem.toLocalBasicInterviewItem(id: Int): LocalBasicInterviewItem {
    return LocalBasicInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = userId
    )
}

fun BasicInterviewItem.toRemoteBasicInterviewItem(): RemoteBasicInterviewItem {
    return RemoteBasicInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = null // userId is set by the server, not sent in the request
    )
}

fun LocalBasicInterviewItem.toBasicInterviewItem(): BasicInterviewItem {
    return BasicInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = userId
    )
}

fun LocalBasicInterviewItem.toRemoteBasicInterviewItem(): RemoteBasicInterviewItem {
    return RemoteBasicInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = null // userId is set by the server, not sent in the request
    )
}

fun RemoteBasicInterviewItem.toBasicInterviewItem(): BasicInterviewItem {
    return BasicInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        upcoming = upcoming,
        approved = approved,
        id = id ?: throw IllegalStateException("Remote basic interview missing ID: $this"),
        userId = userId
    )
}

fun RemoteBasicInterviewItem.toLocalBasicInterviewItem(): LocalBasicInterviewItem {
    val serverId = id ?: throw IllegalStateException("Remote basic interview missing ID: $this")
    return LocalBasicInterviewItem(
        childName = childName,
        guardianName = guardianName,
        guardianPhone = guardianPhone,
        age = age,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        upcoming = upcoming,
        approved = approved,
        id = serverId,
        userId = userId
    )
}

fun List<BasicInterviewItem>.toLocalBasicInterviewItemList(ids: List<Int>): List<LocalBasicInterviewItem> {
    if (this.size != ids.size) {
        throw IllegalArgumentException("The number of items (${this.size}) must match the number of IDs (${ids.size})")
    }
    return this.mapIndexed { index, item ->
        LocalBasicInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            upcoming = item.upcoming,
            approved = item.approved,
            id = ids[index],
            userId = item.userId
        )
    }
}

fun List<BasicInterviewItem>.toRemoteBasicInterviewItemList(): List<RemoteBasicInterviewItem> {
    return this.map { item ->
        RemoteBasicInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id,
            userId = null // userId is set by the server, not sent in the request
        )
    }
}

fun List<LocalBasicInterviewItem>.toBasicInterviewItemListFromLocal(): List<BasicInterviewItem> {
    return this.map { item ->
        BasicInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id,
            userId = item.userId
        )
    }
}

fun List<LocalBasicInterviewItem>.toRemoteBasicInterviewItemListFromLocal(): List<RemoteBasicInterviewItem> {
    return this.map { item ->
        RemoteBasicInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id,
            userId = null // userId is set by the server, not sent in the request
        )
    }
}

fun List<RemoteBasicInterviewItem>.toBasicInterviewItemListFromRemote(): List<BasicInterviewItem> {
    return this.map { item ->
        BasicInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id ?: throw IllegalStateException("Remote basic interview missing ID: $item"),
            userId = item.userId
        )
    }
}

fun List<RemoteBasicInterviewItem>.toLocalBasicInterviewItemListFromRemote(): List<LocalBasicInterviewItem> {
    return this.map { item ->
        val serverId = item.id ?: throw IllegalStateException("Remote basic interview missing ID: $item")
        LocalBasicInterviewItem(
            childName = item.childName,
            guardianName = item.guardianName,
            guardianPhone = item.guardianPhone,
            age = item.age,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            upcoming = item.upcoming,
            approved = item.approved,
            id = serverId,
            userId = item.userId
        )
    }
}