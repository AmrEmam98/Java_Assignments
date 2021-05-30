package Smile_XChart_Assignmet;

import org.apache.commons.csv.CSVFormat;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.measure.NominalScale;
import smile.data.vector.IntVector;
import smile.io.Read;
import smile.plot.swing.BarPlot;
import smile.plot.swing.Histogram;
import smile.regression.RandomForest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class SmileExample {

    public static void main(String[] args) {

        String fileName = "src/main/java/Smile_XChart_Assignmet/titanic_train.csv";
        try {

            DataFrame df = Read.csv(fileName, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            System.out.println(df.schema());
            System.out.println(df.summary());
            df = df.select("PassengerId", "Pclass", "Age", "SibSp", "Name", "Parch", "Sex", "Survived");
            SmileExample.encodeColumn(df, "Pclass");
            df = df.merge(IntVector.of("Gender", encodeColumn(df, "Sex")));
            df = df.merge(IntVector.of("PclassValues", encodeColumn(df, "Pclass")));
            df = df.drop("Sex").drop("Pclass").drop("Name");
            SmileExample.startEda(df);
            System.out.println("===============After EDA=======================");
            df=df.omitNullRows();
            System.out.println(df.schema());
            System.out.println(df.summary());

            RandomForest randomForest=RandomForest.fit(Formula.lhs("Survived"),df);
            System.out.println(randomForest.metrics());


                DataFrame testDf=Read.csv("src/main/java/Smile_XChart_Assignmet/titanic_test.csv",CSVFormat.DEFAULT.withFirstRecordAsHeader());
                System.out.println(testDf.summary());
                testDf=testDf.select("PassengerId", "Pclass", "Age", "SibSp", "Name", "Parch", "Sex");
                testDf = testDf.merge(IntVector.of("Gender", encodeColumn(testDf, "Sex")));
                testDf = testDf.drop("Sex").drop("Name");
                testDf=testDf.omitNullRows();
                System.out.println(testDf.summary());
                Arrays.stream(randomForest.predict(df)).forEach(System.out::println);

        } catch (IOException | URISyntaxException | InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static int[] encodeColumn(DataFrame df, String columnName) {
        String[] values = df.stringVector(columnName).distinct().toArray(new String[]{});
        Arrays.stream(values).forEach(System.out::println);
        int[] newValues = df.stringVector(columnName).factorize(new NominalScale(values)).toIntArray();
        return newValues;
    }

    public static void startEda(DataFrame df) throws InterruptedException, InvocationTargetException {
        df = df.omitNullRows();
        DataFrame survivedData = DataFrame.of(df.stream().filter(t -> t.get("Survived").equals(1)));
        DataFrame notSurvivedData = DataFrame.of(df.stream().filter(t -> t.get("Survived").equals(0)));
        System.out.println("===============Survived=======================");
        System.out.println(survivedData.summary());
        System.out.println("===============notSurvived=======================");
        System.out.println(notSurvivedData.summary());
            Histogram.of(survivedData.doubleVector("Age").toDoubleArray())
                    .canvas().setAxisLabel(0,"Age").setAxisLabel(1,"Count")
                    .setTitle("Age frequencies among surviving passengers")
                    .window();

        Map map = survivedData.stream ()
                .collect (Collectors.groupingBy (t -> Double.valueOf (t.getDouble ("Age")).intValue (), Collectors.counting ()));

        double[] breaks = ((Collection<Integer>) map.keySet ())
                .stream ()
                .mapToDouble (l -> Double.valueOf (l))
                .toArray ();

        double[] valuesInt = ((Collection<Long>) map.values ())
                .stream ().mapToDouble (i -> i.intValue ())
                .toArray ();
        double[][]data=new double[][]{breaks,valuesInt};
        BarPlot.of(data,new String[]{"Ages","Freq"})   .canvas().setAxisLabel(0,"Age").setAxisLabel(1,"Count")
                .setTitle("Age frequencies among surviving passengers")
                .window();


    }
}
