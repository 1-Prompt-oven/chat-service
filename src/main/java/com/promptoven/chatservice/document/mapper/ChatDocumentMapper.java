package com.promptoven.chatservice.document.mapper;

import com.promptoven.chatservice.document.ChatRoomDocument;
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
}
