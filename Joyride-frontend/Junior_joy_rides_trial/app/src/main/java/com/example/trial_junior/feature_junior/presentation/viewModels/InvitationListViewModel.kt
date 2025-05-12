package com.example.trial_junior.feature_junior.presentation.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.trial_junior.core.util.ListStrings
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.domain.model.InvitationItem
import com.example.trial_junior.feature_junior.domain.use_case.InvitationUseCaseResult
import com.example.trial_junior.feature_junior.domain.use_case.InvitationUseCases
import com.example.trial_junior.feature_junior.domain.util.InvitationItemOrder
import com.example.trial_junior.feature_junior.domain.util.SortingDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class InvitationListViewModel @Inject constructor(
    private val invitationUseCases: InvitationUseCases,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : ListViewModel<InvitationItem>(dispatcher) {

    private val _invitationItemOrder = mutableStateOf<InvitationItemOrder>(
        InvitationItemOrder.Time(SortingDirection.Down, showHosted = true, showApproved = true)
    )
    val invitationItemOrder: State<InvitationItemOrder> = _invitationItemOrder

    override val state: State<ListState<InvitationItem>> = _state

    private var recentlyDeletedItem: InvitationItem? = null

    override suspend fun fetchItems(): ListUseCaseResult<InvitationItem> {
        return try {
            when (val result = invitationUseCases.getInvitationItems(
                invitationItemOrder = _invitationItemOrder.value
            )) {
                is InvitationUseCaseResult.Success -> ListUseCaseResult.Success(result.invitationItems)
                is InvitationUseCaseResult.Error -> ListUseCaseResult.Error(result.message)
            }
        } catch (e: Exception) {
            ListUseCaseResult.Error("${ListStrings.CANT_GET_INVITATIONS}: ${e.message}")
        }
    }

    override suspend fun deleteItem(item: InvitationItem) {
        invitationUseCases.deleteInvitationItem(item)
    }

    override suspend fun restoreItem(item: InvitationItem) {
        invitationUseCases.addInvitationItem(item)
    }

    override suspend fun toggleHosted(item: InvitationItem) {
        invitationUseCases.toggleHostedInvitationItem(item)
    }

    override suspend fun toggleApproved(item: InvitationItem) {
        invitationUseCases.toggleApprovedInvitationItem(item)
    }

    fun onEvent(event: InvitationListEvent) {
        when (event) {
            is InvitationListEvent.Sort -> {
                val stateOrderAlreadyMatchesEventOrder =
                    _invitationItemOrder.value.sortingDirection == event.invitationItemOrder.sortingDirection &&
                            _invitationItemOrder.value.showHosted == event.invitationItemOrder.showHosted
                if (stateOrderAlreadyMatchesEventOrder) {
                    return
                }
                _invitationItemOrder.value = event.invitationItemOrder
                getItems()
            }
            is InvitationListEvent.Delete -> {
                recentlyDeletedItem = event.invitation
                super.onEvent(ListEvent.DeleteItem(event.invitation))
            }
            is InvitationListEvent.UndoDelete -> {
                recentlyDeletedItem?.let { item ->
                    super.onEvent(ListEvent.RestoreItem(item))
                    recentlyDeletedItem = null
                }
            }
            is InvitationListEvent.ToggleHosted -> {
                super.onEvent(ListEvent.ToggleHosted(event.invitation))
            }
            is InvitationListEvent.ToggleApproved -> {
                super.onEvent(ListEvent.ToggleHosted(event.invitation))
            }
        }
    }
}

sealed class InvitationListEvent {
    data class Sort(val invitationItemOrder: InvitationItemOrder) : InvitationListEvent()
    data class Delete(val invitation: InvitationItem) : InvitationListEvent()
    data class UndoDelete(val invitation: InvitationItem) : InvitationListEvent()
    data class ToggleHosted(val invitation: InvitationItem) : InvitationListEvent()
    data class ToggleApproved(val invitation: InvitationItem) : InvitationListEvent()
}

