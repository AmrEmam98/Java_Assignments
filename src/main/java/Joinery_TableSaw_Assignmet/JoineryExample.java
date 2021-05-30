package Joinery_TableSaw_Assignmet;

import joinery.DataFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JoineryExample {

    public static void main(String[] args) {
        String filePath = "src/main/java/Joinery_TableSaw_Assignmet/titanic.csv";
        try {
            DataFrame<Object> df = DataFrame.readCsv(filePath);
            String dataDescription = df.describe().toString();
            System.out.println(dataDescription);
            Double mean = (Double) df.describe().col("age").get(1);
            System.out.println(df.length());
            DataFrame withNewColumn = JoineryExample.addColumn(df);
            System.out.println(withNewColumn.describe().toString());
            DataFrame<Object> newDataFrame = df.retain("pclass", "survived", "age");
            System.out.println(newDataFrame.describe().toString());
            df = df.retain("pclass", "sibsp", "parch", "fare", "body");
            System.out.println(df.describe().toString());


            DataFrame<Object>joinedDataFrame= newDataFrame.join(df);
            System.out.println(joinedDataFrame.describe().toString());
            System.out.println(df.describe().toString());
            List<Object> col = df.col(0);
            DataFrame<Object>dataWithPcalss1=df.select(objects -> ((Long) objects.get(0)).equals(Long.parseLong("1")));
            System.out.println(dataWithPcalss1.describe().toString());

//            System.out.println(newDataFrame.nonnumeric().describe().toString());
//            System.out.println(newDataFrame.nonnumeric().head().toString());
//            System.out.println(newDataFrame.notnull().toString());
//            DataFrame<Object>mergedDataFrame=newDataFrame.merge(dataWithPcalss1);
//            System.out.println(mergedDataFrame.describe().toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static DataFrame addColumn(DataFrame df) {
        int rowCount = df.length();
        List<Double> doubles = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            doubles.add(i + 1.0);
        }
        return df.add(doubles);
    }
}
