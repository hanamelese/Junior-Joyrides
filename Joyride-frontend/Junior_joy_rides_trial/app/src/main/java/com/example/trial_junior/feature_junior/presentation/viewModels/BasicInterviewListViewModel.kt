package com.example.trial_junior.feature_junior.presentation.viewModels

import androidx.compose.runtime.State
import com.example.trial_junior.core.util.ListStrings
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.domain.model.BasicInterviewItem
import com.example.trial_junior.feature_junior.domain.use_case.BasicInterviewUseCases
import com.example.trial_junior.feature_junior.domain.use_case.BasicInterviewUseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class BasicInterviewListViewModel @Inject constructor(
    private val useCases: BasicInterviewUseCases,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : ListViewModel<BasicInterviewItem>(dispatcher) {

//    override val _state = mutableStateOf(ListState<BasicInterviewItem>())
//    override val state: State<ListState<BasicInterviewItem>> = _state
    override val state: State<ListState<BasicInterviewItem>> = _state

    private var recentlyDeletedItem: BasicInterviewItem? = null

    override suspend fun fetchItems(): ListUseCaseResult<BasicInterviewItem> {
        return try {
            when (val result = useCases.getBasicInterviewItems(showHosted = true, showApproved = true)) {
                is BasicInterviewUseCaseResult.Success -> ListUseCaseResult.Success(result.items)
                is BasicInterviewUseCaseResult.Error -> ListUseCaseResult.Error(result.message)
            }
        } catch (e: Exception) {
            ListUseCaseResult.Error("${ListStrings.CANT_GET_BASIC_INTERVIEWS}: ${e.message}")
        }
    }

    override suspend fun deleteItem(item: BasicInterviewItem) {
        useCases.deleteBasicInterviewItem(item)
    }

    override suspend fun restoreItem(item: BasicInterviewItem) {
        useCases.addBasicInterviewItem(item)
    }

    override suspend fun toggleHosted(item: BasicInterviewItem) {
        useCases.toggleHostedBasicInterviewItem(item)
    }

    override suspend fun toggleApproved(item: BasicInterviewItem) {
        useCases.toggleApprovedBasicInterviewItem(item)
    }

    fun onEvent(event: BasicInterviewEvent) {
        when (event) {
            is BasicInterviewEvent.Delete -> {
                recentlyDeletedItem = event.item
                super.onEvent(ListEvent.DeleteItem(event.item))
            }
            is BasicInterviewEvent.UndoDelete -> {
                recentlyDeletedItem?.let { item ->
                    super.onEvent(ListEvent.RestoreItem(item))
                    recentlyDeletedItem = null
                }
            }
            is BasicInterviewEvent.ToggleHosted -> {
                super.onEvent(ListEvent.ToggleHosted(event.item))
            }
            is BasicInterviewEvent.ToggleApproved -> {
                super.onEvent(ListEvent.ToggleApproved(event.item))
            }
        }
    }
}

sealed class BasicInterviewEvent {
    data class Delete(val item: BasicInterviewItem) : BasicInterviewEvent()
    data class UndoDelete(val item: BasicInterviewItem) : BasicInterviewEvent()
    data class ToggleHosted(val item: BasicInterviewItem) : BasicInterviewEvent()
    data class ToggleApproved(val item: BasicInterviewItem) : BasicInterviewEvent()
}



//package com.example.trial_junior.feature_junior.presentation.invitation_list
//
//
//import com.example.trial_junior.core.util.ListStrings
//import com.example.trial_junior.feature_junior.data.di.IoDispatcher
//import com.example.trial_junior.feature_junior.domain.model.BasicInterviewItem
//import com.example.trial_junior.feature_junior.domain.use_case.BasicInterviewUseCases
//import com.example.trial_junior.feature_junior.domain.use_case.BasicInterviewUseCaseResult
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.CoroutineDispatcher
//import javax.inject.Inject
//
//@HiltViewModel
//class BasicInterviewListViewModel @Inject constructor(
//    private val useCases: BasicInterviewUseCases,
//    @IoDispatcher dispatcher: CoroutineDispatcher
//) : ListViewModel<BasicInterviewItem>(dispatcher) {
//
//    override suspend fun fetchItems(): ListUseCaseResult<BasicInterviewItem> {
//        return try {
//            when (val result = useCases.getBasicInterviewItems(showHosted=true)) {
//                is BasicInterviewUseCaseResult.Success -> ListUseCaseResult.Success(result.items)
//                is BasicInterviewUseCaseResult.Error -> ListUseCaseResult.Error(result.message)
//            }
//        } catch (e: Exception) {
//            ListUseCaseResult.Error("${ListStrings.CANT_GET_BASIC_INTERVIEWS}: ${e.message}")
//        }
//    }
//
//    override suspend fun deleteItem(item: BasicInterviewItem) {
//        useCases.deleteBasicInterviewItem(item)
//    }
//
//    override suspend fun restoreItem(item: BasicInterviewItem) {
//        useCases.addBasicInterviewItem(item)
//    }
//
//    override suspend fun toggleHosted(item: BasicInterviewItem) {
//        useCases.toggleHostedBasicInterviewItem(item)
//    }
//}