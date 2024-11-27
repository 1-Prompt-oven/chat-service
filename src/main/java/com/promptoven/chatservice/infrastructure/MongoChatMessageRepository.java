package com.promptoven.chatservice.infrastructure;

import com.promptoven.chatservice.document.ChatMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {

}
