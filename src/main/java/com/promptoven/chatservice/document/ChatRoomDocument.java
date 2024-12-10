package com.promptoven.chatservice.document;

import com.promptoven.chatservice.global.common.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Builder;
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
    private String hostUserUuid;
    private String inviteUserUuid;
    private String recentMessage;
    private LocalDateTime recentMessageTime;
    private Long unreadCount;

    @Builder
    public ChatRoomDocument(String id, String chatRoomName, String hostUserUuid, String inviteUserUuid,
            String recentMessage, LocalDateTime recentMessageTime, Long unreadCount) {
        this.id = id;
        this.chatRoomName = chatRoomName;
        this.hostUserUuid = hostUserUuid;
        this.inviteUserUuid = inviteUserUuid;
        this.recentMessage = recentMessage;
        this.recentMessageTime = recentMessageTime;
        this.unreadCount = unreadCount;
    }
}
