package facomSD.facom.com;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;

import com.servidor.grpc.aplicationGRPC.api.GreeterGrpc;
import com.servidor.grpc.aplicationGRPC.api.Reply;
import com.servidor.grpc.aplicationGRPC.api.Request;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import utils.Constant;

public class ClientTests {
  private ManagedChannel channel;
  private GreeterGrpc.GreeterStub asyncStub;
  StreamObserver<Reply> responseObserver = new StreamObserver<Reply>() {
    @Override
    public void onNext(Reply value) {
      System.out.println(">>>>>  " + value.getMessage());
      try {
        PrintStream fileStream = new PrintStream(new FileOutputStream("respostas.log", true));
        fileStream.append(value.getMessage() + System.getProperty("line.separator"));
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
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
  public void activate() {
    try {
      channel = ManagedChannelBuilder.forAddress(Constant.HOST, 8800).usePlaintext(true).build();
      asyncStub = GreeterGrpc.newStub(channel);
    } catch (Exception e) {
      e.printStackTrace();
    }
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
  // @Reference
  // public void setGrpcServer(GrpcServer grpcServer) {
  // // ensures the server has started before we attempt to connect
  // }
}
