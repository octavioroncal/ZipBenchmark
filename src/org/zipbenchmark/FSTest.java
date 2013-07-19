package org.zipbenchmark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FSTest {


	public static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy/MM/dd/HH/mm/ss");
	public static SimpleDateFormat folderFormatter = new SimpleDateFormat(
			"yyyy/MM/dd/HH/mm");

	public static void writeFS(int numFiles) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
		FileOutputStream stream = null;
		for (int i = 0; i < numFiles; i++) {
			checkIfExists(new File(folderFormatter.format(calendar.getTime())));
			File f = new File(formatter.format(calendar.getTime()));
			stream = new FileOutputStream(f);
			byte[] buf = new byte[1024];
			stream.write(buf);
			calendar.add(Calendar.SECOND, 1);
			stream.close();
		}
	}

	public static void readFS(int numFiles) throws IOException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
		for (int i = 0; i < numFiles; i++) {
			byte[] buffer = new byte[1024];
			FileInputStream fileInputStream = new FileInputStream(new File(formatter.format(calendar.getTime())));
			fileInputStream.read(buffer);
			fileInputStream.close();
			calendar.add(Calendar.SECOND, 1);
		}
	}

	private static void checkIfExists(File uri) {
		if (!uri.exists())
			uri.mkdirs();
	}
}
