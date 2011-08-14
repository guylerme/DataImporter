package br.com.dataimporter.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.com.dataimporter.model.DataMapper;
import br.com.dataimporter.model.OrclDataMapper;
import br.com.dataimporter.model.Table;

/**
 * @author Guylerme Figueiredo
 * 
 *         Classe reponsável por importar dados de arquivos TSV (Tab Separeted
 *         Value)
 */
public class TsvImporter implements Importer {

	/** Caminho do arquivo */
	@SuppressWarnings("unused")
	private String filePath;
	@SuppressWarnings("unused")
	private String fileName;

	@SuppressWarnings("rawtypes")
	private DataMapper dm = new OrclDataMapper();

	public void importData(String filePath, String fileName) {
		this.filePath = filePath;
		this.fileName = fileName;

		/* Passo 1: Abrir o arquivo */

		BufferedReader in = this.loadFile(filePath, fileName);

		/*
		 * Passo 2: Identificar os nomes das colunas a partir da primeira linha
		 * do arquivo
		 */
		String columns;
		try {
			in.ready();

			columns = in.readLine();

			/* Passo 3: Criar a tabela com as colunas definidas */
			Table table = new Table(fileName, columns);

			dm.createTable(table);

			/* Passo 4: Popular a tabela com os dados do arquivo */
			String data;
			while (in.ready()) {
				data = in.readLine();
				dm.insertData(data, table);
			}

			/* Passo 5: Redimensionar os campos da tabela */
			dm.resizeTable(table);

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedReader loadFile(String filePath, String fileName) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath + "/" + fileName));
		} catch (IOException e) {
		}
		return in;
	}
}
