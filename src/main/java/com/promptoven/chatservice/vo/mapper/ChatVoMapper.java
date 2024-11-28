package com.promptoven.chatservice.vo.mapper;

import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.vo.in.CreateRoomRequestVo;
import com.promptoven.chatservice.vo.in.SendMessageVo;
import org.springframework.stereotype.Component;

@Component
public class ChatVoMapper {
    // 매개는 항상 Vo (ex Vo -> Vo / Vo -> Dto)

    public CreateRoomRequestDto toCreateRoomRequestDto(CreateRoomRequestVo roomRequestVo) {
        return CreateRoomRequestDto.builder()
                .hostUserUuid(roomRequestVo.getHostUserUuid())
                .inviteUserUuid(roomRequestVo.getInviteUserUuid())
                .roomName(roomRequestVo.getRoomName())
                .build();
    }

    public SendMessageDto toSendMessageDto(SendMessageVo sendMessageVo) {
        return SendMessageDto.builder()
                .roomId(sendMessageVo.getRoomId())
                .messageType(sendMessageVo.getMessageType())
                .message(sendMessageVo.getMessage())
                .senderUuid(sendMessageVo.getSenderUuid())
                .build();
    }
}
