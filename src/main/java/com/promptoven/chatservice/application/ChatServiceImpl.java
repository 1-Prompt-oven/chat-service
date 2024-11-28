package com.promptoven.chatservice.application;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.document.ChatRoomDocument;
import com.promptoven.chatservice.document.mapper.ChatDocumentMapper;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.dto.mapper.ChatDtoMapper;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;
import com.promptoven.chatservice.infrastructure.MongoChatMessageRepository;
import com.promptoven.chatservice.infrastructure.MongoChatRepository;
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

    @Override
    public CreateRoomResponseDto createChatRoom(CreateRoomRequestDto createRoomRequestDto) {

        ChatRoomDocument chatRoomDocument = chatDtoMapper.toChatRoomDocument(
                chatDtoMapper.toChatRoomDto(createRoomRequestDto));

        return chatDocumentMapper.toCreateRoomResponseDto(mongoChatRepository.save(chatRoomDocument));
    }

    @Override
    public Mono<ChatMessageDocument> sendMessage(SendMessageDto sendMessageDto) {
        return mongoChatMessageRepository.save(chatDtoMapper.toChatMessageDocument(sendMessageDto));
    }
}
