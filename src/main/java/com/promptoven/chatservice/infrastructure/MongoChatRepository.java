package com.promptoven.chatservice.infrastructure;

import com.promptoven.chatservice.document.ChatRoomDocument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


public interface MongoChatRepository extends MongoRepository<ChatRoomDocument, String> {

    Optional<ChatRoomDocument> findByParticipantsContaining(String hostUserUuid, String inviteUserUuid);


    @Query("{ 'participants.userUuid': ?0 }")
    List<ChatRoomDocument> findByParticipantUserUuid(String userUuid);
}
