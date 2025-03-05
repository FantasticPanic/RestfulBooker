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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

class HomePage {
	
	private WebDriver driver;
	static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	String sutURL = "https://automationintesting.online/";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setUp() throws Exception {
		driver = new ChromeDriver();
		driver.get(sutURL);
	}

	@AfterEach
	void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	void Homepage_image_is_logo() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement logo = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.className("hotel-logoUrl")));
		assertThat(logo.getDomAttribute("src")).containsIgnoringCase("logo");
	}
	
	@Test
	void Website_description_displays_on_homepage()
	{	
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement logo = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.className("hotel-logoUrl")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "arguments[0].scrollIntoView();";
		js.executeScript(script, logo);
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
}
