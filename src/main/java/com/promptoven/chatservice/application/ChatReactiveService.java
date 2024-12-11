package com.promptoven.chatservice.application;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatReactiveService {

    Mono<ChatMessageDocument> sendMessage(SendMessageDto sendMessageDto);

    Flux<ChatMessageResponseDto> getMessageByRoomId(String roomId);

    Flux<ChatRoomResponseDto> getChatRoomList(String userUuid);

    Mono<Void> updateRead(String roomId, String userUuid);

    Mono<Void> leaveChatRoom(String roomId, String userUuid);

}
