package com.example.foodstep.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlace is a Querydsl query type for Place
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlace extends EntityPathBase<Place> {

    private static final long serialVersionUID = 1816781136L;

    public static final QPlace place = new QPlace("place");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath addressRoad = createString("addressRoad");

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> dateInit = _super.dateInit;

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> dateMod = _super.dateMod;

    public final StringPath description = createString("description");

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath name = createString("name");

    public final StringPath notification = createString("notification");

    public final StringPath phone = createString("phone");

    public final EnumPath<com.example.foodstep.enums.PlaceCategory> placeCategory = createEnum("placeCategory", com.example.foodstep.enums.PlaceCategory.class);

    public final NumberPath<Float> rateAvg = createNumber("rateAvg", Float.class);

    public final ListPath<Review, QReview> reviews = this.<Review, QReview>createList("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public QPlace(String variable) {
        super(Place.class, forVariable(variable));
    }

    public QPlace(Path<? extends Place> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlace(PathMetadata metadata) {
        super(Place.class, metadata);
    }

}

