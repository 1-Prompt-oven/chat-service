package com.promptoven.chatservice.application;

import com.mongodb.client.model.changestream.OperationType;
import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.document.mapper.ChatFluxMapper;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatFluxMapper chatFluxMapper;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<ChatMessageResponseDto> getMessageByRoomId(String roomId) {

        ChangeStreamOptions options = ChangeStreamOptions.builder()
                .filter(Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("operationType").is(OperationType.INSERT.getValue())),
                        // 새로운 데이터가 삽입될 때
                        Aggregation.match(Criteria.where("fullDocument.roomId").is(roomId)) // 해당 roomId와 일치하는 데이터만
                ))
                .build();

        return chatFluxMapper.toChatMessageResponseDto(
                reactiveMongoTemplate.changeStream("chatMessage", options, Document.class)
                        .map(ChangeStreamEvent::getBody)
                        .map(document -> ChatMessageDocument.builder()
                                .id(document.get("_id", ObjectId.class).toString())
                                .roomId(document.getString("roomId"))
                                .senderUuid(document.getString("senderId"))
                                .messageType(document.getString("messageType"))
                                .message(document.getString("message"))
                                .createdAt(LocalDateTime.ofInstant(document.getDate("createdAt").toInstant(),
                                        ZoneId.systemDefault()))
                                .updatedAt(LocalDateTime.ofInstant(document.getDate("updatedAt").toInstant(),
                                        ZoneId.systemDefault()))
                                .build()
                        ));
    }
}
