package com.qsgsoft.EMTrack.Support;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JOptionPane;

import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;

/**
 * <font face="courier" size="2">This Class includes all common functions
 * related to Office application</font>
 * 
 * @author <font face="courier" size="2">QSG</font>
 * @version <font face="courier" size="2">1.0</font>
 */
@SuppressWarnings("unused")
public class OfficeCommonFunctions {
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.OfficeCommonFunctions");
	public Properties EnvDetails;
	// private static final String FILE_PATH = "C:/Beezag/Beezag_Results/";
	private static WritableCellFormat courier;

	/**
	 * This function is used to write data into the Excel file
	 * 
	 * @throws Exception
	 */
	public void WriteResult(String strTCId, String strTO, String strResult,
			String strReason, double dbTimeTaken, String strRunOn)
			throws Exception {
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		String FILE_PATH = pathProps.getProperty("Resultpath");
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		EnvDetails = objReadEnvironment.ReadEnvironment();
		// Read the existing file
		Workbook wb = Workbook.getWorkbook(new File(FILE_PATH));

		// Create a writable workbook with the same name using the workbook
		// object that has been read from file
		WritableWorkbook wwb = Workbook.createWorkbook(new File(FILE_PATH), wb);

		// Total number of rows in the sheet
		int intRowCount = wwb.getSheet(0).getRows();
		for (int intRow = 4; intRow <= intRowCount; intRow++) {
			// Target cell
			WritableCell cell = wwb.getSheet(0).getWritableCell(1, intRow);

			if (cell.getType() == CellType.EMPTY) {
				WritableCellFormat wcf = new WritableCellFormat();
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
				wcf.setWrap(true);

				WritableFont courier10pt = new WritableFont(
						WritableFont.COURIER, 10);
				// Define the cell format
				courier = new WritableCellFormat(courier10pt);
				wcf.setFont(courier10pt);

				// Enter the TC ID
				Label label1 = new Label(1, intRow, strTCId, wcf);
				wwb.getSheet(0).addCell(label1);
				// Enter Test Objective
				Label label2 = new Label(2, intRow, strTO, wcf);
				wwb.getSheet(0).addCell(label2);
				// Enter Result
				Label label3 = new Label(3, intRow, strResult, wcf);
				wwb.getSheet(0).addCell(label3);
				// Enter Reason
				Label label4 = new Label(4, intRow, strReason, wcf);
				wwb.getSheet(0).addCell(label4);
				// Enter Time taken
				String strTime = String.valueOf(dbTimeTaken);
				Label label5 = new Label(5, intRow, strTime, wcf);
				wwb.getSheet(0).addCell(label5);

				String strRes = RunON();
				Label label6 = new Label(6, intRow, strRes, wcf);
				wwb.getSheet(0).addCell(label6);
				// Write the data
				wwb.write();
				intRow = intRowCount + 1;
			}
		}
		wwb.close();
	}

	public void WriteResult(String strTCId, String strTO, String strResult,
			String strReason, double dbTimeTaken, String FILE_PATH,
			String strRunOn) throws Exception {
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		EnvDetails = objReadEnvironment.ReadEnvironment();
		// Read the existing file
		Workbook wb = Workbook.getWorkbook(new File(FILE_PATH));

		// Create a writable workbook with the same name using the workbook
		// object that has been read from file
		WritableWorkbook wwb = Workbook.createWorkbook(new File(FILE_PATH), wb);

		// Total number of rows in the sheet
		int intRowCount = wwb.getSheet(0).getRows();
		for (int intRow = 4; intRow <= intRowCount; intRow++) {
			// Target cell
			WritableCell cell = wwb.getSheet(0).getWritableCell(1, intRow);

			if (cell.getType() == CellType.EMPTY) {
				WritableCellFormat wcf = new WritableCellFormat();
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
				wcf.setWrap(true);

				WritableFont courier10pt = new WritableFont(
						WritableFont.COURIER, 10);
				// Define the cell format
				courier = new WritableCellFormat(courier10pt);
				wcf.setFont(courier10pt);

				// Enter the TC ID
				Label label1 = new Label(1, intRow, strTCId, wcf);
				wwb.getSheet(0).addCell(label1);
				// Enter Test Objective
				Label label2 = new Label(2, intRow, strTO, wcf);
				wwb.getSheet(0).addCell(label2);
				// Enter Result
				Label label3 = new Label(3, intRow, strResult, wcf);
				wwb.getSheet(0).addCell(label3);
				// Enter Reason
				Label label4 = new Label(4, intRow, strReason, wcf);
				wwb.getSheet(0).addCell(label4);
				// Enter Time taken
				String strTime = String.valueOf(dbTimeTaken);
				Label label5 = new Label(5, intRow, strTime, wcf);
				wwb.getSheet(0).addCell(label5);

				String strRes = RunON();
				Label label6 = new Label(6, intRow, strRes, wcf);
				wwb.getSheet(0).addCell(label6);
				// Write the data
				wwb.write();
				intRow = intRowCount + 1;
			}
		}
		wwb.close();
	}

