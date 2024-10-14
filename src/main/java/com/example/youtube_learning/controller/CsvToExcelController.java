package com.example.youtube_learning.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.youtube_learning.service.ExportToExcelImpl;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/export")
public class CsvToExcelController {

	@Autowired
	private ExportToExcelImpl excelImpl;

	@PostMapping(value = "/csv-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/octet-stream")
	@Operation(summary = "Convert CSV to Excel", description = "Upload a CSV file and download the result as an Excel file.")
	public ResponseEntity<?> export(@RequestParam("customersFile") MultipartFile file) throws IOException {

		Workbook workbook = excelImpl.exportToExcel(file);

		workbook.setSheetName(0, "customers-data");
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		workbook.write(outStream);
		workbook.close();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customers.xlsx");

		return ResponseEntity.ok().headers(headers)
				.body(new InputStreamResource(new ByteArrayInputStream(outStream.toByteArray())));
	}

}
