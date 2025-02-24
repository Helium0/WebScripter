import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ListsAndMaps {




    private static List<String> createStringList() {
        return new ArrayList<>();
    }

    public static String convertListToArrayAndReturnString(List<String> stringList, String http) {

        String first = stringList.get(0).split(" ")[0];
        if(!first.contains("200")) {
            return first;
        }
        String updated = first.replace("200","1200");
        return http+updated;

    }



    public static String convertListToArrayAndReturnSecondString(List<String> stringList, String http) {
        String first = stringList.get(0).split(" ")[0];
        if(!first.contains("200")) {
            return first;
        }
        String uptaded = first.replace("200","1200");
        return http+uptaded;

    }



    public static List<String> convertListWebElIntoStringList(List<WebElement> webElementList) {
        List<String> sample = createStringList();
        for (WebElement example : webElementList) {
            String text = example.getDomAttribute("srcset");
            sample.add(text);
        }
        return sample;

    }

    public static Set<String> convertSetInToList(List<String> sample) {
        Set<String> y = new HashSet<>(sample);
        y.stream().map(e -> y.add(e));
        return y;
    }










}
