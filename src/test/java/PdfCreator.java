import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;


public class PdfCreator {
    public static void main(String[] args) throws FileNotFoundException {


        String myPath = System.getProperty("user.dir") + "\\src\\test\\resources\\myPdf.pdf";

        PdfDocument pdf = new PdfDocument(new PdfWriter(myPath));
        Document document = new Document(pdf);
        String myLine = "TEST";
        String aa = "okok";
        document.add(new Paragraph(myLine));
        document.add(new Paragraph(aa));
        document.close();

        System.out.println("created file!");

    }
}