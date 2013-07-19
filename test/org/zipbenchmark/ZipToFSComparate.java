package org.zipbenchmark;

import java.io.File;
import java.util.Date;

import org.junit.After;
import org.junit.Test;

public class ZipToFSComparate {

	private int numFiles = 10000;

	@After
	public void tearDown() {
		new File("test.zip").delete();
		deleteFolder(new File("2000/"));
	}

	// @Test
	public void writeBench() throws Exception {
		Date initDate = new Date();
		ZipTest.createZip(numFiles);
		Date zipDate = new Date();
		System.out.println("Time to create zip: "
				+ (zipDate.getTime() - initDate.getTime()) + " ms");
		FSTest.writeFS(numFiles);
		Date FSDate = new Date();
		System.out.println("Time to create FS: "
				+ (FSDate.getTime() - zipDate.getTime()) + " ms");
	}

	@Test
	public void readBench() throws Exception {
		createZip();
		//createFS();
		Date initDate = new Date();
		ZipTest.readZip();
		Date zipDate = new Date();
		System.out.println("Time to read zip: "
				+ (zipDate.getTime() - initDate.getTime()) + " ms");
		//FSTest.readFS(numFiles);
		ZipTest.findDeepestFile();
		Date FSDate = new Date();
		System.out.println("Time to find in zip: "
				+ (FSDate.getTime() - zipDate.getTime()) + " ms");
	}

	private void createZip() throws Exception {
		ZipTest.createZip(numFiles);
	}

	public void createFS() throws Exception {
		FSTest.writeFS(numFiles);
	}

	private void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f : files)
				if (f.isDirectory())
					deleteFolder(f);
				else
					f.delete();
		}
		folder.delete();
	}
}
