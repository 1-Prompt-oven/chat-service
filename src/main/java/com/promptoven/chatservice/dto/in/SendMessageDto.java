package com.promptoven.chatservice.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendMessageDto {
    private String roomId;
    private String messageType;
    private String message;
    private String senderUuid;

    @Builder
    public SendMessageDto(String roomId, String messageType, String message, String senderUuid) {
        this.roomId = roomId;
        this.messageType = messageType;
        this.message = message;
        this.senderUuid = senderUuid;
    }
}
