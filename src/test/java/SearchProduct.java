import com.itextpdf.layout.element.Image;
import helper.ProjectHelper;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.element.Paragraph;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.NavigationBarPage;
import pages.ProductPage;
import pages.SearchPage;
import utilities.ImageCreator;
import utilities.PdfCreator;
import waits.ProjectWaits;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SearchProduct extends BasePage {





    private final String WEBSITE_URL = "https://www.303avenue.pl";

    private final boolean HAS_NEXT_PAGE = true;  // Zmienna do paginacji

    private final int SCROLL_AMOUNT = 1000; // Liczba pikseli do przewinięcia w każdym kroku

    private final String SEARCH_PRODUCT = "Spodnie";

    private final String IMAGE_ATTRIBUTE = "data-srcset";

    private final By NEWSLETTER = By.xpath("//button[contains(@aria-label,'Close dialog')]");

    private final By FIRST_PICTURE = By.xpath(".//img[@class='ProductItem__Image ProductItem__Image--alternate Image--fadeIn lazyautosizes Image--lazyLoaded']");

    private final By SECOND_PICTURE = By.xpath(".//img[@class='ProductItem__Image Image--fadeIn lazyautosizes Image--lazyLoaded']");

    private final By PRODUCT_HEAD_LABEL = By.xpath(".//span[@class='ProductItem__Label Heading Text--subdued']");

    private final By PRODUCT_PRICE = By.xpath(".//div[contains(@class, 'ProductItem__PriceList  Heading')]//span");



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
        List<String> firstColours = productPage.getAllColours().stream().map(e -> e.getDomAttribute("title")).collect(Collectors.toList());

        pdfCreator.getDocument().add(new Paragraph(searchResult));
        pdfCreator.getDocument().add(new Paragraph(
                "Dostepne kolory: " + ListsAndMaps.convertSetInToList(firstColours)));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        ProjectWaits.wait(driver).until(ExpectedConditions.presenceOfElementLocated(NEWSLETTER)).click();


        while (HAS_NEXT_PAGE) {

            ProjectHelper.sideBarMover(js,SCROLL_AMOUNT);

            List<WebElement> testProducts = productPage.getAllProducts();
            for (int i = 0; i < testProducts.size(); i++) {   // iteracja po długości listy produktów

                WebElement product = testProducts.get(i);

                WebElement productName = product.findElement(By.xpath(".//h5[@class='ProductItem__Title Heading']/a"));

                ProjectWaits.wait(driver).until(ExpectedConditions
                        .presenceOfElementLocated(FIRST_PICTURE));


                String productImage = productPage.productPicture(product, FIRST_PICTURE, IMAGE_ATTRIBUTE);
                String productImageTwo = productPage.productPicture(product, SECOND_PICTURE, IMAGE_ATTRIBUTE);

                String updatedPictureProduct = ListsAndMaps.productStringURL(productImage, ProjectHelper.addHttpsProtocolPrefix());
                System.out.println(updatedPictureProduct + "          OK"); // wyświetlam linki w konsoli dla pierwszego obrazu

                String updatedPictureProductTwo = ListsAndMaps.productStringURL(productImageTwo, ProjectHelper.addHttpsProtocolPrefix());
                System.out.println(updatedPictureProductTwo + "          OK"); // dla drugiego obrazu

                boolean priceAfterDiscount = productPage.discountedProductOrNo(product, PRODUCT_HEAD_LABEL, js);
                String updatedtTag = productPage.productTagReturner(product, PRODUCT_HEAD_LABEL, js);
                String updatedProductPrice = productPage.productPriceReturner(product,PRODUCT_PRICE, js);

                List<WebElement> productColors = product.findElements(By.xpath(".//label[@class='color-switcher__option']//span"));

                String colourName = productColors.stream().map(element -> element.getDomAttribute("title")).findFirst().orElse("NULL");
                List<WebElement> productWeb = product.findElements(By.xpath(".//div[@class='ProductItem__Wrapper']/a"));

                String productHyperLink = productPage.hyperLinkToTheProduct(productWeb);

                List<WebElement> productSizes = product.findElements(By.xpath(".//div[@class='ProductItem__QuickAdd__Item ']"));
                List<String> noDuplicatedSizes = productSizes.stream().map(element -> (String) js.executeScript("return arguments[0].textContent;", element))
                        .map(e -> e.isEmpty() ? "Brak" : e).collect(Collectors.toList());
                if (noDuplicatedSizes.isEmpty()) {
                    noDuplicatedSizes = List.of("Brak");
                }

                Image image = ImageCreator.validatingImages(updatedPictureProduct);
                Image second = ImageCreator.validatingImages(updatedPictureProductTwo);

                pdfCreator.getDocument().add(new Paragraph("\n" + "Wyswietlony produkt: " + productName.getText()));
                pdfCreator.getDocument().add(new Paragraph("Kolor: " + colourName + " " + " [] " + "Cena: "
                        + updatedProductPrice + " [] " + "Cena po rabacie: " + priceAfterDiscount + " [] " + "Oznaczenie produktu: " + updatedtTag + "\n"));
                pdfCreator.getDocument().add(new Paragraph("Dostepne rozmiary w tym kolorze: " + noDuplicatedSizes));
                pdfCreator.getDocument().add(new Paragraph("Link do strony produktu: " + "\n" + WEBSITE_URL + productHyperLink));

                if (updatedPictureProduct.equals("Brak obrazu") && updatedPictureProductTwo.equals("Brak obrazu")) {
                    pdfCreator.getDocument().add(new Paragraph("Brak obrazu oraz linku do zdjecia dla tego produktu"));
                } else {
                    pdfCreator.getDocument().add(new Paragraph("Link do zdjecia: " + "\n").add(ScrapperHelper.convertStringInToURI(updatedPictureProduct, PdfAction.createURI(updatedPictureProduct))));
                    pdfCreator.getDocument().add(new Paragraph().add(ScrapperHelper.convertStringInToURI(updatedPictureProductTwo, PdfAction.createURI(updatedPictureProductTwo))));
                }
                
                if (image != null && second != null) {
                    pdfCreator.getDocument().add(image);
                    pdfCreator.getDocument().add(second);
                } else {
                    System.out.println("Obraz pierwszy jest null - pomijam dodawanie do PDF");
                    System.out.println("Obraz drugi jest null - pomijam dodawanie do PDF");
                }
            }

           if(productPage.nextPageProducts(HAS_NEXT_PAGE) == false) {
               break;
           }

        }

        pdfCreator.closeDocument();
        }
    }