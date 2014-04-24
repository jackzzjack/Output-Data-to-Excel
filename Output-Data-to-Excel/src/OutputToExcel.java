import java.io.FileNotFoundException;
import java.io.IOException;

public class OutputToExcel {

	public static void main(String[] args) {
		InputDatas input = null;
		try {
			input = new InputDatas(args[1]);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}		
		
		try {
			input.execute();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ExcelDoc doc1 = new ExcelDoc(args[2]);
		doc1.createWorkbook();
		doc1.createSheet();
		
		doc1.writeGroup(input.getGroups());
		doc1.setColumnAutoSize(0, 9);
		
		try {
			doc1.writeToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
