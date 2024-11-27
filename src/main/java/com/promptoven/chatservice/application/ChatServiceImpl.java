package com.promptoven.chatservice.application;

import com.promptoven.chatservice.document.ChatRoomDocument;
import com.promptoven.chatservice.document.mapper.ChatDocumentMapper;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.mapper.ChatDtoMapper;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;
import com.promptoven.chatservice.infrastructure.MongoChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatDtoMapper chatDtoMapper;
    private final ChatDocumentMapper chatDocumentMapper;
    private final MongoChatRepository mongoChatRepository;

    @Override
    public CreateRoomResponseDto createChatRoom(CreateRoomRequestDto createRoomRequestDto) {

        ChatRoomDocument chatRoomDocument = chatDtoMapper.toChatRoomDocument(chatDtoMapper.toChatRoomDto(createRoomRequestDto));

        return chatDocumentMapper.toCreateRoomResponseDto(mongoChatRepository.save(chatRoomDocument));
    }
}
