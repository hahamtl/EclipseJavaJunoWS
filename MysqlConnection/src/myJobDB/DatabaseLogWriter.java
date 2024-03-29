package myJobDB;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class DatabaseLogWriter {
	private String fileName;
	private SimpleDateFormat sDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss.SS");

	public DatabaseLogWriter(String name) {

		fileName = name + "DatabaseLog.txt";

	}

	public void clearFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			out.write("");
			out.close();
		} catch (IOException e) {
		}

	}

	public void appendWrite(String s) {

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName,
					true));
			out.newLine();

			out.write("[ "
					+sDateFormat.format(new   java.util.Date()) + " ] == >> " + s);
			out.close();
		} catch (IOException e) {
		}
	}

}
