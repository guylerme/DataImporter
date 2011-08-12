package br.com.dataimporter.model;

import java.util.HashMap;

public interface DataMapper {

	public abstract void createTable(Table table);

	public abstract void insertData(String data, Table table);

	public void insertData(HashMap<String, String> data, Table table);

	public abstract void resizeTable(Table table);

	public boolean existsTable(Table table);

}