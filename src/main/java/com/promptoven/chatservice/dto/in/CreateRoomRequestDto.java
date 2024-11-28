package com.promptoven.chatservice.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateRoomRequestDto {

    private String hostUserUuid;
    private String inviteUserUuid;
    private String roomName;

    @Builder
    public CreateRoomRequestDto(String hostUserUuid, String inviteUserUuid, String roomName) {
        this.hostUserUuid = hostUserUuid;
        this.inviteUserUuid = inviteUserUuid;
        this.roomName = roomName;
    }
}
