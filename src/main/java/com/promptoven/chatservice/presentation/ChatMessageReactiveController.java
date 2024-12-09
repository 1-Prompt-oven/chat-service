package com.promptoven.chatservice.presentation;

import com.promptoven.chatservice.application.ChatMessageService;
import com.promptoven.chatservice.dto.mapper.ChatDtoMapper;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.vo.out.ChatMessageResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("v1/member/chat")
@RequiredArgsConstructor
public class ChatMessageReactiveController {

    private final ChatDtoMapper chatDtoMapper;
    private final ChatMessageService chatMessageService;

    @GetMapping(value = "/new/{roomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatMessageResponseVo> getChatByRoomId(@PathVariable String roomId) {

        Flux<ChatMessageResponseDto> chatMessageResponseDtoFlux = chatMessageService.getMessageByRoomId(roomId);

        return chatDtoMapper.toChatMessageResponseVo(chatMessageResponseDtoFlux);
    }
}
