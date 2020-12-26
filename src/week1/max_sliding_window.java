package week1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class max_sliding_window {
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
	
	public class SlidingWindow{
		int n;
		int sequence[];
		int m;
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			sequence = new int[n];
			for (int i = 0; i < n; i++) {
				sequence[i] = in.nextInt();
			}
			m = in.nextInt();
		}
		int[] getMaxSequence(){
			int[] ans = new int[n - m + 1];
			// This is an O(nm) algorithm
			for(int i = 0; i < n - m + 1; i++) {
				int maxValue = Integer.MIN_VALUE;
				for(int j = i; j < i + m; j++) {
					if(sequence[j] > maxValue) {
						maxValue = sequence[j];
					}
				}
				ans[i] = maxValue;
			}
			return ans;
		}
		
		//Haoyun: Below we implement an O(n) algorithm. 
		//The idea is that, to slide the window we need a queue.
		//To get an O(n) algorithm we need each max function to be O(1)
		//We have actually achieved an O(1) method for each max function
		//by using a valueStack and a maxStack.
		//The problem becomes to implement a queue by stacks.
		//See https://stackoverflow.com/questions/69192/how-to-implement-a-queue-using-two-stacks
		
		// We first build the stack with max
		class StackWithMax{
			public Stack<Integer> valueStack= new Stack<>();
			public Stack<Integer> maxStack= new Stack<>();			
			public int max = Integer.MIN_VALUE;
			
			public void push(int value) {
				valueStack.push(value);
				max = Math.max(max, value);
				maxStack.push(max);
			}
			
			public int pop() {
				maxStack.pop();
				return valueStack.pop();			
			}
			
			public int getMax() {
				if(!maxStack.isEmpty()) {
					return maxStack.peek();
				}
				return Integer.MIN_VALUE;
			}
			
			public boolean isEmpty() {
				return valueStack.isEmpty();
			}
		}
		
		// We then build queue by two stacks, in and out
		
		class QueueByStacks{
			public StackWithMax inbox = new StackWithMax();
			public StackWithMax outbox = new StackWithMax();
			
			public void add(int value) {
				inbox.push(value);
			}
			
			public int remove() {
				if(outbox.isEmpty() && inbox.isEmpty()) {
					System.out.print("Empty queue!");
					return -1;
				}
				
				if(outbox.isEmpty()) {
					while(!inbox.isEmpty()) {
						outbox.push(inbox.pop());
					}
					return outbox.pop();
				}
				else {
					return outbox.pop();
				}
				
			}
			
			public int getMax() {
				return Math.max(inbox.getMax(), outbox.getMax());
			}
		}
		
		//Finally, we write the 0(N) int[] getMaxSequence() method
		
		int[] getMaxSequenceON() {
			int[] maxSeq = new int[n - m + 1];
			QueueByStacks que = new QueueByStacks();
			
			for(int i = 0; i < m; i++) {
				que.add(sequence[i]);
			}
			maxSeq[0] = que.getMax();
			
			for(int i = m; i < n; i++) {
				que.remove();
				que.add(sequence[i]);
				maxSeq[i - m + 1] = que.getMax();
			}
			
			return maxSeq;
		}
		
		//Chujie also implemented solutions based on hint 2 and 3.
		//Have a look if time permits (12/19/2020)
		
	}
	
	public static void main(String[] args) throws IOException {
		new Thread(null, new Runnable() {
          public void run() {
              try {
                  new max_sliding_window().run();
              } catch (IOException e) {
              }
          }
      }, "1", 1 << 26).start();
	}
	public void run() throws IOException {
		SlidingWindow sWindow = new SlidingWindow();
		sWindow.read();
		int[] ans = sWindow.getMaxSequence();
		for(int i = 0; i < ans.length; i++) {
			System.out.print(ans[i] + " ");
		}
	}
}