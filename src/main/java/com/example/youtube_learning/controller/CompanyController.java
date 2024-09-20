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

import com.example.youtube_learning.entity.Company;
import com.example.youtube_learning.service.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	private final CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping("/")
	public ResponseEntity<List<Company>> getAllCompanies() {
		List<Company> companies = companyService.getAllCompanies();
		return ResponseEntity.ok(companies);
	}

	@PostMapping("/")
	public ResponseEntity<Company> createCompany(@RequestBody Company company) {
		Company createdCompany = companyService.createCompany(company);
		return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
	}

	@GetMapping("/{companyId}")
	public ResponseEntity<Company> getByCompanyId(@PathVariable(value = "companyId") long companyId) {
		Company company = companyService.getCompanyById(companyId);
		if (company != null) {
			return ResponseEntity.ok(company);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{companyId}")
	public ResponseEntity<Company> updateCompany(@PathVariable(value = "companyId") long companyId,
			@RequestBody Company updatedCompany) {
		Company updated = companyService.updateCompany(companyId, updatedCompany);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{companyId}")
	public ResponseEntity<Void> deleteCompany(@PathVariable(value = "companyId") long companyId) {
		boolean deleted = companyService.deleteCompanyById(companyId);
		if (deleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
