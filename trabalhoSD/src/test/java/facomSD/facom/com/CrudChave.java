package facomSD.facom.com;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import facomSD.facom.com.threads.ThreadStartRing;

public class CrudChave {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRun() {
		try {
			ClientTests c = new ClientTests();

			File arquivo = new File("respostas.log");
			arquivo.delete(); // deleta arquivo de respostas
			arquivo.createNewFile();

			Thread t = new Thread(new ThreadStartRing(4, 4));
			t.start();
			Thread.sleep(4000);

			c.activate();
			
			//CRUD CHAVE 0
			c.greet("create 0:teste0");
			String resposta = getResposta(arquivo, 1);
			assertTrue(resposta.equals("Dados criados com sucesso"));
			c.greet("read 0");
			resposta = getResposta(arquivo, 2);
			assertTrue(resposta.equals("teste0"));
			c.greet("update 0:update");
			resposta = getResposta(arquivo, 3);
			assertTrue(resposta.equals("Dados alterados com sucesso"));
			c.greet("read 0");
			resposta = getResposta(arquivo, 4);
			assertTrue(resposta.equals("update"));
			c.greet("delete 0");
			resposta = getResposta(arquivo, 5);
			assertTrue(resposta.equals("Dados removidos com sucesso"));
			c.greet("read 0");
			resposta = getResposta(arquivo, 6);
			assertTrue(resposta.equals("Dados nao encontrados"));
			
			//CRUD CHAVE 2^M-1
			c.greet("create 15:teste15");
			resposta = getResposta(arquivo, 7);
			assertTrue(resposta.equals("Dados criados com sucesso"));
			c.greet("read 15");
			resposta = getResposta(arquivo, 8);
			assertTrue(resposta.equals("teste15"));
			c.greet("update 15:update");
			resposta = getResposta(arquivo, 9);
			assertTrue(resposta.equals("Dados alterados com sucesso"));
			c.greet("read 15");
			resposta = getResposta(arquivo, 10);
			assertTrue(resposta.equals("update"));
			c.greet("delete 15");
			resposta = getResposta(arquivo, 11);
			assertTrue(resposta.equals("Dados removidos com sucesso"));
			c.greet("read 15");
			resposta = getResposta(arquivo, 12);
			assertTrue(resposta.equals("Dados nao encontrados"));
			
			//READ CHAVE MAIOR QUE 2^M-1
			c.greet("read 16");
			resposta = getResposta(arquivo, 13);
			assertTrue(resposta.equals("Erro, verifique a key informada"));
			
			//READ CHAVE NEGATIVA
			c.greet("read -1");
			resposta = getResposta(arquivo, 14);
			assertTrue(resposta.equals("ERRO! Verifique a syntaxe do comando e a Key informada!"));
			
//			Thread.sleep(30000);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getResposta(File f, int n) {
		String linha = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			while (n > 0) {
				while ((linha = br.readLine()) == null) {
				}
				n = n - 1;
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return linha;
	}

}
