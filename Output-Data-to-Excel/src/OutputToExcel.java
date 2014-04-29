import java.io.FileNotFoundException;
import java.io.IOException;

public class OutputToExcel {

	public static void main(String[] args) {
		if (args.length != 4 && args.length != 3) {
			System.err.println("arguments is not 3 or 4.");
			System.err.println("Usage: java OutputToExcel [text file] [excel file name] [space row] [sheet name (default is sheet1)]");
			
			System.exit(1);
		}
		
		String sheet_name;
		if (args.length == 3) {
			sheet_name = "sheet1";
		} else {
			sheet_name = args[3];
		}
		
		InputDatas input = null;
		try {
			input = new InputDatas(args[0]);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}		
		
		try {
			input.execute();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ExcelDoc doc1 = new ExcelDoc(args[1]);
		String output_msg;
		if (doc1.checkFileExists() == false) {
			// File is not exist.
			// Create new excel file.
			doc1.createWorkbook();
			doc1.createSheet(sheet_name);
			doc1.writeGroup(input.getGroups());
			output_msg = "Write the excel file successful.";
		} else {
			// File is exist.
			// append the excel file.
			doc1.setWorkbookCursor();
			if (doc1.setSheetCursor(sheet_name) == true) {
				// New Sheet.
				doc1.writeGroup(input.getGroups(), 0, 0);
			} else {
				// Exist Sheet.
				int index = doc1.getExistsIndex();
				doc1.writeGroup(input.getGroups(), index, Integer.valueOf(args[2]));
			}
			output_msg = "Append the excel file successful.";
		}
		
		doc1.setColumnAutoSize(0, 11);
		
		try {
			doc1.writeToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(output_msg);
	}

}
