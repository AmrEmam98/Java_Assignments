package Smile_XChart_Assignmet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchart.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XChartExample {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/java/Smile_XChart_Assignmet/titanic_csv.json");
            List<TitanicPassenger> allPassenger = mapper.readValue(fileInputStream, new TypeReference<List<TitanicPassenger>>() {
            });

            allPassenger.stream().forEach(System.out::println);
            XChartExample.catagoryChartGraphPassengers(allPassenger);
            XChartExample.boxChartGraphPassengers(allPassenger);
            XChartExample.pieChartGraphPassengers(allPassenger);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void catagoryChartGraphPassengers(List<TitanicPassenger> titanicPassengers) {

        CategoryChart chart = new CategoryChartBuilder().width(1024)
                .height(700)
                .title("Age Histogram")
                .xAxisTitle("Names")
                .yAxisTitle("Age")
                .build();


        List<Float> ages = titanicPassengers.stream().map(TitanicPassenger::getAge).limit(8).collect(Collectors.toList());
        List<String> names = titanicPassengers.stream().map(TitanicPassenger::getName).limit(8).collect(Collectors.toList());

        chart.addSeries("Passenger's Ages ", names, ages);
        new SwingWrapper(chart).displayChart();

    }

    public static void boxChartGraphPassengers(List<TitanicPassenger> titanicPassengers) {
        BoxChart chart = new BoxChartBuilder().width(1024).height(700).title("New Title").build();
        List<Float> ages = titanicPassengers.stream().map(TitanicPassenger::getAge).collect(Collectors.toList());
        chart.addSeries("Passenger's Ages ", ages);
        new SwingWrapper(chart).displayChart();

    }

    public static void pieChartGraphPassengers(List<TitanicPassenger> titanicPassengers) {
        PieChart chart = new PieChartBuilder().width(1024).height(700).title("New Title").build();
        Map<Float, Long> pclassesMap = titanicPassengers.stream()
                .collect(Collectors.groupingBy(TitanicPassenger::getPclass, Collectors.counting()));

        pclassesMap.keySet().stream().forEach((key) -> chart.addSeries("Pclass " + key, pclassesMap.get(key)));
        new SwingWrapper(chart).displayChart();
    }


}
