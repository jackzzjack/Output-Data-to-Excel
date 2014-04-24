import java.util.ArrayList;

public class Group {
	
	private ArrayList<String> lines;

	public Group() {
		lines = new ArrayList<String>();
	}

	public ArrayList<String> getGroup() {
		return lines;
	}

	public void addLine(String line) {
		lines.add(line);
	}
}