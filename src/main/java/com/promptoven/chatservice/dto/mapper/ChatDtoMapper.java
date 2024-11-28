package com.promptoven.chatservice.dto.mapper;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.document.ChatRoomDocument;
import com.promptoven.chatservice.dto.in.ChatRoomDto;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;
import com.promptoven.chatservice.vo.out.ChatMessageResponseVo;
import com.promptoven.chatservice.vo.out.CreateRoomResponseVo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

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

    public ChatMessageDocument toChatMessageDocument(SendMessageDto sendMessageDto) {
        return ChatMessageDocument.builder()
                .roomId(sendMessageDto.getRoomId())
                .messageType(sendMessageDto.getMessageType())
                .message(sendMessageDto.getMessage())
                .senderUuid(sendMessageDto.getSenderUuid())
                .build();
    }

    public Flux<ChatMessageResponseVo> toChatMessageResponseVo(Flux<ChatMessageResponseDto> chatMessageResponseDtoFlux) {
        return chatMessageResponseDtoFlux.map(chatMessageDocument ->
                ChatMessageResponseVo.builder()
                        .id(chatMessageDocument.getId())
                        .roomId(chatMessageDocument.getRoomId())
                        .messageType(chatMessageDocument.getMessageType())
                        .message(chatMessageDocument.getMessage())
                        .senderUuid(chatMessageDocument.getSenderUuid())
                        .createdAt(chatMessageDocument.getCreatedAt())
                        .updatedAt(chatMessageDocument.getUpdatedAt())
                        .build()
        );
    }
}