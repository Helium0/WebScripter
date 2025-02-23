package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NavigationBarPage {

    private WebDriver driver;


    @FindBy(partialLinkText = "Szukaj")
    private WebElement searchElement;




    public NavigationBarPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public SearchPage clickForSearch() {
        searchElement.click();
       return new SearchPage(driver);
    }

}
