import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import waits.ProjectWaits;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BasePage {


    protected WebDriver driver;
    protected JSONArray jsonScrap = new JSONArray();

    private final By POLICY = By.id("shopify-pc__banner__btn-accept");
    private final By COOKIES = By.cssSelector(".CustomCookiePop_exit");

    @BeforeTest
    public void webPage() {
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        chromeOptions();
        driver.get("https://www.303avenue.pl/");
        driver.manage().window().fullscreen();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
        driver.findElement(POLICY).click();
        ProjectWaits.wait(driver).until(ExpectedConditions.elementToBeClickable(COOKIES)).click();
//        driver.findElement(COOKIES).click();
    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    private Map browserMap() {
        return new HashMap<>();
    }

    private ChromeOptions chromeOptions() {
        ChromeOptions options =new ChromeOptions();
        Map savePopUp = browserMap();
        savePopUp.put("credentials_enable_service", false);
        savePopUp.put("profile.password_manager_enabled", false);
        savePopUp.put("autofill.profile_enabled", false);
        options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("prefs", savePopUp);
        return options;
    }
}
