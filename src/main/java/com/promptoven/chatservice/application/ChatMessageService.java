package com.promptoven.chatservice.application;

import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import reactor.core.publisher.Flux;

public interface ChatMessageService {

    Flux<ChatMessageResponseDto> getMessageByRoomId(String roomId);
}
