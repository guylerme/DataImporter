/**
 * 
 */
package br.com.dataimporter.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Guylerme Figueiredo
 * 
 *         Classe respons�vel por realizar o mapeamento objeto relacional
 */
public class OrclDataMapper implements DataMapper {

	private Connection conn = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.dataimporter.model.DataMapper#createTable(java.lang.String,
	 * java.lang.String)
	 */
	public void createTable(Table table) {

		conn = (Connection) OracleConn.getInstance().getConnection();

		try {

			String sql = "CREATE TABLE " + table.getName() + " (";

			/* Baseados na lista de colunas o comando DDL � criado */
			for (int i = 0; i < table.getColumns().size(); i++) {
				if (i == table.getColumns().size() - 1)
					sql += table.getColumns().get(i) + " varchar2(4000))";
				else
					sql += table.getColumns().get(i) + " varchar2(4000), ";
			}

			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.execute();

			pstmt.close();
			conn.close();

		} catch (SQLException ex) {

			System.out.println("\n*** SQLException caught ***\n");
			while (ex != null) {
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("Message:  " + ex.getMessage());
				System.out.println("Vendor:   " + ex.getErrorCode());
				ex = ex.getNextException();
				System.out.println("");
			}

		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void insertData(String data, Table table) {
		conn = (Connection) OracleConn.getInstance().getConnection();

		try {

			/* Substitui os caracteres especiais ' por _ */
			String[] separatedData = data.replaceAll("\'", "_").split("\t");

			String sql = "INSERT INTO " + table.getName() + " (";

			/* Cria parte do comando a partir da lista de colunas */
			for (int i = 0; i < table.getColumns().size(); i++) {
				if (i == table.getColumns().size() - 1)
					sql += table.getColumns().get(i) + ")";
				else
					sql += table.getColumns().get(i) + ", ";
			}

			sql += " VALUES(\'";

			/*
			 * Completa a cria��o do comando DML com a lista de dados a serem
			 * inseridos
			 */
			for (int i = 0; i < table.getColumns().size(); i++) {
				if (i == table.getColumns().size() - 1) {
					if (i < separatedData.length)
						sql += separatedData[i] + "\')";
					else
						sql += "" + "\')";
				}

				else {
					if (i < separatedData.length)
						sql += separatedData[i] + "\', \'";
					else
						sql += "" + "\', \'";
				}

			}

			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.execute();

			pstmt.close();
			conn.close();

		} catch (SQLException ex) {

			System.out.println("\n*** SQLException caught ***\n");
			while (ex != null) {
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("Message:  " + ex.getMessage());
				System.out.println("Vendor:   " + ex.getErrorCode());
				ex = ex.getNextException();
				System.out.println("");
			}

		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
		}

	}

	public void resizeTable(Table table) {

		conn = (Connection) OracleConn.getInstance().getConnection();

		try {

			// Abre a conexao com o BD
			String sql;
			PreparedStatement pstmt;
			ResultSet rs;
			int tamanho = 0;

			/*
			 * Redimensiona todas as colunas tomando como par�metro o maior
			 * registro de cada coluna
			 */
			for (int i = 0; i < table.getColumns().size(); i++) {
				sql = "SELECT MAX (LENGTH (" + table.getColumns().get(i)
						+ ")) FROM \"" + table.getName().toUpperCase() + "\"";

				pstmt = conn.prepareStatement(sql);

				pstmt.execute();

				rs = pstmt.getResultSet();

				if (rs.next())
					tamanho = rs.getInt(1) + 1;

				sql = "ALTER TABLE \"" + table.getName().toUpperCase()
						+ "\" MODIFY " + table.getColumns().get(i)
						+ " varchar2(" + tamanho + ")";

				pstmt = conn.prepareStatement(sql);

				pstmt.execute();

				pstmt.close();
			}

			conn.close();

		} catch (SQLException ex) {

			System.out.println("\n*** SQLException caught ***\n");
			while (ex != null) {
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("Message:  " + ex.getMessage());
				System.out.println("Vendor:   " + ex.getErrorCode());
				ex = ex.getNextException();
				System.out.println("");
			}

		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
		}

	}
}
