package com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterview_Update

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.domain.model.BasicInterviewItem
import com.example.trial_junior.feature_junior.domain.use_case.BasicInterviewUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasicInterviewNewUpdateViewModel @Inject constructor(
    private val basicInterviewUseCases: BasicInterviewUseCases,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = mutableStateOf(BasicInterviewNewUpdateState())
    val state: State<BasicInterviewNewUpdateState> = _state

    private val errorHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    private var currentBasicInterviewId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveBasicInterview : UiEvent()
        object Back : UiEvent()
    }


    init {
        savedStateHandle.get<Int>("basicInterviewId")?.let { id ->
            if (id != -1) {
                println("Fetching BasicInterview with id: $id") // Debug log
                viewModelScope.launch(dispatcher + errorHandler) {
                    basicInterviewUseCases.getBasicInterviewItemById(id)?.also { basicInterview ->
                        currentBasicInterviewId = id
                        println("Fetched BasicInterview: $basicInterview") // Debug log
                        _state.value = _state.value.copy(
                            basicInterview = basicInterview,
                            isLoading = false,
                            isChildNameHintVisible = basicInterview.childName.isBlank(),
                            isGuardianNameHintVisible = basicInterview.guardianName.isBlank(),
                            isGuardianPhoneHintVisible = basicInterview.guardianPhone <= 0,
                            isAgeHintVisible = basicInterview.age <= 0,
                            isGuardianEmailHintVisible = basicInterview.guardianEmail.isBlank(),
                            isSpecialRequestsHintVisible = basicInterview.specialRequests.isBlank()
                        )
                    } ?: run {
                        println("No BasicInterview found for id: $id") // Debug log
                        _state.value = _state.value.copy(isLoading = false)
                    }
                }
            } else {
                println("basicInterviewId is -1, creating new entry") // Debug log
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

//    init {
//        savedStateHandle.get<Int>("basicInterviewId")?.let { id ->
//            if (id != -1) {
//                viewModelScope.launch(dispatcher + errorHandler) {
//                    basicInterviewUseCases.getBasicInterviewItemById(id)?.also { basicInterview ->
//                        currentBasicInterviewId = id
//                        _state.value = _state.value.copy(
//                            basicInterview = basicInterview,
//                            isLoading = false,
//                            isChildNameHintVisible = basicInterview.childName.isBlank(),
//                            isGuardianNameHintVisible = basicInterview.guardianName.isBlank(),
//                            isGuardianPhoneHintVisible = basicInterview.guardianPhone <= 0,
//                            isAgeHintVisible = basicInterview.age <= 0,
//                            isGuardianEmailHintVisible = basicInterview.guardianEmail.isBlank(),
//                            isSpecialRequestsHintVisible = basicInterview.specialRequests.isBlank()
//                        )
//                    }
//                }
//            } else {
//                _state.value = _state.value.copy(
//                    isLoading = false
//                )
//            }
//        }
//    }

    fun onEvent(event: BasicInterviewNewUpdateEvent) {
        when (event) {
            is BasicInterviewNewUpdateEvent.EnteredChildName -> {
                _state.value = _state.value.copy(
                    basicInterview = _state.value.basicInterview.copy(childName = event.value)
                )
            }
            is BasicInterviewNewUpdateEvent.ChangedChildNameFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.basicInterview.childName.isBlank()
                _state.value = _state.value.copy(isChildNameHintVisible = shouldHintBeVisible)
            }
            is BasicInterviewNewUpdateEvent.EnteredGuardianName -> {
                _state.value = _state.value.copy(
                    basicInterview = _state.value.basicInterview.copy(guardianName = event.value)
                )
            }
            is BasicInterviewNewUpdateEvent.ChangedGuardianNameFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.basicInterview.guardianName.isBlank()
                _state.value = _state.value.copy(isGuardianNameHintVisible = shouldHintBeVisible)
            }
            is BasicInterviewNewUpdateEvent.EnteredGuardianPhone -> {
                _state.value = _state.value.copy(
                    basicInterview = _state.value.basicInterview.copy(guardianPhone = event.value)
                )
            }
            is BasicInterviewNewUpdateEvent.ChangedGuardianPhoneFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.basicInterview.guardianPhone <= 0
                _state.value = _state.value.copy(isGuardianPhoneHintVisible = shouldHintBeVisible)
            }
            is BasicInterviewNewUpdateEvent.EnteredAge -> {
                _state.value = _state.value.copy(
                    basicInterview = _state.value.basicInterview.copy(age = event.value)
                )
            }
            is BasicInterviewNewUpdateEvent.ChangedAgeFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.basicInterview.age <= 0
                _state.value = _state.value.copy(isAgeHintVisible = shouldHintBeVisible)
            }
            is BasicInterviewNewUpdateEvent.EnteredGuardianEmail -> {
                _state.value = _state.value.copy(
                    basicInterview = _state.value.basicInterview.copy(guardianEmail = event.value)
                )
            }
            is BasicInterviewNewUpdateEvent.ChangedGuardianEmailFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.basicInterview.guardianEmail.isBlank()
                _state.value = _state.value.copy(isGuardianEmailHintVisible = shouldHintBeVisible)
            }
            is BasicInterviewNewUpdateEvent.EnteredSpecialRequests -> {
                _state.value = _state.value.copy(
                    basicInterview = _state.value.basicInterview.copy(specialRequests = event.value)
                )
            }
            is BasicInterviewNewUpdateEvent.ChangedSpecialRequestsFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.basicInterview.specialRequests.isBlank()
                _state.value = _state.value.copy(isSpecialRequestsHintVisible = shouldHintBeVisible)
            }
            BasicInterviewNewUpdateEvent.SaveBasicInterview -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    try {
                        if (currentBasicInterviewId != null) {
                            basicInterviewUseCases.updateBasicInterviewItem(_state.value.basicInterview)
                        } else {
                            basicInterviewUseCases.addBasicInterviewItem(_state.value.basicInterview.copy(id = null))
                        }
                        _eventFlow.emit(UiEvent.SaveBasicInterview)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Error saving Basic Interview"
                            )
                        )
                    }
                }
            }
            BasicInterviewNewUpdateEvent.Back -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    _eventFlow.emit(UiEvent.Back)
                }
            }
        }
    }
}

