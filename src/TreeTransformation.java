import java.io.*;
import java.util.*;
import java.util.List;

public class TreeTransformation {

  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("Usage: java TreeTransformation <file1> <file2>");
      System.exit(1);
    }

    // Read the input files
    String file1 = args[0];
    String file2 = args[1];
    List<int[]> edges1 = readEdges(file1);
    List<int[]> edges2 = readEdges(file2);
  }

    public static List<int[]> readEdges(String filename){
      List<int[]> edges = new ArrayList<>();
      try {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
          String[] parts = line.trim().split(",");
          int[] edge = new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
          edges.add(edge);
        }
        reader.close();
      } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
        System.exit(1);
      }
      return edges;
    }

    public static Map<Integer, TreeNode> buildTree(List < int[]>edges){
      Map<Integer, TreeNode> nodes = new HashMap<>();
      // First pass to create nodes
      for (int[] edge : edges) {
        int parent_id = edge[0];
        int child_id = edge[1];
        if (!nodes.containsKey(parent_id)) {
          nodes.put(parent_id, new TreeNode(parent_id));
        }
        if (!nodes.containsKey(child_id)) {
          nodes.put(child_id, new TreeNode(child_id));
        }
      }
      // Second pass to build the tree
      for (int[] edge : edges) {
        int parent_id = edge[0];
        int child_id = edge[1];
        TreeNode parent = nodes.get(parent_id);
        TreeNode child = nodes.get(child_id);
        parent.children.add(child);
      }
      return nodes;
    }

    public static List<String> transformTree
    (Map < Integer, TreeNode > tree1, Map < Integer, TreeNode > tree2){
      List<String> operations = new ArrayList<>();
      Set<TreeNode> leaves1 = findLeaves(tree1);
      Set<TreeNode> leaves2 = findLeaves(tree2);

      for (TreeNode leaf : leaves1) {
        if (!leaves2.contains(leaf)) {
          if (canRemoveNode(leaf)) {
            operations.add("REMOVE(" + leaf.id + ")");
          } else {
            System.err.println("Cannot remove node " + leaf.id + " with children");
            System.exit(1);
          }
        }
      }

      for (TreeNode leaf : leaves2) {
        if (!leaves1.contains(leaf)) {
          operations.add("ADD(" + leaf.parent.id + ", " + leaf.id + ")");
        }
      }

      return operations;
    }

    public static Set<TreeNode> findLeaves (Map < Integer, TreeNode > tree){
      Set<TreeNode> leaves = new HashSet<>();
      for (TreeNode node : tree.values()) {
        if (node.children.isEmpty()) {
          leaves.add(node);
        }
      }
      return leaves;
    }

    public static boolean canRemoveNode (TreeNode node){
      return node.children.stream().allMatch(child -> child.children.isEmpty());
    }
  }
