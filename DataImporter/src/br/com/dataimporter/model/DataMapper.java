package br.com.dataimporter.model;

public interface DataMapper {

	public abstract void createTable(Table table);

	public abstract void insertData(String data, Table table);

	public abstract void resizeTable(Table table);

}