package com.example.trial_junior.feature_junior.domain.repo

import com.example.trial_junior.feature_junior.domain.model.WishListItem

interface WishListRepo {
    suspend fun getAllWishListItems(): List<WishListItem>
    suspend fun getAllWishListItemsFromLocalCache(): List<WishListItem>
    suspend fun getAllWishListItemsFromRemote()
    suspend fun getSingleWishListItemById(id: Int): WishListItem?
    suspend fun addWishListItem(item: WishListItem)
    suspend fun updateWishListItem(item: WishListItem)
    suspend fun deleteWishListItem(item: WishListItem)
}