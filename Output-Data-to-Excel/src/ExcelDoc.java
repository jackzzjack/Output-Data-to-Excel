import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDoc {
	
	private File _file;
	private Workbook _workbook;
	private Sheet _sheet1;
	
	private boolean exists;
	
	public ExcelDoc(String _fileName) {
		_file = new File(_fileName);
		
		exists = checkFileExists();
	}
	
	public boolean checkFileExists() {
		return _file.exists();
	}

	public void createWorkbook() {
		_workbook = new XSSFWorkbook();
	}
	
	public void createSheet(String name) {
		_sheet1 = _workbook.createSheet(name);
	}
	
	public void writeGroup(ArrayList<Group> groups) {
		writeGroup(groups, 0, 0);
	}
	
	/*
	 * start_row is not added the space row already.
	 * if start_row == 5, space == 4
	 * 
	 * graph:	X is previous data
	 * 		X
	 * 		X
	 * 		X
	 * 		X
	 * 		X
	 * 		space row
	 * 		space row
	 * 		space row
	 * 		space row
	 * 		data
	 * 		.
	 * 		.
	 * 		.
	 */
	public void writeGroup(ArrayList<Group> groups, int start_row, int space) {
		// Create the space(null) rows
		for (int i=start_row; i < start_row+space; i++) {
			_sheet1.createRow(i);
		}
		
		int data_start_row = start_row + space;
		for (int i=0; i < groups.size(); i++) {
			ArrayList<String> lines = groups.get(i).getGroup();
			for (int j=0; j < lines.size(); j++) {
				writeToCell(j+data_start_row, i, Double.valueOf(lines.get(j)));
			}
		}
	}
	
	private void writeToCell(int row, int col, double data) {
		if (_sheet1.getRow(row) == null) {
			_sheet1.createRow(row).createCell(col).setCellValue(data);
		} else {
			_sheet1.getRow(row).createCell(col).setCellValue(data);
		}
	}
	
	public void writeToFile() throws IOException {
		FileOutputStream FOS = new FileOutputStream(_file);
		_workbook.write(FOS);
		FOS.close();
	}
	
	public void setColumnAutoSize(int from, int to) {
		for (int i=from; i < to; i++)
			_sheet1.autoSizeColumn(i);
	}

	/*
	 * May have some bug issue.
	 */
	public int getExistsIndex() {
		int index = 0;
		
		while (_sheet1.getRow(index) != null) {
			index++;
		}
		
		return index;
	}

	public void setSheetCursor(String name) {
		_sheet1 = _workbook.getSheet(name);
		
		if (_sheet1 == null)
			_sheet1 = _workbook.createSheet(name);
	}

	public void setWorkbookCursor() {
		FileInputStream FIS = null;
		try {
			FIS = new FileInputStream(_file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			_workbook = WorkbookFactory.create(FIS);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
