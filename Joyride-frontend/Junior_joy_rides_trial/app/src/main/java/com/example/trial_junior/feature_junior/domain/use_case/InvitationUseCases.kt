package com.example.trial_junior.feature_junior.domain.use_case

import com.example.trial_junior.core.util.UseCasesStrings
import com.example.trial_junior.feature_junior.domain.model.InvitationItem
import com.example.trial_junior.feature_junior.domain.repo.InvitationListRepo
import com.example.trial_junior.feature_junior.domain.util.InvalidInvitationItemException
import com.example.trial_junior.feature_junior.domain.util.InvitationItemOrder
import com.example.trial_junior.feature_junior.domain.util.SortingDirection
import javax.inject.Inject

class InvitationUseCases @Inject constructor(
    private val repo: InvitationListRepo
) {
    suspend fun addInvitationItem( invitation: InvitationItem){
        if (invitation.childName.isBlank()|| invitation.guardianEmail.isBlank() || invitation.address.isBlank() || invitation.date.isBlank() || invitation.time < 0 || invitation.specialRequests.isBlank() || invitation.age < 0 || invitation.guardianPhone < 0){
            throw InvalidInvitationItemException(UseCasesStrings.EMPTY_FIELDS)
        }
        repo.addInvitationItem(invitation)
    }

    suspend fun updateInvitationItem(invitation: InvitationItem){
        if (invitation.childName.isBlank()|| invitation.guardianEmail.isBlank() || invitation.address.isBlank() || invitation.date.isBlank() || invitation.time < 0 || invitation.specialRequests.isBlank() || invitation.age < 0 || invitation.guardianPhone < 0){
            throw InvalidInvitationItemException(UseCasesStrings.EMPTY_FIELDS)
        }
        repo.updateInvitationItem(invitation)
    }

    suspend fun deleteInvitationItem(invitation: InvitationItem){
        repo.deleteInvitationItem(invitation)
    }

    suspend fun toggleHostedInvitationItem(invitation: InvitationItem){
        repo.updateInvitationItem(invitation.copy(upcoming = !invitation.upcoming))
    }

    suspend fun toggleApprovedInvitationItem(invitation: InvitationItem){
        repo.updateInvitationItem(invitation.copy(approved = !invitation.approved))
    }

    suspend fun getInvitationItemById(id: Int): InvitationItem? {
        return repo.getSingleInvitationItemById(id)
    }

    suspend fun getInvitationItems(
        invitationItemOrder: InvitationItemOrder = InvitationItemOrder.Time(SortingDirection.Down, true, true)
    ): InvitationUseCaseResult {
        var invitations = repo.getAllInvitationsFromLocalCache()
        if (invitations.isEmpty()) {
            invitations = repo.getAllInvitations()
        }

        val filteredInvitations = invitations.filter { item ->
            // Apply showHosted filter: if showHosted is false, only include upcoming items
            val hostedFilter = if (!invitationItemOrder.showHosted) item.upcoming else true
            // Apply showApproved filter: if showApproved is false, only include non-approved items
            val approvedFilter = if (!invitationItemOrder.showApproved) !item.approved else true
            // Combine filters: item must pass both conditions
            hostedFilter && approvedFilter
        }

        return when (invitationItemOrder.sortingDirection) {
            is SortingDirection.Down -> {
                when (invitationItemOrder) {
                    is InvitationItemOrder.Time -> InvitationUseCaseResult.Success(filteredInvitations.sortedByDescending { it.time })
                }
            }
            is SortingDirection.Up -> {
                when (invitationItemOrder) {
                    is InvitationItemOrder.Time -> InvitationUseCaseResult.Success(filteredInvitations.sortedBy { it.time })
                }
            }
        }
    }

}

sealed class InvitationUseCaseResult {
    data class Success(val invitationItems: List<InvitationItem>) : InvitationUseCaseResult()
    data class Error(val message: String) : InvitationUseCaseResult()
}