package com.bilals.chatbottest.ui.chat

// Represents a single message in the chat
data class ChatMessage(
    val message: String,
    val isFromUser: Boolean
)

// Represents the entire state of the ChatScreen
data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false
)