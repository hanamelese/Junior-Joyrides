package com.example.trial_junior.feature_junior.data.mapper

import com.example.trial_junior.feature_junior.data.local.dto.LocalInvitationItem
import com.example.trial_junior.feature_junior.data.remote.dto.RemoteInvitationItem
import com.example.trial_junior.feature_junior.domain.model.InvitationItem

fun InvitationItem.toLocalInvitationItem(id: Int): LocalInvitationItem {
    return LocalInvitationItem(
        childName = childName,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        address = address,
        date = date,
        time = time,
        upcoming = upcoming,
        approved = approved,
        guardianPhone = guardianPhone,
        age = age,
        userId = userId,
        id = id
    )
}

fun InvitationItem.toRemoteInvitationItem(): RemoteInvitationItem {
    return RemoteInvitationItem(
        childName = childName,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        address = address,
        date = date,
        time = time,
        upcoming = upcoming,
        approved = approved,
        guardianPhone = guardianPhone,
        age = age,
        userId = null, // Not sent in request; server sets it
        id = id
    )
}

fun LocalInvitationItem.toInvitationItem(): InvitationItem {
    return InvitationItem(
        childName = childName,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        address = address,
        date = date,
        time = time,
        upcoming = upcoming,
        approved = approved,
        guardianPhone = guardianPhone,
        age = age,
        userId = userId,
        id = id
    )
}

fun LocalInvitationItem.toRemoteInvitationItem(): RemoteInvitationItem {
    return RemoteInvitationItem(
        childName = childName,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        address = address,
        date = date,
        time = time,
        upcoming = upcoming,
        approved = approved,
        guardianPhone = guardianPhone,
        age = age,
        userId = userId,
        id = id
    )
}

fun RemoteInvitationItem.toInvitationItem(): InvitationItem {
    return InvitationItem(
        childName = childName,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        address = address,
        date = date,
        time = time,
        upcoming = upcoming,
        approved = approved,
        guardianPhone = guardianPhone,
        age = age,
        userId = userId ?: throw IllegalStateException("userId is null in RemoteInvitationItem"),
        id = id
    )
}

fun RemoteInvitationItem.toLocalInvitationItem(): LocalInvitationItem {
    val serverId = id ?: throw IllegalStateException("Remote invitation missing ID: $this")
    val userId = userId ?: throw IllegalStateException("Remote invitation missing userId: $this")
    return LocalInvitationItem(
        childName = childName,
        guardianEmail = guardianEmail,
        specialRequests = specialRequests,
        address = address,
        date = date,
        time = time,
        upcoming = upcoming,
        approved = approved,
        guardianPhone = guardianPhone,
        age = age,
        userId = userId,
        id = serverId
    )
}

fun List<InvitationItem>.toLocalInvitationItemList(ids: List<Int>): List<LocalInvitationItem> {
    if (this.size != ids.size) {
        throw IllegalArgumentException("The number of items (${this.size}) must match the number of IDs (${ids.size})")
    }
    return this.mapIndexed { index, item ->
        LocalInvitationItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            address = item.address,
            date = item.date,
            time = item.time,
            upcoming = item.upcoming,
            approved = item.approved,
            guardianPhone = item.guardianPhone,
            age = item.age,
            userId = item.userId,
            id = ids[index]
        )
    }
}

fun List<InvitationItem>.toRemoteInvitationItemList(): List<RemoteInvitationItem> {
    return this.map { item ->
        RemoteInvitationItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            address = item.address,
            date = item.date,
            time = item.time,
            upcoming = item.upcoming,
            approved = item.approved,
            guardianPhone = item.guardianPhone,
            age = item.age,
            userId = null, // Not sent in request; server sets it
            id = item.id
        )
    }
}

fun List<LocalInvitationItem>.toInvitationItemListFromLocal(): List<InvitationItem> {
    return this.map { item ->
        InvitationItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            address = item.address,
            date = item.date,
            time = item.time,
            upcoming = item.upcoming,
            approved = item.approved,
            guardianPhone = item.guardianPhone,
            age = item.age,
            userId = item.userId,
            id = item.id
        )
    }
}

fun List<LocalInvitationItem>.toRemoteInvitationItemListFromLocal(): List<RemoteInvitationItem> {
    return this.map { item ->
        RemoteInvitationItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            address = item.address,
            date = item.date,
            time = item.time,
            upcoming = item.upcoming,
            approved = item.approved,
            guardianPhone = item.guardianPhone,
            age = item.age,
            userId = item.userId,
            id = item.id
        )
    }
}

fun List<RemoteInvitationItem>.toInvitationItemListFromRemote(): List<InvitationItem> {
    return this.map { item ->
        InvitationItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            address = item.address,
            date = item.date,
            time = item.time,
            upcoming = item.upcoming,
            approved = item.approved,
            guardianPhone = item.guardianPhone,
            age = item.age,
            userId = item.userId ?: throw IllegalStateException("userId is null in RemoteInvitationItem"),
            id = item.id
        )
    }
}

fun List<RemoteInvitationItem>.toLocalInvitationItemListFromRemote(): List<LocalInvitationItem> {
    return this.map { item ->
        val serverId = item.id ?: throw IllegalStateException("Remote invitation missing ID: $item")
        val userId = item.userId ?: throw IllegalStateException("Remote invitation missing userId: $item")
        LocalInvitationItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            specialRequests = item.specialRequests,
            address = item.address,
            date = item.date,
            time = item.time,
            upcoming = item.upcoming,
            approved = item.approved,
            guardianPhone = item.guardianPhone,
            age = item.age,
            userId = userId,
            id = serverId
        )
    }
}