package com.promptoven.chatservice.infrastructure;

import com.promptoven.chatservice.document.ChatRoomDocument;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface MongoChatRepository extends MongoRepository<ChatRoomDocument, String> {

}
