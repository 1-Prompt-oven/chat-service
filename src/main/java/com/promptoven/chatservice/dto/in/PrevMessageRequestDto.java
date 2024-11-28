package com.promptoven.chatservice.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class PrevMessageRequestDto {

    private String roomId;
    private String lastObjectId;
    private Integer pageSize;
    private Integer page;

    @Builder
    public PrevMessageRequestDto(String roomId, String lastObjectId, Integer pageSize, Integer page) {
        this.roomId = roomId;
        this.lastObjectId = lastObjectId;
        this.pageSize = pageSize;
        this.page = page;
    }
}
