class AVLNode {
    int key;
    AVLNode left, right;
    int height = 1;
    AVLNode(int key) { this.key = key; }
}

class AVLTree {

    static int height(AVLNode n) {
        return n == null ? 0 : n.height;
    }

    static int balance(AVLNode n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    static void updateHeight(AVLNode n) {
        if (n != null) n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    // TODO 1: perform a right rotation around y; return the new subtree root.
    static AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        // Perform rotation
        x.right = y;
        y.left = T2;
        // Update heights (y first, then x since x is new root)
        updateHeight(y);
        updateHeight(x);
        return x; // new subtree root
    }

    // TODO 2: perform a left rotation around x; return the new subtree root.
    static AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        // Perform rotation
        y.left = x;
        x.right = T2;
        // Update heights (x first, then y since y is new root)
        updateHeight(x);
        updateHeight(y);
        return y; // new subtree root
    }

    // TODO 3: standard BST insert + rebalance using the four cases (LL, LR, RL, RR).
    static AVLNode insert(AVLNode node, int key) {
        // Step 1: Standard BST insertion
        if (node == null) return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node; // Duplicate keys not allowed

        // Step 2: Update height of current node
        updateHeight(node);

        // Step 3: Get balance factor to check if this node became unbalanced
        int bf = balance(node);

        // LL Case: Left heavy and key inserted in left subtree
        if (bf > 1 && key < node.left.key)
            return rotateRight(node);

        // RR Case: Right heavy and key inserted in right subtree
        if (bf < -1 && key > node.right.key)
            return rotateLeft(node);

        // LR Case: Left heavy but key inserted in right subtree of left child
        if (bf > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RL Case: Right heavy but key inserted in left subtree of right child
        if (bf < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node; // Node is balanced
    }

    // Helper: find minimum node in a subtree (for delete)
    static AVLNode minNode(AVLNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // Delete a node and rebalance
    static AVLNode delete(AVLNode node, int key) {
        if (node == null) return null;

        if (key < node.key)
            node.left = delete(node.left, key);
        else if (key > node.key)
            node.right = delete(node.right, key);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            AVLNode succ = minNode(node.right);
            node.key = succ.key;
            node.right = delete(node.right, succ.key);
        }

        updateHeight(node);
        int bf = balance(node);

        // LL
        if (bf > 1 && balance(node.left) >= 0)  return rotateRight(node);
        // LR
        if (bf > 1 && balance(node.left) < 0)  { node.left = rotateLeft(node.left); return rotateRight(node); }
        // RR
        if (bf < -1 && balance(node.right) <= 0) return rotateLeft(node);
        // RL
        if (bf < -1 && balance(node.right) > 0) { node.right = rotateRight(node.right); return rotateLeft(node); }

        return node;
    }

    static void inorder(AVLNode node) {
        if (node == null) return;
        inorder(node.left);
        System.out.print(node.key + " ");
        inorder(node.right);
    }

    public static void main(String[] args) {
        int[] sequence = {20, 30, 35, 40, 45, 50, 60, 65, 70, 75, 80, 85, 90};
        AVLNode root = null;

        for (int key : sequence)
            root = insert(root, key);

        System.out.print("AVL Inorder after insertions: ");
        inorder(root);
        System.out.println("\nAVL Height after insertions: " + height(root));

        // Noon deletions
        int[] deletions = {30, 70, 50};
        for (int key : deletions) {
            root = delete(root, key);
            System.out.println("After deleting " + key + ", Height: " + height(root));
        }

        System.out.print("Final AVL Inorder: ");
        inorder(root);
    }
}