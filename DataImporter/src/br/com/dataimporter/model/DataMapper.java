package br.com.dataimporter.model;

import java.util.HashMap;

/**
 * @author Guylerme Figueiredo
 * @description Contrato estabelecido para todo o mapeador objeto-relacional.
 *              Assim podemos a partir deste contrato trabalhar com vários SGBDs
 *              diferentes
 * */
public interface DataMapper {
	/**
	 * @description Método responsável por criar uma tabela no banco de dados
	 * @param table
	 *            Tabela a ser criada no banco de dados
	 * **/
	public abstract void createTable(Table table);

	/**
	 * @description Método responsável por inserir dados em uma tabela no banco
	 * @param data
	 *            Dados a serem inseridos na tabela
	 * @param table
	 *            Tabela onde será inserido os dados
	 * **/
	public abstract void insertData(String data, Table table);

	/**
	 * @description Método responsável por inserir dados em uma tabela no banco
	 *              de dados
	 * @param data
	 *            Dados a serem inseridos no formato de tabela hash
	 * @param table
	 *            Tabela onde será inserido os dados
	 * **/
	public void insertData(HashMap<String, String> data, Table table);

	/**
	 * @description Método que redimensiona o tamanho das colunas
	 * @param table
	 *            Tabela que será redimensionada
	 * **/
	public abstract void resizeTable(Table table);

	/**
	 * @description Método que verifica se a tabela existe
	 * @param table
	 *            Tabela a verificar se existe no banco
	 * **/
	public boolean existsTable(Table table);

}