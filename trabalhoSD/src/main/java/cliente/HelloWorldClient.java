package cliente;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.stackleader.training.grpc.helloworld.api.GreeterGrpc;
import com.stackleader.training.grpc.helloworld.api.Reply;
import com.stackleader.training.grpc.helloworld.api.Request;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.okhttp.OkHttpChannelBuilder;
import utils.GrpcServer;

@Component(immediate = true)
public class HelloWorldClient {
  private final String host = "localhost";
  private final int port = 9877;
  private ManagedChannel channel;
  private GreeterGrpc.GreeterBlockingStub blockingStub;
  String input = "";
  Scanner s;
  CommandHandler commandHandler;
  
  @SuppressWarnings("deprecation")
  @Activate
  public void activate() {
    channel = OkHttpChannelBuilder.forAddress(host, port).usePlaintext(true).build();
    blockingStub = GreeterGrpc.newBlockingStub(channel);
    s = new Scanner(System.in);
    commandHandler = new CommandHandler();
    try {
      while (!input.toLowerCase().equals("sair")) {
        System.out.println("\n-------------------------------------\nDigite o comando ou 'sair' para sair ");
        while (true) {
          input = s.nextLine();
          if (input.toLowerCase().equals("sair"))
            break;
          if (commandHandler.checkComand(input)) {
            System.out.println("enviando comando");
            greet(input);
            break;
          }
          else
            System.out.println("Comando invalido, digite um comando valido");
        }
      }
      shutdown();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }
  
  /**
   * Say hello to server.
   */
  public void greet(String name) {
    System.out.println("enviando: " + name);
    Request request = Request.newBuilder().setName(name).build();
    Reply response;
    try {
      response = blockingStub.send(request);
      System.out.println(">>>  " + response.getMessage());
    } catch (StatusRuntimeException e) {
      e.printStackTrace();
      return;
    }
  }
  
  @Reference
  public void setGrpcServer(GrpcServer grpcServer) {
    // ensures the server has started before we attempt to connect
  }
}