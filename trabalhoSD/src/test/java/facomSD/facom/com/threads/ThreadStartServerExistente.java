package facomSD.facom.com.threads;

import java.math.BigInteger;

import servidor.NodeApp;

public class ThreadStartServerExistente implements Runnable {
	BigInteger id;

	public ThreadStartServerExistente(BigInteger n) {
		this.id = n;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			NodeApp s = new NodeApp();
			s.start(id);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
