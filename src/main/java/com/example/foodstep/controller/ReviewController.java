package com.example.foodstep.controller;


import com.example.foodstep.config.RequestUser;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.review.ReviewCategoryDTO;
import com.example.foodstep.dto.review.ReviewRequestDto;
import com.example.foodstep.dto.review.ReviewResponseDto;
import com.example.foodstep.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<ReviewResponseDto> findReviewDetail(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(reviewService.findReviewDetail(id), HttpStatus.OK);
    }

    @PostMapping("/feed")
    public ResponseEntity<Slice<ReviewResponseDto>> searchFeedListReviews(@RequestBody @Valid ReviewCategoryDTO reviewCategoryDTO, @RequestUser User user) {
        return new ResponseEntity<>(reviewService.searchFeedListReviews(reviewCategoryDTO, user), HttpStatus.OK);
    }

    @PostMapping("/view/{id}")
    public ResponseEntity<String> viewReviewDeep(@PathVariable(name = "id") int id, @RequestUser User user) {
        reviewService.viewReviewDeep(id, user);
        return new ResponseEntity<>("Editing Review : Success", HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody @Valid ReviewRequestDto reviewRequestDto, @RequestUser User user) {
        reviewService.addReview(reviewRequestDto, user);
        return new ResponseEntity<>("Adding Review : Success", HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editReview(@RequestBody ReviewRequestDto reviewRequestDto){
        reviewService.editReview(reviewRequestDto);
        return new ResponseEntity<>("Editing Review : Success", HttpStatus.OK);
    }



}
