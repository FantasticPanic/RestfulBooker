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
	void Sucessfully_book_a_2_night_stay() 
	{
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				WebElement logo = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.className("hotel-logoUrl")));
		ScrollToView(logo);
		WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div/div/div[3]/button"));
		button.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		Actions actions = new Actions(driver);
		WebElement draggable = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div/div[2]/div[2]/div/div[2]/div[4]/div[1]/div[1]"));
		int offset = 150;
		Point initLocation = draggable.getLocation();
		actions.moveToElement(draggable).clickAndHold();
		actions.moveToElement(driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div/div[2]/div[2]/div/div[2]/div[5]/div[1]/div[3]")));
		actions.release().build().perform();
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
		
		
		assertThat(firstNameInput.getDomAttribute("value")).isEqualTo(firstNameValue);
		assertThat(driver.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div[2]/h3")))
					.isEqualTo("Booking Succesful");
	}
}
