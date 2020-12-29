package week3;

import java.util.*;
import java.io.*;

public class substring_equality {
	long m1 = 1000000007;
	long m2 = 1000000009;	
	int x = 31;
	
	public class Solver {
		private String s;
		public Solver(String s) {
			this.s = s;
		}
		public boolean ask(int a, int b, int l) {
			return s.substring(a, a + l).equals(s.substring(b, b + l));
		}
		public boolean askFast(int a, int b, int l) {
			//Haoyun: we just used long; Chujie used BigInteger class
			long[] h1 = computeSmallH(s, m1, x);
			long[] h2 = computeSmallH(s, m2, x);
			
			long hashA1 = (h1[a + l] % m1 - (long)Math.pow(x, l) * h1[a] % m1 + m1) % m1;
			long hashA2 = (h2[a + l] % m2 - (long)Math.pow(x, l) * h2[a] % m2 + m2) % m2;
			
			long hashB1 = (h1[b + l] % m1 - (long)Math.pow(x, l) * h1[b] % m1 + m1) % m1;
			long hashB2 = (h2[b + l] % m2 - (long)Math.pow(x, l) * h2[b] % m2 + m2) % m2;
			
			return (hashA1 == hashB1 && hashA2 == hashB2);
		}
		
		public long[] computeSmallH(String s, long m, int x) {
			long[] h = new long[s.length() + 1];
			h[0] = 0;
			for(int i = 1; i < h.length; i++) {
				h[i] = ((x * h[i - 1] + s.charAt(i - 1)) % m + m) % m;
			}
			return h;
		}
	}

	public void run() throws IOException {
		FastScanner in = new FastScanner();
		PrintWriter out = new PrintWriter(System.out);
		String s = in.next();
		int q = in.nextInt();
		Solver solver = new Solver(s);
		for (int i = 0; i < q; i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			int l = in.nextInt();
			out.println(solver.askFast(a, b, l) ? "Yes" : "No");
		}
		out.close();
	}

	static public void main(String[] args) throws IOException {
	    new substring_equality().run();
	}

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
}
