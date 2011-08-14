package br.com.dataimporter.controller;

/**
 * @author Guylerme Figueiredo
 * @description Classe que implementa o padr�o FrontController. O objetivo � ser
 *              o controlador de toda a aplica��o. A partir dela s�o feitas as
 *              delega��es de responsabilidades para as demais classes.
 */
public class FrontController {

	Importer importer;

	public FrontController(String filePath, String fileName) {

		/*
		 * dependendo da extens�o do arquivo ele faz a chamada para a classe
		 * respons�vel
		 */
		if (filePath.contains(".tsv")) {
			importer = new TsvImporter();
			importer.importData(filePath, fileName);
		} else if (fileName.contains(".txt")) {
			importer = new JsonImporter();
			importer.importData(filePath, fileName);
		}
	}
}
