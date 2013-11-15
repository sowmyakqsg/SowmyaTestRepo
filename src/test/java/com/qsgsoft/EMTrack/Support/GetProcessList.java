package com.qsgsoft.EMTrack.Support;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * This code get the process list which are currently running(shows in your task
 * manager) in your system and stored at the file named as "process.txt"
 * Remarks: The file is stored at where this java file is available. Cache the
 * file path which you need
 */
public class GetProcessList {
	
	public String GetProcessName() {
		Process p;
		Runtime runTime;
		String process = null, line = null;
		try {
			// Get Runtime environment of System
			runTime = Runtime.getRuntime();

			// Execute command thru Runtime
			p = runTime.exec("tasklist"); // For Windows
			// p=r.exec("ps ux"); //For Linux

			// Create Inputstream for Read Processes
			InputStream inputStream = p.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			// Read the processes from sysrtem and add & as delimeter for
			// tokenize the output
			line = bufferedReader.readLine();
			process = "&";

			while (line != null) {
				line = bufferedReader.readLine();
				process += line + "&";
				//System.out.println(line + "&");
			}

			// Close the Streams
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();

		} catch (IOException e) {
			System.out.println("Exception arise during the read Processes");
			e.printStackTrace();
		}
		return process;
	}

	public String GetProcessListData() {
		Process p;
		Runtime runTime;
		String process = null;
		try {
			System.out.println("Processes Reading is started...");

			// Get Runtime environment of System
			runTime = Runtime.getRuntime();

			// Execute command thru Runtime
			p = runTime.exec("tasklist"); // For Windows
			// p=r.exec("ps ux"); //For Linux

			// Create Inputstream for Read Processes
			InputStream inputStream = p.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			// Read the processes from sysrtem and add & as delimeter for
			// tokenize the output
			String line = bufferedReader.readLine();
			process = "&";

			while (line != null) {
				line = bufferedReader.readLine();
				process += line + "&";
				System.out.println(line + "&");
			}

			// Close the Streams
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();

			System.out.println("Processes are read.");
		} catch (IOException e) {
			System.out.println("Exception arise during the read Processes");
			e.printStackTrace();
		}
		return process;
	}

	public void showProcessData() {
		try {

			// Call the method For Read the process
			String proc = GetProcessListData();

			// Create Streams for write processes
			// Given the file path which you need.Its store the file at where
			// your java file.
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					new FileOutputStream("ProcessList.txt"));
			BufferedWriter bufferedWriter = new BufferedWriter(
					outputStreamWriter);

			// Tokenize the output for write the processes
			StringTokenizer st = new StringTokenizer(proc, "&");

			while (st.hasMoreTokens()) {
				bufferedWriter.write(st.nextToken()); // Write the data in file
				bufferedWriter.newLine(); // Allocate new line for next line
			}

			// Close the outputStreams
			bufferedWriter.close();
			outputStreamWriter.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public static void main(String[] args) {
		GetProcessList gpl = new GetProcessList();
		gpl.showProcessData();

	}
}
