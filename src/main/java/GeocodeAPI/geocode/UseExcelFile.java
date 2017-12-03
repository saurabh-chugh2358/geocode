package GeocodeAPI.geocode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by anirudh on 20/10/14.
 */
public class UseExcelFile {

	private String FILE_PATH;;

	public String getFILE_PATH() {
		return FILE_PATH;
	}

	public void setFILE_PATH(String fILE_PATH) {
		FILE_PATH = fILE_PATH;
	}

	public List<Location> readExcelFile() {
		List<Location> zipList = getzipListFromExcel();
		return zipList;
	}

	private List getzipListFromExcel() {
		List<Location> zipList = new ArrayList<Location>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(FILE_PATH);
			// Using XSSF for xlsx format, for xls use HSSF
			Workbook workbook = new XSSFWorkbook(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			//looping over each workbook sheet
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator rowIterator = sheet.iterator();

				//iterating over each row
				while (rowIterator.hasNext()) {
					Location location = new Location();
					Row row = (Row) rowIterator.next();
					Iterator cellIterator = row.cellIterator();

					//Iterating over each cell (column wise)  in a particular row.
					while (cellIterator.hasNext()) {
						Cell cell = (Cell) cellIterator.next();
						//Cell with index 1 contains ZIPCODE
						if (cell.getColumnIndex() == 0) {
							location.setZipcode(String.valueOf(cell.getNumericCellValue()).substring(0, 5));
							System.out.println(location.getZipcode());
						}
						//Cell with index 2 contains Population
						else if (cell.getColumnIndex() == 1) {
							location.setPopulation(String.valueOf(cell.getNumericCellValue()));
						}
					}
					//end iterating a row, add all the elements of a row in list
					zipList.add(location);
				}
			}
			fis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zipList;
	}

	private int rowIndex = 0;
	private Workbook workbook = new XSSFWorkbook();
	private Sheet studentsSheet = workbook.createSheet("VALongLats");
	
	public void writeLongLats(Location location){
		Row row = studentsSheet.createRow(rowIndex++);
		int cellIndex = 1;
		//first place in row is Zip Code
		row.createCell(cellIndex++).setCellValue(location.getZipcode());

		//Second place in row is Longitude
		row.createCell(cellIndex++).setCellValue(location.getLongitude());

		//second place in row is Latitude
		row.createCell(cellIndex++).setCellValue(location.getLatitude());

		//write this workbook in excel file.
		try {
			FileOutputStream fos = new FileOutputStream(FILE_PATH);
			workbook.write(fos);
			fos.close();

			System.out.println(FILE_PATH + " is successfully written");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}