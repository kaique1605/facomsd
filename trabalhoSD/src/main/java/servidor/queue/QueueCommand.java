package servidor.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import servidor.ClientData;
import utils.Constant;

public class QueueCommand {
  private BlockingQueue<ClientData> f1 = new ArrayBlockingQueue<ClientData>(Constant.MAX_QUEUE);
  private BlockingQueue<ClientData> f2 = new ArrayBlockingQueue<ClientData>(Constant.MAX_QUEUE);
  private BlockingQueue<ClientData> f3 = new ArrayBlockingQueue<ClientData>(Constant.MAX_QUEUE);
  private BlockingQueue<ClientData> f4 = new ArrayBlockingQueue<ClientData>(Constant.MAX_QUEUE);
  
  public void produceF1(ClientData elemento) {
    producer(f1, elemento);
    // System.out.println(elemento.getComando() + " adicionado em f1");
  }
  
  public void produceF2(ClientData elemento) {
    producer(f2, elemento);
    // System.out.println(elemento.getComando() + " adicionado em f2");
  }
  
  public void produceF3(ClientData elemento) {
    producer(f3, elemento);
    // System.out.println(elemento.getComando() + " adicionado em f3");
  }
  
  public void produceF4(ClientData elemento) {
    producer(f4, elemento);
    // System.out.println(elemento.getComando() + " adicionado em f3");
  }
  
  public ClientData consumeF1() throws InterruptedException {
    return f1.take();
  }
  
  public ClientData consumeF2() throws InterruptedException {
    return f2.take();
  }
  
  public ClientData consumeF3() throws InterruptedException {
    return f3.take();
  }
  
  public ClientData consumeF4() throws InterruptedException {
    return f4.take();
  }
  
  private boolean producer(BlockingQueue<ClientData> queue, ClientData elemento) {
    queue = resizeIf(queue);
    return queue.offer(elemento);
  }
  
  private BlockingQueue<ClientData> resizeIf(BlockingQueue<ClientData> queue) {
    int remainig = queue.remainingCapacity();
    if (remainig < 1) {
      int realSize = remainig + queue.size();
      queue = new ArrayBlockingQueue<ClientData>((int) (realSize + Constant.QUEUE_INCREMENT_RESIZE));
    }
    return queue;
  }
}
