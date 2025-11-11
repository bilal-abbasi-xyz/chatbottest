package com.bilals.chatbottest // Updated package

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bilals.chatbottest.ui.chat.ChatScreen // Updated package
// import com.bilals.chatbottest.ui.theme.ChatBotTestTheme // Removed this import
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // ChatBotTestTheme wrapper removed
            ChatScreen()
        }
    }
}