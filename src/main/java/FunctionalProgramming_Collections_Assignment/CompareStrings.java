package FunctionalProgramming_Collections_Assignment;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CompareStrings {

    String betterString(String str1, String str2, BiPredicate<String, String> biPredicate) {
        if (biPredicate.test(str1, str1))
            return str1;
        return str2;
    }

    boolean checkString(String str1, Predicate<String> stringPredicate) {
        return stringPredicate.test(str1);
    }
    boolean better(String str1,String str2){
        return str1.length()>str2.length();
    }
    static boolean  checkIfAllLetter(String str) {

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

 void  printAllCities(List<City> cityList, Consumer<City>consumer){
        for(City c:cityList){
            consumer.accept(c);
        }
 }

}
