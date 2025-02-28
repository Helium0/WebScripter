package utilities;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageCreator {




    private static ImageData createImageStringData(String text) throws MalformedURLException {
        return ImageDataFactory.create(new URL(text));
    }

    public static Image convertStrigInToImage(String text) throws MalformedURLException {
        return new Image(createImageStringData(text));
    }

    public static Image validatingImages(String text) {
        Image image = null;
        try {
            if(!"https:Brak".contains(text)) {
                image = convertStrigInToImage(text);
            } else {
                text = "Brak obrazu";
            }
        } catch (IOException e) {
            System.out.println("Blad przy ladowaniu obrazu");
        }
        return image;
    }


}
