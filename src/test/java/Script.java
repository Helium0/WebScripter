import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;


public class Script extends BasePage {



    private final By IMAGE = By.xpath("//div[contains(@class,'Image--zoomOut')]");



    @Test
    public void getProducts() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.partialLinkText("Nowości")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(IMAGE)).isDisplayed();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'ProductList ProductList--grid')]")));

        WebElement allProducts = driver.findElement(By.xpath("//div[contains(@class,'ProductList ProductList--grid')]"));

        List<WebElement> productsList = allProducts.findElements(By.xpath("//div[contains(@class,'Grid__Cell 1/2--phone 1/2--tablet-and-up 1/3--lap-and-up')]"));
        System.out.println(productsList.size());

        Thread.sleep(3000);
        for(WebElement product : productsList) {
//            WebElement image2 = product.findElement(By.xpath("//noscript"));
            WebElement image = product.findElement(By.xpath("//img[@class='ProductItem__Image ProductItem__Image--alternate Image--fadeIn lazyautosizes Image--lazyLoaded']"));
            WebElement details = product.findElement(By.xpath(".//h5//a"));
            WebElement price = product.findElement(By.xpath(".//span[contains(@class,'ProductItem__Price Price Text--subdued')]"));
            WebElement colour = product.findElement(By.xpath(".//span[@title]"));

//$x("//img[contains(@srcset,'1200w')]")
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("product_image", image2.getText());
            jsonObject.put("product_image", image.getDomAttribute("srcset"));
            jsonObject.put("product_name", details.getText());
            jsonObject.put("product_price", price.getText());
            jsonObject.put("product_colour", colour.getDomAttribute("title"));

            jsonScrap.put(jsonObject);

        }

        System.out.println(jsonScrap.toString(1));

        System.out.println(jsonScrap.getJSONObject(1));


//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//        //wait for element to be visible and then click on it
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(.,'Shop by Category')]")));
//        driver.findElement(By.xpath("//a[contains(.,'Shop by Category')]")).click();
//
//        Thread.sleep(3000);
//        //wait for phones, tablets category element to be visible and then click on it
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,'Phone, Tablets & Ipod')]")));
//        driver.findElement(By.xpath("//span[contains(.,'Phone, Tablets & Ipod')]")).click();
//
//
//        //wait for all products to load on the screen
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='entry_212408']//div[@class='row']")));
//
//
//        //fetch and store the reference to all products parent web element
//        //nested locators 1
//        WebElement allProducts = driver.findElement(By.xpath("//div[@id='entry_212408']//div[@class='row']"));
//
//
//        //use the parent web element to fetch list of web elements for each individual product
//        //nested locators 2
//        List<WebElement> productList = allProducts.findElements(By.xpath("//div[contains(@class,'product-layout product-grid no-desc')]"));
//
//
//        //traverse the list of product web elements to scrap the required data
//        for(WebElement product : productList)
//        {
//            WebElement detail = product.findElement(By.xpath(".//a[@class='text-ellipsis-2']"));
//            WebElement price = product.findElement(By.xpath(".//span[@class='price-new']"));
//
//
//            //store product image link, name and price in a json object
//            JSONObject productMetaData = new JSONObject();
//            productMetaData.put("product_image", detail.getAttribute("href"));
//            productMetaData.put("product_name", detail.getText());
//            productMetaData.put("product_price", price.getText());
//
//            //add each product detail to json array to aggregate the data at one location
//            jsonScrap.put(productMetaData);
//        }
//
//        //print all the scraped products data on console
//        System.out.println(jsonScrap.toString(1));



    }

}
