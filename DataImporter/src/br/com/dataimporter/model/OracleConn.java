/**
 * 
 */
package br.com.dataimporter.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Guylerme Figueiredo
 * 
 *         Classe de conexão com o banco de dados
 */
public class OracleConn {
	/**
	 * Driver de conexão do Oracle
	 */
	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	/**
	 * String de conexão com o banco de dados
	 */
	// Petrobras
	public static final String DBURL = "jdbc:oracle:thin:@lanceta:1521:d01";

	/**
	 * Usuário de conexão com o banco de dados
	 */
	public static final String USR = "insp_cy85";

	/**
	 * Senha de conexão com o banco de dados
	 */
	public static final String PASS = "falcon";

	public static OracleConn instance;

	private Connection connection = null;

	public OracleConn() {
		connection = createConnection(DRIVER, DBURL, USR, PASS);
	}

	private Connection createConnection(String DRIVER, String DBURL,
			String USER, String PASSWORD) {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(DBURL, USER, PASSWORD);
			System.out.println("Conectei ao Oracle!!");
		} catch (ClassNotFoundException e) {
			System.out.println("Erro de conexão: " + e.toString());
		} catch (SQLException e) {
			System.out.println("Erro de conexão: " + e.toString());
		} catch (Exception e) {
			System.out.println("Erro de conexão: " + e.toString());
		}

		return connection;
	}

	/**
	 * @description Método que implementa o design patter Singleton para
	 *              retornar somente uma instância da conexão
	 **/
	public static OracleConn getInstance() {
		if (instance == null) {
			instance = new OracleConn();
		}

		return instance;
	}

	public Connection getConnection() {
		Connection connec = connection;

		try {
			if (connec.isClosed()) {
				connec = createConnection(DRIVER, DBURL, USR, PASS);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connec;
	}
}
