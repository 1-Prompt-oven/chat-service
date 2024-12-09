package com.promptoven.chatservice.global.common.utils;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class CursorPage<T> {

    private List<T> content;
    private String lastObjectId;
    private Boolean hasNext;
    private Integer pageSize;
    private Integer page;

    public boolean hasNext() {
        return lastObjectId != null;
    }

    @Builder
    public CursorPage(List<T> content, String lastObjectId, Boolean hasNext, Integer pageSize,
            Integer page) {
        this.content = content;
        this.lastObjectId = lastObjectId;
        this.hasNext = hasNext;
        this.pageSize = pageSize;
        this.page = page;
    }
}
