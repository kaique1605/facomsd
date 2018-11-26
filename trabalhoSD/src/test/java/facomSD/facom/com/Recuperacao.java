package facomSD.facom.com;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import facomSD.facom.com.threads.ThreadStartRing;
import facomSD.facom.com.threads.ThreadStartServerExistente;

public class Recuperacao {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRun() {
		try {
		  int i = 1;
			ClientTests c = new ClientTests();
			File arquivo = new File("respostas.log");//nao comentar
			try {
				arquivo.delete(); // deleta arquivo de respostas
				arquivo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
			
			/* Bloco 1 */

//         /*
			Thread t = new Thread(new ThreadStartRing(4, 4));
			t.start();
			Thread.sleep(4000);
			c.activate(); 
			
			//CREATE EM CADA SERVER
			c.greet("create 1:teste1");
      String resposta = getResposta(arquivo, i); i++;
      assertTrue(resposta.equals("Dados criados com sucesso"));
			c.greet("create 5:teste5");
			resposta = getResposta(arquivo, i);i++;
			assertTrue(resposta.equals("Dados criados com sucesso"));
			c.greet("create 10:teste10");
			resposta = getResposta(arquivo, i); i++;
			assertTrue(resposta.equals("Dados criados com sucesso"));
			c.greet("create 15:teste15");
			resposta = getResposta(arquivo, i); i++;
			assertTrue(resposta.equals("Dados criados com sucesso"));
			t.stop();
			c.shutdown();
			while(t.isAlive()) {}
			
				System.out.println(" 	---------------------- Interrompida -------------------------");
			// t.destroy();
			// Thread.sleep(4000);
//		*/		
			
			
			
			
			
			System.out.println("---------------------------------- Recuperando servidores -------------------------");
			
			
			/* Bloco 2 */
			
//			/*			
//			Constant.setMaxKey("15"); //se comentar a primeira parte é preciso setar maxKey
			Thread t0 = new Thread(new ThreadStartServerExistente(BigInteger.valueOf(0)));
			Thread t1 = new Thread(new ThreadStartServerExistente(BigInteger.valueOf(1)));
			Thread t2 = new Thread(new ThreadStartServerExistente(BigInteger.valueOf(2)));
			Thread t3 = new Thread(new ThreadStartServerExistente(BigInteger.valueOf(3)));
			t0.start();
			t1.start();
			t2.start();
			t3.start();
			String resposta2;
			ClientTests c2 = new ClientTests();
			Thread.sleep(4000);
			c2.activate();
			System.out.println("iniciando testes");
			// READ EM CADA SERVER
			c2.greet("read 1");
      resposta2 = getResposta(arquivo, i); i++;//5 se nao comentar a primeira parte
      assertTrue(resposta2.equals("teste1"));
			c2.greet("read 5");
			resposta2 = getResposta(arquivo, i); i++;//6 se nao comentar a primeira parte
			assertTrue(resposta2.equals("teste5"));
			c2.greet("read 10");
			resposta2 = getResposta(arquivo, i); i++;//7 se nao comentar a primeira parte
			assertTrue(resposta2.equals("teste10"));
			c2.greet("read 15");
			resposta2 = getResposta(arquivo, i); i++;//8 se nao comentar a primeira parte
			assertTrue(resposta2.equals("teste15"));
			//Thread.sleep(20000);
//			*/		
			

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
