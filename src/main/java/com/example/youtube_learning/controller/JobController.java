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

import com.example.youtube_learning.entity.JobPosting;
import com.example.youtube_learning.service.JobService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Jobs")
public class JobController {

	private final JobService jobService;

	public JobController(JobService jobService) {
		this.jobService = jobService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<JobPosting>> retrieveAllJobs() {
		List<JobPosting> jobs = jobService.getAllJobs();
		return ResponseEntity.ok(jobs);
	}

	@PostMapping("/create")
	public ResponseEntity<JobPosting> createJob(@RequestBody JobPosting job) {
		JobPosting createdJob = jobService.createJob(job);
		return new ResponseEntity<>(createdJob, HttpStatus.CREATED); // Return 201 CREATED
	}

	@GetMapping("/{jobId}")
	public ResponseEntity<JobPosting> getByJobId(@PathVariable(value = "jobId") long jobId) {
		JobPosting job = jobService.getJobById(jobId);
		if (job != null) {
			return ResponseEntity.ok(job); // Return 200 OK if job is found
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 NOT FOUND
		}
	}

	@PutMapping("/{jobId}")
	public ResponseEntity<Boolean> updateJob(@PathVariable(value = "jobId") long jobId, @RequestBody JobPosting updatedJob) {
		boolean updated = jobService.updateJob(jobId, updatedJob);
		if (updated) {
			return ResponseEntity.ok(true); // Return 200 OK if update is successful
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 NOT FOUND if job does not exist
		}
	}

	@DeleteMapping("/{jobId}")
	public ResponseEntity<Void> deleteJob(@PathVariable(value = "jobId") long jobId) {
		boolean deleted = jobService.deleteJob(jobId);
		if (deleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 NO CONTENT if delete is successful
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 NOT FOUND if job does not exist
		}
	}
}
