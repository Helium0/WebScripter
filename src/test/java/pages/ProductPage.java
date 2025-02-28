package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import waits.ProjectWaits;

import java.util.ArrayList;
import java.util.List;


public class ProductPage {

    private WebDriver driver;

    private final By SIZE_LABEL = By.xpath("//div[contains(@class, 'ProductItem__QuickAdd roman_sorting')]");
    private final By SPECYFIC_SIZE = By.xpath(".//div[contains(@class, 'ProductItem__QuickAdd__Item')]");




    @FindBy(xpath = "//div[contains(@class, 'ProductItem__QuickAdd roman_sorting')]")
    private WebElement sizeLabel;

    @FindBy(xpath = "//div[@class='ProductList ProductList--grid Grid']")
    private WebElement products;

    @FindBy(xpath = "//div[contains(@class, 'Grid__Cell 1/2 1/3--tablet 1/4--lap-and-up')]")
    private List<WebElement> productsWebElementList;

    @FindBy(xpath = ".//label[@class='color-switcher__option']//span")
    private List<WebElement> colours;

    @FindBy(xpath = "//div[@class='SectionHeader__Description']")
    private WebElement searchedProducts;

    @FindBy(xpath = ".//label[@class='color-switcher__option']//span")
    private List<WebElement> productColors;

    @FindBy(xpath = "//a[@title='Następna strona']")
    private WebElement nextPageElement;

    @FindBy(xpath = ".//span[@class='ProductItem__Label Heading Text--subdued']")
    private WebElement productTag;


    public ProductPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public By searchProductSizeLabel() {
        return this.SIZE_LABEL;
    }

    public By searchSpecyficProductSize() {
        return this.SPECYFIC_SIZE;
    }


    public WebElement countSearchedProducts() {
        return this.searchedProducts;
    }

    public List<WebElement> getAllProducts() {
        return new ArrayList<>(productsWebElementList);
    }

    public List<WebElement> getAllColours() {
        return new ArrayList<>(colours);
    }

    public boolean nextPageProducts(boolean hasNextPage) {
        try {
            if (nextPageElement.isDisplayed() && nextPageElement.isEnabled()) {
                nextPageElement.click();
                ProjectWaits.wait(driver).until(ExpectedConditions.visibilityOfAllElements(productsWebElementList));
            } else {
                hasNextPage = false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Brak nastepnej strony");
            hasNextPage = false;

        }
        return hasNextPage;

    }

    public  String productPicture(WebElement element, By by, String text) {
        String productImage = "";
        try {
            productImage= element.findElement(by).getDomAttribute(text);
        } catch (NoSuchElementException e) {
            productImage = "Brak obrazu";
        }
        return productImage;
    }

    public boolean discountedProductOrNo(WebElement element, By by, JavascriptExecutor js) {
        String updatedTagproduct = "";
        boolean priceAfterdiscount = false;
        try {
            WebElement test = element.findElement(by);
            updatedTagproduct = (String) js.executeScript("return arguments[0].textContent;", test);
            if(updatedTagproduct.trim().contains("OFF")) {
                priceAfterdiscount = true;
                }

            } catch (NoSuchElementException e) {

        }
        return priceAfterdiscount;

    }

    /* Te dwie metody discountedProductOrNo / productTagReturner moze wrzucic do Mapy? Klucz jako Cena i wartosc true albo false
    tak samo z tagiem. Klucz Product Tag i wartość z metody
    dodatkowo metoda productPriceReturner */

    public String productTagReturner(WebElement element, By by, JavascriptExecutor js) {
        String updatedTagproduct = "REGULAR";
        try {
            WebElement test = element.findElement(by);
            updatedTagproduct = (String) js.executeScript("return arguments[0].textContent;", test);

         } catch (NoSuchElementException e) {

        }
        return updatedTagproduct;
    }

    public String productPriceReturner(WebElement element, By by, JavascriptExecutor javascriptExecutor) {
        String price = "";
        WebElement prodP = element.findElement(by);
        price = (String) javascriptExecutor.executeScript("return arguments[0].textContent;", prodP);
        return price;
    }

    public String hyperLinkToTheProduct(List<WebElement> webElementList) {
        String link = "";
        for (WebElement element : webElementList) {
            link = element.getDomAttribute("href");
        }
        return link;
    }


}
