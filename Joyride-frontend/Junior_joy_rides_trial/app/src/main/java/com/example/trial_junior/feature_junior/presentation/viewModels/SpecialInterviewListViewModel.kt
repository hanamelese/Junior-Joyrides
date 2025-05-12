package com.example.trial_junior.feature_junior.presentation.viewModels

import androidx.compose.runtime.State
import com.example.trial_junior.core.util.ListStrings
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.domain.model.SpecialInterviewItem
import com.example.trial_junior.feature_junior.domain.use_case.SpecialInterviewUseCases
import com.example.trial_junior.feature_junior.domain.use_case.SpecialInterviewUseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class SpecialInterviewListViewModel @Inject constructor(
    private val useCases: SpecialInterviewUseCases,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : ListViewModel<SpecialInterviewItem>(dispatcher) {

    override val state: State<ListState<SpecialInterviewItem>> = _state

    private var recentlyDeletedItem: SpecialInterviewItem? = null

    override suspend fun fetchItems(): ListUseCaseResult<SpecialInterviewItem> {
        return try {
            when (val result = useCases.getSpecialInterviewItems(showHosted = true, showApproved = true)) {
                is SpecialInterviewUseCaseResult.Success -> ListUseCaseResult.Success(result.items)
                is SpecialInterviewUseCaseResult.Error -> ListUseCaseResult.Error(result.message)
            }
        } catch (e: Exception) {
            ListUseCaseResult.Error("${ListStrings.CANT_GET_SPECIAL_INTERVIEWS}: ${e.message}")
        }
    }

    override suspend fun deleteItem(item: SpecialInterviewItem) {
        useCases.deleteSpecialInterviewItem(item)
    }

    override suspend fun restoreItem(item: SpecialInterviewItem) {
        useCases.addSpecialInterviewItem(item)
    }

    override suspend fun toggleHosted(item: SpecialInterviewItem) {
        useCases.toggleHostedSpecialInterviewItem(item)
    }

    override suspend fun toggleApproved(item: SpecialInterviewItem) {
        useCases.toggleApprovedSpecialInterviewItem(item)
    }

    fun onEvent(event: SpecialInterviewEvent) {
        when (event) {
            is SpecialInterviewEvent.Delete -> {
                recentlyDeletedItem = event.item
                super.onEvent(ListEvent.DeleteItem(event.item))
            }
            is SpecialInterviewEvent.UndoDelete -> {
                recentlyDeletedItem?.let { item ->
                    super.onEvent(ListEvent.RestoreItem(item))
                    recentlyDeletedItem = null
                }
            }
            is SpecialInterviewEvent.ToggleHosted -> {
                super.onEvent(ListEvent.ToggleHosted(event.item))
            }
            is SpecialInterviewEvent.ToggleApproved-> {
                super.onEvent(ListEvent.ToggleApproved(event.item))
            }
        }
    }
}

sealed class SpecialInterviewEvent {
    data class Delete(val item: SpecialInterviewItem) : SpecialInterviewEvent()
    data class UndoDelete(val item: SpecialInterviewItem) : SpecialInterviewEvent()
    data class ToggleHosted(val item: SpecialInterviewItem) : SpecialInterviewEvent()
    data class ToggleApproved(val item: SpecialInterviewItem) : SpecialInterviewEvent()
}