package servidor;

import java.math.BigInteger;

import com.servidor.grpc.aplicationGRPC.api.Reply;

import io.grpc.stub.StreamObserver;

public class ClientData {
  private String comando;
  StreamObserver<Reply> responseObserver;
  
  public StreamObserver<Reply> getOut() {
    return responseObserver;
  }
  
  public void setOut(StreamObserver<Reply> reply) {
    this.responseObserver = reply;
  }
  
  public String getComando() {
    return comando;
  }
  
  public void setComando(String comando) {
    this.comando = comando;
  }
  
  public BigInteger getKey() {
    return new BigInteger(this.comando.split(" ")[1].split(":")[0]);
  }
  
  public void sendReply(String s) {
    Reply response = Reply.newBuilder().setMessage(s).build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
  
  public void sendReply(Reply response) {
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
