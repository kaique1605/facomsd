package servidor;

import java.math.BigInteger;

public class ThreadStartServer extends Thread {
  NodeApp n;
  String host;
  int porta;
  int antecessor;
  int sucessor;
  BigInteger id;
  BigInteger minKey;
  BigInteger maxKey;
  
  public ThreadStartServer(String host, int porta, BigInteger id, BigInteger minKey, BigInteger maxKey, int antecessor, int sucessor) {
    this.n = new NodeApp();
    this.host = host;
    this.porta = porta;
    this.id = id;
    this.minKey = minKey;
    this.maxKey = maxKey;
    this.antecessor = antecessor;
    this.sucessor = sucessor;
  }
  
  @Override
  public void run() {
    try {
      this.n.start(host, porta, id, minKey, maxKey, antecessor, sucessor);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
