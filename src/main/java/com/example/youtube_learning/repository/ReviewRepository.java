package com.example.youtube_learning.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.youtube_learning.entity.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

//	select * from review where company_id=?;
	List<Review> findByCompanyId(Long companyId);

}