// Event (kept in the same file for now, but can be separated if needed)
sealed class BasicInterviewNewUpdateEvent {
    data class EnteredChildName(val value: String) : BasicInterviewNewUpdateEvent()
    data class ChangedChildNameFocus(val focusState: FocusState) : BasicInterviewNewUpdateEvent()
    data class EnteredGuardianName(val value: String) : BasicInterviewNewUpdateEvent()
    data class ChangedGuardianNameFocus(val focusState: FocusState) : BasicInterviewNewUpdateEvent()
    data class EnteredGuardianPhone(val value: Long) : BasicInterviewNewUpdateEvent()
    data class ChangedGuardianPhoneFocus(val focusState: FocusState) : BasicInterviewNewUpdateEvent()
    data class EnteredAge(val value: Int) : BasicInterviewNewUpdateEvent()
    data class ChangedAgeFocus(val focusState: FocusState) : BasicInterviewNewUpdateEvent()
    data class EnteredGuardianEmail(val value: String) : BasicInterviewNewUpdateEvent()
    data class ChangedGuardianEmailFocus(val focusState: FocusState) : BasicInterviewNewUpdateEvent()
    data class EnteredSpecialRequests(val value: String) : BasicInterviewNewUpdateEvent()
    data class ChangedSpecialRequestsFocus(val focusState: FocusState) : BasicInterviewNewUpdateEvent()
    object SaveBasicInterview : BasicInterviewNewUpdateEvent()
    object Back : BasicInterviewNewUpdateEvent()
}