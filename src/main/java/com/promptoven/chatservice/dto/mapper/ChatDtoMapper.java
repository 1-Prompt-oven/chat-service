package com.promptoven.chatservice.dto.mapper;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.document.ChatRoomDocument;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;
import com.promptoven.chatservice.dto.out.GetChatRoomResponseDto;
import com.promptoven.chatservice.vo.out.ChatMessageResponseVo;
import com.promptoven.chatservice.vo.out.CreateRoomResponseVo;
import com.promptoven.chatservice.vo.out.GetChatRoomResponseVo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ChatDtoMapper {

    // 매개는 항상 Dto (ex Dto -> Dto / Dto -> Vo)

    public ChatRoomDocument toChatRoomDocument(CreateRoomRequestDto createRoomRequestDto) {
        return ChatRoomDocument.builder()
                .chatRoomName(createRoomRequestDto.getRoomName())
                .hostUserUuid(createRoomRequestDto.getHostUserUuid())
                .inviteUserUuid(createRoomRequestDto.getInviteUserUuid())
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

    public Flux<ChatMessageResponseVo> toChatMessageResponseVo(
            Flux<ChatMessageResponseDto> chatMessageResponseDtoFlux) {
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

    public ChatMessageResponseVo toChatMessageResponseVo(ChatMessageResponseDto chatMessageResponseDto) {
        return ChatMessageResponseVo.builder()
                .id(chatMessageResponseDto.getId())
                .roomId(chatMessageResponseDto.getRoomId())
                .messageType(chatMessageResponseDto.getMessageType())
                .message(chatMessageResponseDto.getMessage())
                .senderUuid(chatMessageResponseDto.getSenderUuid())
                .createdAt(chatMessageResponseDto.getCreatedAt())
                .updatedAt(chatMessageResponseDto.getUpdatedAt())
                .build();
    }

    public GetChatRoomResponseVo toGetChatRoomResponseVo(GetChatRoomResponseDto getChatRoomResponseDto) {
        return GetChatRoomResponseVo.builder()
                .chatRoomName(getChatRoomResponseDto.getChatRoomName())
                .partnerUuid(getChatRoomResponseDto.getPartnerUuid())
                .build();
    }

}
