package com.promptoven.chatservice.vo.in;

import lombok.Getter;

@Getter
public class SendMessageVo {

    private String roomId;
    private String messageType;
    private String message;
    private String senderUuid;
}
