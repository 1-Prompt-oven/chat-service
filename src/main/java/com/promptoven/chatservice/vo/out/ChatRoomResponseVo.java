package com.promptoven.chatservice.vo.out;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponseVo {

    private String chatRoomId;
    private String chatRoomName;
    private String partnerUuid;
    private String recentMessage;
    private LocalDateTime recentMessageTime;
}
