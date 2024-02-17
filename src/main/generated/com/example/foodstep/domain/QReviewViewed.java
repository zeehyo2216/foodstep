package com.example.foodstep.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewViewed is a Querydsl query type for ReviewViewed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewViewed extends EntityPathBase<ReviewViewed> {

    private static final long serialVersionUID = -286918893L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewViewed reviewViewed = new QReviewViewed("reviewViewed");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> dateInit = _super.dateInit;

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> dateMod = _super.dateMod;

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final QReview review;

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final QUser user;

    public QReviewViewed(String variable) {
        this(ReviewViewed.class, forVariable(variable), INITS);
    }

    public QReviewViewed(Path<? extends ReviewViewed> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewViewed(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewViewed(PathMetadata metadata, PathInits inits) {
        this(ReviewViewed.class, metadata, inits);
    }

    public QReviewViewed(Class<? extends ReviewViewed> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

