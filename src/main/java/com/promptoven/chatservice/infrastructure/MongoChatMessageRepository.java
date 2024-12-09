package com.promptoven.chatservice.infrastructure;

import com.promptoven.chatservice.document.ChatMessageDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoChatMessageRepository extends ReactiveMongoRepository<ChatMessageDocument, String> {

}
