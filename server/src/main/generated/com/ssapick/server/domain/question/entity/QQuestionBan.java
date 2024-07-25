package com.ssapick.server.domain.question.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionBan is a Querydsl query type for QuestionBan
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionBan extends EntityPathBase<QuestionBan> {

    private static final long serialVersionUID = 858692494L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestionBan questionBan = new QQuestionBan("questionBan");

    public final com.ssapick.server.core.entity.QTimeEntity _super = new com.ssapick.server.core.entity.QTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QQuestion question;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.ssapick.server.domain.user.entity.QUser user;

    public QQuestionBan(String variable) {
        this(QuestionBan.class, forVariable(variable), INITS);
    }

    public QQuestionBan(Path<? extends QuestionBan> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestionBan(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestionBan(PathMetadata metadata, PathInits inits) {
        this(QuestionBan.class, metadata, inits);
    }

    public QQuestionBan(Class<? extends QuestionBan> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new QQuestion(forProperty("question"), inits.get("question")) : null;
        this.user = inits.isInitialized("user") ? new com.ssapick.server.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

