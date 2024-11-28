package com.promptoven.chatservice.document.mapper;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ChatFluxMapper {

    public Flux<ChatMessageResponseDto> toChatMessageResponseDto(Flux<ChatMessageDocument> chatMessageDocumentFlux) {
        return chatMessageDocumentFlux.map(chatMessageEntity ->
                ChatMessageResponseDto.builder()
                        .id(chatMessageEntity.getId())
                        .roomId(chatMessageEntity.getRoomId())
                        .messageType(chatMessageEntity.getMessageType())
                        .message(chatMessageEntity.getMessage())
                        .senderUuid(chatMessageEntity.getSenderUuid())
                        .createdAt(chatMessageEntity.getCreatedAt())
                        .updatedAt(chatMessageEntity.getUpdatedAt())
                        .build()
        );
    }
}
