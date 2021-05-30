package Joinery_TableSaw_Assignmet;

import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Table;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TableSawExample {

    public static void main(String[] args) {
        TableSawExample object = new TableSawExample();
        Table df = null;
        try {
            df = Table.read().csv("src/main/java/Joinery_TableSaw_Assignmet/titanic.csv");
            System.out.println(df.structure().toString());
            System.out.println(df.summary().toString());
            System.out.println(object.addFakeIntegerColumn(df).summary().toString());
            System.out.println(object.addFakeDateColumn(df).summary().toString());
            Table newDataFrameLeft = df.select("name", "pclass", "survived", "sex");
            System.out.println("After Retain");

            System.out.println(df.structure().toString());
            Table newDataFrameRight = df.select("name", "age", "sibsp", "parch");
            System.out.println(newDataFrameLeft.summary().toString());

            System.out.println(newDataFrameLeft.joinOn("name").leftOuter(newDataFrameRight).summary().toString());

            int[] genders = df.stream().mapToInt(row -> {
                String gender = row.getString("sex");
                if (gender.equals("female"))
                    return 1;
                else
                    return 0;
            }).toArray();
            IntColumn genderColumn = IntColumn.create("gender", genders);
            df.addColumns(genderColumn);
            df.removeColumns("sex");
            System.out.println(df.summary().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Table addFakeIntegerColumn(Table df) {
        int rowCount = df.rowCount();
        Integer[] values = new Integer[rowCount];
        List<Integer> colValues = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            values[i] = i + 1;
        }
        IntColumn column = IntColumn.create("FakeColumn", values);
        return df.addColumns(column);
    }

    public Table addFakeDateColumn(Table df) {
        int rowCount = df.rowCount();
        List<LocalDate> colValues = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            colValues.add(LocalDate.of(2021, 5, i % 31 == 0 ? 1 : i % 31));
        }
        DateColumn column = DateColumn.create("FakeDateColumn", colValues);
        return df.addColumns(column);
    }

}
