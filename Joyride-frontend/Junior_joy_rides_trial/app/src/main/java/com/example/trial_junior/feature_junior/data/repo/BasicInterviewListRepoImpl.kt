package com.example.trial_junior.feature_junior.data.repo

import android.util.Log
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.data.local.BasicInterviewDao
import com.example.trial_junior.feature_junior.data.mapper.toBasicInterviewItem
import com.example.trial_junior.feature_junior.data.mapper.toBasicInterviewItemListFromLocal
import com.example.trial_junior.feature_junior.data.mapper.toLocalBasicInterviewItem
import com.example.trial_junior.feature_junior.data.mapper.toLocalBasicInterviewItemListFromRemote
import com.example.trial_junior.feature_junior.data.mapper.toRemoteBasicInterviewItem
import com.example.trial_junior.feature_junior.data.remote.Api
import com.example.trial_junior.feature_junior.domain.model.BasicInterviewItem
import com.example.trial_junior.feature_junior.domain.repo.BasicInterviewListRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class BasicInterviewListRepoImpl(
    private val dao: BasicInterviewDao,
    private val api: Api,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BasicInterviewListRepo {
    override suspend fun getAllBasicInterviews(): List<BasicInterviewItem> {
        getAllBasicInterviewsFromRemote()
        return dao.getAllBasicInterviewItems().toBasicInterviewItemListFromLocal()
    }

    override suspend fun getAllBasicInterviewsFromLocalCache(): List<BasicInterviewItem> {
        return dao.getAllBasicInterviewItems().toBasicInterviewItemListFromLocal()
    }

    override suspend fun getAllBasicInterviewsFromRemote() {
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
        val remoteItems = api.getAllBasicInterviews().filterNotNull()
        dao.addAllBasicInterviewItems(remoteItems.toLocalBasicInterviewItemListFromRemote())
    }

    private fun isCacheEmpty(): Boolean {
        var empty = true
        if (dao.getAllBasicInterviewItems().isNotEmpty()) empty = false
        return empty
    }

    override suspend fun getSingleBasicInterviewItemById(id: Int): BasicInterviewItem? {
        getAllBasicInterviewsFromRemote()
        return dao.getSingleBasicInterviewItemById(id)?.toBasicInterviewItem()
    }

    override suspend fun addBasicInterviewItem(item: BasicInterviewItem) {
        val remoteResponse = api.addBasicInterview(item.toRemoteBasicInterviewItem())
        if (remoteResponse.isSuccessful) {
            val createdItem = remoteResponse.body()
            if (createdItem != null) {
                val serverId = createdItem.id ?: throw Exception("Server returned a null ID")
                val userId = createdItem.userId ?: throw Exception("Server returned a null userId")
                val localItem = item.copy(id = serverId, userId = userId).toLocalBasicInterviewItem(serverId)
                dao.addBasicInterviewItem(localItem)
            } else {
                throw Exception("Failed to get created item from server")
            }
        } else {
            throw Exception("Failed to create basic interview on server: ${remoteResponse.message()}")
        }
    }

    override suspend fun updateBasicInterviewItem(item: BasicInterviewItem) {
        val itemId = item.id ?: throw IllegalArgumentException("Cannot update a basic interview without an ID")
        dao.addBasicInterviewItem(item.toLocalBasicInterviewItem(itemId))
        api.updateBasicInterviewItem(itemId, item.toRemoteBasicInterviewItem())
    }

    override suspend fun deleteBasicInterviewItem(item: BasicInterviewItem) {
        val itemId = item.id ?: throw IllegalArgumentException("Cannot delete a basic interview without an ID")
        dao.deleteBasicInterviewItem(item.toLocalBasicInterviewItem(itemId))
        try {
            val response = api.deleteBasicInterview(itemId)
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