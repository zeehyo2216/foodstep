package com.example.foodstep.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewTagMap is a Querydsl query type for ReviewTagMap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewTagMap extends EntityPathBase<ReviewTagMap> {

    private static final long serialVersionUID = -351546255L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewTagMap reviewTagMap = new QReviewTagMap("reviewTagMap");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReview review;

    public final QTag tag;

    public QReviewTagMap(String variable) {
        this(ReviewTagMap.class, forVariable(variable), INITS);
    }

    public QReviewTagMap(Path<? extends ReviewTagMap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewTagMap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewTagMap(PathMetadata metadata, PathInits inits) {
        this(ReviewTagMap.class, metadata, inits);
    }

    public QReviewTagMap(Class<? extends ReviewTagMap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

