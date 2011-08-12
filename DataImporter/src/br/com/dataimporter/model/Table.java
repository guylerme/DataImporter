/**
 * 
 */
package br.com.dataimporter.model;

import java.util.ArrayList;

/**
 * @author Guylerme Figueiredo
 * 
 *         Classe que irá abstrair o conceito tabela de banco de dados.
 */
public class Table {

	private String name;
	private ArrayList<String> columns = new ArrayList<String>();

	public String getName() {
		return name.toUpperCase();
	}

	public void setName(String name) {
		this.name = name.toUpperCase();
	}

	public ArrayList<String> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}

	public void addColumn(String columnName) {
		columns.add(columnName.toUpperCase());
	}

	public void removeColumn(String columnName) {
		columns.remove(columnName.toUpperCase());
	}

	public Table() {
	}

	public Table(String fileName, String columns) {
		this.setName("TB_"
				+ fileName.replace(".tsv", "").replaceAll(" ", "_")
						.replace(".txt", "").toUpperCase());

		String[] columnsList = columns.split("\t");

		for (int i = 0; i < columnsList.length; i++)
			this.addColumn("C_" + columnsList[i].toUpperCase());
	}

}
