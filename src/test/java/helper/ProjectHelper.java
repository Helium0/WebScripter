package helper;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class ProjectHelper {



    public static Actions actions(WebDriver driver) {
        return new Actions(driver);
    }

    public static JavascriptExecutor js (WebDriver driver) {
        return  (JavascriptExecutor) driver;
    }

    public static void sideBarMover(JavascriptExecutor js, int scrollAmount) throws InterruptedException {
        int scrolls = 0;
        while(true) {
            try {
                js.executeScript("window.scrollBy(0, " + scrollAmount + ");");
                Thread.sleep(2000);
                if(scrolls >= 3) {
                    break;
                }
            } catch (NoSuchElementException e) {
                throw e;
            }
            scrolls ++;
        }
    }

    public static String addHttpsProtocolPrefix() {
        return "https:";
    }

}
