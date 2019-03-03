import java.util.*;
public class FibonacciHeap
{
    private int node_count;
    
    private Node maxNode;
    public void insert(Node node)//Function for insert of FHeap
    {
        if (maxNode != null) {//check the value of maxnode
            node.left = maxNode;//add the new node to the right of the max node
            node.right = maxNode.right;
            maxNode.right = node;
            //check the node to the right and set maxnode accordingly
            if ( node.right!=null) {                               
                node.right.left = node;
            }
            if ( node.right==null)
            {
                node.right = maxNode;
                maxNode.left = node;
            }
            if (node.key > maxNode.key) {
                maxNode = node;
            }
        } else {
            maxNode = node;
        }
        node_count++;
    }
    public void cut(Node child, Node parent)//Function for cut operation of FHeap.
    {
        // Cut/Remove child from parent and reduces the degree of the parent.
        child.left.right = child.right;
        child.right.left = child.left;
        parent.degree--;
        if (parent.child == child) {// reset the parent's child depending on need.
            parent.child = child.right;
        }
        if (parent.degree == 0) {
            parent.child = null;
        }
        //Add child to the root level list of the FHeap.
        child.left = maxNode;
        child.right = maxNode.right;
        maxNode.right = child;
        child.right.left = child;
        //After addition set the parent to null
        child.parent = null;
        // set child_cut to false
        child.child_cut = false;
    }
    public void cascadingCut(Node target)//Function for cascading cut operation of FHeap.
    {
        Node target_parent = target.parent;

        //check if target has a parent.
        if (target_parent != null) {
            // if target's child_cut is not set, set its child_cut.
            if (!target.child_cut) {
                target.child_cut = true;
            } else {
                // if it's child_cut is set, cut it from it's parent.
                cut(target, target_parent);

                // check for it's parent as well and perform it's cascading cut.
                cascadingCut(target_parent);
            }
        }
    }
    public void increaseKey(Node target, int value)//Function for increase key operation of FHeap.
    {
        target.key = value;//Set the value as the new key of the target
        Node target_parent = target.parent;
        //Check if new value violates the max heap property and perform cut and cascading cut of target and its parent if needed.
        if ((target_parent != null) && (target.key > target_parent.key)) {
            cut(target, target_parent);
            cascadingCut(target_parent);
        }
        //If new value exceeds maxnode, then set this as the new maxnode.
        if (target.key > maxNode.key) {
            maxNode = target;
        }
    }
    public Node removeMax()//Function to remove max node of FHeap
    {
        Node target = maxNode;
        if (target != null) {
            int numberofChildren = target.degree;
            Node target_child = target.child;
            Node tempRight;
            while (numberofChildren > 0) {//While the node still has children under it
                tempRight = target_child.right;

                // remove the target's child from child list
                target_child.left.right = target_child.right;
                target_child.right.left = target_child.left;

                // add target_child to root list of heap
                target_child.left = maxNode;
                target_child.right = maxNode.right;
                maxNode.right = target_child;
                target_child.right.left = target_child;

                // set it's parent to null
                target_child.parent = null;
                target_child = tempRight;
                //decrease the target's children count
                numberofChildren--;

            }


            // remove target from top order list of FHeap
            target.left.right = target.right;
            target.right.left = target.left;

            if (target == target.right) {
                maxNode = null;

            } else {
               maxNode = target.right;
               degreewiseMerge();
           }
           node_count--;
           return target;
       }
        return null;
    }
    public void degreewiseMerge()//Function for pair-wise degree merge of FHeap
    {
        int sizeofDegreeTable = 70;//Randomly chosen size for degree table based on formula.
        List<Node> degreeTable =
        new ArrayList<Node>(sizeofDegreeTable);
        // Initialize the degree table required for doing the merge
        for (int i = 0; i < sizeofDegreeTable; i++) {
            degreeTable.add(null);
        }
        // Calculate the number of root nodes in the FHeap
        int numRoots = 0;
        Node max_node = maxNode;
        if (max_node != null) {
            numRoots++;
            max_node = max_node.right;                     
            while (max_node != maxNode) {
                numRoots++;
                max_node = max_node.right;
            }
        }
        // For every node in top level root list 
        while (numRoots > 0) {
            int d = max_node.degree;
            Node next = max_node.right;
            // check if the degree is there in degree table, if not add,if yes then combine and merge
            for (;;) {
                Node max_degree_node = degreeTable.get(d);
                if (max_degree_node == null) {
                    break;
                }
                //Check whos key value is greater
                if (max_node.key < max_degree_node.key) {
                    Node temp = max_degree_node;
                    max_degree_node = max_node;
                    max_node = temp;
                }
                //make max_degree_node the child of max_node as max_node key value is greater
                makeChild(max_degree_node, max_node);
                //set the degree to null as max_node and max_degree_node are combined now
                degreeTable.set(d, null);
                d++;
            }
            //store the new combined max_node in the respective degree table postion
            degreeTable.set(d, max_node);

            // Move forward through list.
            max_node = next;
            numRoots--;
        }
        //Deleting the max node
        maxNode = null;
        // combine entries of the degree table
        for (int i = 0; i < sizeofDegreeTable; i++) {
            Node same_degree_node = degreeTable.get(i);
            if (same_degree_node == null) {
                continue;
            }
            //till max node is not null
            if (maxNode != null) {
                // First remove node from root list.
                same_degree_node.left.right = same_degree_node.right;
                same_degree_node.right.left = same_degree_node.left;

                // Now add to root list, again.
                same_degree_node.left = maxNode;
                same_degree_node.right = maxNode.right;
                maxNode.right = same_degree_node;
                same_degree_node.right.left = same_degree_node;
                // Check if this is a new maximum
                if (same_degree_node.key > maxNode.key) {
                    maxNode = same_degree_node;
                }
            } else {
                maxNode = same_degree_node;
            }
        }
    }
    public void makeChild(Node child, Node parent)//Function to make a node the child of another node of FHeap
    {
        // remove child from root list of heap
        child.left.right = child.right;
        child.right.left = child.left;
        // make child a child of the parent
        child.parent = parent;
        if (parent.child == null) {
            parent.child = child;
            child.right = child;
            child.left = child;
        } else {
            child.left = parent.child;
            child.right = parent.child.right;
            parent.child.right = child;
            child.right.left = child;
        }
        // make child_cut of the child as false
        child.child_cut = false;
        // increment degree of the new parent by 1
        parent.degree++;
    }
}