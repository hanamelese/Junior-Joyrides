package com.example.trial_junior.feature_junior.presentation.viewModels.Invitation_Update

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trial_junior.feature_junior.data.di.IoDispatcher
import com.example.trial_junior.feature_junior.domain.model.InvitationItem
import com.example.trial_junior.feature_junior.domain.use_case.InvitationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitationNewUpdateViewModel @Inject constructor(
    private val invitationUseCases: InvitationUseCases,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = mutableStateOf(InvitationNewUpdateState())
    val state: State<InvitationNewUpdateState> = _state

    private val errorHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    private var currentInvitationId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveInvitation : UiEvent()
        object Back : UiEvent()
    }

    init {
        val id = savedStateHandle.get<Int>("invitationId")
        println("InvitationNewUpdateViewModel: Retrieved invitationId = $id") // Log the initial ID retrieval
        id?.let { invitationId ->
            if (invitationId != -1) {
                println("InvitationNewUpdateViewModel: Fetching Invitation with id: $invitationId") // Log fetch start
                viewModelScope.launch(dispatcher + errorHandler) {
                    val invitation = invitationUseCases.getInvitationItemById(invitationId)
                    println("InvitationNewUpdateViewModel: Fetched Invitation result = $invitation") // Log fetch result
                    invitation?.also { fetchedInvitation ->
                        currentInvitationId = invitationId
                        println("InvitationNewUpdateViewModel: Successfully fetched Invitation: $fetchedInvitation") // Log success
                        _state.value = _state.value.copy(
                            invitation = fetchedInvitation,
                            isLoading = false,
                            isChildNameHintVisible = fetchedInvitation.childName.isBlank(),
                            isGuardianEmailHintVisible = fetchedInvitation.guardianEmail.isBlank(),
                            isSpecialRequestsHintVisible = fetchedInvitation.specialRequests.isBlank(),
                            isAddressHintVisible = fetchedInvitation.address.isBlank(),
                            isDateHintVisible = fetchedInvitation.date.isBlank(),
                            isTimeHintVisible = fetchedInvitation.time <= 0,
                            isGuardianPhoneHintVisible = fetchedInvitation.guardianPhone <= 0,
                            isAgeHintVisible = fetchedInvitation.age <= 0
                        )
                    } ?: run {
                        println("InvitationNewUpdateViewModel: No Invitation found for id: $invitationId") // Log not found
                        _state.value = _state.value.copy(
                            error = "Invitation not found for ID $invitationId",
                            isLoading = false
                        )
                    }
                }
            } else {
                println("InvitationNewUpdateViewModel: invitationId is -1, creating new entry") // Log new entry
                _state.value = _state.value.copy(isLoading = false)
            }
        } ?: run {
            println("InvitationNewUpdateViewModel: invitationId is null") // Log null case
            _state.value = _state.value.copy(
                error = "Invalid invitation ID",
                isLoading = false
            )
        }
    }

