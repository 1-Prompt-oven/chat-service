package com.promptoven.chatservice.dto.out;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageResponseDto {

    private String id;
    private String roomId;
    private String messageType;
    private String message;
    private String senderUuid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ChatMessageResponseDto(String id, String roomId, String messageType, String message, String senderUuid,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.roomId = roomId;
        this.messageType = messageType;
        this.message = message;
        this.senderUuid = senderUuid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
