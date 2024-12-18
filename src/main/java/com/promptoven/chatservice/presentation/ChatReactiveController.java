package com.promptoven.chatservice.presentation;

import com.promptoven.chatservice.application.ChatReactiveService;
import com.promptoven.chatservice.document.mapper.ChatFluxMapper;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomResponseDto;
import com.promptoven.chatservice.vo.out.ChatMessageResponseVo;
import com.promptoven.chatservice.vo.out.ChatRoomResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("v1/member/chat")
@RequiredArgsConstructor
public class ChatReactiveController {

    private final ChatFluxMapper chatFluxMapper;
    private final ChatReactiveService chatReactiveService;

    @GetMapping(value = "/new/{roomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatMessageResponseVo> getChatByRoomId(@PathVariable String roomId) {

        Flux<ChatMessageResponseDto> chatMessageResponseDtoFlux = chatReactiveService.getMessageByRoomId(roomId)
                .doOnNext(chatMessageResponseDto -> log.info("chatMessageResponseDto: {}", chatMessageResponseDto));

        return chatFluxMapper.toChatMessageResponseVo(chatMessageResponseDtoFlux);
    }

    @GetMapping(value = "/chatRoomList/{userUuid}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatRoomResponseVo> getChatRoomList(@PathVariable String userUuid) {

        log.info("userUuid: {}", userUuid);

        Flux<ChatRoomResponseDto> chatRoomResponseDto = chatReactiveService.getChatRoomList(userUuid)
                .switchIfEmpty(Flux.empty()); // 값이 없으면 빈 Flux 반환

        log.info("chatRoomResponseDto: {}", chatRoomResponseDto);

        return chatFluxMapper.toChatRoomResponseVo(chatRoomResponseDto);
    }
}
