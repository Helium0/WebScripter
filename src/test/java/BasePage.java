import org.json.JSONArray;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

public class BasePage {


    protected WebDriver driver;
    protected JSONArray jsonScrap = new JSONArray();


    @BeforeTest
    public void webPage() {
        driver = new ChromeDriver();
        driver.get("https://www.303avenue.pl/");
        driver.manage().window().fullscreen();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
