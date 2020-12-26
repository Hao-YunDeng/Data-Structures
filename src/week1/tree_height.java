package week1;

import java.util.*;
import java.io.*;

public class tree_height {
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

	public class TreeHeight {
		int n;
		int parent[];
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = in.nextInt();
			}
		}

		int computeHeight() {
            // Replace this code with a faster implementation
			int maxHeight = 0;
			for (int vertex = 0; vertex < n; vertex++) {
				int height = 0;
				for (int i = vertex; i != -1; i = parent[i])
					height++;
				maxHeight = Math.max(maxHeight, height);
			}
			return maxHeight;
		}
		
		int computeHeightWithDFS() {
			ArrayList<Integer>[] nodes = new ArrayList[n];
			for(int i = 0; i < n; i++) {
				nodes[i] = new ArrayList<Integer>();
			}
			int rootNode = -1;
			for(int i = 0; i < n; i++) {
				if(parent[i] == -1) {
					rootNode = i;
				}
				else {
					nodes[parent[i]].add(i);
				}
			}			
			return DFS(rootNode, nodes);
		}
		
		int computeHeightWithBFS() {
			ArrayList<Integer>[] nodes = new ArrayList[n];
			for(int i = 0; i < n; i++) {
				nodes[i] = new ArrayList<Integer>();
			}
			int rootNode = -1;
			for(int i = 0; i < n; i++) {
				if(parent[i] == -1) {
					rootNode = i;
				}
				else {
					nodes[parent[i]].add(i);
				}
			}			
			return BFS(rootNode, nodes);
		}
		
		int DFS(int node, ArrayList<Integer>[] nodes) {
			if(node == -1) {
				System.out.println("-1 used"); // Haoyun: Never used
				return 0;
			}
			int maxHeight = 0;
			for(int i = 0; i < nodes[node].size(); i++) { 
				// Haoyun: recursion returns when size = 0
				int childHeight = DFS(nodes[node].get(i), nodes);				
				maxHeight = Math.max(maxHeight, childHeight);
			}
			return maxHeight + 1;
		}
		
		int BFS(int rootNode, ArrayList<Integer>[] nodes) {
			int maxHeight = 0;
			int[] nodeHeights = new int[n];
			Queue<Integer> que = new LinkedList<Integer>();
			que.add(rootNode);
			while(!que.isEmpty()) {
				int node = que.peek();
				que.remove();
				for(int i = 0; i < nodes[node].size(); i++) {
					que.add(nodes[node].get(i));
					nodeHeights[nodes[node].get(i)] = nodeHeights[node] + 1;
					maxHeight = Math.max(maxHeight, nodeHeights[node] + 1);
				}
			}
			return maxHeight + 1; //Haoyun: switched to 1 based count
		}
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_height().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}
	public void run() throws IOException {
		TreeHeight tree = new TreeHeight();
		tree.read();
		System.out.println(tree.computeHeightWithDFS());
		System.out.println(tree.computeHeightWithBFS());
	}
}
