package com.promptoven.chatservice.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetChatRoomResponseVo {
    private String chatRoomName;
    private String partnerUuid;
}
