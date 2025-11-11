package com.bilals.chatbottest.data

import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject

// This is the Implementation. It fulfills the contract.
// It depends on the actual GenerativeModel from the SDK.
class ChatRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel
) : ChatRepository {

    override suspend fun sendMessage(prompt: String): String {
        return try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Error: No text in response"
        } catch (e: Exception) {
            // Basic error handling
            e.localizedMessage ?: "An unknown error occurred"
        }
    }
}