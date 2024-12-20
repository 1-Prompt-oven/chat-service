package com.promptoven.chatservice.application;

import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.in.PrevMessageRequestDto;
import com.promptoven.chatservice.dto.in.SendMessageDto;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomInfoResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomResponseDto;
import com.promptoven.chatservice.dto.out.CreateRoomResponseDto;
import com.promptoven.chatservice.global.common.utils.CursorPage;
import java.util.List;
import reactor.core.publisher.Mono;

public interface ChatService {

    CreateRoomResponseDto createChatRoom(CreateRoomRequestDto createRoomRequestDto);

    CursorPage<ChatMessageResponseDto> getPrevMessages(PrevMessageRequestDto prevMessageRequestDto);

    ChatRoomInfoResponseDto getChatRoomInfo(String chatRoomIdm, String userUuid);

    List<ChatRoomResponseDto> getChatRoomList(String userUuid);
}



