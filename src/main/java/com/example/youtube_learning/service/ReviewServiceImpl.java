package com.example.youtube_learning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.youtube_learning.entity.Company;
import com.example.youtube_learning.entity.Review;
import com.example.youtube_learning.repository.CompanyRepository;
import com.example.youtube_learning.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	private ReviewRepository reviewRepository;
	private CompanyRepository companyRepository;

	public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyRepository companyRepository) {
		this.reviewRepository = reviewRepository;
		this.companyRepository = companyRepository;
	}

	@Override
	public List<Review> getAllReviewsByCompanyId(Long companyId) {
		return reviewRepository.findByCompanyId(companyId);
	}

	@Override
	public Review createReviewByCompanyId(Long companyId, Review review) {
		Optional<Company> company = companyRepository.findById(companyId);
		if (company.isPresent()) {
			review.setCompany(company.get());
			return reviewRepository.save(review);
		}
		return null;
	}

	@Override
	public Review getByCompanyIdAndReviewId(Long companyId, Long reviewId) {
		List<Review> reviews = reviewRepository.findByCompanyId(companyId);

		return reviews.stream().filter(review -> review.getId().equals(reviewId)).findFirst().orElse(null);
	}

	@Override
	public Review updateReviewByReviewIdAndCompanyId(Long companyId, Long reviewId, Review review) {
		Optional<Company> company = companyRepository.findById(companyId);
		if (findCompanyId(companyId)) {
			review.setCompany(company.get());
			return reviewRepository.save(review);
		}
		return null;
	}

	@Override
	public Boolean deleteReviewByReviewIdAndCompanyId(Long companyId, Long reviewId) {
		if (findCompanyId(companyId) && reviewRepository.existsById(reviewId)) {
			Review review = reviewRepository.findById(reviewId).orElse(null);
			Company company = review.getCompany();
			company.getReviews().remove(review);
			Optional<Company> companyOptional = companyRepository.findById(companyId);
			if (companyOptional.isPresent()) {
				Company companyOld = companyOptional.get();
//				companyOld.setName(company.getName());
//				companyOld.setDescription(company.getDescription());
//				companyOld.setJobs(company.getJobs());
				companyOld.setReviews(company.getReviews());
				companyRepository.save(companyOld);
				
			}
			reviewRepository.deleteById(reviewId);
			return true;

		}
		return false;
	}

	public Boolean findCompanyId(Long companyId) {
		Optional<Company> company = companyRepository.findById(companyId);
		if (company.isPresent())
			return true;
		else
			return false;
	}

}
