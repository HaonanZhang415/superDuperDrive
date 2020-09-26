package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;

	private String baseURL;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = baseURL = "http://localhost:" + port;
	}

//	@AfterEach
//	public void afterEach() {
//		if (this.driver != null) {
//			driver.quit();
//		}
//	}

	@Test
	public void testUserSignupLoginAndSubmitMessage() {

		String username = "pzastoup";
		String password = "whatabadpassword";
		String noteTitle = "first note";
		String noteContent = "this is the content of my note test";

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		HomePage homePage = new HomePage(driver);
		homePage.createNewNote(noteTitle, noteContent);

		Note createdNote = homePage.getNote();

		assertEquals(noteTitle, createdNote.getNoteTitle());
		assertEquals(noteContent, createdNote.getNoteDescription());
	}

}
