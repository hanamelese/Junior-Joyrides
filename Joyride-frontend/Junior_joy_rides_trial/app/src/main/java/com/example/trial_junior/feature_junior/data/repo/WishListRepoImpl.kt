package com.example.trial_junior.feature_junior.data.repo

import android.util.Log
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.data.local.WishListDao
import com.example.trial_junior.feature_junior.data.mapper.toLocalWishListItem
import com.example.trial_junior.feature_junior.data.mapper.toLocalWishListItemListFromRemote
import com.example.trial_junior.feature_junior.data.mapper.toRemoteWishListItem
import com.example.trial_junior.feature_junior.data.mapper.toWishListItem
import com.example.trial_junior.feature_junior.data.mapper.toWishListItemListFromLocal
import com.example.trial_junior.feature_junior.data.remote.Api
import com.example.trial_junior.feature_junior.domain.model.WishListItem
import com.example.trial_junior.feature_junior.domain.repo.WishListRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class WishListRepoImpl(
    private val dao: WishListDao,
    private val api: Api,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : WishListRepo {
    override suspend fun getAllWishListItems(): List<WishListItem> {
        getAllWishListItemsFromRemote()
        return dao.getAllWishListItems().toWishListItemListFromLocal()
    }

    override suspend fun getAllWishListItemsFromLocalCache(): List<WishListItem> {
        return dao.getAllWishListItems().toWishListItemListFromLocal()
    }

    override suspend fun getAllWishListItemsFromRemote() {
        return withContext(dispatcher) {
            try {
                refreshRoomCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException, is ConnectException, is HttpException -> {
                        Log.e("HTTP", "Error: No data from Remote")
                        if (isCacheEmpty()) {
                            Log.e("Cache", "Error: No data from local Room cache")
                            throw Exception("Error: Device offline and no data from Room cache")
                        }
                    }
                    else -> throw e
                }
            }
        }
    }

    private suspend fun refreshRoomCache() {
        val remoteItems = api.getAllWishListItems().filterNotNull()
        dao.addAllWishListItems(remoteItems.toLocalWishListItemListFromRemote())
    }

    private fun isCacheEmpty(): Boolean {
        var empty = true
        if (dao.getAllWishListItems().isNotEmpty()) empty = false
        return empty
    }

    override suspend fun getSingleWishListItemById(id: Int): WishListItem? {
        getAllWishListItemsFromRemote()
        return dao.getSingleWishListItemById(id)?.toWishListItem()
    }

    override suspend fun addWishListItem(item: WishListItem) {
        val remoteResponse = api.addWishListItem(item.toRemoteWishListItem())
        if (remoteResponse.isSuccessful) {
            val createdItem = remoteResponse.body()
            if (createdItem != null) {
                val serverId = createdItem.id ?: throw Exception("Server returned a null ID")
                val userId = createdItem.userId ?: throw Exception("Server returned a null userId")
                val localItem = item.copy(id = serverId, userId = userId).toLocalWishListItem(serverId)
                dao.addWishListItem(localItem)
            } else {
                throw Exception("Failed to get created item from server")
            }
        } else {
            throw Exception("Failed to create wish list item on server: ${remoteResponse.message()}")
        }
    }

    override suspend fun updateWishListItem(item: WishListItem) {
        val itemId = item.id ?: throw IllegalArgumentException("Cannot update a wish list item without an ID")
        dao.addWishListItem(item.toLocalWishListItem(itemId))
        api.updateWishListItem(itemId, item.toRemoteWishListItem())
    }

    override suspend fun deleteWishListItem(item: WishListItem) {
        val itemId = item.id ?: throw IllegalArgumentException("Cannot delete a wish list item without an ID")
        dao.deleteWishListItem(item.toLocalWishListItem(itemId))
        try {
            val response = api.deleteWishListItem(itemId)
            if (response.isSuccessful) {
                Log.i("API_DELETE", "Response Success")
            } else {
                Log.i("API_DELETE", "Response Unsuccessful")
                Log.i("API_DELETE", response.message())
            }
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException, is ConnectException, is HttpException -> {
                    Log.e("HTTP", "Error: Could not delete")
                }
                else -> throw e
            }
        }
    }
}