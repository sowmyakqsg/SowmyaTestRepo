package com.qsgsoft.EMTrack.Support;




import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

@SuppressWarnings("deprecation")
public class Date_Time_settings extends SeleneseTestCase {
	
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.beezag.Shared.DOB_setting");

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This function return the date of birth of a 25 yr old
	 */
	public String getPastDate(int intyear) {
		String DATE_FORMAT = "MM d yyyy";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				DATE_FORMAT);
		Calendar c1 = Calendar.getInstance();
		int yearval = 365 * intyear;
		c1.add(Calendar.DATE, -yearval);
		String strDOB = sdf.format(c1.getTime());
		return strDOB;
	}
/* Fetch the current time
 * 
 */
	public String timeNow(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());

	}
	/**
	 * This function return the next date
	 */
	public String getCurrentDate(Selenium selenium, String Dateformat) {
		String DATE_FORMAT = Dateformat;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				DATE_FORMAT);
		Calendar c1 = Calendar.getInstance();
		String strCurrentDate = sdf.format(c1.getTime());
		return strCurrentDate;
	}
	
	public String getCurrentDate(String Dateformat) {
		String DATE_FORMAT = Dateformat;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				DATE_FORMAT);
		Calendar c1 = Calendar.getInstance();
		String strCurrentDate = sdf.format(c1.getTime());
		return strCurrentDate;
	}
	
	public String getPastDay(int intDate) {
		  String DATE_FORMAT = "MM/dd/yyyy";
		  java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
		    DATE_FORMAT);
		  Calendar c1 = Calendar.getInstance();
		      int yearval = intDate;
		  c1.add(Calendar.DATE, -yearval);
		  String strDOB = sdf.format(c1.getTime());
		  return strDOB;
		 }
		 
		 public String getFutureDay(int intDate,String strFormat) {
		  String DATE_FORMAT = strFormat;
		  java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
		    DATE_FORMAT);
		  Calendar c1 = Calendar.getInstance();
		      int yearval = intDate;
		  c1.add(Calendar.DATE, +yearval);
		  String strDOB = sdf.format(c1.getTime());
		  return strDOB;
		 }
		
	public String GetTimeStamp(Selenium selenium) throws Exception {
		String strTimeStamp;
		// Obtain the current time as accurate as milliseconds and return it
		strTimeStamp = selenium
				.getEval("new Date().getFullYear() + new Date().getDate().toString() +  "
						+ "new Date().getDay() +  new Date().getHours() +  new Date().getMinutes() +  "
						+ "new Date().getSeconds() + new Date().getMilliseconds()");
		return strTimeStamp;
	}

	/**
	 * This function returns the name of the month
	 */
	public String getMonth(String strMonth) {
		String[] monthName = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };

		String month = monthName[Integer.parseInt(strMonth) - 1];

		return month;
	}

 public int getTimeDiff1(String Time1,String Time2) throws ParseException{
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		       
		 // Convert the user input into a date object
		  Date time1 = sdf.parse(Time1);
		  Date time2 = sdf.parse(Time2);

		 // Get time values of the date objects
		  long l1 = time1.getTime();
		  long l2 = time2.getTime();
		  double difference = (l1 - l2)/1000; // Calculate the difference in time (divide by 1000 as in milliseconds)
		  difference = (difference <= 0 ? 1 : difference); // If difference is negative, make positive
		  
		  int Timedifference=(int) difference;
		  return Timedifference;
		}
	
 public double roundTwoDecimals(double d) {
	  DecimalFormat twoDForm = new DecimalFormat("#.##");
	  return Double.valueOf(twoDForm.format(d));
	 }
 
 
	public String FutureDate(int intAdd, String DateFormat) {
		String DATE_FORMAT = DateFormat;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				DATE_FORMAT);
		Calendar c1 = Calendar.getInstance();
		// c1.set(2012, 0 , 26);
		// System.out.println("Date is : " + sdf.format(c1.getTime()));
		c1.add(Calendar.DATE, intAdd);
		String strNextDate = sdf.format(c1.getTime());
		return strNextDate;
	}
	

	public String DaytoExistingDate(String strExistDate, int intdays,
			String strTimeFormat) throws ParseException {

		Date todayDate = new Date();
		DateFormat sdf = new SimpleDateFormat(strTimeFormat);
		String strDate = sdf.format(todayDate);

		String date = strExistDate;
		Date parsedDate = sdf.parse(date);
		System.out.println(strDate);
		System.out.println(parsedDate);
		Calendar now = Calendar.getInstance();
		now.setTime(parsedDate);
		now.add(Calendar.DATE, 1);
		date = sdf.format(now.getTime());
		System.out.println(date);

		return date;
	}
	public String compareTwoDates(Calendar c1,Calendar c2){
		String strRes="";
				
			if (c1.before(c2)) {
		       strRes="before";
		       }
		    if (c1.after(c2)) {
		    	strRes="after";     
		      }  
		    if (c1.equals(c2)) {
		    	strRes="same";     
		      }  
		    		  	
		return strRes;
	}
	
	/*public String AddDaytoExistingDate(String strExistDate) throws ParseException{
		
		String dt = strExistDate; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE, 2);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		System.out.println(dt);
		
		return dt;	
	}*/
	
	public String AddDaytoExistingDate(String strExistDate,int intdays,String strDateFormat) throws ParseException{
		
		String dt = strExistDate; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		
		c.add(Calendar.DATE, intdays);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		System.out.println(dt);
		
		return dt;	
	}
	

	public String AddTimeToExistingTimeHourAndMin(String strExistTime, int intHours,
			int intMin, String strTimeFormat) throws ParseException {

		String dt = strExistTime; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat(strTimeFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));

		c.add(Calendar.HOUR, intHours);
		c.add(Calendar.MINUTE, intMin);// number of days to add
		dt = sdf.format(c.getTime()); // dt is now the new date
		System.out.println(dt);

		return dt;
	}

	public String addTimetoExisting(String strExistTime,int intMin,String strTimeFormat) throws ParseException{
		String dt = strExistTime; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat(strTimeFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		
		c.add(Calendar.MINUTE, intMin);  // number of minutes to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		System.out.println(dt);
		
		return dt;	
	}
	
	public String addTimetoExistingHour(String strExistTime,int intHour,String strTimeFormat) throws ParseException{
		String dt = strExistTime; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat(strTimeFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		
		c.add(Calendar.HOUR, intHour);  // number of minutes to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		System.out.println(dt);
		
		return dt;	
	}
	

	public String converDateFormat(String strExistDate, String strInputFormat,
			String strOutputFormat) throws ParseException {
		String dt = strExistDate; // Start date
		try{
			
			SimpleDateFormat sdf = new SimpleDateFormat(strInputFormat);
			SimpleDateFormat sdf1 = new SimpleDateFormat(strOutputFormat);
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(dt));

			dt = sdf1.format(c.getTime()); // dt is now the new date
			System.out.println(dt);

			
		}catch(Exception e){
			
		}
		return dt;
		
	}

public int getTimeDiff(String Time1,String Time2) throws ParseException{
	
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
   	   
 // Convert the user input into a date object
	 Date time1 = sdf.parse(Time1);
	 Date time2 = sdf.parse(Time2);

 // Get time values of the date objects
	 long l1 = time1.getTime();
	 long l2 = time2.getTime();
	 double difference = (l1 - l2)/1000; // Calculate the difference in time (divide by 1000 as in milliseconds)
	 difference = (difference <= 0 ? 1 : difference); // If difference is negative, make positive
	 
	 int Timedifference=(int) difference;
	 return Timedifference;
}

public String getFutureYear(int intYear,String strFormat) {
    String DATE_FORMAT = strFormat;
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
      DATE_FORMAT);
    Calendar c1 = Calendar.getInstance();
        int yearval = intYear;
    c1.add(Calendar.YEAR, +yearval);
    String strDOB = sdf.format(c1.getTime());
    return strDOB;
   }

	public String getTimeOfParticularTimeZone(String strTimeZone,
			String strTimeFormat) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(strTimeFormat);
		dateFormat.setTimeZone(TimeZone.getTimeZone(strTimeZone));
		String strDOB = dateFormat.format(new Date());
		return strDOB;
	}
	
	public int getTimeDiffWithTimeFormat(String Time1,String Time2,String strTimeFormat) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat(strTimeFormat);
	   	   
	 // Convert the user input into a date object
		 Date time1 = sdf.parse(Time1);
		 Date time2 = sdf.parse(Time2);

	 // Get time values of the date objects
		 long l1 = time1.getTime();
		 long l2 = time2.getTime();
		 double difference = (l1 - l2)/1000; // Calculate the difference in time (divide by 1000 as in milliseconds)
		 difference = (difference <= 0 ? 1 : difference); // If difference is negative, make positive
		 
		 int Timedifference=(int) difference;
		 return Timedifference;
	}

	
	public String AddYearToExisting(String strExistTime, int intYear,
			String strTimeFormat) throws ParseException {

		String dt = strExistTime; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat(strTimeFormat);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));

		c.add(Calendar.YEAR, intYear);
		dt = sdf.format(c.getTime()); // dt is now the new date
		System.out.println(dt);

		return dt;
	}
	

	public int getTimeDiffWithTimeFormatInOurTime(String Time1, String Time2,
			String strTimeFormat,int Output) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(strTimeFormat);

		// Convert the user input into a date object
		Date time1 = sdf.parse(Time1);
		Date time2 = sdf.parse(Time2);

		// Get time values of the date objects
		long l1 = time1.getTime();
		long l2 = time2.getTime();
		double difference = (l1 - l2)/Output ; 
		int Timedifference = (int) difference;
		return Timedifference;
	}

	public String getPastDayNew(int intDate, String strDateFormat) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				strDateFormat);
		Calendar c1 = Calendar.getInstance();
		int yearval = intDate;
		c1.add(Calendar.DATE, -yearval);
		String strDOB = sdf.format(c1.getTime());
		return strDOB;
	}

}
