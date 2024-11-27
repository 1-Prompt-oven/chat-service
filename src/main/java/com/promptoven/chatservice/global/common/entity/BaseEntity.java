package com.promptoven.chatservice.global.common.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
public abstract class BaseEntity {

    @CreatedDate
    @Field("created_at")
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    protected LocalDateTime updatedAt;

}