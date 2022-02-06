package blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main {

  public static void main(String[] args) {
//    Create Blockchain
    ArrayList<Block> blockchain = new ArrayList<>();
    int prefix = 4;
    String prefixString = new String(new char[prefix]).replace('\0', '0');

//    Add first block
    Block firstBlock = new Block(
        "Secret transaction #1",
        "0",
        new Date().getTime());
    firstBlock.mineBlock(prefix);
    blockchain.add(firstBlock);

//    Add more blocks
    for (int i = 2; i < 10; i++) {
      Block newBlock = new Block(
          "Secret transaction #" + i,
          blockchain.get(blockchain.size() - 1).getHash(),
          new Date().getTime());
      newBlock.mineBlock(prefix);
      blockchain.add(newBlock);
    }

//    Random check block
    int x = new Random().nextInt(blockchain.size());
    boolean equals = blockchain.get(x).getHash().substring(0, prefix).equals(prefixString);
    String intermediateResult = equals ? "passed" : "NOT passed";
    System.out.println("Random check block #" + x + " is " + intermediateResult);

//    Test chain
    boolean flag = true;
    for (int i = 0; i < blockchain.size(); i++) {
      String previousHash = i == 0 ? "0" : blockchain.get(i - 1).getHash();
      flag = blockchain.get(i).getHash().equals(blockchain.get(i).calculateBlockHash())
          && previousHash.equals(blockchain.get(i).getPreviousHash())
          && blockchain.get(i).getHash().substring(0, prefix).equals(prefixString);
      if (!flag) {
        break;
      }
    }
    System.out.println(flag ? "All checks passed" : "Error");
  }
}
