package com.example.youtube_learning.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.youtube_learning.entity.Review;
import com.example.youtube_learning.service.ReviewService;

@RestController
@RequestMapping("/companies/{companyId}")
public class ReviewController {

	private ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@GetMapping("/reviews")
	public ResponseEntity<List<Review>> getAllreviews(@PathVariable(value = "companyId") long companyId) {
		return new ResponseEntity<>(reviewService.getAllReviewsByCompanyId(companyId), HttpStatus.OK);
	}

	@PostMapping("/reviews")
	public ResponseEntity<Review> addReview(@PathVariable(value = "companyId") Long companyId,
			@RequestBody Review review) {
		return new ResponseEntity<>(reviewService.createReviewByCompanyId(companyId, review), HttpStatus.OK);
	}
	
	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<Review> getReview(@PathVariable(value = "companyId") Long companyId,
			@PathVariable(value = "reviewId") Long reviewId){
		return new ResponseEntity<>(reviewService.getByCompanyIdAndReviewId(companyId, reviewId), HttpStatus.OK);
	}
	
	@PutMapping("/reviews/{reviewId}")
	public ResponseEntity<Review> updateReview(@PathVariable(value = "companyId") Long companyId,
			@PathVariable(value = "reviewId") Long reviewId,@RequestBody Review review){
		return new ResponseEntity<>(reviewService.updateReviewByReviewIdAndCompanyId(companyId,reviewId, review), HttpStatus.OK);
	}
	
	@DeleteMapping("/reviews/{reviewId}")
	public ResponseEntity<Boolean> deleteReview(@PathVariable(value = "companyId") Long companyId,
			@PathVariable(value = "reviewId") Long reviewId){
		return new ResponseEntity<>(reviewService.deleteReviewByReviewIdAndCompanyId(companyId,reviewId), HttpStatus.OK);
	}
}
