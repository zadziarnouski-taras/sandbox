package blockchain;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {

  private String hash;
  private String previousHash;
  private String data;
  private long timestamp;
  private int nonce;

  public Block(String data, String previousHash, long timeStamp) {
    this.data = data;
    this.previousHash = previousHash;
    this.timestamp = timeStamp;
    this.hash = calculateBlockHash();
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public void setPreviousHash(String previousHash) {
    this.previousHash = previousHash;
  }

  public void setData(String data) {
    this.data = data;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public void setNonce(int nonce) {
    this.nonce = nonce;
  }

  public String getHash() {
    return hash;
  }

  public String getPreviousHash() {
    return previousHash;
  }

  public String getData() {
    return data;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public int getNonce() {
    return nonce;
  }

  public String calculateBlockHash() {
    String dataToHash = previousHash
        + timestamp
        + nonce
        + data;
    MessageDigest digest = null;
    byte[] bytes = null;
    try {
      digest = MessageDigest.getInstance("SHA-256");
      bytes = digest.digest(dataToHash.getBytes(UTF_8));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    StringBuffer buffer = new StringBuffer();
    for (byte b : bytes) {
      buffer.append(String.format("%02x", b));
    }
    return buffer.toString();
  }

  public String mineBlock(int prefix) {
    String prefixString = new String(new char[prefix]).replace('\0', '0');
    while (!hash.substring(0, prefix).equals(prefixString)) {
      nonce++;
      hash = calculateBlockHash();
    }
    return hash;
  }

  @Override
  public String toString() {
    return "Block{" +
        "hash='" + hash + '\'' +
        ", previousHash='" + previousHash + '\'' +
        ", data='" + data + '\'' +
        ", timestamp=" + timestamp +
        ", nonce=" + nonce +
        '}' + '\n';
  }
}
