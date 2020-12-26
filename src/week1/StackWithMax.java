package week1;

import java.util.*;
import java.io.*;

public class StackWithMax {
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

    public void solve() throws IOException {
        FastScanner scanner = new FastScanner();
        int queries = scanner.nextInt();
        Stack<Integer> stack = new Stack<Integer>();

        for (int qi = 0; qi < queries; ++qi) {
            String operation = scanner.next();
            if ("push".equals(operation)) {
                int value = scanner.nextInt();
                stack.push(value);
            } else if ("pop".equals(operation)) {
                stack.pop();
            } else if ("max".equals(operation)) {
                System.out.println(Collections.max(stack));
                //Haoyun: this is an O(n) operation
            }
        }
    }
    
    //Write the code here
    // Haoyun: below we implement an O(1) operation for max
    public void solveInConst() throws IOException {
        FastScanner scanner = new FastScanner();
        int queries = scanner.nextInt();
        Stack<Integer> stack = new Stack<Integer>();
        
        int max = Integer.MIN_VALUE;
        Stack<Integer> maxStack = new Stack<Integer>();

        for (int qi = 0; qi < queries; ++qi) {
            String operation = scanner.next();
            if ("push".equals(operation)) {
                int value = scanner.nextInt();
                stack.push(value);
                if(value > max) {
                	max = value;
                	maxStack.push(value);
                }
                else {
                	maxStack.push(max);
                }
            } 
            else if ("pop".equals(operation)) {
                stack.pop();
                maxStack.pop();
            } 
            else if ("max".equals(operation)) {
            	if(maxStack.isEmpty()) {
            		System.out.println("Empty stack!");
            	}
            	else {
            		System.out.println(maxStack.peek());
            	}
                
            }
        }
    }
    

    static public void main(String[] args) throws IOException {
        //new StackWithMax().solve();
        new StackWithMax().solveInConst();
    }
}
