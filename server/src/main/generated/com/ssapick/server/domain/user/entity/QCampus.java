package com.ssapick.server.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCampus is a Querydsl query type for Campus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCampus extends EntityPathBase<Campus> {

    private static final long serialVersionUID = 2008127391L;

    public static final QCampus campus = new QCampus("campus");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Short> section = createNumber("section", Short.class);

    public QCampus(String variable) {
        super(Campus.class, forVariable(variable));
    }

    public QCampus(Path<? extends Campus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCampus(PathMetadata metadata) {
        super(Campus.class, metadata);
    }

}

