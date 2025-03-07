package org.sample.RestfulBooker;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

class HomePage {
	
	private WebDriver driver;
	static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	String sutURL = "https://automationintesting.online/";
	JavascriptExecutor js = (JavascriptExecutor) driver;
	String script = "arguments[0].scrollIntoView();";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setUp() throws Exception {
		driver = new ChromeDriver();
		driver.get(sutURL);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement logo = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.className("hotel-logoUrl")));
		ScrollToView(logo);
	}

	@AfterEach
	void tearDown() throws Exception {
		driver.quit();
	}
	
	public Object ScrollToView(WebElement element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "arguments[0].scrollIntoView();";
		
		return js.executeScript (script, element);
	}

	@Test
	void Welcome_to_Restful_Booker_displays()
	{
		
		WebElement heading = driver.findElement(By.tagName("h1"));
		assertThat(heading.getText()).contains("Welcome to Restful Booker");
	}
	
	@Test
	void Homepage_image_is_logo() 
	{
		WebElement logo = driver.findElement(By.className("hotel-logoUrl"));
		assertThat(logo.getDomAttribute("src")).containsIgnoringCase("logo");
	}
	
	@Test
	void Website_description_displays_on_homepage()
	{	
		WebElement description = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div[2]/p"));
		String descriptionText = description.getText();
		log.debug("Description text: {}", descriptionText);
		assertThat(descriptionText).contains("Welcome to Shady Meadows");
		
	}
	
	@Test
	void Room_description_displays_on_homepage()
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement description = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div/div/div[3]/p"));
		String descriptionText = description.getText();
		assertThat(descriptionText).isNotEmpty();
	}
	
	@Test
	void Room_image_is_displayed()
	{
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement image= wait.until(
					ExpectedConditions.presenceOfElementLocated(By.className("hotel-img")));
			log.debug("Room image file name: {}", image.getDomAttribute("src"));
			assertThat(image.getDomAttribute("src")).containsIgnoringCase("room");
	}
	
	@Test
	void Danger_alert_appears_when_missing_info() throws InterruptedException 
	{
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				WebElement logo = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.className("hotel-logoUrl")));
		ScrollToView(logo);
		WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div/div/div[3]/button"));
		button.click();
		WebElement firstNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("firstname")));
		WebElement lastNameInput = driver.findElement(By.name("lastname"));
		WebElement emailInput = driver.findElement(By.name("email"));
		WebElement phoneInput = driver.findElement(By.name("phone"));
		
		
		String firstNameValue = "Test";
		firstNameInput.sendKeys(firstNameValue);
		String lastNameValue = "User";
		lastNameInput.sendKeys(lastNameValue);
		String emailValue = "testuser123@email.com";
		emailInput.sendKeys(emailValue);
		String phoneValue = "12345678900";
		phoneInput.sendKeys(phoneValue);
		
		
		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div/div[2]/div[3]/button[2]")).click();
		Thread.sleep(500);
		assertThat(driver.findElement(By.cssSelector(".alert-danger")));
	}
	
	@Test
	void Contact_alert_message_displays() throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement contact = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.className("contact")));
		ScrollToView(contact);
		WebElement submitBtn = driver.findElement(By.id("submitContact"));
		submitBtn.click();
		
		Thread.sleep(500);
		assertThat(driver.findElement(By.cssSelector(".alert-danger")));
		
	}
	
	@Test
	void Contact_sucessfully_submits_message()
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement contact = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.className("contact")));
		ScrollToView(contact);
		WebElement submitBtn = driver.findElement(By.id("submitContact"));
		WebElement nameInput = driver.findElement(By.id("name"));
		WebElement emailInput = driver.findElement(By.id("email"));
		WebElement phoneInput = driver.findElement(By.id("phone"));
		WebElement subjectInput = driver.findElement(By.id("subject"));
		WebElement descriptionInput = driver.findElement(By.id("description"));
		
		String nameValue = "TestUser";
		nameInput.sendKeys(nameValue);
		String emailValue = "testuser@email.com";
		emailInput.sendKeys(emailValue);
		String phoneValue = "12345678900";
		phoneInput.sendKeys(phoneValue);
		String subjectValue = "Test Subject";
		subjectInput.sendKeys(subjectValue);
		String descriptionValue = "This is a test message for testing out the contact fields at the bottom";
		descriptionInput.sendKeys(descriptionValue);
		submitBtn.click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement confirmMsg = driver.findElement
								(By.cssSelector("#root > div > div > div.row.contact > div > div > h2"));
		assertThat(confirmMsg.getText()).contains("Thanks for getting in touch");
	}
	
	@Test
	void Calender_select_dates() throws InterruptedException
	{
		
		WebElement bookingBtn = driver.findElement(By.className("openBooking"));
		ScrollToView(bookingBtn);
		bookingBtn.click();
		//WebElement nextMonth = driver.findElement(null)
		WebElement date1 = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/div[1]/button"));
		WebElement date2 = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/div[2]/button"));
		WebElement date3 = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/div[3]/button"));
		WebElement date4 = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/div[4]/button"));
		Actions action = new Actions(driver);
		action.clickAndHold(date1).moveToElement(date2).moveToElement(date3).moveToElement(date4);
		action.release().build().perform();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement firstNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("firstname")));
		WebElement lastNameInput = driver.findElement(By.name("lastname"));
		WebElement emailInput = driver.findElement(By.name("email"));
		WebElement phoneInput = driver.findElement(By.name("phone"));
		
		
		String firstNameValue = "Test";
		firstNameInput.sendKeys(firstNameValue);
		String lastNameValue = "User";
		lastNameInput.sendKeys(lastNameValue);
		String emailValue = "testuser123@email.com";
		emailInput.sendKeys(emailValue);
		String phoneValue = "12345678900";
		phoneInput.sendKeys(phoneValue);
		
		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div/div[2]/div[3]/button[2]")).click();
		Thread.sleep(500);
		WebElement confirmation = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div[2]/h3"));
		assertThat(confirmation.getText()).contains("Booking Successful");
		
	}
}
