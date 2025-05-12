package com.example.trial_junior.feature_junior.data.repo

import android.util.Log
import com.example.trial_junior.feature_junior.data.local.InvitationDao
import com.example.trial_junior.feature_junior.data.remote.Api
import com.example.trial_junior.feature_junior.data.remote.dto.RemoteInvitationItem
import com.example.trial_junior.feature_junior.domain.model.InvitationItem
import com.example.trial_junior.feature_junior.domain.repo.InvitationListRepo
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.data.mapper.toInvitationItem
import com.example.trial_junior.feature_junior.data.mapper.toInvitationItemListFromLocal
import com.example.trial_junior.feature_junior.data.mapper.toLocalInvitationItem
import com.example.trial_junior.feature_junior.data.mapper.toLocalInvitationItemListFromRemote
import com.example.trial_junior.feature_junior.data.mapper.toRemoteInvitationItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class InvitationListRepoImpl @Inject constructor(
    private val dao: InvitationDao,
    private val api: Api,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : InvitationListRepo {
    override suspend fun getAllInvitations(): List<InvitationItem> {
        getAllInvitationsFromRemote()
        return dao.getAllInvitationItems().toInvitationItemListFromLocal()
    }

    override suspend fun getAllInvitationsFromLocalCache(): List<InvitationItem> {
        return dao.getAllInvitationItems().toInvitationItemListFromLocal()
    }

    override suspend fun getAllInvitationsFromRemote() {
        withContext(dispatcher) {
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
        val remoteInvitations = api.getAllInvitations().filterNotNull()
        dao.addAllInvitationItems(remoteInvitations.toLocalInvitationItemListFromRemote())
    }

    private fun isCacheEmpty(): Boolean {
        return dao.getAllInvitationItems().isEmpty()
    }

    override suspend fun getSingleInvitationItemById(id: Int): InvitationItem? {
        getAllInvitationsFromRemote()
        return dao.getSingleInvitationItemById(id)?.toInvitationItem()
    }

    override suspend fun addInvitationItem(invitation: InvitationItem) {
        val remoteItem = invitation.toRemoteInvitationItem()
        val remoteResponse = api.addInvitation(remoteItem)
        if (remoteResponse.isSuccessful) {
            val createdItem = remoteResponse.body()
            if (createdItem != null) {
                val serverId = createdItem.id ?: throw Exception("Server returned a null ID")
                val userId = createdItem.userId ?: throw Exception("Server returned a null userId")
                val localItem = invitation.toLocalInvitationItem(serverId).copy(userId = userId)
                dao.addInvitationItem(localItem)
            } else {
                throw Exception("Failed to get created item from server")
            }
        } else {
            throw Exception("Failed to create invitation on server: ${remoteResponse.message()}")
        }
    }

    override suspend fun updateInvitationItem(invitation: InvitationItem) {
        val invitationId = invitation.id ?: throw IllegalArgumentException("Cannot update an invitation without an ID")
        dao.addInvitationItem(invitation.toLocalInvitationItem(invitationId))
        api.updateInvitationItem(invitationId, invitation.toRemoteInvitationItem())
    }

    override suspend fun deleteInvitationItem(invitation: InvitationItem) {
        val invitationId = invitation.id ?: throw IllegalArgumentException("Cannot update an invitation without an ID")
        dao.deleteInvitationItem(invitation.toLocalInvitationItem(invitationId))
        try {
            val response = api.deleteInvitation(invitationId)
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