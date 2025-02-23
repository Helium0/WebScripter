package utilities;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import java.io.FileNotFoundException;
import java.util.Date;


public class PdfCreator  {

    private PdfDocument pdfDocument;

    private Document document;

    private final String MY_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\scrappedItems"+fillDate()+".pdf";


    public PdfCreator() throws FileNotFoundException {
        this.pdfDocument = new PdfDocument(new PdfWriter(MY_PATH));
        this.document = new Document(pdfDocument);
    }


    public  Document getDocument() {
        return document;
    }


    public void closeDocument() {
        if(document != null) {
            document.close();
        }
    }

    private String fillDate() {
        Date date = new Date();
        return date.toString().replace(" ","_").replace(":","_");
    }




}