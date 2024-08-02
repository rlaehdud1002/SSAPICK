package com.ssapick.server.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPickcoLog is a Querydsl query type for PickcoLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPickcoLog extends EntityPathBase<PickcoLog> {

    private static final long serialVersionUID = -1885765385L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPickcoLog pickcoLog = new QPickcoLog("pickcoLog");

    public final com.ssapick.server.core.entity.QTimeEntity _super = new com.ssapick.server.core.entity.QTimeEntity(this);

    public final NumberPath<Integer> change = createNumber("change", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<PickcoLogType> pickcoLogType = createEnum("pickcoLogType", PickcoLogType.class);

    public final NumberPath<Integer> remain = createNumber("remain", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QPickcoLog(String variable) {
        this(PickcoLog.class, forVariable(variable), INITS);
    }

    public QPickcoLog(Path<? extends PickcoLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPickcoLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPickcoLog(PathMetadata metadata, PathInits inits) {
        this(PickcoLog.class, metadata, inits);
    }

    public QPickcoLog(Class<? extends PickcoLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

