package utilities;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;

import java.net.MalformedURLException;
import java.net.URL;

public class ImageCreator {



//    ImageData imageData = ImageDataFactory.create(new URL());

    private static ImageData createImageStringData(String text) throws MalformedURLException {
        return ImageDataFactory.create(new URL(text));
    }

    public static Image convertStrigInToImage(String text) throws MalformedURLException {
        return new Image(createImageStringData(text));
    }

}
