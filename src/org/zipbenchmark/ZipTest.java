package org.zipbenchmark;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipTest {

	public static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy/MM/dd/HH/mm/ss");

	public static void createZip(int numFiles) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
		ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(
				new File("test.zip")));
		CRC32 crc = new CRC32();
		for (int i = 0; i < numFiles; i++) {
			crc.reset();
			calendar.add(Calendar.SECOND, 1);
			String date = formatter.format(calendar.getTime());
			writeZip(stream, crc, date);
		}
		stream.close();
	}

	private static void writeZip(ZipOutputStream stream, CRC32 crc, String date)
			throws IOException {
		ZipEntry entry = new ZipEntry(date);
		byte[] buf = new byte[1024];
		crc.update(buf, 0, 1024);
		configZipEntry(crc, entry);
		stream.putNextEntry(entry);
		stream.write(buf);
		stream.closeEntry();
	}

	private static void configZipEntry(CRC32 crc, ZipEntry entry) {
		entry.setMethod(ZipEntry.STORED);
		entry.setCompressedSize(1024);
		entry.setSize(1024);
		entry.setCrc(crc.getValue());
	}

	public static void readZip() throws IOException {
		ZipFile zipFile = new ZipFile("test.zip");
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
		byte[] buffer = new byte[1024];
		for (int i = 0; i < zipFile.size(); i++) {
			calendar.add(Calendar.SECOND, 1);
			ZipEntry entry = new ZipEntry(formatter.format(calendar.getTime()));
			InputStream stream = zipFile.getInputStream(entry);
			ZipFile zipFile2 = new ZipFile("test.zip");
			zipFile2.close();
			stream.read(buffer);
			stream.close();
		}
		zipFile.close();
	}


	public static void findDeepestFile() throws IOException {
		String date = "0000/00/00/00/00/00";
		 ZipFile zipFile = new ZipFile("test.zip");
		Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
		while (zipEntries.hasMoreElements()) {
			String newEntry = zipEntries.nextElement().getName();
			if (newEntry.compareTo(date) > 0)
				date = newEntry;
		}
		zipFile.close();
	}
	
	@SuppressWarnings("all")
	public static void findBySort() throws IOException {
		ArrayList<ZipEntry> list = (ArrayList<ZipEntry>) Collections.list(new ZipFile("test.zip")
		.entries());
		Collections.sort(list, new CustomComparator());
	}
	
	public static class CustomComparator implements Comparator<ZipEntry> {
	    @Override
	    public int compare(ZipEntry o1, ZipEntry o2) {
	        return o1.getName().compareTo(o2.getName());
	    }
	}
}
