package com.promptoven.chatservice.document.mapper;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class ChatFluxMapper {

    public Flux<ChatMessageResponseDto> toChatMessageResponseDto(Flux<ChatMessageDocument> chatMessageDocumentFlux) {
        log.info("chatMessageEntityFlux: {}", chatMessageDocumentFlux);
        return chatMessageDocumentFlux.map(chatMessageEntity ->
                ChatMessageResponseDto.builder()
                        .id(chatMessageEntity.getId())
                        .roomId(chatMessageEntity.getRoomId())
                        .messageType(chatMessageEntity.getMessageType())
                        .message(chatMessageEntity.getMessage())
                        .senderUuid(chatMessageEntity.getSenderUuid())
                        .createdAt(chatMessageEntity.getCreatedAt().toString())
                        .updatedAt(chatMessageEntity.getUpdatedAt().toString())
                        .build()
        );
    }
}
