package com.bilals.chatbottest.data

import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject
import android.util.Log

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
        // Log the detailed error to Logcat
        Log.e("ChatRepository", "Error sending message: ${e.message}", e)
        // Return the localized message
        e.localizedMessage ?: "An unknown error occurred"
    }
    }
}