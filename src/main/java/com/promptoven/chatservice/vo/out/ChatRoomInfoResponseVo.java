package com.promptoven.chatservice.vo.out;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomInfoResponseVo {

    private String chatRoomId;
    private String chatRoomName;
    private String partnerUuid;
    private Boolean partnerIsActive;
    private String recentMessage;
    private LocalDateTime recentMessageTime;
    private Long unreadCount;
}
