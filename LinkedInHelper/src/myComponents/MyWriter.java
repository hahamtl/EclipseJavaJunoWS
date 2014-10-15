package myComponents;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MyWriter {

	private boolean ifLogFile;
	private String fileName;
	private int id;

	public MyWriter(boolean type) {
		this.ifLogFile = type;
		if (this.ifLogFile) {
			fileName = "helperLog.txt";
		} else {
			fileName = "userLog.txt";
		}
		this.id = 1;
		clearFile();
	}

	private void clearFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			out.write("");
			out.close();
		} catch (IOException e) {
		}

	}

	public void write(String s) {

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName,
					true));
			out.newLine();
			out.write("[ "+ String.format("%06d", id++) +" ] == >> "+ s );
			

			out.close();
		} catch (IOException e) {
		}
	}

}
