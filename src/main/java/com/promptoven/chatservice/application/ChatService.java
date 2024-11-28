package com.promptoven.chatservice.application;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;
import reactor.core.publisher.Mono;

public interface ChatService {
    CreateRoomResponseDto createChatRoom(CreateRoomRequestDto createRoomRequestDto);

    Mono<ChatMessageDocument> sendMessage(SendMessageDto sendMessageDto);
}



