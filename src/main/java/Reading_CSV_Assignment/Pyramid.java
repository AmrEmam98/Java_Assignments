package Reading_CSV_Assignment;

public class Pyramid {
 String modern_name,site,pharaoh;
 double height;
public Pyramid( String pharaoh,String modern_name, String site, double height) {
	this.modern_name = modern_name;
	this.site = site;
	this.pharaoh = pharaoh;
	this.height = height;
}

public String getModern_name() {
	return modern_name;
}
public void setModern_name(String modern_name) {
	this.modern_name = modern_name;
}
public String getSite() {
	return site;
}
public void setSite(String site) {
	this.site = site;
}
public String getPharaoh() {
	return pharaoh;
}
public void setPharaoh(String pharaoh) {
	this.pharaoh = pharaoh;
}
public double getHeight() {
	return height;
}
public void setHeight(double height) {
	this.height = height;
}

@Override
public String toString() {
	return "Pyramid [modern_name=" + modern_name + ", site=" + site + ", pharaoh=" + pharaoh + ", height=" + height
			+ "]";
}

 
}
