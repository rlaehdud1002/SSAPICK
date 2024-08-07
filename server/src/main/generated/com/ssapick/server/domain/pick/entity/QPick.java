package com.ssapick.server.domain.pick.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPick is a Querydsl query type for Pick
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPick extends EntityPathBase<Pick> {

    private static final long serialVersionUID = 823893559L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPick pick = new QPick("pick");

    public final com.ssapick.server.core.entity.QTimeEntity _super = new com.ssapick.server.core.entity.QTimeEntity(this);

    public final BooleanPath alarm = createBoolean("alarm");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<HintOpen, QHintOpen> hintOpens = this.<HintOpen, QHintOpen>createList("hintOpens", HintOpen.class, QHintOpen.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAlarmSent = createBoolean("isAlarmSent");

    public final BooleanPath isMessageSend = createBoolean("isMessageSend");

    public final com.ssapick.server.domain.question.entity.QQuestion question;

    public final com.ssapick.server.domain.user.entity.QUser receiver;

    public final com.ssapick.server.domain.user.entity.QUser sender;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPick(String variable) {
        this(Pick.class, forVariable(variable), INITS);
    }

    public QPick(Path<? extends Pick> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPick(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPick(PathMetadata metadata, PathInits inits) {
        this(Pick.class, metadata, inits);
    }

    public QPick(Class<? extends Pick> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new com.ssapick.server.domain.question.entity.QQuestion(forProperty("question"), inits.get("question")) : null;
        this.receiver = inits.isInitialized("receiver") ? new com.ssapick.server.domain.user.entity.QUser(forProperty("receiver"), inits.get("receiver")) : null;
        this.sender = inits.isInitialized("sender") ? new com.ssapick.server.domain.user.entity.QUser(forProperty("sender"), inits.get("sender")) : null;
    }

}

