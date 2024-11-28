package com.promptoven.chatservice.infrastructure;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.document.mapper.ChatDocumentMapper;
import com.promptoven.chatservice.dto.in.PrevMessageRequestDto;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.global.common.utils.CursorPage;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomChatRepositoryImpl implements MongoCustomChatRepository {

    private final MongoTemplate mongoTemplate;
    private final ChatDocumentMapper chatDocumentMapper;

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    @Override
    public CursorPage<ChatMessageResponseDto> getPrevMessages(PrevMessageRequestDto prevMessageRequestDto) {

        String roomId = prevMessageRequestDto.getRoomId();
        String lastObjectId = prevMessageRequestDto.getLastObjectId();
        int pageSize = Optional.ofNullable(prevMessageRequestDto.getPageSize()).orElse(DEFAULT_PAGE_SIZE);
        Integer page = Optional.ofNullable(prevMessageRequestDto.getPage()).orElse(DEFAULT_PAGE_NUMBER);

        Query query = new Query();

        // 필터: productUuid
        if (roomId != null) {
            query.addCriteria(Criteria.where("roomId").is(roomId));
        }

        // 페이징 커서 설정
        if (lastObjectId != null && !lastObjectId.isEmpty()) {
            query.addCriteria(Criteria.where("_id").lt(lastObjectId));
        }

        // 정렬 및 제한
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.limit(pageSize + 1);  // 다음 페이지가 있는지 확인하기 위해 1개의 추가 데이터를 요청

        List<ChatMessageDocument> chatMessages = mongoTemplate.find(query, ChatMessageDocument.class);

        String nextObjectId = null;
        boolean hasNext = false;

        // 다음 페이지가 있는지 확인하고, 결과 리스트를 페이지 사이즈에 맞게 조정
        if (chatMessages.size() > pageSize) {
            hasNext = true;
            chatMessages = chatMessages.subList(0, pageSize);
            nextObjectId = chatMessages.get(pageSize - 1).getId();
        }

        List<ChatMessageResponseDto> chatMessageResponseDtoList = chatMessages.stream()
                .map(chatDocumentMapper::toChatMessageResponseDto)
                .collect(Collectors.toList());

        return CursorPage.<ChatMessageResponseDto>builder()
                .content(chatMessageResponseDtoList)
                .lastObjectId(nextObjectId)
                .hasNext(hasNext)
                .page(page)
                .pageSize(pageSize)
                .build();
    }
}
