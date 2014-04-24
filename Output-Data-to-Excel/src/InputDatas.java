import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * LINE =====> LINES
 *             GROUP ======> GROUPS
 *             
 * A1
 * A2                                              A1 B1
 *        =====> Output to Excel will show =====>  
 * B1                                              A2 B2
 * B2
 * 
 */

public class InputDatas {
	
	/*
	 * Debugging Options
	 */
	private final static boolean DUMP_EXECUTE_INPUT_STRINGS = false;
	
	
	private String _inputFileName;
	private ArrayList<Group> _allData;
	private FileReader reader;
	
	public InputDatas(String _inputFileName) throws FileNotFoundException {
		this._inputFileName = _inputFileName;
		this._allData = new ArrayList<Group>();
	}
	
	@SuppressWarnings("unused")
	public void execute() throws IOException {
		reader = new FileReader(_inputFileName);
		BufferedReader BR = new BufferedReader(reader);
		
		String str;
		Group tmp_group = new Group();
		while ((str = BR.readLine()) != null) {
			if (DUMP_EXECUTE_INPUT_STRINGS == true) {
				if (str.matches("[\\s]+"))
					System.out.println("1");
				else
					System.out.println(str);
			}
			
			if (str.matches("[\\s]+")) {
				_allData.add(tmp_group);
				tmp_group = new Group();
			} else {
				tmp_group.addLine(str);
			}
		}
	}
	
	public void close() throws IOException {
		reader.close();
	}
	
	public void dumpGroups() {
		for (int i=0; i < _allData.size(); i++) {
			ArrayList<String> lines = _allData.get(i).getGroup();
			for (int j=0; j < lines.size(); j++) {
				System.out.println(lines.get(j));
			}
		}
	}
	
	public ArrayList<Group> getGroups() {
		return _allData;
	}
	
	public static void main(String[] args) {
		InputDatas input = null;
		
		try {
			input = new InputDatas("tmp");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			input.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		input.dumpGroups();
		
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
