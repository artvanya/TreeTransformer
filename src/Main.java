import java.util.*;
public class Main {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Usage: java Main <file1> <file2>");
      System.exit(1);
    }

    String file1 = args[0];
    String file2 = args[1];

    Map<Integer, TreeNode> tree1 = TreeTransformation.buildTree(TreeTransformation.readEdges(file1));
    Map<Integer, TreeNode> tree2 = TreeTransformation.buildTree(TreeTransformation.readEdges(file1));

    List<String> operations = TreeTransformation.transformTree(tree1, tree2);

    for (String operation : operations) {
      System.out.println(operation);
    }
  }
}
