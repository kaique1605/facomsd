package facomSD.facom.com;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import facomSD.facom.com.threads.ThreadStartRing;

public class CrudNokTest {
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

			int offset = 0;
			for (int i = 0; i < 16; i = i + 5) {
				// CREATE DO ITEM I
				c.greet("create " + i + ":" + i);
				String resposta = getResposta(arquivo, 1 + offset);
				System.out.println("resposta >> "+resposta);
				assertTrue(resposta.equals("Dados criados com sucesso"));
				// CREATE DO ITEM I - JA CRIADO
				c.greet("create " + i + ":" + i);
				resposta = getResposta(arquivo, 2 + offset);
				assertTrue(resposta.equals("Dados ja cadastrados"));
				// READ DO ITEM J - NAO EXISTENTE
				i = i - 1;
				if (i < 0) {
					i = 1;
				}
				if (i + 1 > 15) {
					i = 12;
				}
				c.greet("read " + (i + 2));
				resposta = getResposta(arquivo, 3 + offset);
				assertTrue(resposta.equals("Dados nao encontrados"));
				// UPDATE DO ITEM J - NAO EXISTENTE
				c.greet("update " + (i + 2) + ":update");
				resposta = getResposta(arquivo, 4 + offset);
				assertTrue(resposta.equals("Dados nao encontrados"));
				// CREATE DO ITEM J
				c.greet("create " + i + ":j" + i);
				resposta = getResposta(arquivo, 5 + offset);
				assertTrue(resposta.equals("Dados criados com sucesso"));
				// READ DO ITEM J
				c.greet("read " + i);
				resposta = getResposta(arquivo, 6 + offset);
				assertTrue(resposta.equals("j" + i));
				// DELETE DO ITEM I
				c.greet("delete " + i);
				resposta = getResposta(arquivo, 7 + offset);
				assertTrue(resposta.equals("Dados removidos com sucesso"));
				offset = offset + 7;

			}
			
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
