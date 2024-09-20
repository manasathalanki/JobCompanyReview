package com.example.youtube_learning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.youtube_learning.entity.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long>{
	

}
