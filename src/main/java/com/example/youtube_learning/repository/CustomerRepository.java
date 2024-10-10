package com.example.youtube_learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.youtube_learning.entity.Customer;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer,Integer> {
}