//    init {
//        savedStateHandle.get<Int>("invitationId")?.let { id ->
//            if (id != -1) {
//                viewModelScope.launch(dispatcher + errorHandler) {
//                    invitationUseCases.getInvitationItemById(id)?.also { invitation ->
//                        currentInvitationId = id
//                        _state.value = _state.value.copy(
//                            invitation = invitation,
//                            isLoading = false,
//                            isChildNameHintVisible = invitation.childName.isBlank(),
//                            isGuardianEmailHintVisible = invitation.guardianEmail.isBlank(),
//                            isSpecialRequestsHintVisible = invitation.specialRequests.isBlank(),
//                            isAddressHintVisible = invitation.address.isBlank(),
//                            isDateHintVisible = invitation.date.isBlank(),
//                            isTimeHintVisible = invitation.time <= 0,
//                            isGuardianPhoneHintVisible = invitation.guardianPhone <= 0,
//                            isAgeHintVisible = invitation.age <= 0
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

    fun onEvent(event: InvitationNewUpdateEvent) {
        when (event) {
            is InvitationNewUpdateEvent.EnteredChildName -> {
                _state.value = _state.value.copy(
                    invitation = _state.value.invitation.copy(childName = event.value)
                )
            }
            is InvitationNewUpdateEvent.ChangedChildNameFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.invitation.childName.isBlank()
                _state.value = _state.value.copy(isChildNameHintVisible = shouldHintBeVisible)
            }
            is InvitationNewUpdateEvent.EnteredGuardianEmail -> {
                _state.value = _state.value.copy(
                    invitation = _state.value.invitation.copy(guardianEmail = event.value)
                )
            }
            is InvitationNewUpdateEvent.ChangedGuardianEmailFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.invitation.guardianEmail.isBlank()
                _state.value = _state.value.copy(isGuardianEmailHintVisible = shouldHintBeVisible)
            }
            is InvitationNewUpdateEvent.EnteredSpecialRequests -> {
                _state.value = _state.value.copy(
                    invitation = _state.value.invitation.copy(specialRequests = event.value)
                )
            }
            is InvitationNewUpdateEvent.ChangedSpecialRequestsFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.invitation.specialRequests.isBlank()
                _state.value = _state.value.copy(isSpecialRequestsHintVisible = shouldHintBeVisible)
            }
            is InvitationNewUpdateEvent.EnteredAddress -> {
                _state.value = _state.value.copy(
                    invitation = _state.value.invitation.copy(address = event.value)
                )
            }
            is InvitationNewUpdateEvent.ChangedAddressFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.invitation.address.isBlank()
                _state.value = _state.value.copy(isAddressHintVisible = shouldHintBeVisible)
            }
            is InvitationNewUpdateEvent.EnteredDate -> {
                _state.value = _state.value.copy(
                    invitation = _state.value.invitation.copy(date = event.value)
                )
            }
            is InvitationNewUpdateEvent.ChangedDateFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.invitation.date.isBlank()
                _state.value = _state.value.copy(isDateHintVisible = shouldHintBeVisible)
            }
            is InvitationNewUpdateEvent.EnteredTime -> {
                _state.value = _state.value.copy(
                    invitation = _state.value.invitation.copy(time = event.value)
                )
            }
            is InvitationNewUpdateEvent.ChangedTimeFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.invitation.time <= 0
                _state.value = _state.value.copy(isTimeHintVisible = shouldHintBeVisible)
            }
            is InvitationNewUpdateEvent.EnteredGuardianPhone -> {
                _state.value = _state.value.copy(
                    invitation = _state.value.invitation.copy(guardianPhone = event.value)
                )
            }
            is InvitationNewUpdateEvent.ChangedGuardianPhoneFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.invitation.guardianPhone <= 0
                _state.value = _state.value.copy(isGuardianPhoneHintVisible = shouldHintBeVisible)
            }
            is InvitationNewUpdateEvent.EnteredAge -> {
                _state.value = _state.value.copy(
                    invitation = _state.value.invitation.copy(age = event.value)
                )
            }
            is InvitationNewUpdateEvent.ChangedAgeFocus -> {
                val shouldHintBeVisible = !event.focusState.isFocused && _state.value.invitation.age <= 0
                _state.value = _state.value.copy(isAgeHintVisible = shouldHintBeVisible)
            }
            InvitationNewUpdateEvent.SaveInvitation -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    try {
                        if (currentInvitationId != null) {
                            invitationUseCases.updateInvitationItem(_state.value.invitation)
                        } else {
                            invitationUseCases.addInvitationItem(_state.value.invitation.copy(id = null))
                        }
                        _eventFlow.emit(UiEvent.SaveInvitation)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Error saving Invitation"
                            )
                        )
                    }
                }
            }
            InvitationNewUpdateEvent.Back -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    _eventFlow.emit(UiEvent.Back)
                }
            }
        }
    }
}

sealed class InvitationNewUpdateEvent {
    data class EnteredChildName(val value: String) : InvitationNewUpdateEvent()
    data class ChangedChildNameFocus(val focusState: FocusState) : InvitationNewUpdateEvent()
    data class EnteredGuardianEmail(val value: String) : InvitationNewUpdateEvent()
    data class ChangedGuardianEmailFocus(val focusState: FocusState) : InvitationNewUpdateEvent()
    data class EnteredSpecialRequests(val value: String) : InvitationNewUpdateEvent()
    data class ChangedSpecialRequestsFocus(val focusState: FocusState) : InvitationNewUpdateEvent()
    data class EnteredAddress(val value: String) : InvitationNewUpdateEvent()
    data class ChangedAddressFocus(val focusState: FocusState) : InvitationNewUpdateEvent()
    data class EnteredDate(val value: String) : InvitationNewUpdateEvent()
    data class ChangedDateFocus(val focusState: FocusState) : InvitationNewUpdateEvent()
    data class EnteredTime(val value: Long) : InvitationNewUpdateEvent()
    data class ChangedTimeFocus(val focusState: FocusState) : InvitationNewUpdateEvent()
    data class EnteredGuardianPhone(val value: Long) : InvitationNewUpdateEvent()
    data class ChangedGuardianPhoneFocus(val focusState: FocusState) : InvitationNewUpdateEvent()
    data class EnteredAge(val value: Int) : InvitationNewUpdateEvent()
    data class ChangedAgeFocus(val focusState: FocusState) : InvitationNewUpdateEvent()
    object SaveInvitation : InvitationNewUpdateEvent()
    object Back : InvitationNewUpdateEvent()
}