	public void writeResultData(String[] strTestData,String strFilePath,String strSheetName) throws Exception{
		// Read the existing file
		Workbook wb = Workbook.getWorkbook(new File(strFilePath));

		// Create a writable workbook with the same name using the workbook
		// object that has been read from file
		WritableWorkbook wwb = Workbook.createWorkbook(new File(strFilePath), wb);

		// Total number of rows in the sheet
		int intRowCount = wwb.getSheet(strSheetName).getRows();
		for (int intRow = 1; intRow <= intRowCount; intRow++) {
				
				// Target cell
				WritableCell cell = wwb.getSheet(strSheetName).getWritableCell(1, intRow);
				if (cell.getType() == CellType.EMPTY) {
					WritableCellFormat wcf = new WritableCellFormat();
					wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
					wcf.setWrap(true);

					WritableFont courier10pt = new WritableFont(
							WritableFont.COURIER, 10);
					// Define the cell format
					courier = new WritableCellFormat(courier10pt);
					wcf.setFont(courier10pt);
					
					for(int intRec=1;intRec<=strTestData.length;intRec++){
					
						Label label1 = new Label(intRec, intRow, strTestData[intRec-1], wcf);
						wwb.getSheet(strSheetName).addCell(label1);					
						
					}
					
					// Write the data
					wwb.write();
					intRow = intRowCount + 1;
						
				}
				
						
		}
		
		wwb.close();
	}
	
	
	public void WriteTestData(String[] strTestData,String strFilePath,String strSheetName,int intRow) throws Exception {
		
		// Read the existing file
		Workbook wb = Workbook.getWorkbook(new File(strFilePath));

		// Create a writable workbook with the same name using the workbook
		// object that has been read from file
		WritableWorkbook wwb = Workbook.createWorkbook(new File(strFilePath), wb);

		
				WritableCellFormat wcf = new WritableCellFormat();
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
				wcf.setWrap(true);

				WritableFont courier10pt = new WritableFont(
						WritableFont.COURIER, 10);
				// Define the cell format
				courier = new WritableCellFormat(courier10pt);
				wcf.setFont(courier10pt);

				for(int intRec=1;intRec<=strTestData.length;intRec++){
					Label label1 = new Label(intRec, intRow, strTestData[intRec-1], wcf);
					wwb.getSheet(strSheetName).addCell(label1);
				}
						
				// Write the data
				wwb.write();
						
		
		wwb.close();
	}
	
public void WriteTestDatas(String[] strTestData,String strFilePath,String strSheetName,int intCol) throws Exception {
		
		// Read the existing file
		Workbook wb = Workbook.getWorkbook(new File(strFilePath));

		// Create a writable workbook with the same name using the workbook
		// object that has been read from file
		WritableWorkbook wwb = Workbook.createWorkbook(new File(strFilePath), wb);
		
		// Total number of rows in the sheet
		int intRowCount = wwb.getSheet(strSheetName).getRows();
		for (int intRow = 2; intRow <= intRowCount;) {
			
			// Target cell
			//WritableCell cell = wwb.getSheet(strSheetName).getWritableCell(intCol, intRow);

			//if (cell.getType() == CellType.EMPTY) {
				WritableCellFormat wcf = new WritableCellFormat();
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
				wcf.setWrap(true);

				WritableFont courier10pt = new WritableFont(
						WritableFont.COURIER, 10);
				// Define the cell format
				courier = new WritableCellFormat(courier10pt);
				wcf.setFont(courier10pt);
				int intTestData=strTestData.length+2;
				
				/*for(int intRec=intRow;intRec<=intTestData;intRec++){
					Label label1 = new Label(intRec, (intCol-1), strTestData[intRec-intRow], wcf);
					wwb.getSheet(strSheetName).addCell(label1);					
					// Write the data
					wwb.write();							
				}*/
				Label label1 = new Label(6, 4, strTestData[0], wcf);
				wwb.getSheet(strSheetName).addCell(label1);					
								
				Label label2 = new Label(6,5 , strTestData[1], wcf);
				wwb.getSheet(strSheetName).addCell(label2);					
				// Write the data
				wwb.write();	
				
				break;
			//}
			
		}
		wwb.close();
	}
	

