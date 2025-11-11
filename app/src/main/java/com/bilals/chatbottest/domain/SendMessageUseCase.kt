package com.bilals.chatbottest.domain

import com.bilals.chatbottest.data.ChatRepository
import javax.inject.Inject

// The UseCase depends on the Repository *interface*, not the implementation.
class SendMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    // The 'invoke' operator allows us to call this class as if it were a function
    suspend operator fun invoke(prompt: String): String {
        return repository.sendMessage(prompt)
    }
}