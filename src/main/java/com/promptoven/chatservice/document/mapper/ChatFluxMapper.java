package com.promptoven.chatservice.document.mapper;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomResponseDto;
import com.promptoven.chatservice.vo.out.ChatMessageResponseVo;
import com.promptoven.chatservice.vo.out.ChatRoomResponseVo;
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
                        .isRead(chatMessageEntity.getIsRead())
                        .createdAt(chatMessageEntity.getCreatedAt())
                        .updatedAt(chatMessageEntity.getUpdatedAt())
                        .build()
        );
    }

    public Flux<ChatMessageResponseVo> toChatMessageResponseVo(
            Flux<ChatMessageResponseDto> chatMessageResponseDtoFlux) {
        return chatMessageResponseDtoFlux.map(chatMessageDocument ->
                ChatMessageResponseVo.builder()
                        .id(chatMessageDocument.getId())
                        .roomId(chatMessageDocument.getRoomId())
                        .messageType(chatMessageDocument.getMessageType())
                        .message(chatMessageDocument.getMessage())
                        .senderUuid(chatMessageDocument.getSenderUuid())
                        .isRead(chatMessageDocument.getIsRead())
                        .createdAt(chatMessageDocument.getCreatedAt())
                        .updatedAt(chatMessageDocument.getUpdatedAt())
                        .build()
        );
    }

    public Flux<ChatRoomResponseVo> toChatRoomResponseVo(Flux<ChatRoomResponseDto> chatRoomResponseDtoFlux) {
        return chatRoomResponseDtoFlux.map(chatRoomResponseDto ->
                ChatRoomResponseVo.builder()
                        .chatRoomId(chatRoomResponseDto.getChatRoomId())
                        .chatRoomName(chatRoomResponseDto.getChatRoomName())
                        .partnerUuid(chatRoomResponseDto.getPartnerUuid())
                        .partnerIsActive(chatRoomResponseDto.getPartnerIsActive())
                        .recentMessage(chatRoomResponseDto.getRecentMessage())
                        .recentMessageTime(chatRoomResponseDto.getRecentMessageTime())
                        .unreadCount(chatRoomResponseDto.getUnreadCount())
                        .build()
        );
    }
}
