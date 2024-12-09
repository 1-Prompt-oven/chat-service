package com.promptoven.chatservice.application;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.document.ChatRoomDocument;
import com.promptoven.chatservice.document.mapper.ChatDocumentMapper;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.in.PrevMessageRequestDto;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.dto.mapper.ChatDtoMapper;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;
import com.promptoven.chatservice.dto.out.GetChatRoomResponseDto;
import com.promptoven.chatservice.global.common.utils.CursorPage;
import com.promptoven.chatservice.infrastructure.MongoChatMessageRepository;
import com.promptoven.chatservice.infrastructure.MongoChatRepository;
import com.promptoven.chatservice.infrastructure.MongoCustomChatRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatDtoMapper chatDtoMapper;
    private final ChatDocumentMapper chatDocumentMapper;
    private final MongoChatRepository mongoChatRepository;
    private final MongoChatMessageRepository mongoChatMessageRepository;
    private final MongoCustomChatRepository mongoCustomChatRepository;

    @Override
    public CreateRoomResponseDto createChatRoom(CreateRoomRequestDto createRoomRequestDto) {

        ChatRoomDocument chatRoomDocument = chatDtoMapper.toChatRoomDocument(createRoomRequestDto);

        return chatDocumentMapper.toCreateRoomResponseDto(mongoChatRepository.save(chatRoomDocument));
    }

    @Override
    public Mono<ChatMessageDocument> sendMessage(SendMessageDto sendMessageDto) {
        return mongoChatMessageRepository.save(chatDtoMapper.toChatMessageDocument(sendMessageDto));
    }

    @Override
    public CursorPage<ChatMessageResponseDto> getPrevMessages(PrevMessageRequestDto prevMessageRequestDto) {
        return mongoCustomChatRepository.getPrevMessages(prevMessageRequestDto);
    }

    @Override
    public List<GetChatRoomResponseDto> getChatRoomList(String userUuid) {

        List<ChatRoomDocument> chatRoomList = mongoChatRepository.findByHostUserUuidOrInviteUserUuid(userUuid, userUuid);

        return chatRoomList.stream()
                .map(chatRoom -> {
                    // 상대방 UUID 결정
                    String partnerUuid = chatRoom.getHostUserUuid().equals(userUuid)
                            ? chatRoom.getInviteUserUuid()
                            : chatRoom.getHostUserUuid();

                    return new GetChatRoomResponseDto(
                            chatRoom.getChatRoomName(),
                            partnerUuid
                    );
                })
                .collect(Collectors.toList());
    }
}
