package servidor;

import java.io.File;
import java.math.BigInteger;

import utils.Constant;

public class BuildRing {

	public void buildRing(int n, int m) {
		try {
			File diretorio = new File("logs");
			remover(diretorio);
//			System.out.println("pow: " + Integer.toString((int) Math.pow(2, m)));
			Constant.setMaxKey(Integer.toString((int) Math.pow(2, m)));
			Constant.atualizaKeys();
			BigInteger intervalo;
			BigInteger offset = BigInteger.ZERO;
//			System.out.println("reminder "+Constant.maxKey+" \\ "+n+": " + Constant.maxKey.mod(BigInteger.valueOf(n)));
			if (Constant.maxKey.mod(BigInteger.valueOf(n)) != BigInteger.ZERO) {
				offset = Constant.maxKey.mod(BigInteger.valueOf(n));
			}
			intervalo = Constant.maxKey.subtract(offset).divide(BigInteger.valueOf(n));
//			System.out.println("intervalo = " + intervalo);
			Constant.setMaxKey(Integer.toString((int) Math.pow(2, m) - 1));
			int porta = Constant.portInitial;
			int antecessor = Constant.portInitial + n - 1;
			int sucessor = Constant.portInitial + 1;
			BigInteger minKey = BigInteger.ZERO;
			BigInteger maxKey = intervalo.add(offset.subtract(BigInteger.ONE));

			Thread[] t = new Thread[n];
			for (int i = 0; i < n; i++) {
				if (i == n - 1) {
					sucessor = Constant.portInitial;
				}

				t[i] = new Thread(new ThreadStartServer(Constant.HOST, porta, BigInteger.valueOf(i), minKey, maxKey,
						antecessor, sucessor));
//				System.out.println("iniciando server " + i);
				t[i].run();
//				System.out.println("thread iniciada");
				if (i == 0) {
					antecessor = Constant.portInitial;
				} else {
					antecessor = antecessor + 1;
				}
				porta = porta + 1;

				sucessor = sucessor + 1;
				minKey = maxKey.add(BigInteger.valueOf(1));
				maxKey = maxKey.add(intervalo);

			}

			for (int i = 0; i < n; i++) {
				t[i].join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public void remover (File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; ++i) {
                remover (files[i]);
            }
        }
        f.delete();
    }
}
