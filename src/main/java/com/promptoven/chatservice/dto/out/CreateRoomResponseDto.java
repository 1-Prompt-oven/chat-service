package com.promptoven.chatservice.dto.out;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateRoomResponseDto {

    private String roomId;
    private String roomName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public CreateRoomResponseDto(String roomId, String roomName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
