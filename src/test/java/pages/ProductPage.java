package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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

    @FindBy(xpath = "//div[@class='SectionHeader__Description']")
    private WebElement searchedProducts;

    public ProductPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public By searchProductSizeLabel() {
        return this.SIZE_LABEL;
    }

    public By searchSpecyficProductSize() {
        return this.SPECYFIC_SIZE;
    }

    public WebElement availableProducts() {
        return this.products;
    }

    public WebElement countSearchedProducts() {
        return this.searchedProducts;
    }

    public List<WebElement> getAllProducts() {
        return new ArrayList<>(productsWebElementList);
    }

//    public List<ProductComponent> getDisplayedProduct(Predicate<ProductComponent> condition) {
//        return getAllProducts().stream().filter(condition).collect(Collectors.toList());
//    }

}
