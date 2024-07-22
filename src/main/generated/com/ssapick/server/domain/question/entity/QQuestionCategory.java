package com.ssapick.server.domain.question.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuestionCategory is a Querydsl query type for QuestionCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionCategory extends EntityPathBase<QuestionCategory> {

    private static final long serialVersionUID = -2098694945L;

    public static final QQuestionCategory questionCategory = new QQuestionCategory("questionCategory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath thumbnail = createString("thumbnail");

    public QQuestionCategory(String variable) {
        super(QuestionCategory.class, forVariable(variable));
    }

    public QQuestionCategory(Path<? extends QuestionCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestionCategory(PathMetadata metadata) {
        super(QuestionCategory.class, metadata);
    }

}

