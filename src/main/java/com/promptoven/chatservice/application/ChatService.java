package com.promptoven.chatservice.application;

import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;

public interface ChatService {
    CreateRoomResponseDto createChatRoom(CreateRoomRequestDto createRoomRequestDto);

    void sendMessage(SendMessageDto sendMessageDto);
}



