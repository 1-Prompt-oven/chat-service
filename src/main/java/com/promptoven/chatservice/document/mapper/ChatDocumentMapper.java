package com.promptoven.chatservice.document.mapper;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.document.ChatRoomDocument;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomInfoResponseDto;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ChatDocumentMapper {

    public CreateRoomResponseDto toCreateRoomResponseDto(ChatRoomDocument chatRoomDocument) {
        return CreateRoomResponseDto.builder()
                .roomId(chatRoomDocument.getId())
                .roomName(chatRoomDocument.getChatRoomName())
                .createdAt(chatRoomDocument.getCreatedAt())
                .updatedAt(chatRoomDocument.getUpdatedAt())
                .build();
    }

    public ChatMessageResponseDto toChatMessageResponseDto(ChatMessageDocument chatMessageDocument) {
        return ChatMessageResponseDto.builder()
                .id(chatMessageDocument.getId())
                .roomId(chatMessageDocument.getRoomId())
                .senderUuid(chatMessageDocument.getSenderUuid())
                .messageType(chatMessageDocument.getMessageType())
                .message(chatMessageDocument.getMessage())
                .isRead(chatMessageDocument.getIsRead())
                .createdAt(chatMessageDocument.getCreatedAt())
                .updatedAt(chatMessageDocument.getUpdatedAt())
                .build();
    }

    public ChatRoomInfoResponseDto toChatRoomInfoResponseDto(ChatRoomDocument chatRoomDocument) {

    }
}