	public void WriteTestDatatoSpecifiedCell(String strTestData,
			String strFilePath, String strSheetName, int intRow, int intCol)
			throws Exception {

		// Read the existing file
		Workbook wb = Workbook.getWorkbook(new File(strFilePath));

		// Create a writable workbook with the same name using the workbook
		// object that has been read from file
		WritableWorkbook wwb = Workbook.createWorkbook(new File(strFilePath),
				wb);

		WritableCellFormat wcf = new WritableCellFormat();
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		wcf.setWrap(true);

		WritableFont courier10pt = new WritableFont(WritableFont.COURIER, 10);
		// Define the cell format
		courier = new WritableCellFormat(courier10pt);
		wcf.setFont(courier10pt);

		Label label1 = new Label(intCol - 1, intRow - 1, strTestData, wcf);
		wwb.getSheet(strSheetName).addCell(label1);

		// Write the data
		wwb.write();

		wwb.close();
	}

	
	public void WriteResultdb_Excel(String strTCId, String strTO,
			String strResult, String strReason, double dbTimeTaken,
			String FILE_PATH, boolean blnwrite, String strstarttime,
			String strDate, long lDateTime, String strbrowser,
			String strversion, String StrSessionId) throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		// strDate = date.toString();

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		EnvDetails = objReadEnvironment.ReadEnvironment();

		// WRITE RESULTS TO EXCEL UNDER ANY CONDITION WITHOUT CHECKING ANY FLAG.

		// Read the existing file
		Workbook wb = Workbook.getWorkbook(new File(FILE_PATH));

		// Create a writable workbook with the same name using the workbook
		// object that has been read from file
		WritableWorkbook wwb = Workbook.createWorkbook(new File(FILE_PATH), wb);

		// Total number of rows in the sheet
		int intRowCount = wwb.getSheet(0).getRows();
		for (int intRow = 4; intRow <= intRowCount; intRow++) {
			// Target cell
			WritableCell cell = wwb.getSheet(0).getWritableCell(1, intRow);

			if (cell.getType() == CellType.EMPTY) {
				WritableCellFormat wcf = new WritableCellFormat();
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
				wcf.setWrap(true);

				WritableFont courier10pt = new WritableFont(
						WritableFont.COURIER, 10);
				// Define the cell format
				courier = new WritableCellFormat(courier10pt);
				wcf.setFont(courier10pt);

				Label label0 = new Label(0, intRow, "", wcf);
				wwb.getSheet(0).addCell(label0);

				// Enter the TC ID
				Label label1 = new Label(1, intRow, strTCId, wcf);
				wwb.getSheet(0).addCell(label1);
				// Enter Test Objective
				Label label2 = new Label(2, intRow, strTO, wcf);
				wwb.getSheet(0).addCell(label2);
				// Enter Result
				Label label3 = new Label(3, intRow, strResult, wcf);
				wwb.getSheet(0).addCell(label3);
				// Enter Reason
				Label label4 = new Label(4, intRow, strReason, wcf);
				wwb.getSheet(0).addCell(label4);
				// Enter Time taken
				String strTime = String.valueOf(dbTimeTaken);
				Label label5 = new Label(5, intRow, strTime, wcf);
				wwb.getSheet(0).addCell(label5);

				String strRes = RunON();
				Label label6 = new Label(6, intRow, strRes, wcf);
				wwb.getSheet(0).addCell(label6);
				// Write the data
				wwb.write();
				intRow = intRowCount + 1;
			}
		}
		wwb.close();

