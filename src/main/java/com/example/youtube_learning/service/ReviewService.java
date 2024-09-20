package com.example.youtube_learning.service;

import java.util.List;

import com.example.youtube_learning.entity.Review;

public interface ReviewService {

	List<Review> getAllReviewsByCompanyId(Long companyId);

	Review createReviewByCompanyId(Long companyId, Review review);

	Review getByCompanyIdAndReviewId(Long companyId, Long reviewId);

	Review updateReviewByReviewIdAndCompanyId(Long companyId, Long reviewId, Review review);
	
	Boolean deleteReviewByReviewIdAndCompanyId(Long companyId, Long reviewId);
}
