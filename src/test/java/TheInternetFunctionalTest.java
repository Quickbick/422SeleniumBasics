import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TheInternetFunctionalTest {

    WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    /*
    @Nested
    class TheInternet_Form_Authentication {
    }
    */

    void openPage(){
        driver.get("https://the-internet.herokuapp.com/login");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }

    @Test
    void test_page_load(){
        openPage();
        WebElement titleElement = driver.findElement(By.cssSelector("h2"));
        String expectedTitle = "Login Page";
        assertEquals(expectedTitle, titleElement.getText());
    }

    void login(String username, String passwordText){
        WebElement userName = driver.findElement(By.cssSelector("input[id='username']"));
        WebElement password = driver.findElement(By.cssSelector("input[id='password']"));
        userName.sendKeys(username);
        password.sendKeys(passwordText);
        WebElement loginButton = driver.findElement(By.cssSelector("button[class='radius']"));
        loginButton.click();
    }

    String getFlashMessage(){
        WebElement divFlash = driver.findElement(By.cssSelector("div[id='flash']"));
        return divFlash.getText();
    }

    @Test
    void test_login_happy_path(){
        openPage();
        login("tomsmith", "SuperSecretPassword!");
        String expectedStartsWith = "You logged into a secure area!";
        assertTrue(getFlashMessage().startsWith(expectedStartsWith));
    }

    @Test
    void test_incorrect_username(){
        openPage();
        login("subu", "SuperSecretPassword!");
        String expectedStartsWith = "Your username is invalid!";
        assertTrue(getFlashMessage().startsWith(expectedStartsWith));
    }

    @Test
    void test_incorrect_password(){
        openPage();
        login("tomsmith", "wrongpassword");
        String expectedStartsWith = "Your password is invalid!";
        assertTrue(getFlashMessage().startsWith(expectedStartsWith));
    }

    @Test
    void test_dropdown(){
        driver.get("https://the-internet.herokuapp.com/dropdown");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement dropdown = driver.findElement(By.cssSelector("select[id='dropdown']"));
        Select selectDropdown = new Select((dropdown));
        selectDropdown.selectByVisibleText("Option 2");
    }

}
