package servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.Properties;
import java.util.concurrent.Semaphore;

import com.servidor.grpc.aplicationGRPC.api.GreeterGrpc;
import com.servidor.grpc.aplicationGRPC.api.Reply;
import com.servidor.grpc.aplicationGRPC.api.Request;

import io.grpc.BindableService;
import io.grpc.stub.StreamObserver;
import servidor.dataBase.Data;
import servidor.dataBase.RecoveryData;
import servidor.queue.QueueCommand;

public class ServerClass extends GreeterGrpc.GreeterImplBase implements BindableService {
	public static Semaphore mutex_f1 = new Semaphore(1);
	public static Semaphore mutex = new Semaphore(1);
	private QueueCommand queueCommand = null;
	private Queue queue = null;
	private Data dataBase;
	private Finger finger;

	public ServerClass(String andress, int port, BigInteger id, BigInteger minKey, BigInteger maxKey, int antecessor,
			int sucessor) {
		try {
			System.out.println("iniciando serverClass");
			RecoveryData recovery = new RecoveryData();
			dataBase = new Data();
			File diretorio = new File("logs\\" + id.toString());
			if (diretorio.exists()) {
				// recupera chaves
				System.out.println("Servidor ja existente, recuperando dados finger");
				finger = recuperaDadosFinger(id);
				if (finger == null) {
					System.out.println("Erro ao recuperar informacoes do servidor");
					System.exit(1);
				}
				recovery.recovery(dataBase, finger);
			} else {
				diretorio.mkdirs();
				finger = new Finger(andress, port, id, minKey, maxKey, antecessor, sucessor);
				finger.print();
				// criar arquivo properties
				Properties props = new Properties();
				File f = new File("logs\\" + id.toString() + "\\server.properties");
				props.setProperty("andress", andress);
				props.setProperty("port", Integer.toString(port));
				props.setProperty("id", id.toString());
				props.setProperty("minKey", minKey.toString());
				props.setProperty("maxKey", maxKey.toString());
				props.setProperty("antecessor", Integer.toString(antecessor));
				props.setProperty("sucessor", Integer.toString(sucessor));
				props.store(new FileOutputStream(f), "propertiesServer");

			}

			queueCommand = new QueueCommand();
			queue = new Queue(queueCommand, dataBase, finger);
			queue.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ServerClass(BigInteger id) {
		try {
			RecoveryData recovery = new RecoveryData();
			dataBase = new Data();
			System.out.println("Recuperando dados finger");
			finger = recuperaDadosFinger(id);
			if (finger == null) {
				System.out.println("Erro ao recuperar informacoes do servidor, verifique se o id esta correto");
				System.exit(1);
			}
			recovery.recovery(dataBase, finger);
			queueCommand = new QueueCommand();
			queue = new Queue(queueCommand, dataBase, finger);
			queue.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Finger recuperaDadosFinger(BigInteger id) {
		try {
			Properties props = new Properties();
			FileInputStream file = new FileInputStream("logs\\" + id.toString() + "\\server.properties");
			props.load(file);
			Finger finger = new Finger(props.getProperty("andress"), Integer.parseInt(props.getProperty("port")),
					new BigInteger(props.getProperty("id")), new BigInteger(props.getProperty("minKey")),
					new BigInteger(props.getProperty("maxKey")), Integer.parseInt(props.getProperty("antecessor")),
					Integer.parseInt(props.getProperty("sucessor")));
			file.close();
			return finger;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void send(Request req, StreamObserver<Reply> responseObserver) {
		HandlerThreadServer h = new HandlerThreadServer(queueCommand, req, responseObserver, finger);
		h.run();
		try {
			h.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void create(Request req, StreamObserver<Reply> responseObserver) {
		HandlerThreadServer h = new HandlerThreadServer(queueCommand, req, responseObserver, finger);
		h.run();
		try {
			h.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Request req, StreamObserver<Reply> responseObserver) {
		HandlerThreadServer h = new HandlerThreadServer(queueCommand, req, responseObserver, finger);
		h.run();
		try {
			h.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Request req, StreamObserver<Reply> responseObserver) {
		HandlerThreadServer h = new HandlerThreadServer(queueCommand, req, responseObserver, finger);
		h.run();
		try {
			h.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read(Request req, StreamObserver<Reply> responseObserver) {
		HandlerThreadServer h = new HandlerThreadServer(queueCommand, req, responseObserver, finger);
		h.run();
		try {
			h.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Finger getFinger() {
		return this.finger;
	}
}