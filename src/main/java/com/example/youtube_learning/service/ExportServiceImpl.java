package com.example.youtube_learning.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.youtube_learning.entity.Customer;
import com.example.youtube_learning.repository.CustomerRepository;

@Service
public class ExportServiceImpl {

	@Autowired
	CustomerRepository customerRepository;

	public Workbook exportToExcel(MultipartFile file) throws IOException {

		// Create a new workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a new sheet
		Sheet sheet = workbook.createSheet("CSV Data");

		// Read the CSV file
		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String line;
			int rowIndex = 0;

			// Iterate through each line of the CSV
			while ((line = br.readLine()) != null) {
				Row row = sheet.createRow(rowIndex++);
				String[] data = line.split(",");

				// Iterate through each cell of the CSV line
				for (int i = 0; i < data.length; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(data[i]); // Set the cell value
				}
			}
		}

		// Return the workbook to be written as an Excel file
		return workbook;
	}

	// read data from csv file and store into db
	public List<Customer> convertCsvToDb(MultipartFile file) throws IOException {
		List<Customer> customerList = new ArrayList<>();
		InputStream inputStream = file.getInputStream();
		InputStreamReader reader = new InputStreamReader(inputStream);

		CSVParser parser = CSVFormat.EXCEL.withHeader().parse(reader);
		Iterable<CSVRecord> records = parser.getRecords();
		for (CSVRecord record : records) {
			Customer cs = new Customer();
			cs.setId(Integer.parseInt(record.get("id")));
			cs.setContactNo(record.get("contactNo"));
			cs.setCountry(record.get("country"));
			cs.setDob(record.get("dob"));
			cs.setEmail(record.get("email"));
			cs.setFirstName(record.get("firstName"));
			cs.setLastName(record.get("lastName"));
			cs.setGender(record.get("gender"));
			customerList.add(cs);

		}
		customerRepository.saveAll(customerList);

		return customerList;

	}

}
