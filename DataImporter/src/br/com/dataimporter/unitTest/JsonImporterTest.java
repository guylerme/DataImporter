/**
 * 
 */
package br.com.dataimporter.unitTest;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import br.com.dataimporter.controller.JsonImporter;

/**
 * @author keilaeguy
 * 
 */
public class JsonImporterTest {

	String filePath = "G:/";
	String fileName = "autores-open_library-test.txt";
	private BufferedReader in, in_expected;

	/**
	 * Test method for
	 * {@link br.com.dataimporter.controller.JsonImporter#loadFile(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testLoadFile() {
		JsonImporter importer = new JsonImporter();
		try {
			in_expected = new BufferedReader(new FileReader(filePath + "/"
					+ fileName));
			in = importer.loadFile(filePath, fileName);

			assertEquals(in_expected.readLine(), in.readLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
