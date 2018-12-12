package servidor;

import java.io.IOException;
import java.math.BigInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import io.grpc.Server;
import io.grpc.ServerBuilder;

@Component(immediate = true)
public class Node {
  // private final int port = 5000;
  private static Server server;
  // private BindableService greeterService;
  
  // @Activate
  // public void activate(String andress, int port, BigInteger id, BigInteger
  // minKey, BigInteger maxKey, int antecessor,
  // int sucessor) throws InterruptedException {
  // start(andress, port, id, minKey, maxKey, antecessor, sucessor);
  // }
  public static void start(String andress, int port, BigInteger id, BigInteger minKey, BigInteger maxKey, int antecessor, int sucessor)
      throws InterruptedException {
    try {
      ServerClass serverClass = new ServerClass(andress, port, id, minKey, maxKey, antecessor, sucessor);
      server = ServerBuilder.forPort(serverClass.getFinger().getPort()).addService(serverClass).build();
      server.start();
      System.out.println("Server iniciado");
      // server.awaitTermination();
    } catch (IOException ex) {
    }
  }
  
  public static void start(BigInteger id) throws InterruptedException {
    try {
      ServerClass serverClass = new ServerClass(id);
      Server server = ServerBuilder.forPort(serverClass.getFinger().getPort()).addService(serverClass).build();
      server.start();
      System.out.println("Server iniciado");
      server.awaitTermination();
    } catch (IOException ex) {
    }
  }
  
  // @Reference
  // public void setGreeterService(BindableService greeterService) {
  // this.greeterService = greeterService;
  // }
  @Deactivate
  public void deactivate() {
    if (server != null) {
      server.shutdownNow();
    }
  }
  
  public static void main(String[] args) {
    try {
      if (args.length > 1) {
        start(args[0], Integer.parseInt(args[1]), new BigInteger(args[2]), new BigInteger(args[3]), new BigInteger(args[4]),
            Integer.parseInt(args[5]), Integer.parseInt(args[6]));
      }
      else {
        start(new BigInteger(args[0]));
      }
    } catch (NumberFormatException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}