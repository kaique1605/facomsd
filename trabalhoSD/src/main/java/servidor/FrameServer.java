package servidor;

import java.awt.BorderLayout;
import java.math.BigInteger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FrameServer extends JFrame {
  private JTextArea texto = new JTextArea();
  
  public FrameServer(BigInteger serverId){
    //Define o título da janela
    super("Server: "+serverId);
    this.setLayout(new BorderLayout());
    this.getContentPane().add(texto);
    
//    this.montaJanela();
  } 

//  private void montaJanela(){
//     this.getContentPane().add(texto);
//  }
  
  public void append(String text) {
    texto.append(text+"\n");
    this.getContentPane().add(texto);
  }
 
  public void start(){
    this.setSize(240,720);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
}
