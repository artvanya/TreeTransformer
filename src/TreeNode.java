import java.util.ArrayList;
import java.util.List;

class TreeNode {
  public int id;
  public List<TreeNode> children;
  public boolean isLeaf;

  TreeNode parent;

  public TreeNode(int id) {
    this.id = id;
    this.children = new ArrayList<>();
    this.isLeaf = true;
  }

  public void addChild(TreeNode child) {
    this.children.add(child);
    this.isLeaf = false;
  }
}
