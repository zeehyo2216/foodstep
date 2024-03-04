package com.example.foodstep.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReviewImage extends BaseEntity {
    @NotNull
    private String imagePath;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;
}
