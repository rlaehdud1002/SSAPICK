package com.ssapick.server.domain.question.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionRegistration is a Querydsl query type for QuestionRegistration
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionRegistration extends EntityPathBase<QuestionRegistration> {

    private static final long serialVersionUID = -736573414L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestionRegistration questionRegistration = new QQuestionRegistration("questionRegistration");

    public final com.ssapick.server.domain.user.entity.QUser author;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QQuestionCategory questionCategory;

    public QQuestionRegistration(String variable) {
        this(QuestionRegistration.class, forVariable(variable), INITS);
    }

    public QQuestionRegistration(Path<? extends QuestionRegistration> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestionRegistration(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestionRegistration(PathMetadata metadata, PathInits inits) {
        this(QuestionRegistration.class, metadata, inits);
    }

    public QQuestionRegistration(Class<? extends QuestionRegistration> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.ssapick.server.domain.user.entity.QUser(forProperty("author"), inits.get("author")) : null;
        this.questionCategory = inits.isInitialized("questionCategory") ? new QQuestionCategory(forProperty("questionCategory")) : null;
    }

}

