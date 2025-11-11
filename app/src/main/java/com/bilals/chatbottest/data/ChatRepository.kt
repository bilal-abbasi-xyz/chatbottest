package com.bilals.chatbottest.data

// This is the Interface. It defines the "contract" for our data layer.
// The ViewModel will depend on this, not the implementation.
interface ChatRepository {
    suspend fun sendMessage(prompt: String): String
}