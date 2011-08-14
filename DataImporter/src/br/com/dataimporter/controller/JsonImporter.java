package br.com.dataimporter.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import br.com.dataimporter.model.DataMapper;
import br.com.dataimporter.model.OrclDataMapper;
import br.com.dataimporter.model.Table;

/**
 * @author Guylerme Figueiredo
 * @description Classe que implementa a interface Importer e é responsável por
 *              realizar a importação de dados a partir de arquivos no formato
 *              JSON
 */
public class JsonImporter implements Importer {

	/* Caminho do arquivo */
	@SuppressWarnings("unused")
	private String filePath;
	/* Nome do arquivo */
	@SuppressWarnings("unused")
	private String fileName;

	/* Mapeador objeto-relacional */
	private DataMapper dm;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.dataimporter.controller.Importer#importData(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void importData(String filePath, String fileName) {
		this.filePath = filePath;
		this.fileName = fileName;

		/*
		 * variável que receberá a tag JSON que será processada pelo método
		 * analyseTag
		 */
		String tag = "";

		/* buffer utilizado para ler o arquivo */
		BufferedReader in = this.loadFile(filePath, fileName);

		/*
		 * contador utilizado para delimitar a tag JSON. Incrementado a cada
		 * parênteses aberto e decrementado a cada parênteses fechado
		 */
		int cont = 0;

		/* linha lida do arquivo. */
		String data;

		try {

			while (in.ready()) {
				data = in.readLine();
				/*
				 * Se a linha começar com / é porque é comentário, então é
				 * desconsiderada.
				 */
				if (data.charAt(0) == '/')
					data = in.readLine();

				/* Estrutura utilizada para varrer todo os caracteres da linha */
				for (int i = 0; i < data.length(); i++) {
					/*
					 * Se o caracter for abertura de parênteses o contador é
					 * incrementado
					 */
					if (data.charAt(i) == '{')
						cont++;
					else {
						/*
						 * Se o caracter for fechar parênteses o contador é
						 * decrementado
						 */
						if (data.charAt(i) == '}')
							cont--;
					}

					/* O caracter é concatenado a tag */
					tag += data.charAt(i);

					/*
					 * Se o contador for zerado e a tag não estiver vazia quer
					 * dizer que a tag JSON já está formada, sendo assim ela é
					 * analisada
					 */
					if ((cont == 0) && !(tag.trim().equalsIgnoreCase(""))) {
						analyseTag(tag, fileName);

						/* A tag é limpa para iniciar a formação da nova tag */
						tag = "";

					}

				}
			}

		} catch (IOException e) {
		}
	}

	/* Método que irá extrair os dados da tag e armazenar no banco de dados */
	private void analyseTag(String tag, String fileName) {

		/* Mapeador objeto-relacionar */
		this.dm = new OrclDataMapper();

		/* tag json */
		String json = tag.trim();

		/* O campo é o par nome do atributo e dado */
		String campo = "";

		/* Nome do atributo */
		String key = "";

		/* Dado a ser armazenado */
		String data = "";

		/* Tabela hash que irá armazenar os campos e seus respectivos dados */
		HashMap<String, String> listaCampos = new HashMap<String, String>();

		// ignorando a primeira e a ultima chaves, com o início em 1 e o término
		// em n-1
		for (int i = 1; i < json.length() - 1; i++) {
			if (json.charAt(i) != '{') {
				/* Se encontrar a vírgula, ele entende que é o fim do campo. */
				if (json.charAt(i) != ',')
					campo += json.charAt(i);
				else {
					/*
					 * A partir da ocorrência do primeiro : divide-se o nome do
					 * atributo e o respectivo dado
					 */
					key = campo.trim().split(": ", 2)[0];
					data = campo.trim().split(": ", 2)[1];
					/*
					 * Adiciona o par na tabela hash com os caracteres todos em
					 * maiusculo
					 */
					listaCampos.put(key.trim().toUpperCase(), data.trim()
							.toUpperCase());

					campo = "";

				}
			} else {
				/*
				 * Esta parte é para tratar atributos que possuem valores sendo
				 * uma tag json, sendo assim ele trata todo o valor dentro do
				 * parênteses como sendo um valor único
				 */
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

		/* Para o último campo */
		key = campo.trim().split(": ", 2)[0];
		data = campo.trim().split(": ", 2)[1];
		listaCampos.put(key.trim().toUpperCase(), data.trim().toUpperCase());

		Table table = new Table();

		/*
		 * Cria um objeto tabela atribuindo o nome do arquivo como o nome da
		 * tabela
		 */
		table.setName(fileName);

		/*
		 * Para cada campo na tabela hash é criada uma coluna correspondente na
		 * tabela
		 */
		Set<String> chaves = listaCampos.keySet();
		for (String chave : chaves) {
			if (chave != null)
				table.addColumn(chave);
		}

		/* Verifica se a tabela já existe e depois solicita a inserção dos dados */
		if (dm.existsTable(table))
			dm.insertData(listaCampos, table);
		else {
			dm.createTable(table);
			dm.insertData(listaCampos, table);
		}

	}

	/*
	 * Utilizado para carregar o arquivo no buffer
	 * 
	 * @see br.com.dataimporter.controller.Importer#loadFile(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public BufferedReader loadFile(String filePath, String fileName) {
		BufferedReader in = null;
		try {
			/* Carrega o arquivo em um buffer */
			in = new BufferedReader(new FileReader(filePath + "/" + fileName));
		} catch (IOException e) {
		}
		return in;
	}
}
