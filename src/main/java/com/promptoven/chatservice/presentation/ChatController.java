package com.promptoven.chatservice.presentation;

import com.promptoven.chatservice.application.ChatReactiveService;
import com.promptoven.chatservice.application.ChatService;
import com.promptoven.chatservice.document.ChatMessageDocument;
import com.promptoven.chatservice.dto.in.CreateRoomRequestDto;
import com.promptoven.chatservice.dto.in.PrevMessageRequestDto;
import com.promptoven.chatservice.dto.mapper.ChatDtoMapper;
import com.promptoven.chatservice.dto.out.ChatMessageResponseDto;
import com.promptoven.chatservice.dto.out.ChatRoomInfoResponseDto;
import com.promptoven.chatservice.global.common.response.BaseResponse;
import com.promptoven.chatservice.global.common.utils.CursorPage;
import com.promptoven.chatservice.vo.in.CreateRoomRequestVo;
import com.promptoven.chatservice.vo.in.SendMessageVo;
import com.promptoven.chatservice.vo.mapper.ChatVoMapper;
import com.promptoven.chatservice.vo.out.ChatMessageResponseVo;
import com.promptoven.chatservice.vo.out.ChatRoomInfoResponseVo;
import com.promptoven.chatservice.vo.out.CreateRoomResponseVo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/member/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatReactiveService chatReactiveService;
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
        return chatReactiveService.sendMessage(chatVoMapper.toSendMessageDto(sendMessageVo));
    }

    @GetMapping(value = "/previous/{roomId}")
    public BaseResponse<CursorPage<ChatMessageResponseVo>> getChatByRoomId(@PathVariable String roomId,
            @RequestParam(required = false) String lastObjectId, @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer page) {

        CursorPage<ChatMessageResponseDto> prevMessages = chatService.getPrevMessages(PrevMessageRequestDto.builder()
                .roomId(roomId)
                .lastObjectId(lastObjectId)
                .pageSize(pageSize)
                .page(page)
                .build());

        List<ChatMessageResponseVo> chatMessageResponseVoList = prevMessages.getContent().stream()
                .map(chatDtoMapper::toChatMessageResponseVo)
                .toList();

        CursorPage<ChatMessageResponseVo> chatMessageResponseVoCursorPage = CursorPage.<ChatMessageResponseVo>builder()
                .content(chatMessageResponseVoList)
                .lastObjectId(prevMessages.getLastObjectId())
                .hasNext(prevMessages.getHasNext())
                .pageSize(prevMessages.getPageSize())
                .page(prevMessages.getPage())
                .build();

        return new BaseResponse<>(chatMessageResponseVoCursorPage);
    }

    @PutMapping("/updateRead/{roomId}")
    public Mono<Void> updateRead(@PathVariable String roomId, @RequestParam String userUuid) {
        return chatReactiveService.updateRead(roomId, userUuid);
    }

    @DeleteMapping("/{roomId}")
    public Mono<Void> leaveChatRoom(@PathVariable String roomId, @RequestParam String userUuid) {
        return chatReactiveService.leaveChatRoom(roomId, userUuid);
    }

    @GetMapping("/{roomId}")
    public BaseResponse<ChatRoomInfoResponseVo> getChatRoom(@PathVariable String roomId, @RequestParam String userUuid) {
        ChatRoomInfoResponseDto chatRoomInfoResponseDto = chatService.getChatRoomInfo(roomId, userUuid);
        return new BaseResponse<>(chatDtoMapper.toChatRoomInfoResponseVo(chatRoomInfoResponseDto));
    }
}
