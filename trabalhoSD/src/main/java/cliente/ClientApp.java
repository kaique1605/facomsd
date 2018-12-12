package cliente;

public class ClientApp {
  public static void main(String[] args) {
    Client client = new Client();
    client.activate(8880);
    Thread t = new Thread(client);
    t.start();
    try {
      t.join();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
