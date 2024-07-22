package com.ssapick.server.domain.pick.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessage extends EntityPathBase<Message> {

    private static final long serialVersionUID = 407970353L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessage message = new QMessage("message");

    public final com.ssapick.server.core.entity.QTimeEntity _super = new com.ssapick.server.core.entity.QTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath fromDeleted = createBoolean("fromDeleted");

    public final com.ssapick.server.domain.user.entity.QUser fromUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAlarmSent = createBoolean("isAlarmSent");

    public final QPick pick;

    public final BooleanPath toDeleted = createBoolean("toDeleted");

    public final com.ssapick.server.domain.user.entity.QUser toUser;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMessage(String variable) {
        this(Message.class, forVariable(variable), INITS);
    }

    public QMessage(Path<? extends Message> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessage(PathMetadata metadata, PathInits inits) {
        this(Message.class, metadata, inits);
    }

    public QMessage(Class<? extends Message> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fromUser = inits.isInitialized("fromUser") ? new com.ssapick.server.domain.user.entity.QUser(forProperty("fromUser"), inits.get("fromUser")) : null;
        this.pick = inits.isInitialized("pick") ? new QPick(forProperty("pick"), inits.get("pick")) : null;
        this.toUser = inits.isInitialized("toUser") ? new com.ssapick.server.domain.user.entity.QUser(forProperty("toUser"), inits.get("toUser")) : null;
    }

}

