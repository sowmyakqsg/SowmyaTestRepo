package functionality;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Download {
	WebDriver driver = new FirefoxDriver();
	private boolean followRedirects = true;

	@Test
	public void downloadAFile() throws Exception {
		FileDownloader downloadTestFile = new FileDownloader(driver);
		// driver.get("https://qa2.intermedix.com/filetransfer/FileDownloadServlet?id=54941");
		driver.get("https://qa2.intermedix.com/common/Frames.jsp");

		URL fileToDownload = new URL(
				"https://qa2.intermedix.com/filetransfer/FileDownloadServlet?id=47838");
		File downloadedFile = new File("C://CrossBrowserTestingEMTrack/filetransfer/Incident.txt");
		System.out.print(fileToDownload.getFile());
		if (downloadedFile.canWrite() == false)
			downloadedFile.setWritable(true);

		HttpClient client = new DefaultHttpClient();
		BasicHttpContext localContext = new BasicHttpContext();

		HttpGet httpget = new HttpGet(fileToDownload.toURI());
		HttpParams httpRequestParameters = httpget.getParams();
		httpRequestParameters.setParameter(ClientPNames.HANDLE_REDIRECTS,
				this.followRedirects);
		httpget.setParams(httpRequestParameters);

		HttpResponse response = client.execute(httpget, localContext);

		FileUtils.copyInputStreamToFile(response.getEntity().getContent(),
				downloadedFile);
		response.getEntity().getContent().close();

		String downloadedFileAbsolutePath = downloadedFile.getAbsolutePath();
	}

	public void followRedirectsWhenDownloading(boolean value) {
		this.followRedirects = value;
	}
}
