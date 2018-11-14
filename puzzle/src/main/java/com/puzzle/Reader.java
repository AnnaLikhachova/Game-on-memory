package com.puzzle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Reader class.
 *
 * @author Anna Likhachova
 * @version 1.0
 */

public class Reader {

	Reader() {

	}

	public String read(String string) throws IOException {
		Locale.setDefault(new Locale("en"));
		String line = null;
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(string), StandardCharsets.UTF_8));
		String result = "";
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			result += line + "\n";
		}
		reader.close();
		return result;
	}


	public String readFile(String string) throws IOException {
		StringBuilder stringBuffer = new StringBuilder();
		InputStream inputStream = getClass().getResourceAsStream(string);
		System.out.print(string);
		BufferedReader bufferedReader = null;
		if (inputStream == null) {
			System.out.println("B is null");
		} else {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String text;
			while ((text = bufferedReader.readLine()) != null) {
				stringBuffer.append(text);
			}
		}
		bufferedReader.close();
		return stringBuffer.toString();
	}
	
	
}