		// WRITE RESULTS TO QNET WHEN THE FLAG IS SET AS TRUE IN THE PROPERTY
		// FILE.
		if (blnwrite == true) {

			// Enters this loop when results should be written to Qnet
			// Fetch the Build ID and Cycle Environment Map ID from Property file
			String strBuild = EnvDetails.getProperty("Build");
			String strCycleEnviMapID = EnvDetails.getProperty("CycleEnviMapID");

			// Removes the String Prefixes of Test Case ID value.
			if (strTCId.startsWith("BQS-")) {
				strTCId = strTCId.substring(4);
			}
			if (strTCId.startsWith("FTS-")) {
				strTCId = strTCId.substring(5);
			}

			// Converts time taken value from Seconds to Minutes
			dbTimeTaken = dbTimeTaken / 60;

			// Write Results to Qnet Database.
			writeToDB(strCycleEnviMapID, strTCId, strBuild, strResult,
					strReason, dbTimeTaken, FILE_PATH, strDate + " "
							+ strstarttime, strbrowser);

		}

	}
	
	private int userWarning() {
		// TODO Auto-generated method stub
		int response = 999;
		try {

			Object[] options = { "OK", "CANCEL" };
			response = JOptionPane.showOptionDialog(null,
					"Results will be written to Qnet.Click OK to continue",
					"Warning",

					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,

					null, options, options[0]);
			switch (response) {
			case 0:
				log4j.info("OK Clicked");
				break;
			case 1:
				log4j.info("Cancel Clicked");
				break;
			default:
				log4j.info("Invalid selection");

			}
		}// end of try
		catch (Exception e) {
			log4j.info("Exception occured while displaying warning" + e);
		}
		return response;
	}
	
	public void writeToDB(String strCycleEnviMapID, String strTestCaseID,
			String strBuildID, String strResult, String strComments,
			double dblTimeTaken, String strFilePath, String strExecutedDate,
			String strBrowserName) throws Exception {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // Start JDBC
		String strSqlURL = "jdbc:sqlserver://192.168.27.7;DatabaseName=QNET";
		String strUsername = "autoaccess";
		String strPassword = "admin@456";
		String strInsertSQL;

		try {
			Class.forName(driverName); // LOAD THE DATABASE DRIVER
			Connection conn = DriverManager.getConnection(strSqlURL,strUsername, strPassword);
			// ESTABLISH THE CONNECTION TO THE DATABASE

			Statement stmt = conn.createStatement(); // GET A STATEMENT FOR THE CONNECTION
			strInsertSQL = "Insert into tblAutomationResults "
					+ "(CycleEnviMapID,TestCaseID,BuildID,Result,Comments,TimeTaken,FilePath,ExecutedDate,BrowserName) values "
					+ "('" + strCycleEnviMapID + "','" + strTestCaseID + "','"
					+ strBuildID + "','" + strResult + "','" + strComments
					+ "','" + dblTimeTaken + "','" + strFilePath + "','"
					+ strExecutedDate + "','" + strBrowserName + "')"; // Insert
																		// a
																		// record*/

			stmt.executeUpdate(strInsertSQL); // EXECUTE THE SQL QUERY AND STORE IN RESULTS SET
			// CLOSE THE RESULT, STATEMENT AND CONNECTION
			stmt.close();
			conn.close();
			System.out.println("Insert to Table was successful");
		
		}catch (SQLException se) {//// HANDLE THE SQL EXCEPTION
			System.out.println("SQL Exception:");
			// PRINT TILL ALL THE ECEPTIONS ARE RAISED
			while (se != null) {
				System.out.println("State : " + se.getSQLState());
				System.out.println("Message: " + se.getMessage());
				System.out.println("Error : " + se.getErrorCode());
				se = se.getNextException();
			}
			
		}catch (Exception e) {//// CATCH THE CLASS EXCEPTION	
			System.out.println(e);
		}
	}
	/*public void WriteResultdb_Excel(String strTCId, String strTO, String strResult,
			String strReason, double dbTimeTaken, String FILE_PATH,
			boolean blnwrite, String strstarttime, String strDate,long lDateTime,
			String strbrowser, String strversion, String StrSessionId) throws Exception {
		
		
		String strtimetaken=Double.toString(dbTimeTaken);
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		EnvDetails = objReadEnvironment.ReadEnvironment();
		if(blnwrite==true){
		
		// Read the existing file
		Workbook wb = Workbook.getWorkbook(new File(FILE_PATH));

		// Create a writable workbook with the same name using the workbook
		// object that has been read from file
		WritableWorkbook wwb = Workbook.createWorkbook(new File(FILE_PATH), wb);

		// Total number of rows in the sheet
		int intRowCount = wwb.getSheet(0).getRows();
		for (int intRow = 4; intRow <= intRowCount; intRow++) {
			// Target cell
			WritableCell cell = wwb.getSheet(0).getWritableCell(1, intRow);

			if (cell.getType() == CellType.EMPTY) {
				WritableCellFormat wcf = new WritableCellFormat();
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
				wcf.setWrap(true);

				WritableFont courier10pt = new WritableFont(
						WritableFont.COURIER, 10);
				// Define the cell format
				courier = new WritableCellFormat(courier10pt);
				wcf.setFont(courier10pt);

				
				Label label0 = new Label(0, intRow, "", wcf);
				wwb.getSheet(0).addCell(label0);
				
				// Enter the TC ID
				Label label1 = new Label(1, intRow, strTCId, wcf);
				wwb.getSheet(0).addCell(label1);
				// Enter Test Objective
				Label label2 = new Label(2, intRow, strTO, wcf);
				wwb.getSheet(0).addCell(label2);
				// Enter Result
				Label label3 = new Label(3, intRow, strResult, wcf);
				wwb.getSheet(0).addCell(label3);
				// Enter Reason
				Label label4 = new Label(4, intRow, strReason, wcf);
				wwb.getSheet(0).addCell(label4);
				// Enter Time taken
				String strTime = String.valueOf(dbTimeTaken);
				Label label5 = new Label(5, intRow, strTime, wcf);
				wwb.getSheet(0).addCell(label5);

				String strRes = RunON();
				Label label6 = new Label(6, intRow, strRes, wcf);
				wwb.getSheet(0).addCell(label6);
				// Write the data
				wwb.write();
				intRow = intRowCount + 1;
			}
		}
		wwb.close();
		}else{
			HashMap<String, String> hm = new HashMap<String, String>();
			String strBuild=EnvDetails.getProperty("Build");
			
			hm.put("tcId", strTCId);
			System.out.println(hm.put("tcId", strTCId));
			hm.put("objective", strTO);
			System.out.println(hm.put("objective", strTO));
			hm.put("automationResult", strResult);
			System.out.println(hm.put("automationResult", strResult));
			if (strResult == "PASS") {
				hm.put("reasonForFailure", "NIL");
				System.out.println(hm.put("reasonForFailure", "NIL"));
			} else {
				hm.put("reasonForFailure", strReason);
				System.out.println(hm.put("reasonForFailure", strReason));
			}
			hm.put("browser", strbrowser);
			System.out.println(hm.put("browser", strbrowser));
			hm.put("timeTaken", strtimetaken);
			System.out.println(hm.put("timeTaken", strtimetaken));
			hm.put("build", strversion);
			System.out.println(hm.put("build", strversion));
			
			if(EnvDetails.getProperty("urlEU").contains("test")){
				hm.put("server", "QA");
			}else if(EnvDetails.getProperty("urlEU").contains("stage")){
				hm.put("server", "STAGE");
			}else {
				hm.put("server", "LIVE");
			}
			
			//hm.put("timeStamp", String.valueOf(lDateTime));
			hm.put("timeStamp",strBuild+strDate);
			System.out.println("========================="+strBuild+strDate+"=====================");	
			//hm.put("timeStamp", "567801239043");
			//System.out.println(hm.put("timeStamp", String.valueOf(lDateTime)));
			String strRun_on = RunON();
			
			hm.put("runOn", strRun_on);
			System.out.println(hm.put("runOn", strRun_on));
			hm.put("date", strDate);
			System.out.println(hm.put("date", strDate));
			hm.put("time", strstarttime);
			System.out.println(hm.put("time", strstarttime));
			
			hm.put("videoUrl", StrSessionId);
			System.out.println(hm.put("videoUrl", StrSessionId));

			AddTestCase objadd = new AddTestCase();
			objadd.doPost(hm);
			
		}		
	}*/

	public String readAndVerifyDataExcel(String strTestData[][],
			String FILE_PATH) throws Exception {
		String strReason = "";
		Sheet ws = null;
		try {
			// Read the existing file
			Workbook wb = Workbook.getWorkbook(new File(FILE_PATH));
			ws = wb.getSheet(0);

			int intRowCount = wb.getSheet(0).getRows();
			int intColCount = wb.getSheet(0).getColumns();
			for (int intRow = 0; intRow < intRowCount; intRow++) {
				for (int intCol = 0; intCol < intColCount; intCol++) {
					// Read content of the cell


					String strCellContent = ws.getCell(intCol, intRow)
					.getContents();
					if (strCellContent.equals(strTestData[intRow][intCol])) {
						log4j.info("Specified Data "
								+ strTestData[intRow][intCol]
								                      + " is displayed in the report");
					} else {
						log4j.info("Specified Data "
								+ strTestData[intRow][intCol]
								                      + " is NOT displayed in the report");
						strReason = "Specified Data "
							+ strTestData[intRow][intCol]
							                      + " is NOT displayed in the report";
					}
				}

			}


			if (intRowCount != strTestData.length) {
				strReason = strReason
				+ " All the necessary Data are not displayed " +
				"in the status snapshot report";
			}
			wb.close();
		} catch (Exception e) {
			strReason = e.toString();

		}

		return strReason;
	}
	
	public String readAndVerifyParticularDataExcel(String strTestData[],
			String FILE_PATH) throws Exception {
		String strReason = "";
		Sheet ws = null;
		int intResCnt = 0;
		try {
			// Read the existing file
			Workbook wb = Workbook.getWorkbook(new File(FILE_PATH));
			ws = wb.getSheet(0);

			int intRowCount = wb.getSheet(0).getRows();
			int intColCount = wb.getSheet(0).getColumns();
			for (int intRow = 0; intRow < intRowCount; intRow++) {
				intResCnt = 0;
				String strCellContent = ws.getCell(1, intRow).getContents();
				if (strCellContent.equals(strTestData[1])) {

					for (int intCol = 0; intCol < intColCount; intCol++) {
						// Read content of the cell

						strCellContent = ws.getCell(intCol, intRow)
								.getContents();
						if (strCellContent.equals(strTestData[intCol])
								|| strCellContent.matches(strTestData[intCol])) {
							log4j.info("Specified Data " + strTestData[intCol]
									+ " is displayed in the report");
							intResCnt++;

						}else if(strCellContent.contains(";")){
							String[] strArData=strTestData[intCol].split("; ");
							for(String strTesDt:strArData){
								if(strCellContent.contains(strTesDt)){
									log4j.info("Specified Data " + strTesDt
										       									+ " is displayed in the report");
									
								}else{
									log4j.info("Specified Data " + strTesDt
	       									+ " is NOT displayed in the report");
									strReason = strReason
									+ "Specified Data " +strTesDt
								       									+ " is NOT displayed in the report";
								}
							}
							
							intResCnt++;
						}else{
							log4j.info("Specified Data " + strTestData[intCol]
								       									+ " is NOT displayed in the report");
								strReason = strReason
								+ "Specified Data " + strTestData[intCol]
							       									+ " is NOT displayed in the report";
						}
					}
					break;
				}
				/*if (intResCnt == strTestData.length)
					break;*/
			}

			if (intResCnt != strTestData.length) {
				strReason = strReason
						+ " All the necessary details are NOT displayed "
						+ "in the report";
			}
			wb.close();
		} catch (Exception e) {
			strReason = e.toString();

		}

		return strReason;
	}

	/**
	 * This function is used calculate the Time taken for test case execution
	 */
	public double TimeTaken(Date dtStartDate) {
		Date dtEndDate = new Date();
		double dtTimeTaken = dtEndDate.getTime() - dtStartDate.getTime();
		dtTimeTaken = dtTimeTaken / 1000;
		return dtTimeTaken;

	}

	public String RunON() throws Exception {
		String strResult = "";
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		EnvDetails = objReadEnvironment.ReadEnvironment();
		if (EnvDetails.getProperty("Server").equals("saucelabs.com")) {
			strResult = "Sauce Labs";
		} else {
			strResult = "Local System";
		}
		return strResult;
	}
	
	public String externalIP() throws IOException {

		String ip = "";
		try {
			// http://automation.whatismyip.com/n09230945.asp
			URL whatismyip = new URL("http://ifconfig.me/ip");
			URLConnection connection = whatismyip.openConnection();
			connection.setConnectTimeout(50 * 1000);
			connection.addRequestProperty("Protocol", "Http/1.1");
			connection.addRequestProperty("Connection", "keep-alive");
			connection.addRequestProperty("Keep-Alive", "1000");
			connection.addRequestProperty("User-Agent", "Web-Agent");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			ip = in.readLine(); // you get the IP as a String
			System.out.println(ip);

		} catch (Exception e) {
			ip = e.toString();
			log4j.info(e);
		}
		return ip;
	}

	public void writeResultDataToParticularRow(String[] strTestData,
			String strFilePath, String strSheetName, int intRowInput)
	throws Exception {
		// Read the existing file
		Workbook wb = Workbook.getWorkbook(new File(strFilePath));

		// Create a writable workbook with the same name using the workbook
		// object that has been read from file
		WritableWorkbook wwb = Workbook.createWorkbook(new File(strFilePath),
				wb);

		WritableCellFormat wcf = new WritableCellFormat();
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		wcf.setWrap(true);

		WritableFont courier10pt = new WritableFont(WritableFont.COURIER, 10);
		// Define the cell format
		courier = new WritableCellFormat(courier10pt);
		wcf.setFont(courier10pt);

		for (int intRec = 0; intRec < strTestData.length; intRec++) {

			Label label1 = new Label(intRec, intRowInput, strTestData[intRec],
					wcf);
			wwb.getSheet(strSheetName).addCell(label1);

		}

		// Write the data
		wwb.write();

		wwb.close();
	}

	public String round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    double FinVal=(double) tmp / factor;
	    String result = String.format("%.2f", FinVal);
	    return result;
	}
	
	public String readAndVerifyParticularDataExcelUpload(String strTestData[],
			String FILE_PATH) throws Exception {
		String strReason = "";
		Sheet ws = null;
		int intResCnt = 0;
		try {
			// Read the existing file
			Workbook wb = Workbook.getWorkbook(new File(FILE_PATH));
			ws = wb.getSheet(0);

			int intRowCount = wb.getSheet(0).getRows();
			int intColCount = wb.getSheet(0).getColumns();
			for (int intRow = 0; intRow < intRowCount; intRow++) {
				intResCnt = 0;
				String strCellContent = ws.getCell(1, intRow).getContents();
				if (strCellContent.equals(strTestData[1])) {

					for (int intCol = 0; intCol < intColCount; intCol++) {
						// Read content of the cell

						strCellContent = ws.getCell(intCol, intRow)
								.getContents();
						if (strCellContent.equals(strTestData[intCol])
								|| strCellContent.matches(strTestData[intCol])) {
							log4j.info("Specified Data " + strTestData[intCol]
									+ " is displayed in the Upload Template");
							intResCnt++;

						} else if (strCellContent.contains(";")) {
							String[] strArData = strTestData[intCol]
									.split("; ");
							for (String strTesDt : strArData) {
								if (strCellContent.contains(strTesDt)) {
									log4j.info("Specified Data "
											+ strTesDt
											+ " is displayed in the Upload Template");

								} else {
									log4j.info("Specified Data "
											+ strTesDt
											+ " is NOT displayed in the Upload Template");
									strReason = strReason
											+ "Specified Data "
											+ strTesDt
											+ " is NOT displayed in the Upload Template";
								}
							}

							intResCnt++;
						} else {
							log4j.info("Specified Data "
									+ strTestData[intCol]
									+ " is NOT displayed in the Upload Template");
							strReason = strReason
									+ "Specified Data "
									+ strTestData[intCol]
									+ " is NOT displayed in the Upload Template";
						}
					}
					break;
				}
			}
			if (intResCnt != strTestData.length) {
				strReason = strReason
						+ " All the necessary details are NOT displayed "
						+ "in the Upload Template";
			}
			wb.close();
		} catch (Exception e) {
			strReason = e.toString();
		}
		return strReason;
	}

}
