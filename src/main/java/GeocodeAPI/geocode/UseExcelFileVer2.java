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
public class UseExcelFileVer2 {

	private String FILE_PATH;;

	public String getFILE_PATH() {
		return FILE_PATH;
	}

	public void setFILE_PATH(String fILE_PATH) {
		FILE_PATH = fILE_PATH;
	}

	public List<FullLocation> readExcelFile() {
		List<FullLocation> locList = getLocListFromExcel();
		return locList;
	}

	private List getLocListFromExcel() {
		List<FullLocation> locList = new ArrayList<FullLocation>();
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
					FullLocation location = new FullLocation();
					Row row = (Row) rowIterator.next();
					Iterator cellIterator = row.cellIterator();

					//Iterating over each cell (column wise)  in a particular row.
					while (cellIterator.hasNext()) {
						Cell cell = (Cell) cellIterator.next();
						//Cell with index 1 contains Company Name
						if (cell.getColumnIndex() == 2) {
							location.setCompany(cell.getStringCellValue());
						}//Cell with index 2 contains Address
						else if (cell.getColumnIndex() == 4) {
							//System.out.println(cell.getStringCellValue());
							if(Cell.CELL_TYPE_STRING == cell.getCellType()){
								location.setAddress(cell.getStringCellValue());
							}else {
								location.setAddress(String.valueOf(cell.getNumericCellValue()));
							}
						}//Cell with index 2 contains City
						else if (cell.getColumnIndex() == 5) {
							location.setCity(cell.getStringCellValue());
						}//Cell with index 2 contains State
						else if (cell.getColumnIndex() == 6) {
							location.setState(cell.getStringCellValue());
						}
						else if (cell.getColumnIndex() == 0) {
							if(Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
								location.setId((int) (cell.getNumericCellValue()));
							}
						}
					}
					//end iterating a row, add all the elements of a row in list
					locList.add(location);
				}
			}
			fis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return locList;
	}

	private int rowIndex = 0;
	private Workbook workbook = new XSSFWorkbook();
	private Sheet studentsSheet = workbook.createSheet("LongLats");
	
	public void writeLongLats(FullLocation location){
		Row row = studentsSheet.createRow(rowIndex++);
		int cellIndex = 0;
		//first place in row is Id
		row.createCell(cellIndex++).setCellValue(location.getId());
		//Second place in row is Company
		row.createCell(cellIndex++).setCellValue(location.getCompany());
		//Third place in row is Address
		row.createCell(cellIndex++).setCellValue(location.getAddress());
		//Forth place in row is City
		row.createCell(cellIndex++).setCellValue(location.getCity());
		//Fifth place in row is State
		row.createCell(cellIndex++).setCellValue(location.getState());
		//Sixth place in row is Longitude
		row.createCell(cellIndex++).setCellValue(location.getLongitude());
		//Seventh place in row is marks in Latitude
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