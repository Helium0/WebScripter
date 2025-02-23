package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {



    @FindBy(xpath = "//input[@class='Search__Input Heading']")
    private WebElement searchInput;


    public SearchPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public WebElement searchSpecyficProduct() {
        return this.searchInput;
    }

}
