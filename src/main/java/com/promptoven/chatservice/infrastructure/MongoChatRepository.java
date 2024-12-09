package com.promptoven.chatservice.infrastructure;

import com.promptoven.chatservice.document.ChatRoomDocument;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoChatRepository extends MongoRepository<ChatRoomDocument, String> {
    List<ChatRoomDocument> findByHostUserUuidOrInviteUserUuid(String userUuid_1, String userUuid_2);
}
