package br.com.dataimporter.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import br.com.dataimporter.model.DataMapper;
import br.com.dataimporter.model.OrclDataMapper;
import br.com.dataimporter.model.Table;

public class JsonImporter implements Importer {

	/** Caminho do arquivo */
	private String filePath;
	private String fileName;

	private DataMapper dm = new OrclDataMapper();

	/* (non-Javadoc)
	 * @see br.com.dataimporter.controller.Importer#importData(java.lang.String, java.lang.String)
	 */
	@Override
	public void importData(String filePath, String fileName) {
		this.filePath = filePath;
		this.fileName = fileName;

		String json = "";
		String tag = "";

		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath
					+ "/" + fileName));

			int cont = 0;
			String data;

			while (in.ready()) {
				data = in.readLine();
				if (data.charAt(0) == '/')
					data = in.readLine();

				for (int i = 0; i < data.length(); i++) {
					if (data.charAt(i) == '{')
						cont++;
					else {
						if (data.charAt(i) == '}')
							cont--;
					}

					tag += data.charAt(i);

					if ((cont == 0) && !(tag.trim().equalsIgnoreCase(""))) {
						analyseTag(tag, fileName);
						tag = "";

					}

				}
			}

		} catch (IOException e) {
		}
	}

	private static void analyseTag(String tag, String fileName) {

		DataMapper dm = new OrclDataMapper();
		String json = tag.trim();

		String campo = "";
		String key = "";
		String data = "";

		HashMap<String, String> listaCampos = new HashMap<String, String>();

		// ignorando a primeira e a ultima chaves
		for (int i = 1; i < json.length() - 1; i++) {
			if (json.charAt(i) != '{') {
				if (json.charAt(i) != ',')
					campo += json.charAt(i);
				else {

					key = campo.trim().split(": ", 2)[0];
					data = campo.trim().split(": ", 2)[1];
					listaCampos.put(key.trim().toUpperCase(), data.trim()
							.toUpperCase());

					campo = "";

				}
			} else {
				while (json.charAt(i) != '}') {
					campo += json.charAt(i);
					i++;
				}
				campo += json.charAt(i);
				key = campo.trim().split(": ", 2)[0];
				data = campo.trim().split(": ", 2)[1];
				listaCampos.put(key.trim().toUpperCase(), data.trim()
						.toUpperCase());
				i++;
				campo = "";
			}

		}
		key = campo.trim().split(": ", 2)[0];
		data = campo.trim().split(": ", 2)[1];
		listaCampos.put(key.trim().toUpperCase(), data.trim().toUpperCase());

		Table table = new Table();

		table.setName(fileName);

		Set<String> chaves = listaCampos.keySet();
		for (String chave : chaves) {
			if (chave != null)
				table.addColumn(chave);
		}

		if (dm.existsTable(table))
			dm.insertData(listaCampos, table);
		else {
			dm.createTable(table);
			dm.insertData(listaCampos, table);
		}

	}
}
