package com.promptoven.chatservice.infrastructure;

import com.promptoven.chatservice.document.ChatRoomDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoChatRepository extends MongoRepository<ChatRoomDocument, String> {

}
