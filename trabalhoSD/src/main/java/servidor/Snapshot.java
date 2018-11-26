package servidor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;

import servidor.dataBase.Data;

public class Snapshot implements Runnable {
	
	private Finger finger;
	private Data dataBase;
	
	public Snapshot(Finger finger,Data database) {
		this.finger = finger;
		this.dataBase = database;
	}

	@Override
	public void run() {
		while(true) {
			
			try{
				File firstLog = new File("logs\\" + this.finger.getId() + "\\" + this.finger.getLogNumber() + ".log");
				if (!firstLog.exists()) {
					firstLog.createNewFile();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			
			try {
				Thread.sleep(30000);
				System.out.println("Gravando Snapshot do Servidor " + this.finger.getId());
				
				this.finger.incrementLog();
				
				File log = new File("logs\\" + this.finger.getId() + "\\" + this.finger.getLogNumber() + ".log");
				if (!log.exists()) {
					log.createNewFile();
					System.out.println("Criando novo log pelo snap");
				}
				
				File snapshot = new File("logs\\" + this.finger.getId() + "\\" + (this.finger.getLogNumber() - 1) + ".snap");
				if (!snapshot.exists()) {
					snapshot.createNewFile();
				}

				PrintStream fileStream = new PrintStream(
						new FileOutputStream("logs\\" + this.finger.getId() + "\\" + (this.finger.getLogNumber() - 1) + ".snap", true));
				for (BigInteger key  : dataBase.getDados().keySet()) {
							String comando = "create " + key + ":" + this.dataBase.read(key);
							fileStream.append(comando + System.getProperty("line.separator"));
				}
				fileStream.close();
				
				if(this.finger.getLogNumber() >= 3) {
				  
				  File f = new File ("logs\\" + this.finger.getId());
				  if(f.length() > 7) {
				    File[] files = f.listFiles();
				    files[0].delete();
				  }
				  
					File snapDeletar = new File("logs\\" + this.finger.getId() + "\\" + (this.finger.getLogNumber() - 4) + ".snap");
					if (snapDeletar.exists()) {
						snapDeletar.delete();
					}
					
					File logDeletar = new File("logs\\" + this.finger.getId() + "\\" + (this.finger.getLogNumber() - 3) + ".log");
					if (logDeletar.exists()) {
						logDeletar.delete();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
