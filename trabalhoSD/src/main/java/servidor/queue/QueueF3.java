package servidor.queue;

import servidor.ClientData;
import servidor.FrameServer;
import servidor.command.ExecuteCommand;
import servidor.dataBase.Data;

public class QueueF3 extends Queue implements Runnable {
  public QueueF3(QueueCommand queue, Data data, FrameServer frame) {
    super(queue, data, frame);
  }
  
  ExecuteCommand execute = new ExecuteCommand();
  
  @Override
  public void run() {
    try {
      System.out.println("Iniciando F3");
      while (true) {
        // ServerClass.mutex.acquire();
        // System.out.println("esperando comando para executar no banco");
        ClientData elemento = super.queue.consumeF3();
        // System.out.println("executando: " + elemento.getComando());
        // out = elemento.getOut();
        String resposta = execute.execute(elemento.getComando(), data);
        // System.out.println("resposta F3: " + resposta);
        super.frame.append("executando: " + elemento.getComando());
        elemento.sendReply(resposta);
        // elemento.getOut().writeObject(resposta);
      }
    } catch (InterruptedException e) {
      System.out.println("erro");
    } catch (Exception e) {
      System.out.println("erro");
    }
  }
}
