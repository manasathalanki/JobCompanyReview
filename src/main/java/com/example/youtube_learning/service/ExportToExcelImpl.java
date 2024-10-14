package com.example.youtube_learning.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExportToExcelImpl {

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
}
