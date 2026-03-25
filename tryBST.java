/*
Zimbini Hlela Vuke - 4498123
Practical 7 – Binary Search Tree

This program builds a perfect balanced BST with numbers in [1..2^n -1]
and removes all even numbered nodes.

the code was implemented and understood by me with the guidance and  explanation from ChaGPT.
*/

class tNode {
    int key;
    tNode left, right;

    tNode(int k) {
        key = k;
        left = right = null;
    }
}

class BST {

    tNode root;

    // Insert node
    tNode insertRec(tNode root, int key) {

        if (root == null)
            return new tNode(key);

        if (key < root.key)
            root.left = insertRec(root.left, key);

        else if (key > root.key)
            root.right = insertRec(root.right, key);

        return root;
    }

    void insert(int key) {
        root = insertRec(root, key);
    }

    // Delete node
    tNode deleteRec(tNode root, int key) {

        if (root == null)
            return root;

        if (key < root.key)
            root.left = deleteRec(root.left, key);

        else if (key > root.key)
            root.right = deleteRec(root.right, key);

        else {

            if (root.left == null)
                return root.right;

            else if (root.right == null)
                return root.left;

            root.key = minValue(root.right);

            root.right = deleteRec(root.right, root.key);
        }

        return root;
    }

    int minValue(tNode root) {
        int min = root.key;
        while (root.left != null) {
            min = root.left.key;
            root = root.left;
        }
        return min;
    }

    void delete(int key) {
        root = deleteRec(root, key);
    }

    // Check BST validity
    boolean isBSTUtil(tNode node, int min, int max) {

        if (node == null)
            return true;

        if (node.key < min || node.key > max)
            return false;

        return isBSTUtil(node.left, min, node.key - 1)
                && isBSTUtil(node.right, node.key + 1, max);
    }

    boolean isBST() {
        return isBSTUtil(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    // Remove even numbers
    void removeEvens(int max) {
        for (int i = 2; i <= max; i += 2)
            delete(i);
    }

    // Build balanced BST
    void buildBalanced(int start, int end) {

        if (start > end)
            return;

        int mid = (start + end) / 2;

        insert(mid);

        buildBalanced(start, mid - 1);
        buildBalanced(mid + 1, end);
    }
}

public class tryBST {

    static double mean(long arr[]) {

        long sum = 0;

        for (long v : arr)
            sum += v;

        return (double) sum / arr.length;
    }

    static double std(long arr[], double mean) {

        double sum = 0;

        for (long v : arr)
            sum += Math.pow(v - mean, 2);

        return Math.sqrt(sum / arr.length);
    }

    public static void main(String[] args) {

        int n = 20;
        int max = (int) Math.pow(2, n) - 1;

        int repetitions = 30;

        long populateTimes[] = new long[repetitions];
        long deleteTimes[] = new long[repetitions];

        for (int i = 0; i < repetitions; i++) {

            BST tree = new BST();

            long start = System.currentTimeMillis();

            tree.buildBalanced(1, max);

            long end = System.currentTimeMillis();

            populateTimes[i] = end - start;

            if (!tree.isBST())
                System.out.println("Tree error!");

            start = System.currentTimeMillis();

            tree.removeEvens(max);

            end = System.currentTimeMillis();

            deleteTimes[i] = end - start;
        }

        double avgPop = mean(populateTimes);
        double stdPop = std(populateTimes, avgPop);

        double avgDel = mean(deleteTimes);
        double stdDel = std(deleteTimes, avgDel);

        System.out.println("Number of keys: " + max);
        System.out.println();

        System.out.println("Method\t\tAverage time (ms)\tStd Deviation");

        System.out.println("Populate tree\t" + avgPop + "\t\t" + stdPop);

        System.out.println("Remove evens\t" + avgDel + "\t\t" + stdDel);
    }
}
