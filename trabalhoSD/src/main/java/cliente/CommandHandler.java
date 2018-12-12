package cliente;

public class CommandHandler {
  private String[] menu = "Create Read Update Delete".split(" ");
  private String regex = "[a-zA-Z0-9]*";
  
  public boolean checkComand(String command) {
    String comando = (command.split(" ")[0]).toLowerCase();
    if (comando.matches(regex)) {
      for (String s : menu) {
        if (s.toLowerCase().equals(comando)) {
          return true;
        }
      }
    }
    return false;
  }
}
