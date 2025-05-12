package com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterview_Update

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.domain.model.SpecialInterviewItem
import com.example.trial_junior.feature_junior.domain.use_case.SpecialInterviewUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecialInterviewNewUpdateViewModel @Inject constructor(
    private val specialInterviewUseCases: SpecialInterviewUseCases,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = mutableStateOf(SpecialInterviewNewUpdateState())
    val state: State<SpecialInterviewNewUpdateState> = _state

    private val errorHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    private var currentSpecialInterviewId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveSpecialInterview : UiEvent()
        object Back : UiEvent()
    }

    init {
        savedStateHandle.get<Int>("specialInterviewId")?.let { id ->
            if (id != -1) {
                viewModelScope.launch(dispatcher + errorHandler) {
                    specialInterviewUseCases.getSpecialInterviewItemById(id)?.also { specialInterview ->
                        currentSpecialInterviewId = id
                        _state.value = _state.value.copy(
                            specialInterview = specialInterview,
                            isLoading = false,
                            isChildNameHintVisible = specialInterview.childName.isBlank(),
                            isGuardianNameHintVisible = specialInterview.guardianName.isBlank(),
                            isGuardianPhoneHintVisible = specialInterview.guardianPhone <= 0,
                            isAgeHintVisible = specialInterview.age <= 0,
                            isGuardianEmailHintVisible = specialInterview.guardianEmail.isBlank(),
                            isSpecialRequestsHintVisible = specialInterview.specialRequests.isBlank(),
                            isVideoUrlHintVisible = specialInterview.videoUrl.isBlank()
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

    fun onEvent(event: SpecialInterviewNewUpdateEvent) {
        when (event) {
            is SpecialInterviewNewUpdateEvent.EnteredChildName -> {
                _state.value = _state.value.copy(
                    specialInterview = _state.value.specialInterview.copy(childName = event.value)
                )
            }
            is SpecialInterviewNewUpdateEvent.ChangedChildNameFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.specialInterview.childName.isBlank()
                _state.value = _state.value.copy(isChildNameHintVisible = shouldHintBeVisible)
            }
            is SpecialInterviewNewUpdateEvent.EnteredGuardianName -> {
                _state.value = _state.value.copy(
                    specialInterview = _state.value.specialInterview.copy(guardianName = event.value)
                )
            }
            is SpecialInterviewNewUpdateEvent.ChangedGuardianNameFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.specialInterview.guardianName.isBlank()
                _state.value = _state.value.copy(isGuardianNameHintVisible = shouldHintBeVisible)
            }
            is SpecialInterviewNewUpdateEvent.EnteredGuardianPhone -> {
                _state.value = _state.value.copy(
                    specialInterview = _state.value.specialInterview.copy(guardianPhone = event.value)
                )
            }
            is SpecialInterviewNewUpdateEvent.ChangedGuardianPhoneFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.specialInterview.guardianPhone <= 0
                _state.value = _state.value.copy(isGuardianPhoneHintVisible = shouldHintBeVisible)
            }
            is SpecialInterviewNewUpdateEvent.EnteredAge -> {
                _state.value = _state.value.copy(
                    specialInterview = _state.value.specialInterview.copy(age = event.value)
                )
            }
            is SpecialInterviewNewUpdateEvent.ChangedAgeFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.specialInterview.age <= 0
                _state.value = _state.value.copy(isAgeHintVisible = shouldHintBeVisible)
            }
            is SpecialInterviewNewUpdateEvent.EnteredGuardianEmail -> {
                _state.value = _state.value.copy(
                    specialInterview = _state.value.specialInterview.copy(guardianEmail = event.value)
                )
            }
            is SpecialInterviewNewUpdateEvent.ChangedGuardianEmailFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.specialInterview.guardianEmail.isBlank()
                _state.value = _state.value.copy(isGuardianEmailHintVisible = shouldHintBeVisible)
            }
            is SpecialInterviewNewUpdateEvent.EnteredSpecialRequests -> {
                _state.value = _state.value.copy(
                    specialInterview = _state.value.specialInterview.copy(specialRequests = event.value)
                )
            }
            is SpecialInterviewNewUpdateEvent.ChangedSpecialRequestsFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.specialInterview.specialRequests.isBlank()
                _state.value = _state.value.copy(isSpecialRequestsHintVisible = shouldHintBeVisible)
            }
            is SpecialInterviewNewUpdateEvent.EnteredVideoUrl -> {
                _state.value = _state.value.copy(
                    specialInterview = _state.value.specialInterview.copy(videoUrl = event.value)
                )
            }
            is SpecialInterviewNewUpdateEvent.ChangedVideoUrlFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.specialInterview.videoUrl.isBlank()
                _state.value = _state.value.copy(isVideoUrlHintVisible = shouldHintBeVisible)
            }
            SpecialInterviewNewUpdateEvent.SaveSpecialInterview -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    try {
                        if (currentSpecialInterviewId != null) {
                            specialInterviewUseCases.updateSpecialInterviewItem(_state.value.specialInterview)
                        } else {
                            specialInterviewUseCases.addSpecialInterviewItem(_state.value.specialInterview.copy(id = null))
                        }
                        _eventFlow.emit(UiEvent.SaveSpecialInterview)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Error saving Special Interview"
                            )
                        )
                    }
                }
            }
            SpecialInterviewNewUpdateEvent.Back -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    _eventFlow.emit(UiEvent.Back)
                }
            }
        }
    }
}

sealed class SpecialInterviewNewUpdateEvent {
    data class EnteredChildName(val value: String) : SpecialInterviewNewUpdateEvent()
    data class ChangedChildNameFocus(val focusState: FocusState) : SpecialInterviewNewUpdateEvent()
    data class EnteredGuardianName(val value: String) : SpecialInterviewNewUpdateEvent()
    data class ChangedGuardianNameFocus(val focusState: FocusState) : SpecialInterviewNewUpdateEvent()
    data class EnteredGuardianPhone(val value: Long) : SpecialInterviewNewUpdateEvent()
    data class ChangedGuardianPhoneFocus(val focusState: FocusState) : SpecialInterviewNewUpdateEvent()
    data class EnteredAge(val value: Int) : SpecialInterviewNewUpdateEvent()
    data class ChangedAgeFocus(val focusState: FocusState) : SpecialInterviewNewUpdateEvent()
    data class EnteredGuardianEmail(val value: String) : SpecialInterviewNewUpdateEvent()
    data class ChangedGuardianEmailFocus(val focusState: FocusState) : SpecialInterviewNewUpdateEvent()
    data class EnteredSpecialRequests(val value: String) : SpecialInterviewNewUpdateEvent()
    data class ChangedSpecialRequestsFocus(val focusState: FocusState) : SpecialInterviewNewUpdateEvent()
    data class EnteredVideoUrl(val value: String) : SpecialInterviewNewUpdateEvent()
    data class ChangedVideoUrlFocus(val focusState: FocusState) : SpecialInterviewNewUpdateEvent()
    object SaveSpecialInterview : SpecialInterviewNewUpdateEvent()
    object Back : SpecialInterviewNewUpdateEvent()
}