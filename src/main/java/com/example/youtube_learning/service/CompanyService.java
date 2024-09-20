package com.example.youtube_learning.service;

import java.util.List;

import com.example.youtube_learning.entity.Company;

public interface CompanyService {

	Company createCompany(Company company);
	List<Company> getAllCompanies();
	Company getCompanyById(long companyId);
	boolean deleteCompanyById(long companyId);
	Company updateCompany(long companyId,Company updatedCompany);
}
