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




    private final String SEARCH_PRODUCT = "Sweter Classic 02";

//    private final String SEARCH_PRODUCT = "Spodnie Lowen";


//    private final String SEARCH_PRODUCT = "Marynarka Beau";


//        private final String SEARCH_PRODUCT = "Marynarka Stone";



    @Test
    public void searchForOneProduct() throws InterruptedException, IOException {

            PdfCreator pdfCreator = new PdfCreator();
            NavigationBarPage navigationBarPage = new NavigationBarPage(driver);
            SearchPage searchPage = new SearchPage(driver);
            ProductPage productPage = new ProductPage(driver);

            navigationBarPage.clickForSearch();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            ProjectWaits.wait(driver).until(ExpectedConditions.attributeContains(searchPage.searchSpecyficProduct(), "placeholder", "Szukaj"));
            ProjectHelper.actions(driver).click(searchPage.searchSpecyficProduct()).sendKeys(SEARCH_PRODUCT)
                    .keyDown(Keys.ENTER).keyUp(Keys.ENTER).perform();
            ProjectWaits.waitForPresenceElementLocated(driver, productPage.searchProductSizeLabel());
            ProjectWaits.waitForPresenceElementLocated(driver, productPage.searchSpecyficProductSize());
            String searchResult = productPage.countSearchedProducts().getText();
            List<WebElement> testProducts = productPage.availableProducts().findElements(By.xpath("//div[contains(@class, 'Grid__Cell 1/2 1/3--tablet 1/4--lap-and-up')]"));
            List<String> firstColours = productPage.getAllColours().stream().map(e->e.getDomAttribute("title")).collect(Collectors.toList());
            Set<String> availableColours = ListsAndMaps.convertSetInToList(firstColours);


            pdfCreator.getDocument().add(new Paragraph(searchResult));
            pdfCreator.getDocument().add(new Paragraph(
                    "Dostepne kolory: "+availableColours));
//        Thread.sleep(3000);
//
//        List<WebElement> pp = driver.findElements(By.xpath(".//img[@class='ProductItem__Image ProductItem__Image--alternate Image--fadeIn lazyautosizes Image--lazyLoaded']"));
//        List<String> eh = pp.stream()
//                .map(e -> e.getDomAttribute("data-srcset"))  // Pobieramy atrybut data-srcset
////                        .filter(Objects::nonNull)  // Pomijamy null-e
//                .flatMap(srcset -> Arrays.stream(srcset.split(",\\s*"))) // Dzielimy na poszczególne linki
//                .map(link -> link.split(" ")[0]) // Pobieramy tylko sam link (bez rozmiaru)
//                .map(link -> link.replace("200", "1200")) // Zamieniamy "200" na "1200"
//                .map(link -> HTTP_PROTOCOL + link)// Dodajemy HTTP_PROTOCOL
//                .filter(e->e.contains("1200") && !e.contains("11200"))
//                .collect(Collectors.toList());
////                Thread.sleep(2000);
//        System.out.println("Liczba elementów w eh: " + eh.size());





            ProjectHelper.js(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            for (WebElement el : testProducts) {


                WebElement productName = el.findElement(By.xpath(".//h5[@class='ProductItem__Title Heading']/a"));
                WebElement productPrice = el.findElement(By.xpath(".//div[contains(@class, 'ProductItem__PriceList  Heading')]//span"));

                Thread.sleep(3000);

                List<WebElement> pp = driver.findElements(By.xpath(".//img[@class='ProductItem__Image ProductItem__Image--alternate Image--fadeIn lazyautosizes Image--lazyLoaded']"));
                List<String> eh = pp.stream()
                        .map(e -> e.getDomAttribute("data-srcset"))  // Pobieramy atrybut data-srcset
//                        .filter(Objects::nonNull)  // Pomijamy null-e
                        .flatMap(srcset -> Arrays.stream(srcset.split(",\\s*"))) // Dzielimy na poszczególne linki
                        .map(link -> link.split(" ")[0]) // Pobieramy tylko sam link (bez rozmiaru)
                        .map(link -> link.replace("200", "1200")) // Zamieniamy "200" na "1200"
                        .map(link -> HTTP_PROTOCOL + link)// Dodajemy HTTP_PROTOCOL
                        .filter(e -> e.contains("1200") && !e.contains("11200"))
                        .collect(Collectors.toList());
//                Thread.sleep(2000);
                System.out.println("Liczba elementów w eh: " + pp.size());


                tagProduct = PRODUCT_TAG;

                try {
                    WebElement productTag = el.findElement(By.xpath(".//span[@class='ProductItem__Label Heading Text--subdued']"));
                    tagProduct = (String) js.executeScript("return arguments[0].textContent;", productTag);
                    if (tagProduct.trim().contains("OFF")) {
                        priceAfterdiscount = true;
                    }
                } catch (NoSuchElementException e) {

                }

                priceProduct = (String) js.executeScript("return arguments[0].textContent;", productPrice);


                List<WebElement> productColors = el.findElements(By.xpath(".//label[@class='color-switcher__option']//span"));


                String p = productColors.stream().map(element -> element.getDomAttribute("title")).findFirst().orElse("bas");


                List<WebElement> productWeb = el.findElements(By.xpath(".//div[@class='ProductItem__Wrapper']/a"));

                for (WebElement elr : productWeb) {
                    ee = elr.getDomAttribute("href");


                }

                List<WebElement> productSizes = el.findElements(By.xpath(".//div[@class='ProductItem__QuickAdd__Item ']"));
                List<String> noDuplicatedSizes = productSizes.stream().map(element -> (String) js.executeScript("return arguments[0].textContent;", element))
                        .map(e -> e.isEmpty() ? "Brak" : e).collect(Collectors.toList());
                if (noDuplicatedSizes.isEmpty()) {
                    noDuplicatedSizes = List.of("Brak");
                }


                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@class='ProductItem__Image ProductItem__Image--alternate Image--fadeIn lazyautosizes Image--lazyLoaded']")));

                List<WebElement> pp2 = driver.findElements(By.xpath(".//img[@class='ProductItem__Image Image--fadeIn lazyautosizes Image--lazyLoaded']"));



//                Image imFirst = ImageCreator.convertStrigInToImage(ListsAndMaps.convertListToArrayAndReturnString(ListsAndMaps.convertListWebElIntoStringList(pp),HTTP_PROTOCOL));
//                Image imTwo = ImageCreator.convertStrigInToImage(ListsAndMaps.convertListToArrayAndReturnSecondString(ListsAndMaps.convertListWebElIntoStringList(pp2),HTTP_PROTOCOL));

//                imFirst.scaleToFit(200,300);
//                imTwo.scaleToFit(200,300);
//                eh = List.of(eh.size());

//                Thread.sleep(3000);
//
//                for (int i = 0; i<pp.size(); i++)
//                    String productLink = (i < eh.size()) ? eh.get(i) : "Brak zdjecia";
                    pdfCreator.getDocument().add(new Paragraph("\n" + "Wyswietlony produkt: " + productName.getText()));
                    pdfCreator.getDocument().add(new Paragraph("Kolor: " + p + " " + " [] " + "Cena: "
                            + priceProduct + " [] " + "Cena po rabacie: " + priceAfterdiscount + " [] " + "Oznaczenie produktu: " + tagProduct + "\n"));
                    pdfCreator.getDocument().add(new Paragraph("Dostepne rozmiary w tym kolorze: " + noDuplicatedSizes));
                    pdfCreator.getDocument().add(new Paragraph("Link do strony produktu: " + "\n" + WEBSITE_URL + ee));
                for (int i = 0; i<pp.size(); i++) {
                    String productLink = (i < eh.size()) ? eh.get(i) : "Brak zdjecia";
                    pdfCreator.getDocument().add(new Paragraph("Link do zdjecia: " + "\n").add(ScrapperHelper.convertStringInToURI(productLink, PdfAction.createURI(productLink))));
                }


//                pdfCreator.getDocument().add(imFirst);
//                pdfCreator.getDocument().add(imTwo);

            }
            pdfCreator.closeDocument();


        }
    }
