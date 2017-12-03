package GeocodeAPI.geocode;

import java.util.Iterator;
import java.util.List;

public class GeoCodeAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GeoCodeAPIDemo longlats = new GeoCodeAPIDemo();
	/*	UseExcelFile var1 = new UseExcelFile();
		var1.setFILE_PATH("VApopulation.xlsx");
		List<Location> zipLocations= var1.readExcelFile();
		
		var1.setFILE_PATH("testWriteStudents.xlsx");
		Iterator<Location> itr1 = zipLocations.iterator();
		while (itr1.hasNext()) {
			Location location = (Location) itr1.next();
			location = longlats.getLongLats(location.getZipcode());
			var1.writeLongLats(location);
		}*/
		
		UseExcelFileVer2 var2 = new UseExcelFileVer2();
		var2.setFILE_PATH("X/WV.xlsx");
		List<FullLocation> fullLocations= var2.readExcelFile();
		
		var2.setFILE_PATH("WV_NEW.xlsx");
		Iterator<FullLocation> itr2 = fullLocations.iterator();
		while (itr2.hasNext()) {
			FullLocation fullLocation = (FullLocation) itr2.next();
			Location location = longlats.getLongLats(fullLocation.getAddress()+" "+fullLocation.getCity()+" "+fullLocation.getState());
			fullLocation.setLongitude(location.getLongitude());
			fullLocation.setLatitude(location.getLatitude());
			var2.writeLongLats(fullLocation);
		}
	}
}
