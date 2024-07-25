package com.ssapick.server.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserBan is a Querydsl query type for UserBan
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserBan extends EntityPathBase<UserBan> {

    private static final long serialVersionUID = 1425550724L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserBan userBan = new QUserBan("userBan");

    public final com.ssapick.server.core.entity.QTimeEntity _super = new com.ssapick.server.core.entity.QTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QUser fromUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser toUser;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QUserBan(String variable) {
        this(UserBan.class, forVariable(variable), INITS);
    }

    public QUserBan(Path<? extends UserBan> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserBan(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserBan(PathMetadata metadata, PathInits inits) {
        this(UserBan.class, metadata, inits);
    }

    public QUserBan(Class<? extends UserBan> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fromUser = inits.isInitialized("fromUser") ? new QUser(forProperty("fromUser"), inits.get("fromUser")) : null;
        this.toUser = inits.isInitialized("toUser") ? new QUser(forProperty("toUser"), inits.get("toUser")) : null;
    }

}

