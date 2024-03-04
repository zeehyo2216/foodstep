package com.example.foodstep.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewTagMap extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Tag tag;
}