package t2SegurancaDeSistemasUsoDeFuncoesResumo;

import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

	public static void main(String[] args) throws Exception {
		// separa o arquivo de video
		String filaName = "src/assets/FuncoesResumo - SHA1.mp4";

		// cria a string para printar o resultado final
		String result = "";

		// abre arquivo de texto
		InputStream fis = new FileInputStream(filaName);

		// "the web site breaks the file into 1KB blocks (1024 bytes)"
		byte[] buffer = new byte[1024];

		// instancia o algoritmo
		MessageDigest sha = MessageDigest.getInstance("SHA-256");

		// array com o tamanho dos blocos
		ArrayList<Integer> tamanhos = new ArrayList<Integer>();

		// array com os blocos
		ArrayList<byte[]> textos = new ArrayList<byte[]>();

		// instancia no primeiro bloco
		int numRead = fis.read(buffer);

		// para cada bloco
		while (numRead > 0) {
			// adiciona o tamanho do bloco ao vetor posicoes
			tamanhos.add(numRead);
			// adiciona o buffer naquele estado
			textos.add(buffer);
			// le o novo blobo
			buffer = new byte[1024];
			numRead = fis.read(buffer);
		}

		// ambos os vetores sao invertidos
		Collections.reverse(tamanhos);
		Collections.reverse(textos);
	
		//vetor final
		byte[] Hn = null;

		//for (int i = 0; i < 4; i++) {	
		for (int i = 0; i < textos.size(); i++) {		
			//adiciona o trecho atual
			sha.update(textos.get(i), 0, tamanhos.get(i));

			//apenas quando nao for o primeiro
			if (i > 0) {
				//une com o anterior
				sha.update(Hn, 0, 32);
			}

			//transforma
			Hn = sha.digest();
		}

		// para cada posicao
		for (int i = 0; i < Hn.length; i++) {
			// contatena a string
			result += Integer.toString((Hn[i] & 0xff) + 0x100, 16).substring(1);
		}

		// printa o resultado
		System.out.println(result);

		if (result.contentEquals("302256b74111bcba1c04282a1e31da7e547d4a7098cdaec8330d48bd87569516")) {
			System.out.println("correto!!!!!!!!");
		} else if (result.contentEquals("37d88ff100aaf4c63bb828ff1a89f99af2123e143bd758d0eb1573a044e74c84")) {
			System.out.println("o ultimo hash confere");
		} else {
			System.out.println("nao foi");
		}

		// fecha arquivo de texto
		fis.close();

	}

}
