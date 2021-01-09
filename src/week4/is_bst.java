package week4;

import java.util.*;
import java.io.*;

public class is_bst {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        boolean isBinarySearchTree() {
            // Implement correct algorithm here
        	int[] prev = {0};
        	//Haoyun: this is not a good way to implement the following algorithm
        	//I have to make prev an array so that the recursion can modify its value
        	//If one really wants to do, use a global variable of Node prev
        	//as a field in the tree object
        	return isBSTInOrder(0, prev);
        }
        
    	//Haoyun: the simplest way for this problem (strictly less or greater)
    	//is to inOrder traverse, save to an arrayList
    	//and scan again see if increasing
    	//We are not going to implement it here

    	
    	//One can slightly improve the above
        //by making traversal and checking at same time
    	//See below for this algorithm
        boolean isBSTInOrder(int index, int[] prev) {
        	if(tree.length <= 1) return true;
        	
        	if(index != -1) { 
            	if(!isBSTInOrder(tree[index].left, prev)) {
            		return false;
            	}
            	
            	if(prev[0] != -1 && tree[index].key <= tree[prev[0]].key) {
            		return false;
            	}           		           	
            	prev[0] = index; 
            	System.out.println(tree[prev[0]].key);
            	return isBSTInOrder(tree[index].right, prev);
        	}
        	return true;
        }
        //Other ways to do this problem include
        //1. use max and min to record the value regime and update them
        //2. use stack to simulate recursion
        //See leetcode 98
        
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }
    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}
