package com.qsgsoft.EMResource.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class CSVFunctions {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.CSVfunctions");

	public String ReadFromCSV(String strFilePath, String[][] strReportData)
			throws IOException, InterruptedException {
		String strReason = "";
		try {

			int intCount = 0;
			String strCellValue;
			String[] fileName = strFilePath.split("/");
			File file1 = new File(fileName[fileName.length - 1]);
			String path = file1.getAbsolutePath();
			strFilePath = path.replaceAll(fileName[fileName.length - 1],
					strFilePath);
			File file = new File(strFilePath);
			boolean strExists = file.exists();
			if (!strExists) {
				// It returns false if File or directory does not exist
				log4j.info("The CSV report file is NOT found");
				strReason = "The CSV report file is NOT found";
			} else {
				if (file.length() == 0) {
					log4j.info("Length of File is - 0 KB");
					log4j.info("Unable to read the file");
					strReason = "Unable to read the file";
				} else {
					String strFileName = file.getName();
					log4j.info("File Name is: " + strFileName);
					log4j.info("Length of file: " + file.length() + " Bytes");

					BufferedReader bufRdr = new BufferedReader(new FileReader(
							file));

					String line = null;
					int row = 0, col = 0;

					// read each line of text file
					log4j.info("\n ------- Printing the Report's Data ----------------");
					log4j.info("\n ----------- Verifying if the Column headers are present in CSV report ----------");
					while ((line = bufRdr.readLine()) != null) {
						col = 0;
						while (line.contains(",,")) {
							line = line.replaceAll(",,", ",**,");
						}

						if (line.substring(line.length() - 1).equals(",")) {
							String strLastCh1 = line.substring(0,
									line.length() - 1);
							line = strLastCh1 + ",**";
						}

						if (line.substring(0, 1).equals(",")) {
							String strLastCh1 = line
									.substring(1, line.length());
							line = "**," + strLastCh1;
						}

						StringTokenizer strToken = new StringTokenizer(line,
								",");
						while (strToken.hasMoreTokens()) {
							strCellValue = strToken.nextToken();
							if (strReportData[row][col].contains("\\")) {
								if (strCellValue
										.matches(strReportData[row][col])) {
									log4j.info("Specified Data "
											+ strReportData[row][col]
											+ " is displayed in the report");
								} else {
									log4j.info("Specified Data "
											+ strReportData[row][col]
											+ " is NOT displayed in the report");
									strReason = "Specified Data "
											+ strReportData[row][col]
											+ " is NOT displayed in the report";
								}
							} else {
								if (strCellValue
										.equals(strReportData[row][col])) {
									log4j.info("Specified Data "
											+ strReportData[row][col]
											+ " is displayed in the report");
								} else {
									log4j.info("Specified Data "
											+ strReportData[row][col]
											+ " is NOT displayed in the report");
									strReason = "Specified Data "
											+ strReportData[row][col]
											+ " is NOT displayed in the report";
								}
							}
							col++;
							intCount++;

						}
						log4j.info("Completed " + row + " data");
						row++;
					}

					log4j.info(intCount);
					int len = 0;
					for (int i = 0; i < strReportData.length; i++) {
						len = len + strReportData[i].length;
					}

					log4j.info(len);
					if (intCount != len) {
						log4j.info("Report is NOT generated in CSV "
								+ "format with all the specified details");
						strReason = strReason
								+ "Report is NOT generated"
								+ " in CSV format with all the specified details";
					}
					// close the file

					bufRdr.close();
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function ReadFromCSV" + e.toString();
		}
		return strReason;
	}
}
