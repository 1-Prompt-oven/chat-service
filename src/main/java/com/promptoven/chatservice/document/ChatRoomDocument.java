package com.promptoven.chatservice.document;

import com.promptoven.chatservice.global.common.entity.BaseEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "chat")
@NoArgsConstructor
public class ChatRoomDocument extends BaseEntity {

    @Id
    private String id;
    private String chatRoomName;
    private String recentMessage;
    private LocalDateTime recentMessageTime;
    private Long unreadCount;
    private List<Participant> participants;

    @Builder
    public ChatRoomDocument(String id, String chatRoomName, String recentMessage, LocalDateTime recentMessageTime,
            Long unreadCount, List<Participant> participants, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.chatRoomName = chatRoomName;
        this.recentMessage = recentMessage;
        this.recentMessageTime = recentMessageTime;
        this.unreadCount = unreadCount;
        this.participants = participants;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Getter
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Participant {

        private String userUuid;
        private String status;
        private LocalDateTime leftAt;
    }
}


