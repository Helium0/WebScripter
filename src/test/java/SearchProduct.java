import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import helper.ProjectHelper;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.element.Paragraph;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.NavigationBarPage;
import pages.ProductPage;
import pages.SearchPage;
import utilities.ImageCreator;
import utilities.PdfCreator;
import utilities.ProductComponent;
import waits.ProjectWaits;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class SearchProduct extends BasePage {






    private final String HTTP_PROTOCOL = "https:";

    private final String PRODUCT_TAG = "REGULAR";

    private final String WEBSITE_URL = "https://www.303avenue.pl";

    private String ee = "";

    private String priceProduct = "";

    private boolean priceAfterdiscount = false;

    private String tagProduct = "";


//    private final String SEARCH_PRODUCT = "Swetry";


//    private final String SEARCH_PRODUCT = "Sweter Classic 02";

//    private final String SEARCH_PRODUCT = "Spodnie Lowen";
//    private final String SEARCH_PRODUCT = "Spodnie";


//    private final String SEARCH_PRODUCT = "Marynarka Beau";


//        private final String SEARCH_PRODUCT = "Marynarka Stone";

    private final String SEARCH_PRODUCT = "Spodnie";

    @Test
    public void searchForOneProduct() throws InterruptedException, IOException {

        PdfCreator pdfCreator = new PdfCreator();
        NavigationBarPage navigationBarPage = new NavigationBarPage(driver);
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);

        navigationBarPage.clickForSearch();
        ProjectWaits.wait(driver).until(ExpectedConditions.attributeContains(searchPage.searchSpecyficProduct(), "placeholder", "Szukaj"));
        ProjectHelper.actions(driver).click(searchPage.searchSpecyficProduct()).sendKeys(SEARCH_PRODUCT)
                .keyDown(Keys.ENTER).keyUp(Keys.ENTER).perform();
        ProjectWaits.waitForPresenceElementLocated(driver, productPage.searchProductSizeLabel());
        ProjectWaits.waitForPresenceElementLocated(driver, productPage.searchSpecyficProductSize());
        String searchResult = productPage.countSearchedProducts().getText();
        List<WebElement> testProducts = productPage.getAllProducts();
        List<String> firstColours = productPage.getAllColours().stream().map(e -> e.getDomAttribute("title")).collect(Collectors.toList());
        Set<String> availableColours = ListsAndMaps.convertSetInToList(firstColours);

        pdfCreator.getDocument().add(new Paragraph(searchResult));
        pdfCreator.getDocument().add(new Paragraph(
                "Dostepne kolory: " + availableColours));


        JavascriptExecutor js = (JavascriptExecutor) driver;
        ProjectWaits.wait(driver).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@aria-label,'Close dialog')]"))).click();

        int scrollAmount = 1000; // Liczba pikseli do przewinięcia w każdym kroku
