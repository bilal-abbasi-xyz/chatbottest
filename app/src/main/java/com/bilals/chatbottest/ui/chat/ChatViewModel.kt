package com.bilals.chatbottest.ui.chat // Updated package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bilals.chatbottest.domain.SendMessageUseCase // Updated package
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun sendMessage(userMessage: String) {
        if (userMessage.isBlank()) return

        _uiState.update { currentState ->
            currentState.copy(
                isLoading = true,
                messages = currentState.messages + ChatMessage(userMessage, true)
            )
        }

        viewModelScope.launch {
            val aiResponse = sendMessageUseCase(userMessage)
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    messages = currentState.messages + ChatMessage(aiResponse, false)
                )
            }
        }
    }
}