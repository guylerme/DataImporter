package br.com.dataimporter.controller.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;
import net.sf.json.util.JSONStringer;
import br.com.dataimporter.controller.TsvImporter;
import br.com.dataimporter.model.DataMapper;
import br.com.dataimporter.model.OrclDataMapper;
import br.com.dataimporter.model.Table;

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

		String filePath = "G:/";
		String fileName = "autores-open_library-test.txt";

		/*
		 * /type/author /authors/OL1003081A 2 2008-08-20T18:14:00.880822
		 * {"name": "William Pinder Eversley", "personal_name":
		 * "William Pinder Eversley", "death_date": "1918.", "last_modified":
		 * {"type": "/type/datetime", "value": "200
		 * 8-08-20T18:14:00.880822"}, "key
		 * ": "/authors/OL1003081A", "birth_date": "1850", "
		 * type": {"key": "/type/author"}, "revision": 2}
		 */

	}

	
}
