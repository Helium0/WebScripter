import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.element.Link;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ScrapperHelper {


    public static Link convertStringInToURI(String text, PdfAction pdfAction) {
        return new Link(text, pdfAction);
    }

}


