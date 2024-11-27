package com.promptoven.chatservice.dto.mapper;

import com.promptoven.chatservice.document.ChatRoomDocument;
import com.promptoven.chatservice.dto.in.ChatRoomDto;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;
import com.promptoven.chatservice.vo.out.CreateRoomResponseVo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatDtoMapper {

    // 매개는 항상 Dto (ex Dto -> Dto / Dto -> Vo)

    public ChatRoomDto toChatRoomDto(CreateRoomRequestDto createRoomRequestDto) {
        return ChatRoomDto.builder()
                .chatRoomName(createRoomRequestDto.getRoomName())
                .hostUserUuid(createRoomRequestDto.getHostUserUuid())
                .inviteUserUuid(createRoomRequestDto.getInviteUserUuid())
                .build();
    }

    public ChatRoomDocument toChatRoomDocument(ChatRoomDto chatRoomDto) {
        return ChatRoomDocument.builder()
                .chatRoomName(chatRoomDto.getChatRoomName())
                .hostUserUuid(chatRoomDto.getHostUserUuid())
                .inviteUserUuid(chatRoomDto.getInviteUserUuid())
                .build();
    }

    public CreateRoomResponseVo toCreateRoomResponseVo(CreateRoomResponseDto createRoomResponseDto) {
        return CreateRoomResponseVo.builder()
                .roomId(createRoomResponseDto.getRoomId())
                .roomName(createRoomResponseDto.getRoomName())
                .createdAt(createRoomResponseDto.getCreatedAt())
                .updatedAt(createRoomResponseDto.getUpdatedAt())
                .build();
    }
}
