package FunctionalProgramming_Collections_Assignment;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainClass {

    public static void main(String[] args) {
        CountriesDAO countriesDAO = new CountriesDAO();
        CitiesDAO citiesDAO = new CitiesDAO();

        List<Country> countryList = countriesDAO.readAllCountries("src/main/java/FunctionalProgramming_Collections_Assignment/countries.csv");
        List<City> cityList = citiesDAO.readAllCites("src/main/java/FunctionalProgramming_Collections_Assignment/cities.csv");

        Map<Integer, List<City>> countryCities = new HashMap<>();

        for (City c : cityList) {
            if (!countryCities.containsKey(c.code)) {
                List<City> cities = new ArrayList<>();
                cities.add(c);
                countryCities.put(c.code, cities);
            } else {
                countryCities.get(c.code).add(c);
            }
        }


        Predicate<String> predicate = (str) -> str.length() > 5;
        BiPredicate<String, String> biPredicate = (str1, str2) -> str1.length() > str2.length();

        Function<String, Boolean> function = (str) -> str.length() > 0;
        CompareStrings compareStrings = new CompareStrings();
        String out = compareStrings.betterString("Amr", "Emam", (str1, str2) -> str1.length() > str2.length());
        String out2 = compareStrings.betterString("Amr", "Emam", (str1, str2) -> str1.charAt(0) > str2.charAt(0));
        System.out.println(out);
        System.out.println(out2);
        Boolean bool_out = compareStrings.checkString("Amr123", CompareStrings::checkIfAllLetter);
        String out3 = compareStrings.betterString("Amr", "Emam", compareStrings::better);
        System.out.println(bool_out);
        System.out.println(bool_out);
        System.out.println(bool_out);
        Consumer<String> consumer = System.out::println;
        consumer.accept("Amr");


        compareStrings.printAllCities(cityList, City::print);
        Double reduce = cityList.stream()
                .map(City::getPopulation)
                .map((population) -> population + 2)
                .reduce(0.0, (x, y) -> x + y);


        Map<Integer, List<City>> collect = cityList.stream()
                .collect(Collectors.groupingBy(City::getCode));


        List<Optional<Double>> highestPopulationPerCounty = collect.values()
                .stream()//List<City>
                .map(cityList1 -> cityList1.stream()//city
                        .map(City::getPopulation)
                        .max(Double::compare)
                ).collect(Collectors.toList());


        Map<String, List<City>> collect1 = cityList
                .stream()
                .collect(Collectors.groupingBy(City::getContinent));

        List<Double> collect2 = cityList
                .stream()
                .map(City::getPopulation)
                .sorted().collect(Collectors.toList());

    }


}

