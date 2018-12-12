package servidor;

import java.math.BigInteger;

public class Finger {
  private String andress;
  private int port;
  private BigInteger id;
  private BigInteger maxKey;
  private BigInteger minKey;
  private int sucessor;
  private int antecessor;
  private Integer logNumber = 0;
  
  public Finger(String address, int port, BigInteger id, BigInteger minKey, BigInteger maxKey, int antecessor, int sucessor) {
    this.andress = address;
    this.port = port;
    this.id = id;
    this.maxKey = maxKey;
    this.minKey = minKey;
    this.antecessor = antecessor;
    this.sucessor = sucessor;
  }
  
  // public Finger(String andress, int port, BigInteger id, BigInteger minKey,
  // BigInteger maxKey) {
  // this.andress = andress;
  // this.port = port;
  // this.id = id;
  // this.maxKey = maxKey;
  // this.minKey = minKey;
  // this.antecessor = port;
  // this.sucessor = port;
  // }
  public String getAddress() {
    return this.andress;
  }
  
  public int getPort() {
    return this.port;
  }
  
  public BigInteger getId() {
    return this.id;
  }
  
  public BigInteger getMaxKey() {
    return this.maxKey;
  }
  
  public BigInteger getMinKey() {
    return this.minKey;
  }
  
  public int getSucessor() {
    return sucessor;
  }
  
  public int getAntecessor() {
    return antecessor;
  }
  
  public Integer getLogNumber() {
    return logNumber;
  }
  
  public void setLogNumber(Integer logNumber) {
    this.logNumber = logNumber;
  }
  
  public void incrementLog() {
    this.logNumber = this.logNumber + 1;
  }
  
  public void print() {
    System.out.println("---------------------------------");
    System.out.println("id= " + this.id);
    System.out.println("andress= " + this.andress);
    System.out.println("porta= " + this.port);
    System.out.println("minKey= " + this.minKey);
    System.out.println("maxKey= " + this.maxKey);
    System.out.println("antecessor= " + this.antecessor);
    System.out.println("sucessor= " + this.sucessor);
    System.out.println("---------------------------------");
  }
}
