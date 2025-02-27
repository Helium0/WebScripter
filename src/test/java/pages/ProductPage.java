package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.ProductComponent;
import waits.ProjectWaits;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


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

    public  String productPicture (WebElement element, By by, String text) {
        String productImage = "";
        try {
            String pictureAttribute = element.findElement(by).getDomAttribute(text);
        } catch (NoSuchElementException e) {
            productImage = "Brak obrazu";
        }
        return productImage;
    }

}
