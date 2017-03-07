package com.bywangxp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
	public static void main(String[] args) throws IOException {
		File fileDir = new File("D:\\lucence");
		File[] textFiles = fileDir.listFiles();
		for (int i = 0; i < textFiles.length; i++) {
			if (textFiles[i].isFile()) {
				System.out.println("File " + textFiles[i].getCanonicalPath() + "正在被索引....");
				String temp = FileReaderAll(textFiles[i].getCanonicalPath(), "GBK");
			}
		}

	}

	public static String FileReaderAll(String FileName, String charset) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FileName), charset));
		String line = new String();
		String temp = new String();

		while ((line = reader.readLine()) != null) {
			temp += line;
		}
		reader.close();
		return temp;
	}

}
