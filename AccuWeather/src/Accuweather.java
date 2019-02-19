import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Accuweather extends Utility {
	
	static Utility utility;
	static WebDriverWait wait;
	public Accuweather(WebDriver driver) {
		super(driver);
	}

	public static void main(String[] args) throws Exception {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		String path = "./resources/Cities.xlsx";
		FileInputStream fis = new FileInputStream(path);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet("Sheet1");
		int rowcount = (sh.getLastRowNum() - sh.getFirstRowNum());
		Cell cell = null;
		
		FileWriter writer = new FileWriter("./target/cities_url.txt");
		BufferedWriter buffer = new BufferedWriter(writer);
		String city;
		utility=new Utility(driver);
		try {
			for (int i = 1; i <= rowcount; i++) {
				fis = new FileInputStream(path);
				wb = WorkbookFactory.create(fis);
				sh = wb.getSheet("Sheet1");
				city = sh.getRow(i).getCell(0).getStringCellValue();
				
				DesiredCapabilities cap=new DesiredCapabilities();
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, true);
				cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "dismiss");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized");
				options.addArguments("test-type");
				options.addArguments("enable-strict-powerful-feature-restrictions");
				options.addArguments("disable-geolocation");
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				cap = cap.merge(DesiredCapabilities.chrome());
				driver = new ChromeDriver(cap);
				
				driver.get("https://www.accuweather.com");
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				utility.manageAdvertisement();
				driver.findElement(By.xpath("//input[@placeholder='Search location, zip...']")).click();
				driver.findElement(By.xpath("(//input[@class='super-search-input'])[1]")).clear();
				driver.findElement(By.xpath("(//input[@class='super-search-input'])[1]")).sendKeys(city);
				driver.findElement(By.xpath("(//input[@class='super-search-input'])[1]")).sendKeys(Keys.ENTER);
				if (city.equalsIgnoreCase("Hyderabad, TG, IN")) {
					driver.findElement(By.xpath("//a[@href='https://www.accuweather.com/en/in/hyderabad/202190/weather-forecast/202190']")).click();
				} else if (city.equalsIgnoreCase("Bhopal, MP, IN")) {
					driver.findElement(By.xpath("//a[@href='https://www.accuweather.com/en/in/bhopal/204408/weather-forecast/204408']")).click();
				} else if (city.equalsIgnoreCase("Visakhapatnam, AP, IN")) {
					driver.findElement(By.xpath("//a[@href='https://www.accuweather.com/en/in/visakhapatnam/202192/weather-forecast/202192']")).click();
				} else if (city.equalsIgnoreCase("Patna, BR, IN")) {
					driver.findElement(By.xpath("//a[@href='https://www.accuweather.com/en/in/patna/202349/weather-forecast/202349']")).click();
				} else if (city.equalsIgnoreCase("Bhubaneswar, OR, IN")) {
					driver.findElement(By.xpath("//a[@href='https://www.accuweather.com/en/in/bhubaneswar/189781/weather-forecast/189781']")).click();
				} else if (city.equalsIgnoreCase("Nagpur, MH, IN")) {
					driver.findElement(By.xpath("//a[@href='https://www.accuweather.com/en/in/nagpur/204844/weather-forecast/204844']")).click();
				} else if (city.equalsIgnoreCase("Indore, MP, IN")) {
					driver.findElement(By.xpath("//a[@href='https://www.accuweather.com/en/in/indore/204411/weather-forecast/204411']")).click();
				} else if (city.equalsIgnoreCase("Agra, UP, IN")) {
					driver.findElement(By.xpath("//a[@href='https://www.accuweather.com/en/in/agra/206686/weather-forecast/206686']")).click();
				}
				
				
				utility.manageAdvertisement();
				wait = new WebDriverWait(driver,10);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Month')]")));
					
				driver.findElement(By.xpath("//span[contains(text(),'Month')]")).click();
				utility.manageAdvertisement();
				driver.findElement(By.xpath("//a[@class='btr btri btri-view-list']")).click();
				String url = driver.getCurrentUrl();
				buffer.write(i + " : " + city + "	:	" + url + "\n");
				cell = sh.getRow(i).getCell(2);
				if (cell == null) {
					cell = sh.getRow(i).createCell(2);
				}
				cell.setCellValue(url);
				fis.close();
				wb.write(new FileOutputStream(path));
				wb.close();
				utility.CaptureScreenshot(city);
				driver.close();
			}
		} catch (Exception e) {
			System.out.println("Exception caugth here: : :\n"+e.getMessage());
			e.printStackTrace();
		} finally {
			buffer.close();
			
		}
		
	}
}