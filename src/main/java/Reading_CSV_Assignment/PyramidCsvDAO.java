package Reading_CSV_Assignment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class PyramidCsvDAO {
	
public List<Pyramid>readPyramidsFromCSV(String fileName) {
	
	File pyramids_file=new File(fileName);
	List<String>lines=new ArrayList();
	List<Pyramid> allPyramids=new ArrayList<>();
	
	try {
		lines=Files.readAllLines(pyramids_file.toPath());
		for (int i=1 ;i<lines.size();i++) {
			allPyramids.add(createPyramid(lines.get(i)));
		}
		
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	
	
	
	return allPyramids;
	
}

private Pyramid createPyramid(String line) {
  String[] fields=line.split(",");
  double height=0;
  if(!fields[7].equals(""))
   	height=Double.parseDouble(fields[7]);
  return new Pyramid(fields[0], fields[2], fields[4], height);
}

}
