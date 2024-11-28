package com.promptoven.chatservice.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageResponseVo {
    private String id;
    private String roomId;
    private String messageType;
    private String message;
    private String senderUuid;
    private String createdAt;
    private String updatedAt;

    @Builder
    public ChatMessageResponseVo(String id, String roomId, String messageType, String message, String senderUuid,
            String createdAt, String updatedAt) {
        this.id = id;
        this.roomId = roomId;
        this.messageType = messageType;
        this.message = message;
        this.senderUuid = senderUuid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
