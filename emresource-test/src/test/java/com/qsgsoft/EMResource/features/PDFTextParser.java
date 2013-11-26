package com.qsgsoft.EMResource.features;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFTextParser {

	PDFParser parser;
	String parsedText;
	PDFTextStripper pdfStripper;
	PDDocument pdDoc;
	COSDocument cosDoc;
	PDDocumentInformation pdDocInfo;

	// PDFTextParser Constructor
	public PDFTextParser() {
	}

	// Extract text from PDF Document
	String pdftoText(String fileName) {

		System.out.println("Parsing text from PDF file " + fileName + "....");
		File f = new File(fileName);

		if (!f.isFile()) {
			System.out.println("File " + fileName + " does not exist.");
			return null;
		}

		try {
			parser = new PDFParser(new FileInputStream(f));
		} catch (Exception e) {
			System.out.println("Unable to open PDF Parser.");
			return null;
		}

		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (Exception e) {
			System.out
					.println("An exception occured in parsing the PDF Document.");
			e.printStackTrace();
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
			return null;
		}
		System.out.println("Done.");
		return parsedText;
	}

	// Write the parsed text from PDF to a file
	void writeTexttoFile(String pdfText, String fileName) {

		System.out.println("\nWriting PDF text to output text file " + fileName
				+ "....");
		try {
			PrintWriter pw = new PrintWriter(fileName);
			pw.print(pdfText);
			pw.close();
		} catch (Exception e) {
			System.out
					.println("An exception occured in writing the pdf text to file.");
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

	// Extracts text from a PDF Documesnt and writes it to a text file
	public static void main(String args[]) {

		PDFTextParser pdfTextParserObj = new PDFTextParser();
		String pdfToText = pdfTextParserObj.pdftoText(
					"D:\\eclipse\\com.qsgsoft.EMResource\\"
							+ "SupportFiles\\DownloadFiles\\StatusDetailReportconvert.pdf");

		if (pdfToText == null) {
			System.out.println("PDF to Text Conversion failed.");
		} else {
			System.out.println("\nThe text parsed from the PDF Document....\n"
					+ pdfToText);
			String strUniqueName="Txt"+System.currentTimeMillis();
			pdfTextParserObj.writeTexttoFile(pdfToText, "C:\\Documents and Settings\\qsg\\Desktop\\"+strUniqueName+".txt");
		}
	}
}