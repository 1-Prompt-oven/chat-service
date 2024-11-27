package com.promptoven.chatservice.document;

import com.promptoven.chatservice.global.common.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "chatMessage")
@NoArgsConstructor
public class ChatMessageDocument extends BaseEntity {

    @Id
    private String id;
    private String roomId;
    private String messageType;
    private String message;
    private String senderUuid;

    @Builder
    public ChatMessageDocument(String id, String roomId, String messageType, String message, String senderUuid) {
        this.id = id;
        this.roomId = roomId;
        this.messageType = messageType;
        this.message = message;
        this.senderUuid = senderUuid;
    }
}
