package com.promptoven.chatservice.document;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "chatMessage")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDocument {

    @Id
    private String id;
    private String roomId;
    private String messageType;
    private String message;
    private String senderUuid;
    private Boolean isRead;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
