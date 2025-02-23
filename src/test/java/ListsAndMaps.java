import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ListsAndMaps {




    public static String convertListToArrayAndReturnString(List<String> stringList, String http) {
        String text = " ";
        String [] sample = stringList.toArray(new String[0]);
        for(char i = 0; i<sample.length; i++) {
            text = http+sample[0].substring(0,97).replace("200","1200");
        }
        return text;
    }

    public static String convertListToArrayAndReturnSecondString(List<String> stringList, String http) {
        String text = " ";
        String [] sample = stringList.toArray(new String[0]);
        for(char i = 0; i<sample.length; i++) {
            text = http+sample[0].substring(0,70).replace("200","1200");
        }
        return text;
    }



    public static List<String> convertListWebElIntoStringList(List<WebElement> webElementList) {
        List<String> sample = new ArrayList<>();
        for (WebElement example : webElementList) {
            String text = example.getDomAttribute("srcset");
            sample.add(text);
        }
        return sample;

    }










}
