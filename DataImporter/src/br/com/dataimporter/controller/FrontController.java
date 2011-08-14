/**
 * 
 */
package br.com.dataimporter.controller;

/**
 * @author Guylerme Figueiredo
 * 
 */
public class FrontController {

	Importer importer;

	public FrontController(String filePath, String fileName) {

		if (filePath.contains(".tsv")) {
			importer = new TsvImporter();
			importer.importData(filePath, fileName);
		} else if (fileName.contains(".txt")) {
			importer = new JsonImporter();
			importer.importData(filePath, fileName);
		}
	}
}
