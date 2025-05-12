package com.example.trial_junior.feature_junior.presentation.viewModels


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.domain.model.ListItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//@HiltViewModel
abstract class ListViewModel<T : ListItem>  constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    protected open val _state = mutableStateOf(ListState<T>())
    open val state: State<ListState<T>> = _state

    private var getItemsJob: Job? = null

    private val errorHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    abstract suspend fun fetchItems(): ListUseCaseResult<T>
    abstract suspend fun toggleHosted(item: T)
    abstract suspend fun toggleApproved(item: T)

    fun getItems() {
        getItemsJob?.cancel() // Cancel any ongoing fetch operation

        getItemsJob = viewModelScope.launch(dispatcher + errorHandler) {
            _state.value = _state.value.copy(isLoading = true)
            val result = fetchItems()
            _state.value = when (result) {
                is ListUseCaseResult.Success -> {
                    _state.value.copy(
                        items = result.items,
                        isLoading = false,
                        error = null
                    )
                }
                is ListUseCaseResult.Error -> {
                    _state.value.copy(
                        items = emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun onEvent(event: ListEvent<T>) {
        when (event) {
            is ListEvent.DeleteItem -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    deleteItem(event.item)
                    getItems()
                }
            }
            is ListEvent.RestoreItem -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    restoreItem(event.item)
                    getItems()
                }
            }
            is ListEvent.ToggleHosted -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    toggleHosted(event.item)
                    getItems()
                }
            }
            is ListEvent.ToggleApproved -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    toggleApproved(event.item)
                    getItems()
                }
            }
        }
    }

    abstract suspend fun deleteItem(item: T)
    abstract suspend fun restoreItem(item: T)
}

data class ListState<T : ListItem>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class ListEvent<T : ListItem> {
    data class DeleteItem<T : ListItem>(val item: T) : ListEvent<T>()
    data class RestoreItem<T : ListItem>(val item: T) : ListEvent<T>()
    data class ToggleHosted<T : ListItem>(val item: T) : ListEvent<T>()
    data class ToggleApproved<T : ListItem>(val item: T) : ListEvent<T>()
}

sealed class ListUseCaseResult<T : ListItem> {
    data class Success<T : ListItem>(val items: List<T>) : ListUseCaseResult<T>()
    data class Error<T : ListItem>(val message: String) : ListUseCaseResult<T>()
}