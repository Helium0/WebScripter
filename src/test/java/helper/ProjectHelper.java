package helper;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class ProjectHelper {



    public static Actions actions(WebDriver driver) {
        return new Actions(driver);
    }

    public static JavascriptExecutor js(WebDriver driver) {
        return  (JavascriptExecutor) driver;
    }

}