//        int scrolls = 0;
        boolean hasNextPage = true;


        while (hasNextPage) {
            int scrolls = 0;
        while (true) {
            try {
                js.executeScript("window.scrollBy(0, " + scrollAmount + ");");
                Thread.sleep(3000); // Poczekaj na załadowanie nowych elementów
                long updatedHeight = (long) js.executeScript("return document.body.scrollHeight");
                // Spróbuj znaleźć elementy
//
                if (scrolls >= 4) {

                    break;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Nie ma");
                throw e;

            }
            scrolls++;

        }


        for (int i = 0; i < testProducts.size(); i++) {   // iteracja po długości listy produktów


            WebElement product = testProducts.get(i);

            WebElement productName = product.findElement(By.xpath(".//h5[@class='ProductItem__Title Heading']/a"));
            WebElement productPrice = product.findElement(By.xpath(".//div[contains(@class, 'ProductItem__PriceList  Heading')]//span"));

            ProjectWaits.wait(driver).until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath(".//img[@class='ProductItem__Image ProductItem__Image--alternate Image--fadeIn lazyautosizes Image--lazyLoaded']")));

            String productImage;
            String productImageTwo;
            try {
                WebElement pictureProduct = product.findElement(By.xpath(".//img[@class='ProductItem__Image ProductItem__Image--alternate Image--fadeIn lazyautosizes Image--lazyLoaded']"));
                productImage = pictureProduct.getDomAttribute("data-srcset");
                WebElement pictureProductTwo = product.findElement(By.xpath(".//img[@class='ProductItem__Image Image--fadeIn lazyautosizes Image--lazyLoaded']"));
                productImageTwo = pictureProductTwo.getDomAttribute("data-srcset");

            } catch (NoSuchElementException e) {
                productImage = "Brak obrazu";
                productImageTwo = "Brak obrazu";
            }

//            driver.findElement(By.cssSelector(".needsclick klaviyo-close-form go2324193863 kl-private-reset-css-Xuajs1")).click();
            String updatedPictureProduct = ListsAndMaps.productStringURL(productImage, HTTP_PROTOCOL);
            System.out.println(updatedPictureProduct + "          OK");


            tagProduct = PRODUCT_TAG;

            try {
                WebElement productTag = product.findElement(By.xpath(".//span[@class='ProductItem__Label Heading Text--subdued']"));
                tagProduct = (String) js.executeScript("return arguments[0].textContent;", productTag);
                if (tagProduct.trim().contains("OFF")) {
                    priceAfterdiscount = true;
                }
            } catch (NoSuchElementException e) {

            }

            priceProduct = (String) js.executeScript("return arguments[0].textContent;", productPrice);

            List<WebElement> productColors = product.findElements(By.xpath(".//label[@class='color-switcher__option']//span"));
            String colourName = productColors.stream().map(element -> element.getDomAttribute("title")).findFirst().orElse("bas");
            List<WebElement> productWeb = product.findElements(By.xpath(".//div[@class='ProductItem__Wrapper']/a"));

            for (WebElement elr : productWeb) {
                ee = elr.getDomAttribute("href");

            }

            List<WebElement> productSizes = product.findElements(By.xpath(".//div[@class='ProductItem__QuickAdd__Item ']"));
            List<String> noDuplicatedSizes = productSizes.stream().map(element -> (String) js.executeScript("return arguments[0].textContent;", element))
                    .map(e -> e.isEmpty() ? "Brak" : e).collect(Collectors.toList());
            if (noDuplicatedSizes.isEmpty()) {
                noDuplicatedSizes = List.of("Brak");
            }


            String updatedPictureProductTwo = ListsAndMaps.productStringURL(productImageTwo, HTTP_PROTOCOL);
            System.out.println(updatedPictureProductTwo + "          OK");


            Image first = null;
            try {
                if (!"https:Brak".contains(updatedPictureProduct)) {
                    first = ImageCreator.convertStrigInToImage(updatedPictureProduct);
                } else {
                    updatedPictureProduct = "Brak obrazu";

                }
            } catch (IOException e) {
                System.out.println("Inny blad przy pierwszym obrazie");
            }

            Image second = null;
            try {
                if (!"https:Brak".contains(updatedPictureProductTwo)) {
                    second = ImageCreator.convertStrigInToImage(updatedPictureProductTwo);
                } else {
                    updatedPictureProductTwo = "Brak obrazu";
                }
            } catch (IOException e) {
                System.out.println("Inny blad przy drugim obrazie");
            }


            pdfCreator.getDocument().add(new Paragraph("\n" + "Wyswietlony produkt: " + productName.getText()));
            pdfCreator.getDocument().add(new Paragraph("Kolor: " + colourName + " " + " [] " + "Cena: "
                    + priceProduct + " [] " + "Cena po rabacie: " + priceAfterdiscount + " [] " + "Oznaczenie produktu: " + tagProduct + "\n"));
            pdfCreator.getDocument().add(new Paragraph("Dostepne rozmiary w tym kolorze: " + noDuplicatedSizes));
            pdfCreator.getDocument().add(new Paragraph("Link do strony produktu: " + "\n" + WEBSITE_URL + ee));

            if (updatedPictureProduct.equals("Brak obrazu") && updatedPictureProductTwo.equals("Brak obrazu")) {
                pdfCreator.getDocument().add(new Paragraph("Brak obrazu oraz linku do zdjecia dla tego produktu"));
            } else {
                pdfCreator.getDocument().add(new Paragraph("Link do zdjecia: " + "\n").add(ScrapperHelper.convertStringInToURI(updatedPictureProduct, PdfAction.createURI(updatedPictureProduct))));
                pdfCreator.getDocument().add(new Paragraph().add(ScrapperHelper.convertStringInToURI(updatedPictureProductTwo, PdfAction.createURI(updatedPictureProductTwo))));
            }


                if (first != null && second !=null) {
                    pdfCreator.getDocument().add(first);
                    pdfCreator.getDocument().add(second);
                } else {
                    System.out.println("Obraz pierwszy jest null - pomijam dodawanie do PDF");
                    System.out.println("Obraz drugi jest null - pomijam dodawanie do PDF");
                }


            }
            pdfCreator.closeDocument();


        }

        try {
            WebElement nextPage = driver.findElement(By.xpath("//a[@title='Następna strona']"));
            nextPage.click();
        } catch (NoSuchElementException e) {
            System.out.println("");
        }
        }
    }



