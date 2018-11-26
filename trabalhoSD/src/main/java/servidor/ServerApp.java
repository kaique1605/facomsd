package servidor;

import java.math.BigInteger;
import java.util.Scanner;

import utils.Constant;

public class ServerApp {
	public static void main(String[] args) {
		try {
			Node server = new Node();
			Scanner s = new Scanner(System.in);
			System.out.println("1-novo server\n2-Server existente");
			int opcao = s.nextInt();
			BuildRing r= new BuildRing();
			if (opcao == 1) {
				
				System.out.println("Numero de nos: ");
				int n = s.nextInt();
				System.out.println("Tamanho m: ");
				int m = s.nextInt();
				r.buildRing(n, m);
				
			} else if (opcao == 2) {
//			  System.out.println(Constant.getMaxKey());
				if(Constant.getMaxKey().equals(BigInteger.ZERO)) {
				  System.out.println("Digite a key maxima: ");
				  String maxKey = ""+s.nextInt();
				  Constant.setMaxKey(maxKey);
				}
				System.out.println("Id do servidor a restaurar: ");
				server.start(BigInteger.valueOf((s.nextInt())));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
