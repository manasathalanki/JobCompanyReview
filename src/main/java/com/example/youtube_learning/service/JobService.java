package com.example.youtube_learning.service;

import java.util.List;

import com.example.youtube_learning.entity.Job;


public interface JobService {

	Job createJob(Job job);

	List<Job> getAllJobs();

	Job getJobById(long jobId);

	boolean deleteJob(long jobId);

	boolean updateJob(long jobId, Job updatedJob);
}
