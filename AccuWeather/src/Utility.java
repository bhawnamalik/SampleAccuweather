import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utility {
	public static WebDriver driver;
	static WebDriverWait wait;
	
	public Utility(WebDriver driver) {
		Utility.driver = driver;
	}

	public void CaptureScreenshot(String city) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		long picname = System.currentTimeMillis();
		FileUtils.copyFile(src, new File("./screenshots/" + city + picname + ".png"));

	}

	public void manageAdvertisement() throws Exception {
		try {
			if (driver.findElement(By.xpath("//*[contains(@id,'bx-close-inside-')]")).isDisplayed()) {
				System.out.println("Ads here");
				driver.findElement(By.xpath("//div/a[@class='bx-close bx-close-link bx-close-inside']")).click();
			}
		} catch (NoSuchElementException e) {
			System.out.println("No ads here");
		}
		
	}
}