package cliente;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.servidor.grpc.aplicationGRPC.api.GreeterGrpc;
import com.servidor.grpc.aplicationGRPC.api.Reply;
import com.servidor.grpc.aplicationGRPC.api.Request;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import utils.Constant;

@Component(immediate = true)
public class Client implements Runnable {
  // private final String host = "localhost";
  // private final int port = 9877;
  private ManagedChannel channel;
  private GreeterGrpc.GreeterStub asyncStub;
  private String input = "";
  private Scanner s;
  private CommandHandler commandHandler;
  StreamObserver<Reply> responseObserver = new StreamObserver<Reply>() {
    @Override
    public void onNext(Reply value) {
      System.out.println(">>>>>  " + value.getMessage());
    }
    
    @Override
    public void onError(Throwable t) {
      // TODO Auto-generated method stub
      t.printStackTrace();
    }
    
    @Override
    public void onCompleted() {
      // TODO Auto-generated method stub
    }
  };
  
  @SuppressWarnings("deprecation")
  @Activate
  public void activate(int port) {
    channel = ManagedChannelBuilder.forAddress(Constant.HOST, port).usePlaintext(true).build();
    asyncStub = GreeterGrpc.newStub(channel);
  }
  
  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }
  
  /**
   * Say to server.
   */
  public void greet(String name) {
    Request request = Request.newBuilder().setName(name).build();
    String tipo = name.split(" ")[0].toLowerCase();
    try {
      if (tipo.equals("create")) {
        asyncStub.create(request, responseObserver);
      }
      else if (tipo.equals("read")) {
        asyncStub.read(request, responseObserver);
      }
      else if (tipo.equals("update")) {
        asyncStub.update(request, responseObserver);
      }
      else if (tipo.equals("delete")) {
        asyncStub.delete(request, responseObserver);
      }
      // asyncStub.send(request, responseObserver);
    } catch (StatusRuntimeException e) {
      e.printStackTrace();
      return;
    }
  }
  
  @Override
  public void run() {
    // TODO Auto-generated method stub
    commandHandler = new CommandHandler();
    s = new Scanner(System.in);
    System.out.println("\n-------------------------------------\nDigite o comando ou 'sair' para sair ");
    while (!input.toLowerCase().equals("sair")) {
      while (true) {
        input = s.nextLine();
        if (input.toLowerCase().equals("sair"))
          break;
        if (commandHandler.checkComand(input)) {
          greet(input);
          break;
        }
        else
          System.out.println("Comando invalido, digite um comando valido");
      }
    }
    System.out.println("finalizando");
    try {
      Thread.sleep(5000);
      shutdown();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  // @Reference
  // public void setGrpcServer(GrpcServer grpcServer) {
  // // ensures the server has started before we attempt to connect
  // }
}