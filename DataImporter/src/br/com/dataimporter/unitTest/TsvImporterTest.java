/**
 * 
 */
package br.com.dataimporter.unitTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

import br.com.dataimporter.controller.TsvImporter;

/**
 * @author keilaeguy
 *
 */
public class TsvImporterTest {

	String filePath = "";
	String fileName = "";
	private Object in, in_expected;
	
	
	/**
	 * Test method for {@link br.com.dataimporter.controller.TsvImporter#loadFile(java.lang.String, java.lang.String)}.
	 */
	
	
	@Test
	public final void testLoadFile() {
		TsvImporter importer = new TsvImporter();
		try {
			 in_expected = new BufferedReader(new FileReader(filePath + "/" + fileName));
			 in = importer.loadFile(filePath, fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		assertEquals(in_expected,in);
		
	}

}
