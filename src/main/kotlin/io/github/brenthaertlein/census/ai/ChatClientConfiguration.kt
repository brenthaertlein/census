package io.github.brenthaertlein.census.ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatClientConfiguration(private val openAiChatModel: ChatModel) {

    @Bean
    fun chatClient(): ChatClient = ChatClient.builder(openAiChatModel)
        .build()
}