package servidor.dataBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import servidor.Finger;
import servidor.command.ExecuteCommand;

public class RecoveryData {
  public void recovery(Data data, Finger finger) {
    Integer tentativas = 0;
    Boolean deuCerto = false;
    Integer ultimoTentado = finger.getLogNumber();
    System.out.println("Recuperando banco de dados");
    ExecuteCommand execute = new ExecuteCommand();
    while (tentativas <= 3 && deuCerto == false) {
      System.out.println("Recuperando banco de dados do snapshot" + ultimoTentado);
      if (ultimoTentado == 0) {
        File tempFile = new File("logs\\" + finger.getId() + "\\" + ultimoTentado + ".snap");
        if (tempFile.exists()) {
          try {
            BufferedReader brSnap = new BufferedReader(new FileReader("logs\\" + finger.getId() + "\\" + ultimoTentado + ".snap"));
            BufferedReader brLog = new BufferedReader(new FileReader("logs\\" + finger.getId() + "\\" + (ultimoTentado + 1) + ".log"));
            String linha;
            while ((linha = brSnap.readLine()) != null) {
              execute.execute(linha, data);
            }
            while ((linha = brLog.readLine()) != null) {
              execute.execute(linha, data);
            }
            brSnap.close();
            brLog.close();
            deuCerto = true;
          } catch (Exception erro) {
            deuCerto = false;
            System.out.println(erro.getMessage());
            break;
          }
        }
        else {
          try {
            BufferedReader brLog = new BufferedReader(new FileReader("logs\\" + finger.getId() + "\\" + ultimoTentado + ".log"));
            String linha;
            while ((linha = brLog.readLine()) != null) {
              execute.execute(linha, data);
            }
            brLog.close();
            deuCerto = true;
          } catch (Exception erro) {
            deuCerto = false;
            System.out.println(erro.getMessage());
            break;
          }
        }
      }
      else {
        try {
          BufferedReader brSnap = new BufferedReader(new FileReader("logs\\" + finger.getId() + "\\" + ultimoTentado + ".snap"));
          BufferedReader brLog = new BufferedReader(new FileReader("logs\\" + finger.getId() + "\\" + (ultimoTentado + 1) + ".log"));
          String linha;
          while ((linha = brSnap.readLine()) != null) {
            execute.execute(linha, data);
          }
          while ((linha = brLog.readLine()) != null) {
            execute.execute(linha, data);
          }
          brSnap.close();
          brLog.close();
          deuCerto = true;
        } catch (Exception erro) {
          deuCerto = false;
          tentativas++;
          ultimoTentado--;
          System.out.println(erro.getMessage());
        }
      }
    }
    if (deuCerto == false) {
      System.out.println("Erro ao recuperar banco de dados!");
      System.exit(1);
    }
  }
}
