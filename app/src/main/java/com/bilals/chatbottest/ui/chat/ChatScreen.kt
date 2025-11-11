package com.bilals.chatbottest.ui.chat

// Imports remaining the same
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
// Removed: import androidx.compose.material.icons.Icons
// Removed: import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
// Removed: import androidx.compose.material3.Icon
// Removed: import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton // Added this import
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel() // Hilt provides the ViewModel
) {
    // Collect the state from the ViewModel (UDF)
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    // Auto-scroll when a new message appears
    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    Scaffold(
        bottomBar = {
            MessageInput(
                onSendMessage = { message ->
                    viewModel.sendMessage(message) // Event flows up
                },
                isLoading = uiState.isLoading
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            reverseLayout = false
        ) {
            items(uiState.messages) { message ->
                ChatMessageItem(message = message)
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (message.isFromUser) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.secondaryContainer,
            shadowElevation = 2.dp
        ) {
            Text(
                text = message.message,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun MessageInput(
    onSendMessage: (String) -> Unit,
    isLoading: Boolean
) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.weight(1f),
            label = { Text("Ask me anything...") },
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.width(8.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            // Replaced IconButton with TextButton
            TextButton(
                onClick = {
                    if (text.isNotBlank()) {
                        onSendMessage(text)
                        text = ""
                    }
                },
                enabled = text.isNotBlank()
            ) {
                Text("Send")
            }
        }
    }
}