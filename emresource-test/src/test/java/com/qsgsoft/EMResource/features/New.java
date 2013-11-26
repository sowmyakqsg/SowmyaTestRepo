package com.qsgsoft.EMResource.features;

import java.io.File;
import java.io.IOException;

public class New {
	public static void main(String args[],String strSrcFile,String strDestFile) {

		 strSrcFile = "C:/Documents and Settings/qsg/Desktop/StatusDetailReportconvert.pdf";
		 strDestFile = "C:/Documents and Settings/qsg/Desktop/StatusDetailReportconvert.xls";

		@SuppressWarnings("unused")
		boolean blnDone;

		File f = new File(strSrcFile);
		if (f.exists()) {
			blnDone = pdfToHtml(strSrcFile, strDestFile);
			if (blnDone = true) {
				System.out.println("Conversion Completed");
			} else
				System.out.println("File doesn't exists");
		}
	}

	public static boolean pdfToHtml(String strSrcFile, String strDestFile) {
		try {

			String[] strCMD = {"C:/PDFToExcelConverter/PDF2Excel.exe" };
			Runtime objRunTime = Runtime.getRuntime();
			Process objProcess = objRunTime.exec(strCMD);
			objProcess.waitFor();

		} catch (IOException e1) {
			return false;
		} catch (InterruptedException e2) {
			return false;
		}
		return true;
	}

}
