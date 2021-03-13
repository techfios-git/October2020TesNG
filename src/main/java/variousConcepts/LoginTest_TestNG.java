package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest_TestNG {

	WebDriver driver;
	String browser = null;
	String url = null;
	
	@BeforeTest
	public void beforeTest() {
		System.out.println("Before Test");
	}
	
	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Before Suite");
	}
	
	@BeforeClass
	public void readConfig() {
		
		System.out.println("Before Class");
		Properties prop = new Properties();
		//InputStream //BufferedReder //FileReader //Scanner
		
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Used browser: " + browser);
			url = prop.getProperty("url");
			System.out.println("Used environment: " + url);
			
		}catch(IOException e) {
			
			e.printStackTrace();
		}
		
	}

	@BeforeMethod
	public void init() {
		
		System.out.println("Before Method");
		if(browser.equalsIgnoreCase("chrome")) {
			
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			
		}else if(browser.equalsIgnoreCase("firefox")){
			
			System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			
		}

		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test (priority=1) 
	public void loginTest() {
		System.out.println("Test 1");
		Assert.assertEquals(driver.getTitle(), "Login - iBilling", "Wrong page!!");

		// Element Library
		// String using WebElement class
		WebElement USER_NAME_FIELD_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_FIELD_ELEMENT = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement SIGNIN_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@name='login']"));

		USER_NAME_FIELD_ELEMENT.clear();
		USER_NAME_FIELD_ELEMENT.sendKeys("demo@techfios.com");
		PASSWORD_FIELD_ELEMENT.sendKeys("abc123");
		SIGNIN_BUTTON_ELEMENT.click();

		Assert.assertEquals(driver.getTitle(), "Dashboard- iBilling", "Wrong page!!");

	}

	@Test (priority=2) 
	public void addCustomerTest() {
		System.out.println("Test 2");
		// Element Library
		By USER_NAME_FIELD = By.id("username");
		By PASSWORD_FIELD = By.id("password");
		By SIGNIN_BUTTON = By.name("login");
		By MENU_BUTTON = By.xpath("//i[@class='fa fa-dedent']");
		By DASHBOARD_BUTTON = By.xpath("//span[contains(text(), 'Dashboard')]");
		By CUSTOMERS_BUTTON = By.xpath("//span[contains(text(), 'Customers')]");
		By ADD_CUSTOMER_BUTTON = By.xpath("//a[contains(text(), 'Add Customer')]");
		By ADD_CONTACT_LOCATOR = By.xpath("//h5[contains(text(),'Add Contact')]");
		By FULL_NAME_FIELD = By.xpath("//input[@id='account']");
		By COMPANY_NAME_FIELD = By.xpath("//select[@id='cid']");
		By EMAIL_FIELD = By.xpath("//input[@id='email']");
		By PHONE_FIELD = By.xpath("//input[@id='phone']");
		By ADDRESS_FIELD = By.xpath("//input[@id='address']");
		By CITY_FIELD = By.xpath("//input[@id='city']");
		By STATE_REGION_FIELD = By.xpath("//input[@id='state']");
		By ZIP_FIELD = By.xpath("//input[@id='zip']");
		By SUBMIT_BUTTON = By.xpath("//button[@class='btn btn-primary']");
		By LIST_CONTACTS_BUTTON = By.xpath("//a[contains(text(),'List Contacts')]");

		// Login Data
		String loginId = "demo@techfios.com";
		String password = "abc123";

		// Test Data or Mock Data
		String fullName = "Test October";
		String companyName = "Techfios";
		String emailAddress = "testOctober@gamil.com";
		String phoneNumber = "23532523";

		// Log In
		Assert.assertEquals(driver.getTitle(), "Login - iBilling", "Wrong page!!");
		driver.findElement(USER_NAME_FIELD).sendKeys(loginId);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON).click();
		Assert.assertEquals(driver.getTitle(), "Dashboard- iBilling", "Wrong page!!");

		// add customer
		driver.findElement(CUSTOMERS_BUTTON).click();
		
		// explicit wait
		waitForElement(driver, 5, ADD_CUSTOMER_BUTTON);
		driver.findElement(ADD_CUSTOMER_BUTTON).click();

		waitForElement(driver, 3, FULL_NAME_FIELD);

		// generate random no
		Random rnd = new Random();
		int generatedNo = rnd.nextInt(999);

		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName + generatedNo);
		
		//dropdown
		Select sel = new Select(driver.findElement(COMPANY_NAME_FIELD));
		sel.selectByVisibleText(companyName);
		
		
	}
	
	public void waitForElement(WebDriver driver, int timeInSecomds, By locator) {
		
		WebDriverWait wait = new WebDriverWait(driver, timeInSecomds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}

	@AfterMethod
	public void tearDown() {
		System.out.println("After Method");
		driver.close();
		driver.quit();
	}
	
	@AfterClass
	public void afterClass() {
		System.out.println("After class");
	}
	
	@AfterTest
	public void afterTest() {
		System.out.println("After Test");
	}
	
	@AfterSuite
	public void afterSuite() {
		System.out.println("After Suite");
	}

}
