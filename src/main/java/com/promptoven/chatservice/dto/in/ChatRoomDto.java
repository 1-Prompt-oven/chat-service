package com.promptoven.chatservice.dto.in;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomDto {

    private String chatRoomName;
    private String hostUserUuid;
    private String inviteUserUuid;

    @Builder
    public ChatRoomDto(String chatRoomName, String hostUserUuid, String inviteUserUuid) {
        this.chatRoomName = chatRoomName;
        this.hostUserUuid = hostUserUuid;
        this.inviteUserUuid = inviteUserUuid;
    }
}
