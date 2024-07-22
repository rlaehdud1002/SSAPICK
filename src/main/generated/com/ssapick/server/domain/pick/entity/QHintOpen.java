package com.ssapick.server.domain.pick.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHintOpen is a Querydsl query type for HintOpen
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHintOpen extends EntityPathBase<HintOpen> {

    private static final long serialVersionUID = -1249640313L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHintOpen hintOpen = new QHintOpen("hintOpen");

    public final com.ssapick.server.core.entity.QTimeEntity _super = new com.ssapick.server.core.entity.QTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QHint hint;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPick pick;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QHintOpen(String variable) {
        this(HintOpen.class, forVariable(variable), INITS);
    }

    public QHintOpen(Path<? extends HintOpen> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHintOpen(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHintOpen(PathMetadata metadata, PathInits inits) {
        this(HintOpen.class, metadata, inits);
    }

    public QHintOpen(Class<? extends HintOpen> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hint = inits.isInitialized("hint") ? new QHint(forProperty("hint"), inits.get("hint")) : null;
        this.pick = inits.isInitialized("pick") ? new QPick(forProperty("pick"), inits.get("pick")) : null;
    }

}

