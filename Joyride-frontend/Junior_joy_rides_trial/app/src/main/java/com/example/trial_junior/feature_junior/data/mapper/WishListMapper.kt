package com.example.trial_junior.feature_junior.data.mapper

import com.example.trial_junior.feature_junior.data.local.dto.LocalWishListItem
import com.example.trial_junior.feature_junior.data.remote.dto.RemoteWishListItem
import com.example.trial_junior.feature_junior.domain.model.WishListItem

fun WishListItem.toLocalWishListItem(id: Int): LocalWishListItem {
    return LocalWishListItem(
        childName = childName,
        guardianEmail = guardianEmail,
        age = age,
        date = date,
        specialRequests = specialRequests,
        imageUrl = imageUrl,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = userId
    )
}

fun WishListItem.toRemoteWishListItem(): RemoteWishListItem {
    return RemoteWishListItem(
        childName = childName,
        guardianEmail = guardianEmail,
        age = age,
        date = date,
        specialRequests = specialRequests,
        imageUrl = imageUrl,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = null // userId is set by the server, not sent in the request
    )
}

fun LocalWishListItem.toWishListItem(): WishListItem {
    return WishListItem(
        childName = childName,
        guardianEmail = guardianEmail,
        age = age,
        date = date,
        specialRequests = specialRequests,
        imageUrl = imageUrl,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = userId
    )
}

fun LocalWishListItem.toRemoteWishListItem(): RemoteWishListItem {
    return RemoteWishListItem(
        childName = childName,
        guardianEmail = guardianEmail,
        age = age,
        date = date,
        specialRequests = specialRequests,
        imageUrl = imageUrl,
        upcoming = upcoming,
        approved = approved,
        id = id,
        userId = null // userId is set by the server, not sent in the request
    )
}

fun RemoteWishListItem.toWishListItem(): WishListItem {
    return WishListItem(
        childName = childName,
        guardianEmail = guardianEmail,
        age = age,
        date = date,
        specialRequests = specialRequests,
        imageUrl = imageUrl,
        upcoming = upcoming,
        approved = approved,
        id = id ?: throw IllegalStateException("Remote wish list item missing ID: $this"),
        userId = userId
    )
}

fun RemoteWishListItem.toLocalWishListItem(): LocalWishListItem {
    val serverId = id ?: throw IllegalStateException("Remote wish list item missing ID: $this")
    return LocalWishListItem(
        childName = childName,
        guardianEmail = guardianEmail,
        age = age,
        date = date,
        specialRequests = specialRequests,
        imageUrl = imageUrl,
        upcoming = upcoming,
        approved = approved,
        id = serverId,
        userId = userId
    )
}

fun List<WishListItem>.toLocalWishListItemList(ids: List<Int>): List<LocalWishListItem> {
    if (this.size != ids.size) {
        throw IllegalArgumentException("The number of items (${this.size}) must match the number of IDs (${ids.size})")
    }
    return this.mapIndexed { index, item ->
        LocalWishListItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            age = item.age,
            date = item.date,
            specialRequests = item.specialRequests,
            imageUrl = item.imageUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = ids[index],
            userId = item.userId
        )
    }
}

fun List<WishListItem>.toRemoteWishListItemList(): List<RemoteWishListItem> {
    return this.map { item ->
        RemoteWishListItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            age = item.age,
            date = item.date,
            specialRequests = item.specialRequests,
            imageUrl = item.imageUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id,
            userId = null // userId is set by the server, not sent in the request
        )
    }
}

fun List<LocalWishListItem>.toWishListItemListFromLocal(): List<WishListItem> {
    return this.map { item ->
        WishListItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            age = item.age,
            date = item.date,
            specialRequests = item.specialRequests,
            imageUrl = item.imageUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id,
            userId = item.userId
        )
    }
}

fun List<LocalWishListItem>.toRemoteWishListItemListFromLocal(): List<RemoteWishListItem> {
    return this.map { item ->
        RemoteWishListItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            age = item.age,
            date = item.date,
            specialRequests = item.specialRequests,
            imageUrl = item.imageUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id,
            userId = null // userId is set by the server, not sent in the request
        )
    }
}

fun List<RemoteWishListItem>.toWishListItemListFromRemote(): List<WishListItem> {
    return this.map { item ->
        WishListItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            age = item.age,
            date = item.date,
            specialRequests = item.specialRequests,
            imageUrl = item.imageUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = item.id ?: throw IllegalStateException("Remote wish list item missing ID: $item"),
            userId = item.userId
        )
    }
}

fun List<RemoteWishListItem>.toLocalWishListItemListFromRemote(): List<LocalWishListItem> {
    return this.map { item ->
        val serverId = item.id ?: throw IllegalStateException("Remote wish list item missing ID: $item")
        LocalWishListItem(
            childName = item.childName,
            guardianEmail = item.guardianEmail,
            age = item.age,
            date = item.date,
            specialRequests = item.specialRequests,
            imageUrl = item.imageUrl,
            upcoming = item.upcoming,
            approved = item.approved,
            id = serverId,
            userId = item.userId
        )
    }
}