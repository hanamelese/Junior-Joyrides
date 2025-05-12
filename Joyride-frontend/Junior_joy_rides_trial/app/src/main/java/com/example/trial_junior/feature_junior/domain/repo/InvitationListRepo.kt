package com.example.trial_junior.feature_junior.domain.repo

import com.example.trial_junior.feature_junior.domain.model.InvitationItem

interface InvitationListRepo {
    suspend fun getAllInvitations(): List<InvitationItem>
    suspend fun getAllInvitationsFromLocalCache(): List<InvitationItem>
    suspend fun getAllInvitationsFromRemote()
    suspend fun getSingleInvitationItemById(id: Int): InvitationItem?
    suspend fun addInvitationItem(invitation: InvitationItem)
    suspend fun updateInvitationItem(invitation: InvitationItem)
    suspend fun deleteInvitationItem(invitation: InvitationItem)
}
