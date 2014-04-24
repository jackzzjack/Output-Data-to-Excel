import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDoc {
	
	private String _fileName;
	private Workbook _workbook;
	private Sheet _sheet1;
	
	public ExcelDoc(String _fileName) {
		this._fileName = _fileName;
	}
	
	public void createWorkbook() {
		_workbook = new XSSFWorkbook();
	}
	
	public void createSheet() {
		_sheet1 = _workbook.createSheet("sheet1");
	}
	
	public void writeGroup(ArrayList<Group> groups) {
		for (int i=0; i < groups.size(); i++) {
			ArrayList<String> lines = groups.get(i).getGroup();
			for (int j=0; j < lines.size(); j++) {
				writeToCell(j, i, Double.valueOf(lines.get(j)));
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
		FileOutputStream FOS = new FileOutputStream(_fileName);
		_workbook.write(FOS);
		FOS.close();
	}
	
	public void setColumnAutoSize(int from, int to) {
		for (int i=from; i < to; i++)
			_sheet1.autoSizeColumn(i);
	}
}
