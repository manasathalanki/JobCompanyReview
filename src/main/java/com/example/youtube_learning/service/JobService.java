package com.example.youtube_learning.service;

import java.util.List;

import com.example.youtube_learning.entity.JobPosting;


public interface JobService {

	JobPosting createJob(JobPosting job);

	List<JobPosting> getAllJobs();

	JobPosting getJobById(long jobId);

	boolean deleteJob(long jobId);

	boolean updateJob(long jobId, JobPosting updatedJob);
}
