import org.openqa.selenium.WebElement;

import java.util.*;


public class ListsAndMaps {




    private static List<String> createStringList() {
        return new ArrayList<>();
    }


    public static String productStringURL(String basicAttribute, String http) {
        String productImage = "";
        if(basicAttribute != null) {
            productImage = Arrays.stream(basicAttribute.split(",\\s*"))
                    .map(link -> link.split(" ")[0])
                    .map(link -> link.replace("200","1200"))
                    .map(link -> http+link)
                    .findFirst()
                    .orElse("Brak zdjecia");
        }
        return productImage;
    }


    public static Set<String> convertSetInToList(List<String> sample) {
        Set<String> y = new HashSet<>(sample);
        y.stream().map(e -> y.add(e));
        return y;
    }










}
