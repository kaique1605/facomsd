package servidor.queue;

import servidor.ClientData;
import servidor.ServerClass;

public class QueueF1 extends Queue implements Runnable {
  public QueueF1(QueueCommand queue) {
    super(queue);
  }
  
  @Override
  public void run() {
    try {
      System.out.println("Iniciando F1");
      while (true) {
        ClientData elemento = super.queue.consumeF1();
        // System.out.println(elemento.getComando()+" Consumido de f1");
        ServerClass.mutex_f1.acquire();
        super.queue.produceF2(elemento);
        super.queue.produceF3(elemento);
        ServerClass.mutex_f1.release();
      }
    } catch (InterruptedException e) {
      System.out.println("Erro, f1 finalizada");
    }
  }
}
