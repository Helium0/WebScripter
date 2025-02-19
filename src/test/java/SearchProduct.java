import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SearchProduct extends BasePage {

    private final String SEARCH_PRODUCT = "Marynarka Ethel";


    @Test
    public void searchForOneProduct() throws InterruptedException {


        WebElement searchElement = driver.findElement(By.partialLinkText("Szukaj"));
        searchElement.click();
        WebElement userSearchInput = driver.findElement(By.xpath("//input[@class='Search__Input Heading']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeContains(userSearchInput,"placeholder","Szukaj"));
//        userSearchInput.sendKeys(SEARCH_PRODUCT);
        Actions actions = new Actions(driver);
        actions.click(userSearchInput).sendKeys(SEARCH_PRODUCT).keyDown(Keys.ENTER).keyUp(Keys.ENTER).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'ProductItem__QuickAdd roman_sorting')]")));
        WebElement size = driver.findElement(By.xpath("//div[@class='ProductItem__QuickAdd roman_sorting']"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//div[contains(@class, 'ProductItem__QuickAdd__Item')]")));

        List<WebElement> availableSizes = size.findElements(By.xpath(".//div[@class='ProductItem__QuickAdd__Item ']"));
//        String productName = driver.findElement(By.xpath("//div[@class='ProductItem__Info ProductItem__Info--center']//a")).getText();
        WebElement productPrice = driver.findElement(By.xpath("//div[@class='ProductItem__PriceList  Heading']//span"));
//        WebElement details = driver.findElement(By.xpath("//div[@class='ProductItem__Info ProductItem__Info--center']//a"));

        WebElement products = driver.findElement(By.xpath("//div[@class='ProductList ProductList--grid Grid']"));

        List<WebElement> testProducts = products.findElements(By.xpath("//div[contains(@class, 'Grid__Cell 1/2 1/3--tablet 1/4--lap-and-up')]"));
        System.out.println(testProducts.size());

        JSONArray scrappedArr = new JSONArray();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        for(WebElement el : testProducts) {
//            JSONObject objectTwo = new JSONObject();
            WebElement testPPPP = el.findElement(By.xpath(".//h5//a"));
//            objectTwo.put("Nazwa produktu",testPPPP.getText());
//            scrappedArr.put(objectTwo);

            String prodN = (String) js.executeScript("return arguments[0].textContent;",testPPPP);
            System.out.println(prodN);
        }


        WebElement productColor = driver.findElement(By.xpath("//div[@class='color-switcher color-switcher--small color-switcher--margin']//span"));




        String prodP = (String) js.executeScript("return arguments[0].textContent;", productPrice);
        for (WebElement product : availableSizes) {
            String text = (String) js.executeScript("return arguments[0].textContent;", product);
            JSONObject object = new JSONObject();
            object.put("dostępny_rozmiar", text.trim());
            scrappedArr.put(object);
        }
//        System.out.println("https://www.303avenue.pl"+details.getDomAttribute("href"));
//        System.out.println("Nazwa produktu: "+SEARCH_PRODUCT);
//        System.out.println("Cena: "+prodP);
//        System.out.println("Dostępny kolor: "+productColor.getDomAttribute("title"));
//        System.out.println("Liczba znalezionych rozmiarów: " + availableSizes.size());
        System.out.println(scrappedArr.toString(1));



    }
}
