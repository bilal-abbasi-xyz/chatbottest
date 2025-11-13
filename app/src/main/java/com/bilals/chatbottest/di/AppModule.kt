package com.bilals.chatbottest.di

import com.bilals.chatbottest.BuildConfig
import com.bilals.chatbottest.data.ChatRepository
import com.bilals.chatbottest.data.ChatRepositoryImpl
import com.bilals.chatbottest.domain.SendMessageUseCase
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        // Define generation configuration
        val generationConfig = generationConfig {
            temperature = 0.8f // Controls the randomness of the output. Lower values mean less random.
            topK = 20          // The maximum number of tokens to sample from at each step.
            topP = 0.95f       // The cumulative probability cutoff for token selection.
            maxOutputTokens = 2048 // The maximum number of tokens to generate in a response.
            stopSequences = listOf() // Sequences that will stop the generation.
        }

        // Define safety settings to control content generation
        val safetySettings = listOf(
            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE)
        )

        return GenerativeModel(
            modelName = "gemini-2.0-flash-latest",
            apiKey = BuildConfig.GEMINI_API_KEY,
            generationConfig = generationConfig,
            safetySettings = safetySettings
        )
    }

    @Provides
    @Singleton
    fun provideChatRepository(generativeModel: GenerativeModel): ChatRepository {
        return ChatRepositoryImpl(generativeModel)
    }

    @Provides
    @Singleton
    fun provideSendMessageUseCase(chatRepository: ChatRepository): SendMessageUseCase {
        return SendMessageUseCase(chatRepository)
    }
}