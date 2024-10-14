package com.example.youtube_learning.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.youtube_learning.entity.Customer;
import com.example.youtube_learning.service.ExportServiceImpl;
import com.itextpdf.text.DocumentException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/export")
@Tag(description = "Export/convert one form of data to another", name = "Export")
public class ExportController {

	@Autowired
	private ExportServiceImpl exportImpl;

	@PostMapping(value = "/csv-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/octet-stream")
	@Operation(summary = "Convert CSV to Excel", description = "Upload a CSV file and download the result as an Excel file.")
	public ResponseEntity<?> export(@RequestParam("customersFile") MultipartFile file) throws IOException {

		Workbook workbook = exportImpl.exportToExcel(file);

		workbook.setSheetName(0, "customers-data");
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		workbook.write(outStream);
		workbook.close();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customers.xlsx");

		return ResponseEntity.ok().headers(headers)
				.body(new InputStreamResource(new ByteArrayInputStream(outStream.toByteArray())));
	}

	@PostMapping(value = "/csv-db", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Convert CSV to DB", description = "Upload a CSV file and return the stored data into the db")
	public ResponseEntity<?> uploadCsvToDB(@RequestParam("customersFile") MultipartFile file) throws IOException {

		List<Customer> customerList = exportImpl.convertCsvToDb(file);

		return ResponseEntity.ok().body("Stored SuccessFully");
	}

	@GetMapping(value = "/db-pdf", produces = "application/pdf")
	@Operation(summary = "Convert DB to PDF", description = "Export Db data to pdf")
	public ResponseEntity<InputStreamResource> exportDbToPdf() throws IOException, DocumentException {

		ByteArrayInputStream customerList = exportImpl.generatePdf();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=customers.pdf");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(customerList));
	}

}
