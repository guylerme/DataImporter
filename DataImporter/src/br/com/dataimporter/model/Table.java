/**
 * 
 */
package br.com.dataimporter.model;

import java.util.ArrayList;

/**
 * @author Guylerme Figueiredo
 * 
 * @description Classe que irá abstrair o conceito tabela de banco de dados.
 */
public class Table {

	private String name;
	private ArrayList<String> columns = new ArrayList<String>();

	public String getName() {
		return name.toUpperCase();
	}

	public void setName(String name) {
		/*
		 * Todo nome de tabela irá começar com TB_. Fazemos também a troca de
		 * espaços por _ e retira-se a extensão do arquivo
		 */
		this.name = "TB_"
				+ name.replace(".tsv", "").replaceAll(" ", "_")
						.replace(".txt", "").toUpperCase();

	}

	public ArrayList<String> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}

	public void addColumn(String columnName) {
		/* Todo nome de coluna irá começar com C_ */
		columns.add("C_" + columnName.toUpperCase());
	}

	public void removeColumn(String columnName) {
		columns.remove(columnName.toUpperCase());
	}

	public Table() {
	}

	public Table(String fileName, String columns) {
		this.setName(fileName);

		String[] columnsList = columns.split("\t");

		for (int i = 0; i < columnsList.length; i++)
			this.addColumn(columnsList[i]);
	}

}
