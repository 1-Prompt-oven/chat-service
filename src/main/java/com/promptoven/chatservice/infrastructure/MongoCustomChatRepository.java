package com.promptoven.chatservice.infrastructure;

import com.promptoven.chatservice.dto.in.PrevMessageRequestDto;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.global.common.utils.CursorPage;


public interface MongoCustomChatRepository {

    CursorPage<ChatMessageResponseDto> getPrevMessages(PrevMessageRequestDto prevMessageRequestDto);
}
