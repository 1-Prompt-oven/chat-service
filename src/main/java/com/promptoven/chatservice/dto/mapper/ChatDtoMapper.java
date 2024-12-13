package com.promptoven.chatservice.dto.mapper;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.document.ChatRoomDocument;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomInfoResponseDto;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;
import com.promptoven.chatservice.vo.out.ChatMessageResponseVo;
import com.promptoven.chatservice.vo.out.ChatRoomInfoResponseVo;
import com.promptoven.chatservice.vo.out.CreateRoomResponseVo;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatDtoMapper {

    // 매개는 항상 Dto (ex Dto -> Dto / Dto -> Vo)

    public ChatRoomDocument toChatRoomDocument(CreateRoomRequestDto createRoomRequestDto) {
        List<ChatRoomDocument.Participant> participants = List.of(
                ChatRoomDocument.Participant.builder()
                        .userUuid(createRoomRequestDto.getHostUserUuid()) // 방 생성자
                        .status("active") // 활성 상태
                        .leftAt(null) // 나간 시간 없음
                        .build(),
                ChatRoomDocument.Participant.builder()
                        .userUuid(createRoomRequestDto.getInviteUserUuid()) // 초대된 사용자
                        .status("active") // 활성 상태
                        .leftAt(null) // 나간 시간 없음
                        .build()
        );

        return ChatRoomDocument.builder()
                .chatRoomName(createRoomRequestDto.getRoomName())
                .participants(participants)
                .unreadCount(0L)
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
                .isRead(false)
                .build();
    }

    public ChatMessageResponseVo toChatMessageResponseVo(ChatMessageResponseDto chatMessageResponseDto) {
        return ChatMessageResponseVo.builder()
                .id(chatMessageResponseDto.getId())
                .roomId(chatMessageResponseDto.getRoomId())
                .messageType(chatMessageResponseDto.getMessageType())
                .message(chatMessageResponseDto.getMessage())
                .senderUuid(chatMessageResponseDto.getSenderUuid())
                .isRead(chatMessageResponseDto.getIsRead())
                .createdAt(chatMessageResponseDto.getCreatedAt())
                .updatedAt(chatMessageResponseDto.getUpdatedAt())
                .build();
    }

    public ChatRoomInfoResponseVo toChatRoomInfoResponseVo(ChatRoomInfoResponseDto chatRoomInfoResponseDto) {
        return ChatRoomInfoResponseVo.builder()
                .chatRoomId(chatRoomInfoResponseDto.getChatRoomId())
                .chatRoomName(chatRoomInfoResponseDto.getChatRoomName())
                .partnerUuid(chatRoomInfoResponseDto.getPartnerUuid())
                .partnerIsActive(chatRoomInfoResponseDto.getPartnerIsActive())
                .build();
    }
}
