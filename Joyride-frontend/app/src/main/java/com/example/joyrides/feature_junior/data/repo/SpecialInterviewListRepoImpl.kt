package com.example.trial_junior.feature_junior.data.repo

import android.util.Log
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.data.local.SpecialInterviewDao
import com.example.trial_junior.feature_junior.data.mapper.toLocalSpecialInterviewItem
import com.example.trial_junior.feature_junior.data.mapper.toLocalSpecialInterviewItemListFromRemote
import com.example.trial_junior.feature_junior.data.mapper.toRemoteSpecialInterviewItem
import com.example.trial_junior.feature_junior.data.mapper.toSpecialInterviewItem
import com.example.trial_junior.feature_junior.data.mapper.toSpecialInterviewItemListFromLocal
import com.example.trial_junior.feature_junior.data.remote.Api
import com.example.trial_junior.feature_junior.domain.model.SpecialInterviewItem
import com.example.trial_junior.feature_junior.domain.repo.SpecialInterviewListRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class SpecialInterviewListRepoImpl(
    private val dao: SpecialInterviewDao,
    private val api: Api,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SpecialInterviewListRepo {
    override suspend fun getAllSpecialInterviews(): List<SpecialInterviewItem> {
        getAllSpecialInterviewsFromRemote()
        return dao.getAllSpecialInterviewItems().toSpecialInterviewItemListFromLocal()
    }

    override suspend fun getAllSpecialInterviewsFromLocalCache(): List<SpecialInterviewItem> {
        return dao.getAllSpecialInterviewItems().toSpecialInterviewItemListFromLocal()
    }

    override suspend fun getAllSpecialInterviewsFromRemote() {
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
        val remoteItems = api.getAllSpecialInterviews().filterNotNull()
        dao.addAllSpecialInterviewItems(remoteItems.toLocalSpecialInterviewItemListFromRemote())
    }

    private fun isCacheEmpty(): Boolean {
        var empty = true
        if (dao.getAllSpecialInterviewItems().isNotEmpty()) empty = false
        return empty
    }

    override suspend fun getSingleSpecialInterviewItemById(id: Int): SpecialInterviewItem? {
        getAllSpecialInterviewsFromRemote()
        return dao.getSingleSpecialInterviewItemById(id)?.toSpecialInterviewItem()
    }

    override suspend fun addSpecialInterviewItem(item: SpecialInterviewItem) {
        val remoteResponse = api.addSpecialInterview(item.toRemoteSpecialInterviewItem())
        if (remoteResponse.isSuccessful) {
            val createdItem = remoteResponse.body()
            if (createdItem != null) {
                val serverId = createdItem.id ?: throw Exception("Server returned a null ID")
                val userId = createdItem.userId ?: throw Exception("Server returned a null userId")
                val localItem = item.copy(id = serverId, userId = userId).toLocalSpecialInterviewItem(serverId)
                dao.addSpecialInterviewItem(localItem)
            } else {
                throw Exception("Failed to get created item from server")
            }
        } else {
            throw Exception("Failed to create special interview on server: ${remoteResponse.message()}")
        }
    }

    override suspend fun updateSpecialInterviewItem(item: SpecialInterviewItem) {
        val itemId = item.id ?: throw IllegalArgumentException("Cannot update a special interview without an ID")
        dao.addSpecialInterviewItem(item.toLocalSpecialInterviewItem(itemId))
        api.updateSpecialInterviewItem(itemId, item.toRemoteSpecialInterviewItem())
    }

    override suspend fun deleteSpecialInterviewItem(item: SpecialInterviewItem) {
        val itemId = item.id ?: throw IllegalArgumentException("Cannot delete a special interview without an ID")
        dao.deleteSpecialInterviewItem(item.toLocalSpecialInterviewItem(itemId))
        try {
            val response = api.deleteSpecialInterview(itemId)
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