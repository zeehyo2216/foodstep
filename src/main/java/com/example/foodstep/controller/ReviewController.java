package com.example.foodstep.controller;


import com.example.foodstep.config.RequestUser;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.review.ReviewAddRequestDto;
import com.example.foodstep.dto.review.ReviewCategoryDTO;
import com.example.foodstep.dto.review.ReviewDto;
import com.example.foodstep.dto.review.ReviewResponseDto;
import com.example.foodstep.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/all")
    public ResponseEntity<List<ReviewDto>> reviewListAll() {
        return new ResponseEntity<>(reviewService.findAllReviews(), HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<ReviewResponseDto> findReviewDetail(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(reviewService.findReviewDetail(id), HttpStatus.OK);
    }

    @PostMapping("/feed")
    public ResponseEntity<Slice<ReviewResponseDto>> searchFeedListReviews(@RequestBody @Valid ReviewCategoryDTO reviewCategoryDTO, @RequestUser User user) {
        return new ResponseEntity<>(reviewService.searchFeedListReviews(reviewCategoryDTO, user), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody @Valid ReviewAddRequestDto reviewAddRequestDto, @RequestUser User user) {
        reviewService.addReview(reviewAddRequestDto, user);
        return new ResponseEntity<>("Adding Review : Success", HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editReview(@RequestBody ReviewDto reviewDto) throws NoSuchElementException {
        reviewService.editReview(reviewDto);
        return new ResponseEntity<>("Editing Review : Success", HttpStatus.OK);
    }



}
