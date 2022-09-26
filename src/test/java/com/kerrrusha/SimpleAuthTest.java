package com.kerrrusha;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleAuthTest {
	WebDriver driver;

	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setupTest() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	public void correctInputTest() throws InterruptedException {
		final String HTML_PATH = Objects.requireNonNull(
				SimpleAuthTest.class.getClassLoader().getResource("Signup.html")
		).getPath();
		driver.get("file://" + HTML_PATH);

		WebElement email = driver.findElement(By.cssSelector("input[placeholder*='example']"));
		WebElement phone = driver.findElement(By.cssSelector("input[placeholder*='+380']"));
		WebElement password = driver.findElement(By.cssSelector("#password"));
		WebElement passwordRepeat = driver.findElement(By.cssSelector("#passwordRepeat"));
		WebElement firstName = driver.findElement(By.cssSelector("input#firstName.form-control"));
		WebElement surname = driver.findElement(By.cssSelector("input#surname.form-control"));
		WebElement lastName = driver.findElement(By.cssSelector("input#lastName.form-control"));
		WebElement address = driver.findElement(By.cssSelector("input[id='address']"));
		WebElement department = driver.findElement(By.cssSelector("#department"));
		WebElement checkbox1 = driver.findElement(By.cssSelector("input#defaultCheck1.form-check-input"));
		WebElement checkbox2 = driver.findElement(By.cssSelector("input#defaultCheck2.form-check-input"));
		WebElement radio2 = driver.findElement(By.cssSelector("input#exampleRadios2.form-check-input"));
		WebElement submit = driver.findElement(By.cssSelector(".w-100.btn.btn-primary.btn-lg.my-5[type='submit']"));

		email.sendKeys("test@gmail.com");
		phone.sendKeys("+380123456789");
		password.sendKeys("testPassword123");
		passwordRepeat.sendKeys("testPassword123");
		firstName.sendKeys("Kirill");
		surname.sendKeys("Koval");
		lastName.sendKeys("Fathername");
		address.sendKeys("Address");
		department.sendKeys("123");

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radio2);
		Thread.sleep(1000);

		Stream.of(checkbox1, checkbox2, radio2).forEach(WebElement::click);

		submit.click();

		assertTrue(driver.findElements(By.cssSelector(".is-invalid")).isEmpty());
	}
}
