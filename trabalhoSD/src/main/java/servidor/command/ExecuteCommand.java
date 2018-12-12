package servidor.command;

import servidor.dataBase.Data;

public class ExecuteCommand {
  public String execute(String comando, Data dataBase) throws Exception {
    int tipo = ComandQuery.getTipoComando(comando);
    switch (tipo) {
      case 1:// create
        try {
          System.out.println("*****  executando create");
          if (comando.contains(":") && !ComandQuery.getValue(comando).isEmpty()) {
            return dataBase.create(ComandQuery.getKey(comando), ComandQuery.getValue(comando));
          }
          return "Formato invalido";
        } catch (Exception e) {
          return "Erro ao processar comando";
        }
      case 2:// read
        try {
          System.out.println("*****  executando read key " + ComandQuery.getKey(comando));
          if (!ComandQuery.getKey(comando).equals(" ")) {
            return dataBase.read(ComandQuery.getKey(comando));
          }
          return "Informe a key";
        } catch (Exception e) {
          return "Erro ao processar comando";
        }
      case 3:// update
        try {
          System.out.println("*****  executando update");
          if (comando.contains(":") && !ComandQuery.getValue(comando).equals("")) {
            return dataBase.update(ComandQuery.getKey(comando), ComandQuery.getValue(comando));
          }
          return "Formato invalido";
        } catch (Exception e) {
          return "Erro ao processar comando";
        }
      case 4:// delete
        try {
          System.out.println("*****  executando delete");
          return dataBase.delete(ComandQuery.getKey(comando));
        } catch (Exception e) {
          return "Erro ao processar comando";
        }
      default:
        return "Comando invalido";
    }
  }
  // public String execute(ClientData elemento) throws Exception {
  // String comando = elemento.getComando();
  //// Data dataBase = elemento.getData();
  // return execute(comando, dataBase);
  // }
}
