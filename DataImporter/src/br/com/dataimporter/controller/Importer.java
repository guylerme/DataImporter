package br.com.dataimporter.controller;

import java.io.BufferedReader;

/**
 * @author Guylerme Figueiredo
 * @description Interface para estabelecer o contrato do Importador
 */
public interface Importer {

	/* M�todo respons�vel por importar os dados */
	public abstract void importData(String filePath, String fileName);

	/* M�todo respons�vel por carregar o arquivo a ser importado */
	public BufferedReader loadFile(String filePath, String fileName);

}