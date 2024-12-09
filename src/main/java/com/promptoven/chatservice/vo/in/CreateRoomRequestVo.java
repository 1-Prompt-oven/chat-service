package com.promptoven.chatservice.vo.in;

import lombok.Getter;

@Getter
public class CreateRoomRequestVo {

    private String hostUserUuid;
    private String inviteUserUuid;
    private String roomName;
}
