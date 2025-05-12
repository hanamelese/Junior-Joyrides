package com.example.trial_junior.feature_junior.presentation.viewModels.WishList_Update

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.domain.use_case.WishListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListNewUpdateViewModel @Inject constructor(
    private val wishListUseCases: WishListUseCases,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = mutableStateOf(WishListNewUpdateState())
    val state: State<WishListNewUpdateState> = _state

    private val errorHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    private var currentWishListId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveWishList : UiEvent()
        object Back : UiEvent()
    }

    init {
        savedStateHandle.get<Int>("wishListId")?.let { id ->
            if (id != -1) {
                viewModelScope.launch(dispatcher + errorHandler) {
                    wishListUseCases.getWishListItemById(id)?.also { wishList ->
                        currentWishListId = id
                        _state.value = _state.value.copy(
                            wishlist = wishList,
                            isLoading = false,
                            isChildNameHintVisible = wishList.childName.isBlank(),
                            isAgeHintVisible = wishList.age <= 0,
                            isDateHintVisible = wishList.date.isBlank(),
                            isGuardianEmailHintVisible = wishList.guardianEmail.isBlank(),
                            isImageUrlHintVisible = wishList.imageUrl.isBlank(),
                            isSpecialRequestsHintVisible = wishList.specialRequests.isBlank()
                        )
                    }
                }
            } else {
                _state.value = _state.value.copy(
                    isLoading = false
                )
            }
        }
    }

    fun onEvent(event: WishListNewUpdateEvent) {
        when (event) {
            is WishListNewUpdateEvent.EnteredChildName -> {
                _state.value = _state.value.copy(
                    wishlist = _state.value.wishlist.copy(childName = event.value)
                )
            }
            is WishListNewUpdateEvent.ChangedChildNameFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.wishlist.childName.isBlank()
                _state.value = _state.value.copy(isChildNameHintVisible = shouldHintBeVisible)
            }
            is WishListNewUpdateEvent.EnteredAge -> {
                _state.value = _state.value.copy(
                    wishlist = _state.value.wishlist.copy(age = event.value)
                )
            }
            is WishListNewUpdateEvent.ChangedAgeFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.wishlist.age <= 0
                _state.value = _state.value.copy(isAgeHintVisible = shouldHintBeVisible)
            }
            is WishListNewUpdateEvent.EnteredDate -> {
                _state.value = _state.value.copy(
                    wishlist = _state.value.wishlist.copy(date = event.value)
                )
            }
            is WishListNewUpdateEvent.ChangedDateFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.wishlist.date.isBlank()
                _state.value = _state.value.copy(isDateHintVisible = shouldHintBeVisible)
            }
            is WishListNewUpdateEvent.EnteredGuardianEmail -> {
                _state.value = _state.value.copy(
                    wishlist = _state.value.wishlist.copy(guardianEmail = event.value)
                )
            }
            is WishListNewUpdateEvent.ChangedGuardianEmailFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.wishlist.guardianEmail.isBlank()
                _state.value = _state.value.copy(isGuardianEmailHintVisible = shouldHintBeVisible)
            }
            is WishListNewUpdateEvent.EnteredImageUrl -> {
                _state.value = _state.value.copy(
                    wishlist = _state.value.wishlist.copy(imageUrl = event.value)
                )
            }
            is WishListNewUpdateEvent.ChangedImageUrlFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.wishlist.imageUrl.isBlank()
                _state.value = _state.value.copy(isImageUrlHintVisible = shouldHintBeVisible)
            }
            is WishListNewUpdateEvent.EnteredSpecialRequests -> {
                _state.value = _state.value.copy(
                    wishlist = _state.value.wishlist.copy(specialRequests = event.value)
                )
            }
            is WishListNewUpdateEvent.ChangedSpecialRequestsFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.wishlist.specialRequests.isBlank()
                _state.value = _state.value.copy(isSpecialRequestsHintVisible = shouldHintBeVisible)
            }
            WishListNewUpdateEvent.SaveWishList -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    try {
                        if (currentWishListId != null) {
                            wishListUseCases.updateWishListItem(_state.value.wishlist)
                        } else {
                            wishListUseCases.addWishListItem(_state.value.wishlist.copy(id = null))
                        }
                        _eventFlow.emit(UiEvent.SaveWishList)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Error saving Wishlist"
                            )
                        )
                    }
                }
            }
            WishListNewUpdateEvent.Back -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    _eventFlow.emit(UiEvent.Back)
                }
            }
        }
    }
}

sealed class WishListNewUpdateEvent {
    data class EnteredChildName(val value: String) : WishListNewUpdateEvent()
    data class ChangedChildNameFocus(val focusState: FocusState) : WishListNewUpdateEvent()
    data class EnteredAge(val value: Int) : WishListNewUpdateEvent() // Changed to Int
    data class ChangedAgeFocus(val focusState: FocusState) : WishListNewUpdateEvent()
    data class EnteredDate(val value: String) : WishListNewUpdateEvent()
    data class ChangedDateFocus(val focusState: FocusState) : WishListNewUpdateEvent()
    data class EnteredGuardianEmail(val value: String) : WishListNewUpdateEvent()
    data class ChangedGuardianEmailFocus(val focusState: FocusState) : WishListNewUpdateEvent()
    data class EnteredImageUrl(val value: String) : WishListNewUpdateEvent()
    data class ChangedImageUrlFocus(val focusState: FocusState) : WishListNewUpdateEvent()
    data class EnteredSpecialRequests(val value: String) : WishListNewUpdateEvent()
    data class ChangedSpecialRequestsFocus(val focusState: FocusState) : WishListNewUpdateEvent()
    object SaveWishList : WishListNewUpdateEvent()
    object Back : WishListNewUpdateEvent()
}









