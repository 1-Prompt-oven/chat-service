package com.promptoven.chatservice.dto.out;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomInfoResponseDto {

    private String chatRoomId;
    private String chatRoomName;
    private String partnerUuid;
    private Boolean partnerIsActive;
    private String recentMessage;
    private LocalDateTime recentMessageTime;
    private Long unreadCount;
}
