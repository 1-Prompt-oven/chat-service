package com.promptoven.chatservice.application;

import com.mongodb.client.model.changestream.OperationType;
import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.document.ChatRoomDocument;
import com.promptoven.chatservice.document.mapper.ChatFluxMapper;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomResponseDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatReactiveServiceImpl implements ChatReactiveService {

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
                                .senderUuid(document.getString("senderUuid"))
                                .messageType(document.getString("messageType"))
                                .message(document.getString("message"))
                                .createdAt(LocalDateTime.ofInstant(document.getDate("createdAt").toInstant(),
                                        ZoneId.systemDefault()))
                                .updatedAt(LocalDateTime.ofInstant(document.getDate("updatedAt").toInstant(),
                                        ZoneId.systemDefault()))
                                .build()
                        )
        );
    }

    @Override
    public Flux<ChatRoomResponseDto> getChatRoomList(String userUuid) {
        Flux<ChatRoomResponseDto> initialData = reactiveMongoTemplate.find(
                Query.query(new Criteria().orOperator(
                        Criteria.where("hostUserUuid").is(userUuid),
                        Criteria.where("inviteUserUuid").is(userUuid)
                )),
                ChatRoomDocument.class
        ).map(chatRoom -> {
            String partnerUuid = chatRoom.getHostUserUuid().equals(userUuid)
                    ? chatRoom.getInviteUserUuid()
                    : chatRoom.getHostUserUuid();

            return ChatRoomResponseDto.builder()
                    .chatRoomId(chatRoom.getId())
                    .chatRoomName(chatRoom.getChatRoomName())
                    .recentMessage(chatRoom.getRecentMessage())
                    .recentMessageTime(chatRoom.getRecentMessageTime())
                    .partnerUuid(partnerUuid)
                    .build();
        });

        ChangeStreamOptions options = ChangeStreamOptions.builder()
                .filter(Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("operationType").is(OperationType.INSERT.getValue()))
                ))
                .build();

        Flux<ChatRoomResponseDto> updates = reactiveMongoTemplate.changeStream("chatMessage", options, Document.class)
                .map(ChangeStreamEvent::getBody)
                .flatMap(document -> {
                    String chatRoomId = document.getString("roomId");
                    String message = document.getString("message");
                    LocalDateTime recentMessageTime = LocalDateTime.ofInstant(
                            document.getDate("updatedAt").toInstant(),
                            ZoneId.systemDefault()
                    );

                    // ChatRoom 업데이트 및 partnerUuid 설정
                    return updateChatRecentMessage(chatRoomId, message, recentMessageTime)
                            .flatMap(chatRoom -> {
                                String partnerUuid = chatRoom.getHostUserUuid().equals(userUuid)
                                        ? chatRoom.getInviteUserUuid()
                                        : chatRoom.getHostUserUuid();

                                return Mono.just(ChatRoomResponseDto.builder()
                                        .chatRoomId(chatRoomId)
                                        .chatRoomName(chatRoom.getChatRoomName())
                                        .recentMessage(message)
                                        .recentMessageTime(recentMessageTime)
                                        .partnerUuid(partnerUuid)
                                        .build());
                            });
                });
        return Flux.concat(initialData, updates);
    }

    private Mono<ChatRoomDocument> updateChatRecentMessage(String roomId, String recentMessage,
            LocalDateTime recentMessageTime) {
        return reactiveMongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is(roomId)),
                Update.update("recentMessage", recentMessage)
                        .set("recentMessageTime", recentMessageTime),
                ChatRoomDocument.class,
                "chat"
        );
    }

}
