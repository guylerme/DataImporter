package br.com.dataimporter.controller.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;
import net.sf.json.util.JSONStringer;
import br.com.dataimporter.controller.TsvImporter;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Connection conn = OracleConn.getInstance();
		// System.out.println("SUCESSO");

		// TsvImporter importer = new TsvImporter();
		// importer.importData("D:/Documents and Settings/cy85/Desktop/freebase-datadump-tsv/data/book/","book.tsv");

		// importer.importData("F:/","autores-open_library-test.txt");

		String filePath = "F:/";
		String fileName = "autores-open_library-test.txt";
		String json = "";

		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath
					+ "/" + fileName));

			int cont = 0;
			String data;
			String tag = "";
			while (in.ready()) {
				data = in.readLine();
				if (data.charAt(0) == '/')
					data =in.readLine();

				for (int i = 0; i < data.length(); i++) {
					if (data.charAt(i) == '{')
						cont++;
					else {
						if (data.charAt(i) == '}')
							cont --;
					}

					tag += data.charAt(i);

					if ((cont == 0) && !(tag.trim().equalsIgnoreCase(""))) {
						analyseTag(tag);

					}

				}
			}

		} catch (IOException e) {
		}

		/*
		 * /type/author /authors/OL1003081A 2 2008-08-20T18:14:00.880822
		 * {"name": "William Pinder Eversley", "personal_name":
		 * "William Pinder Eversley", "death_date": "1918.", "last_modified":
		 * {"type": "/type/datetime", "value": "200
		 * 8-08-20T18:14:00.880822"}, "key
		 * ": "/authors/OL1003081A", "birth_date": "1850", "
		 * type": {"key": "/type/author"}, "revision": 2}
		 */

		json = json.trim();

		String campo = "";
		ArrayList listaCampos = new ArrayList();

		// ignorando a primeira e a ultima chaves
		for (int i = 1; i < json.length() - 1; i++) {
			if (json.charAt(i) != '{') {
				if (json.charAt(i) != ',')
					campo += json.charAt(i);
				else {
					listaCampos.add(campo.trim());
					campo = "";

				}
			} else {
				while (json.charAt(i) != '}') {
					campo += json.charAt(i);
					i++;
				}
				campo += json.charAt(i);
				listaCampos.add(campo.trim());
				i++;
				campo = "";
			}

		}
		listaCampos.add(campo.trim());
		System.out.println("Teste");
	}

	private static void analyseTag(String tag) {
		tag = "";

	}
}
