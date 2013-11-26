package com.qsgsoft.EMResource.support;

import java.io.File;
import java.util.Properties;

import com.qsgsoft.EMResource.support.Paths_Properties;

import jxl.Sheet;
import jxl.Workbook;

public class ReadData {

	/**
	 * This function is used to read data from the Excel file
	 * 
	 * @throws Exception
	 */
	public String readData(String SheetName, int intRow, int intColumn)
			throws Exception {
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();

		String FILE_PATH = pathProps.getProperty("UserDetails_path");
		String[] fileName = FILE_PATH.split("/");
		File file = new File(fileName[fileName.length - 1]);
		String path = file.getAbsolutePath();
		FILE_PATH = path.replaceAll(fileName[fileName.length - 1], FILE_PATH);
		Sheet ws = null;

		// Read the existing file
		Workbook wb = Workbook.getWorkbook(new File(FILE_PATH));
		ws = wb.getSheet(SheetName);

		// Read content of the cell
		String strCellContent = ws.getCell(intColumn - 1, intRow - 1)
				.getContents();
		wb.close();

		return strCellContent;
	}

	public String readInfoExcel(String SheetName, int intRow, int intColumn,
			String FILE_PATH) throws Exception {

		Sheet ws = null;

		// Read the existing file
		String[] fileName = FILE_PATH.split("/");
		File file = new File(fileName[fileName.length - 1]);
		String path = file.getAbsolutePath();
		FILE_PATH = path.replaceAll(fileName[fileName.length - 1], FILE_PATH);
		Workbook wb = Workbook.getWorkbook(new File(FILE_PATH));
		ws = wb.getSheet(SheetName);

		// Read content of the cell
		String strCellContent = ws.getCell(intColumn - 1, intRow - 1)
				.getContents();
		wb.close();

		return strCellContent;
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws IndexOutOfBoundsException
	 * @throws WriteException
	 * @throws BiffException
	 * @throws RowsExceededException
	 */
	/*
	 * public static void main(String[] args) throws RowsExceededException,
	 * BiffException, WriteException, IndexOutOfBoundsException, IOException {
	 * ReadDataFromExcel rdExcel = new ReadDataFromExcel(); // TODO
	 * Auto-generated method stub String strUsername =
	 * rdExcel.ReadFromExcel("EndUser", 2, 5); String strpassword =
	 * rdExcel.ReadFromExcel("EndUser", 2,6);
	 * 
	 * System.out.println(strUsername); System.out.println(strpassword); }
	 */

}
