public class Node{
    //Declaring the parameters of a node.
    Node left,right,child,parent;
    int degree = 0;
    boolean child_cut = false;
    private String keyword;
    int key;
    //Initializing a node's default parameters
    Node(String keyword, int key){
        this.keyword = keyword;
        this.key = key;
        this.left= this;
        this.right = this;
        this.parent = null;
        this.degree = 0;
    }
    //Getter for a node's information i.e the Keyword.
    public String getKeyword(){
        return this.keyword;
    }
}