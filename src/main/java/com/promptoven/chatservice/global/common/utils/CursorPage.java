//package com.promptoven.chatservice.global.common.utils;
//
//import com.promptoven.reviewReadService.dto.out.ReviewOutPaginationDto;
//import com.promptoven.reviewReadService.vo.out.ReadResponseVo;
//import java.time.LocalDateTime;
//import java.util.List;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//public class CursorPage<T> {
//
//    private List<T> content;
//    private LocalDateTime lastCreatedAt;
//    private String lastId;
//    private Boolean hasNext;
//    private Integer pageSize;
//    private Integer page;
//
//    public boolean hasNext() {
//        return lastId != null;
//    }
//
//    @Builder
//    public CursorPage(List<T> content, String lastId, LocalDateTime lastCreatedAt, Boolean hasNext, Integer pageSize,
//            Integer page) {
//        this.content = content;
//        this.lastCreatedAt = lastCreatedAt;
//        this.lastId = lastId;
//        this.hasNext = hasNext;
//        this.pageSize = pageSize;
//        this.page = page;
//    }
//
//    public static CursorPage<ReadResponseVo> toCursorPage(ReviewOutPaginationDto cursorPage) {
//        return CursorPage.<ReadResponseVo>builder()
//                .content(ReadResponseVo.toVoList(cursorPage.getReviewResponseDtoList()))
//                .lastCreatedAt(cursorPage.getLastCreatedAt())
//                .lastId(cursorPage.getLastId())
//                .hasNext(cursorPage.getHasNext())
//                .pageSize(cursorPage.getPageSize())
//                .page(cursorPage.getPage())
//                .build();
//    }
//}
