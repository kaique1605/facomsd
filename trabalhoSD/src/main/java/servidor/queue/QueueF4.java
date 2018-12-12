package servidor.queue;

import java.math.BigInteger;

import com.servidor.grpc.aplicationGRPC.api.GreeterGrpc;
import com.servidor.grpc.aplicationGRPC.api.Reply;
import com.servidor.grpc.aplicationGRPC.api.Request;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import servidor.ClientData;
import servidor.Finger;
import servidor.FrameServer;
import servidor.command.ExecuteCommand;
import utils.Constant;

public class QueueF4 extends Queue implements Runnable {
  public QueueF4(QueueCommand queue, Finger finger, FrameServer frame) {
    super(queue, finger, frame);
  }
  
  // ManagedChannel channel;
  ExecuteCommand execute = new ExecuteCommand();
  @SuppressWarnings("deprecation")
  ManagedChannel channelAntecessor = ManagedChannelBuilder.forAddress(super.finger.getAddress(), finger.getAntecessor()).usePlaintext(true)
      .build();
  @SuppressWarnings("deprecation")
  ManagedChannel channelSucessor = ManagedChannelBuilder.forAddress(super.finger.getAddress(), finger.getSucessor()).usePlaintext(true)
      .build();
  GreeterGrpc.GreeterStub asyncStubAntecessor = GreeterGrpc.newStub(channelAntecessor);
  GreeterGrpc.GreeterStub asyncStubSucessor = GreeterGrpc.newStub(channelSucessor);
  
  @SuppressWarnings("deprecation")
  @Override
  public void run() {
    try {
      System.out.println("Iniciando F4");
      while (true) {
        ClientData elemento = super.queue.consumeF4();
        super.frame.append(">Consultando proximo server<");
        StreamObserver<Reply> responseObserver = new StreamObserver<Reply>() {
          @Override
          public void onNext(Reply value) {
            System.out.println(">>Resposta recebida");
            frame.append(">Resposta recebida<");
            elemento.sendReply(value.getMessage());
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
        // channel = ManagedChannelBuilder.forAddress(super.finger.getAddress(),
        // checkWay(elemento))
        // .usePlaintext(true).build();
        GreeterGrpc.GreeterStub asyncStub;
        if (checkWay(elemento) == finger.getAntecessor()) {
          asyncStub = asyncStubAntecessor;
        }
        else {
          asyncStub = asyncStubSucessor;
        }
        Request request = Request.newBuilder().setName(elemento.getComando()).build();
        // asyncStub.send(request, responseObserver);
        String tipo = elemento.getComando().split(" ")[0].toLowerCase();
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
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public int checkWay(ClientData elemento) {
    /*
     * //maior passa direita e menor para esquerda BigInteger antecessor, sucessor,
     * key; key = elemento.getKey(); if (key.compareTo(super.finger.getMaxKey()) ==
     * 1) { return finger.getSucessor(); } return finger.getAntecessor();
     */
    BigInteger antecessor, sucessor, key;
    key = elemento.getKey();
    if (key.compareTo(super.finger.getMaxKey()) == 1) {   // key maior
      sucessor = key.subtract(super.finger.getMaxKey());
      antecessor = super.finger.getMaxKey().add(Constant.maxKey.subtract(key));
    }
    else {
      antecessor = Constant.maxKey.subtract(super.finger.getMaxKey().add(key));
      sucessor = super.finger.getMaxKey().subtract(key);
    }
    if (antecessor.compareTo(sucessor) <= 0) {
      // server da esquerda
      System.out.println("antecessor");
      return finger.getAntecessor();
    }
    else {
      // server da direita
      System.out.println("sucessor");
      return finger.getSucessor();
    }
  }
}
