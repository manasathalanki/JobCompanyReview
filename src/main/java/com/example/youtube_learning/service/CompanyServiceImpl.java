package com.example.youtube_learning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.youtube_learning.entity.Company;
import com.example.youtube_learning.exceptions.CompanyIdNotFOund;
import com.example.youtube_learning.repository.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	private CompanyRepository companyRepository;

	public CompanyServiceImpl(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public Company createCompany(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public List<Company> getAllCompanies() {
		return (List<Company>) companyRepository.findAll();
	}

	@Override
	public Company getCompanyById(long companyId) {

		if (companyRepository.existsById(companyId)) {

			return companyRepository.findById(companyId).get();
		} else
			throw new CompanyIdNotFOund("Company Id not present in the database");

	}

	@Override
	public boolean deleteCompanyById(long companyId) {
		try {
			companyRepository.deleteById(companyId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Company updateCompany(long companyId, Company updatedCompany) {
		Optional<Company> companyOptional = companyRepository.findById(companyId);
		if (companyOptional.isPresent()) {
			Company company = companyOptional.get();
			company.setName(updatedCompany.getName());
			company.setDescription(updatedCompany.getDescription());
			company.setJobs(updatedCompany.getJobs());
			company.setReviews(updatedCompany.getReviews());
			companyRepository.save(company);
			return company;
		}
		return null;
	}

}
