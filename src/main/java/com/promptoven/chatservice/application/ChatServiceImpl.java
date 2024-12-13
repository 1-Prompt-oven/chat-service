package com.promptoven.chatservice.application;

import static com.promptoven.chatservice.global.common.response.BaseResponseStatus.NO_EXIST_CHAT_ROOM;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.document.ChatRoomDocument;
import com.promptoven.chatservice.document.mapper.ChatDocumentMapper;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.in.PrevMessageRequestDto;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.dto.mapper.ChatDtoMapper;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomInfoResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomResponseDto;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;
import com.promptoven.chatservice.global.common.utils.CursorPage;
import com.promptoven.chatservice.global.error.BaseException;
import com.promptoven.chatservice.infrastructure.MongoChatMessageRepository;
import com.promptoven.chatservice.infrastructure.MongoChatRepository;
import com.promptoven.chatservice.infrastructure.MongoCustomChatRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatDtoMapper chatDtoMapper;
    private final ChatDocumentMapper chatDocumentMapper;
    private final MongoChatRepository mongoChatRepository;
    private final MongoCustomChatRepository mongoCustomChatRepository;

    @Override
    public CreateRoomResponseDto createChatRoom(CreateRoomRequestDto createRoomRequestDto) {

        String hostUserUuid = createRoomRequestDto.getHostUserUuid();
        String inviteUserUuid = createRoomRequestDto.getInviteUserUuid();

        Optional<ChatRoomDocument> existingRoom = mongoChatRepository.findByParticipantsContaining(hostUserUuid, inviteUserUuid);

        if (existingRoom.isPresent()) {
            ChatRoomDocument chatRoom = existingRoom.get();
            return new CreateRoomResponseDto(chatRoom.getId(), chatRoom.getChatRoomName());
        }

        ChatRoomDocument chatRoomDocument = chatDtoMapper.toChatRoomDocument(createRoomRequestDto);

        return chatDocumentMapper.toCreateRoomResponseDto(mongoChatRepository.save(chatRoomDocument));
    }

    @Override
    public CursorPage<ChatMessageResponseDto> getPrevMessages(PrevMessageRequestDto prevMessageRequestDto) {
        return mongoCustomChatRepository.getPrevMessages(prevMessageRequestDto);
    }

    @Override
    public ChatRoomInfoResponseDto getChatRoomInfo(String chatRoomId, String userUuid) {

        ChatRoomDocument chatRoomDocument = mongoChatRepository.findById(chatRoomId).orElseThrow(() -> new BaseException(NO_EXIST_CHAT_ROOM));

        String partnerUuid = chatRoomDocument.getParticipants().stream()
                .map(ChatRoomDocument.Participant::getUserUuid)
                .filter(uuid -> !uuid.equals(userUuid))
                .findFirst()
                .orElse(null);

        Boolean partnerIsActive = chatRoomDocument.getParticipants().stream()
                .anyMatch(participant -> !participant.getUserUuid().equals(userUuid) && "active".equals(participant.getStatus()));

        return ChatRoomInfoResponseDto.builder()
                .chatRoomId(chatRoomDocument.getId())
                .chatRoomName(chatRoomDocument.getChatRoomName())
                .partnerUuid(partnerUuid)
                .partnerIsActive(partnerIsActive)
                .recentMessage(chatRoomDocument.getRecentMessage())
                .recentMessageTime(chatRoomDocument.getRecentMessageTime())
                .unreadCount(chatRoomDocument.getUnreadCount())
                .build();
    }

}
