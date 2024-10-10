package com.example.youtube_learning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.youtube_learning.entity.JobPosting;
import com.example.youtube_learning.exceptions.JobIdNotFound;
import com.example.youtube_learning.repository.JobPostingRepository;

@Service
public class JobServiceImpl implements JobService {

	private JobPostingRepository jobRepository;

	public JobServiceImpl(JobPostingRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Override
	public JobPosting createJob(JobPosting job) {
		return jobRepository.save(job);
	}

	@Override
	public JobPosting getJobById(long jobId) {
		
		if(jobRepository.existsById(jobId)) {
			return jobRepository.findById(jobId).get();
		}
		else
			throw new JobIdNotFound("Job Id Not found in database");
		
	}

	@Override
	public boolean deleteJob(long jobId) {
		try {
			jobRepository.deleteById(jobId);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean updateJob(long jobId, JobPosting updatedJob) {
		Optional<JobPosting> jobOptional = jobRepository.findById(jobId);
		if (jobOptional.isPresent()) {
			JobPosting job = jobOptional.get();
			job.setDescription(updatedJob.getDescription());
			job.setTitle(updatedJob.getTitle());
			job.setMinSalary(updatedJob.getMinSalary());
			job.setMaxSalary(updatedJob.getMaxSalary());
			job.setLocation(updatedJob.getLocation());
			jobRepository.save(job);
			return true;
		}
		return false;
	}

	@Override
	public List<JobPosting> getAllJobs() {
		return (List<JobPosting>) jobRepository.findAll();

	}

}
