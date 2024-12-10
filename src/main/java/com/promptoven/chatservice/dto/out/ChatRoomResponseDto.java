package com.promptoven.chatservice.dto.out;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponseDto {

    private String chatRoomId;
    private String chatRoomName;
    private String partnerUuid;
    private String recentMessage;
    private LocalDateTime recentMessageTime;
}
