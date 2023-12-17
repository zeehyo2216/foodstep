package com.example.foodstep.controller;


import com.example.foodstep.config.RequestUser;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.review.ReviewDto;
import com.example.foodstep.dto.review.ReviewRequestDto;
import com.example.foodstep.service.ReviewService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ReviewDto> findReviewDetail(@PathVariable(name = "id") int id) throws NoSuchElementException {
        return new ResponseEntity<>(reviewService.findReviewDetail(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody ReviewRequestDto reviewRequestDto, @RequestUser User user) {
        reviewService.addReview(reviewRequestDto, user);
        return new ResponseEntity<>("Adding Review : Success", HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editReview(@RequestBody ReviewDto reviewDto) throws NoSuchElementException {
        reviewService.editReview(reviewDto);
        return new ResponseEntity<>("Editing Review : Success", HttpStatus.OK);
    }



}
