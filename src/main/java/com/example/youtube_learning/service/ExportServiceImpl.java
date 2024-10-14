package com.example.youtube_learning.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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

	// export db data to pdf
	public ByteArrayInputStream generatePdf() throws DocumentException {
		List<Customer> customersList = customerRepository.findAll();

		Document document = new Document();

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, out);
		document.open();

//		Adding font and styles
		Font font = FontFactory.getFont(BaseFont.TIMES_BOLD, 16, 6, BaseColor.BLUE);

//		creating a paragraph 
		Paragraph para = new Paragraph("Customer Table", font);
		para.setAlignment(Element.ALIGN_CENTER);

//		add this paragrapgh to document
		document.add(para);

//		add smallest element we can add to the document and use is Chunk
		document.add(Chunk.NEWLINE);

//		Adding tables to the document
		PdfPTable table = new PdfPTable(8);
		// Add PDF Table Header ->
		Stream.of("Id", "FirstName", "LastName", "Email", "Gender", "ContactNo", "Country", "DOB")
				.forEach(headerTitle -> {
					PdfPCell header = new PdfPCell();
					Font headFont = FontFactory.getFont(BaseFont.TIMES_BOLD,16,6,BaseColor.RED);
					header.setBackgroundColor(BaseColor.LIGHT_GRAY);
					header.setHorizontalAlignment(Element.ALIGN_CENTER);
					header.setBorderWidth(2);
					header.setPhrase(new Phrase(headerTitle, headFont));
					table.addCell(header);
				});

		for (Customer customer : customersList) {
			PdfPCell idCell = new PdfPCell(new Phrase(customer.getId()));
			idCell.setPaddingLeft(4);
			idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(idCell);

			PdfPCell firstNameCell = new PdfPCell(new Phrase(customer.getFirstName()));
			firstNameCell.setPaddingLeft(4);
			firstNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			firstNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(firstNameCell);

			PdfPCell lastNameCell = new PdfPCell(new Phrase(String.valueOf(customer.getLastName())));
			lastNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			lastNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			lastNameCell.setPaddingRight(4);
			table.addCell(lastNameCell);

			PdfPCell emailCell = new PdfPCell(new Phrase(String.valueOf(customer.getEmail())));
			emailCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			emailCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			emailCell.setPaddingRight(4);
			table.addCell(emailCell);

			PdfPCell genderCell = new PdfPCell(new Phrase(String.valueOf(customer.getGender())));
			genderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			genderCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			genderCell.setPaddingRight(4);
			table.addCell(genderCell);

			PdfPCell contactNoCell = new PdfPCell(new Phrase(String.valueOf(customer.getContactNo())));
			contactNoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			contactNoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			contactNoCell.setPaddingRight(4);
			table.addCell(contactNoCell);

			PdfPCell countryCell = new PdfPCell(new Phrase(String.valueOf(customer.getCountry())));
			countryCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			countryCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			countryCell.setPaddingRight(4);
			table.addCell(countryCell);

			PdfPCell dobCell = new PdfPCell(new Phrase(String.valueOf(customer.getDob())));
			dobCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			dobCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			dobCell.setPaddingRight(4);
			table.addCell(dobCell);
		}
		document.add(table);
		document.close();

		return new ByteArrayInputStream(out.toByteArray());

	}

}
