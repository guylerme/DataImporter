package br.com.dataimporter.controller;

public interface Importer {

	public abstract void importData(String filePath, String fileName);

}