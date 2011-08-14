package br.com.dataimporter.controller;

/**
 * @author Guylerme Figueiredo
 * @description Classe que implementa o padrão FrontController. O objetivo é ser
 *              o controlador de toda a aplicação. A partir dela são feitas as
 *              delegações de responsabilidades para as demais classes.
 */
public class FrontController {

	Importer importer;

	public FrontController(String filePath, String fileName) {

		/*
		 * dependendo da extensão do arquivo ele faz a chamada para a classe
		 * responsável
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
