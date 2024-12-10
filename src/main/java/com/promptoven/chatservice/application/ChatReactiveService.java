package com.promptoven.chatservice.application;

import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomResponseDto;
import reactor.core.publisher.Flux;

public interface ChatReactiveService {

    Flux<ChatMessageResponseDto> getMessageByRoomId(String roomId);

    Flux<ChatRoomResponseDto> getChatRoomList(String userUuid);
}
