package com.promptoven.chatservice.application;

import com.mongodb.client.model.changestream.FullDocument;
import com.mongodb.client.model.changestream.OperationType;
import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.document.ChatRoomDocument;
import com.promptoven.chatservice.document.mapper.ChatFluxMapper;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.dto.mapper.ChatDtoMapper;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomResponseDto;
import com.promptoven.chatservice.infrastructure.MongoChatMessageRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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
    private final ChatDtoMapper chatDtoMapper;
    private final MongoChatMessageRepository mongoChatMessageRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    // 채팅 내역 실시간으로 받아오는 로직
    @Override
    public Flux<ChatMessageResponseDto> getMessageByRoomId(String roomId) {

        ChangeStreamOptions options = ChangeStreamOptions.builder()
                .filter(Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("operationType").in(
                                OperationType.INSERT.getValue(),
                                OperationType.UPDATE.getValue()
                        )),
                        Aggregation.match(Criteria.where("fullDocument.roomId").is(roomId))
                ))
                .fullDocumentLookup(FullDocument.UPDATE_LOOKUP) // 변경된 문서 전체 조회
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
                                .isRead(document.getBoolean("isRead"))
                                .createdAt(LocalDateTime.ofInstant(document.getDate("createdAt").toInstant(),
                                        ZoneId.systemDefault()))
                                .updatedAt(LocalDateTime.ofInstant(document.getDate("updatedAt").toInstant(),
                                        ZoneId.systemDefault()))
                                .build()
                        )
        );
    }

    // 채팅 목록 실시간으로 받아오는 로직
    @Override
    public Flux<ChatRoomResponseDto> getChatRoomList(String userUuid) {

        Flux<ChatRoomResponseDto> initialData = getInitialChatRooms(userUuid);

        ChangeStreamOptions options = ChangeStreamOptions.builder()
                .filter(Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("operationType").in(
                                OperationType.INSERT.getValue(),
                                OperationType.UPDATE.getValue(),
                                OperationType.REPLACE.getValue()
                        ))
                ))
                .fullDocumentLookup(FullDocument.UPDATE_LOOKUP) // 변경된 문서 전체 조회
                .build();

        Flux<ChatRoomResponseDto> updates = reactiveMongoTemplate.changeStream("chat", options, ChatRoomDocument.class)
                .map(ChangeStreamEvent::getBody)
                .map(chatRoom -> {
                    log.info("chatRoom: {}", chatRoom);

                    String partnerUuid = chatRoom.getParticipants().stream()
                            .filter(participant -> !participant.getUserUuid().equals(userUuid))
                            .map(ChatRoomDocument.Participant::getUserUuid)
                            .findFirst()
                            .orElse(null);

                    return ChatRoomResponseDto.builder()
                            .chatRoomId(chatRoom.getId())
                            .chatRoomName(chatRoom.getChatRoomName())
                            .recentMessage(chatRoom.getRecentMessage())
                            .recentMessageTime(chatRoom.getRecentMessageTime())
                            .partnerUuid(partnerUuid)
                            .partnerIsActive(chatRoom.getParticipants().stream()
                                    .anyMatch(participant -> !participant.getUserUuid().equals(userUuid) && "active".equals(participant.getStatus())))
                            .unreadCount(chatRoom.getUnreadCount())
                            .build();
                });

        return Flux.concat(initialData, updates);
    }

    // 채팅 목록 초기 데이터 조회
    private Flux<ChatRoomResponseDto> getInitialChatRooms(String userUuid) {
        return reactiveMongoTemplate.find(
                Query.query(Criteria.where("participants")
                        .elemMatch(Criteria.where("userUuid").is(userUuid).and("status").ne("inactive"))), // 조건 추가
                ChatRoomDocument.class
        ).map(chatRoom -> ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getId())
                .chatRoomName(chatRoom.getChatRoomName())
                .recentMessage(chatRoom.getRecentMessage())
                .recentMessageTime(chatRoom.getRecentMessageTime())
                .partnerUuid(
                        chatRoom.getParticipants().stream()
                                .filter(participant -> !participant.getUserUuid().equals(userUuid))
                                .map(ChatRoomDocument.Participant::getUserUuid)
                                .findFirst()
                                .orElse(null)
                )
                .partnerIsActive(chatRoom.getParticipants().stream()
                        .anyMatch(participant -> !participant.getUserUuid().equals(userUuid) && "active".equals(participant.getStatus())))
                .unreadCount(chatRoom.getUnreadCount())
                .build());
    }

    // 채팅방 읽음 처리
    @Override
    public Mono<Void> updateRead(String roomId, String userUuid) {
        return reactiveMongoTemplate.updateMulti(
                Query.query(Criteria.where("roomId").is(roomId)
                        .and("senderUuid").ne(userUuid)
                        .and("isRead").is(false)),
                Update.update("isRead", true),
                ChatMessageDocument.class,
                "chatMessage"
        ).then(reactiveMongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is(roomId)),
                new Update().set("unreadCount", 0),
                ChatRoomDocument.class,
                "chat"
        ).then());
    }

    @Override
    public Mono<Void> leaveChatRoom(String roomId, String userUuid) {
        return reactiveMongoTemplate.findById(roomId, ChatRoomDocument.class, "chat")
                .flatMap(chatRoom -> {

                    List<ChatRoomDocument.Participant> updatedParticipants = chatRoom.getParticipants().stream()
                            .map(participant -> {
                                if (participant.getUserUuid().equals(userUuid)) {
                                    return ChatRoomDocument.Participant.builder()
                                            .userUuid(participant.getUserUuid())
                                            .status("inactive")
                                            .build();
                                }
                                return participant;
                            })
                            .toList();

                    ChatRoomDocument updatedChatRoom = ChatRoomDocument.builder()
                            .id(chatRoom.getId())
                            .chatRoomName(chatRoom.getChatRoomName())
                            .recentMessage(chatRoom.getRecentMessage())
                            .recentMessageTime(chatRoom.getRecentMessageTime())
                            .unreadCount(chatRoom.getUnreadCount())
                            .participants(updatedParticipants)
                            .createdAt(chatRoom.getCreatedAt())
                            .build();


                    return reactiveMongoTemplate.save(updatedChatRoom, "chat");
                }).then();
    }

    // 채팅 메시지 전송
    @Override
    public Mono<ChatMessageDocument> sendMessage(SendMessageDto sendMessageDto) {
        return mongoChatMessageRepository.save(chatDtoMapper.toChatMessageDocument(sendMessageDto))
                .flatMap(savedMessage ->
                        updateChatRoomOnMessageSend(
                                savedMessage.getRoomId(),
                                savedMessage.getMessage(),
                                savedMessage.getUpdatedAt()
                        ).thenReturn(savedMessage)
                );
    }

    // 채팅 메시지 전송 시 발생하는 채팅방 업데이트 (읽지않음 수 증가 및 최근 메시지 변경)
    private Mono<ChatRoomDocument> updateChatRoomOnMessageSend(String roomId, String recentMessage, LocalDateTime recentMessageTime) {
        return reactiveMongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is(roomId)),
                new Update()
                        .inc("unreadCount", 1)  // unreadCount 증가
                        .set("recentMessage", recentMessage)  // recentMessage 업데이트
                        .set("recentMessageTime", recentMessageTime),  // recentMessageTime 업데이트
                ChatRoomDocument.class,
                "chat"
        );
    }

}
