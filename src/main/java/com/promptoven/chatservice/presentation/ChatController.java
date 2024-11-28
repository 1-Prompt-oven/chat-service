package com.promptoven.chatservice.presentation;

import com.promptoven.chatservice.application.ChatService;
import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.mapper.ChatDtoMapper;
import com.promptoven.chatservice.global.common.response.BaseResponse;
import com.promptoven.chatservice.vo.in.CreateRoomRequestVo;
import com.promptoven.chatservice.vo.in.SendMessageVo;
import com.promptoven.chatservice.vo.mapper.ChatVoMapper;
import com.promptoven.chatservice.vo.out.CreateRoomResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v1/member/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatVoMapper chatVoMapper;
    private final ChatDtoMapper chatDtoMapper;

    @PostMapping("/createRoom")
    public BaseResponse<CreateRoomResponseVo> createChat(@RequestBody CreateRoomRequestVo createRoomRequestVo) {

        CreateRoomRequestDto createRoomRequestDto = chatVoMapper.toCreateRoomRequestDto(createRoomRequestVo);

        return new BaseResponse<>(
                chatDtoMapper.toCreateRoomResponseVo(chatService.createChatRoom(createRoomRequestDto)));
    }

    @PostMapping("/send")
    public Mono<ChatMessageDocument> sendChatMessage(@RequestBody SendMessageVo sendMessageVo) {
        log.info("sendMessageVo: {}", sendMessageVo);
        return chatService.sendMessage(chatVoMapper.toSendMessageDto(sendMessageVo));
    }
}
