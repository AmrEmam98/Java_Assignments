package Reading_CSV_Assignment;

import java.util.List;

public class TestClass {

	public static void main(String[] args) {
		
		PyramidCsvDAO pDAO=new PyramidCsvDAO();
		List<Pyramid>pyramids=pDAO.readPyramidsFromCSV("src/main/java/Reading_CSV_Assignment/pyramids.csv");
		
		for(Pyramid p:pyramids) {
			System.out.println(p);
		}
		

	}

}
