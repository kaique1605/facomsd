package servidor;

import com.servidor.grpc.aplicationGRPC.api.Reply;
import com.servidor.grpc.aplicationGRPC.api.Request;

import io.grpc.stub.StreamObserver;
import servidor.queue.QueueCommand;
import utils.Constant;

public class HandlerThreadServer extends Thread {
	private String comando;
	private QueueCommand queueCommand;
	private ClientData clientComand;
	private Request req;
	private Finger finger;
	StreamObserver<Reply> responseObserver;

	public HandlerThreadServer(QueueCommand queueCommand, Request req, StreamObserver<Reply> responseObserver,
			Finger finger) {
		try {
			this.finger = finger;
			this.responseObserver = responseObserver;
			this.queueCommand = queueCommand;
			this.req = req;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			clientComand = new ClientData();
			comando = this.req.getName();
			System.out.println("## Recebido: " + comando + " no server "+finger.getId());
			clientComand.setOut(responseObserver);
			if (HandlerCommandClient.checkComand(comando)) {
				
				clientComand.setComando(comando);
				if (clientComand.getKey().compareTo(finger.getMinKey()) >= 0
						&& clientComand.getKey().compareTo(finger.getMaxKey()) <= 0) {
					System.out.println("######## Key encontrada no server: " + finger.getId());
					queueCommand.produceF1(clientComand);
				} else { 
					
					if (finger.getPort() == finger.getAntecessor() || finger.getPort() == finger.getSucessor()
						|| !isViable(clientComand)) {// é unico no do anel
					clientComand.sendReply("Erro, verifique a key informada");
				} else {
//					System.out.println("adicionando em f4");
					queueCommand.produceF4(clientComand);
				}
				}
			} else {
				System.out.println("Syntaxe ou comando incorreto!");
				clientComand.sendReply("ERRO! Verifique a syntaxe do comando e a Key informada!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifica se a chave esta dentro do intervalo de chave minima e maxima do anel
	 * 
	 * @return true caso esteja no intervalo e false caso contrario
	 */
	private boolean isViable(ClientData clientComand) {
		if (clientComand.getKey().compareTo(Constant.getMaxKey()) > 0
				|| clientComand.getKey().compareTo(Constant.getMinKey()) < 0) {
		  
			return false;
		}
		return true;
	}
}
