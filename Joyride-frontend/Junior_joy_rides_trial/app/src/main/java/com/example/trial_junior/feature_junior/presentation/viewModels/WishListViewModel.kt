package com.example.trial_junior.feature_junior.presentation.viewModels

import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.trial_junior.core.util.ListStrings
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.domain.model.WishListItem
import com.example.trial_junior.feature_junior.domain.use_case.WishListUseCases
import com.example.trial_junior.feature_junior.domain.use_case.WishListUseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val useCases: WishListUseCases,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ListViewModel<WishListItem>(dispatcher) {

    override val state: State<ListState<WishListItem>> = _state

    private var recentlyDeletedItem: WishListItem? = null
    private var eventJob: Job? = null

    override suspend fun fetchItems(): ListUseCaseResult<WishListItem> {
        return try {
            when (val result = useCases.getWishListItems(showHosted = true, showApproved = true)) {
                is WishListUseCaseResult.Success -> ListUseCaseResult.Success(result.items)
                is WishListUseCaseResult.Error -> ListUseCaseResult.Error(result.message)
            }
        } catch (e: Exception) {
            ListUseCaseResult.Error("${ListStrings.CANT_GET_WISH_LISTS}: ${e.message}")
        }
    }

    override suspend fun deleteItem(item: WishListItem) {
        useCases.deleteWishListItem(item)
    }

    override suspend fun restoreItem(item: WishListItem) {
        useCases.addWishListItem(item)
    }

    override suspend fun toggleHosted(item: WishListItem) {
        useCases.toggleHostedWishListItem(item)
    }

    override suspend fun toggleApproved(item: WishListItem) {
        useCases.toggleApprovedWishListItem(item)
    }

    fun onEvent(event: WishListEvent) {
        eventJob?.cancel() // Cancel any ongoing event operation

        eventJob = viewModelScope.launch(dispatcher) {
            when (event) {
                is WishListEvent.Delete -> {
                    recentlyDeletedItem = event.item
                    super@WishListViewModel.onEvent(ListEvent.DeleteItem(event.item))
                }
                is WishListEvent.UndoDelete -> {
                    recentlyDeletedItem?.let { item ->
                        super@WishListViewModel.onEvent(ListEvent.RestoreItem(item))
                        recentlyDeletedItem = null
                    }
                }
                is WishListEvent.ToggleHosted -> {
                    super@WishListViewModel.onEvent(ListEvent.ToggleHosted(event.item))
                }
                is WishListEvent.ToggleApproved -> {
                    super@WishListViewModel.onEvent(ListEvent.ToggleApproved(event.item))
                }
            }
        }
    }
}

sealed class WishListEvent {
    data class Delete(val item: WishListItem) : WishListEvent()
    data class UndoDelete(val item: WishListItem) : WishListEvent()
    data class ToggleHosted(val item: WishListItem) : WishListEvent()
    data class ToggleApproved(val item: WishListItem) : WishListEvent()
}