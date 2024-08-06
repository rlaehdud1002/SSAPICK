package com.ssapick.server.domain.pick.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHint is a Querydsl query type for Hint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHint extends EntityPathBase<Hint> {

    private static final long serialVersionUID = 823655581L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHint hint = new QHint("hint");

    public final com.ssapick.server.core.entity.QTimeEntity _super = new com.ssapick.server.core.entity.QTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<HintType> hintType = createEnum("hintType", HintType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.ssapick.server.domain.user.entity.QUser user;

    public QHint(String variable) {
        this(Hint.class, forVariable(variable), INITS);
    }

    public QHint(Path<? extends Hint> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHint(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHint(PathMetadata metadata, PathInits inits) {
        this(Hint.class, metadata, inits);
    }

    public QHint(Class<? extends Hint> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.ssapick.server.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

