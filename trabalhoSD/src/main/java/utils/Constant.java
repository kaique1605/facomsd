package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.Properties;

public class Constant {
  public static int MAX_QUEUE = 2024;
  public static final int QUEUE_INCREMENT_RESIZE = (int) (MAX_QUEUE * 0.03);
  // public static final int SERVER_PORT = 9876;
  public static final String HOST = "127.0.0.1";
  public static BigInteger maxKey = BigInteger.ZERO;
  public static BigInteger minKey = BigInteger.ZERO;
  public static int portInitial = 8800;
  
  public static void setMaxKey(String maxKey2) {
    setPropKey(maxKey2, minKey.toString());
    maxKey = new BigInteger(maxKey2);
  }
  
  public static void setMaxKey(BigInteger maxKey2) {
    setPropKey(maxKey2.toString(), minKey.toString());
    maxKey = maxKey2;
  }
  
  public static BigInteger getMaxKey() {
    Properties props = new Properties();
    try {
      File f = new File("constant.properties");
      if (!f.exists()) {
        f.createNewFile();
      }
      FileInputStream file = new FileInputStream("constant.properties");
      props.load(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (props.getProperty("maxKey") == null) {
      return BigInteger.ZERO;
    }
    return new BigInteger(props.getProperty("maxKey"));
  }
  
  public static BigInteger getMinKey() {
    Properties props = new Properties();
    try {
      File f = new File("constant.properties");
      if (!f.exists()) {
        f.createNewFile();
      }
      FileInputStream file = new FileInputStream("constant.properties");
      props.load(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (props.getProperty("minKey") == null) {
      return BigInteger.ZERO;
    }
    return new BigInteger(props.getProperty("minKey"));
  }
  
  private static void setPropKey(String maxKey, String minKey) {
    try {
      // criar arquivo properties
      Properties props = new Properties();
      File f = new File("constant.properties");
      f.delete();
      f.createNewFile();
      props.setProperty("maxKey", maxKey);
      props.setProperty("minKey", minKey);
      props.store(new FileOutputStream(f), "propertiesServer");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void atualizaKeys() {
    maxKey = getMaxKey();
    minKey = getMinKey();
  }
}
