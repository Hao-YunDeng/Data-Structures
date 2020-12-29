package week3;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
    
    //Haoyun: how to setup prime and multiplier? -- seems to be arbitrary
    private static int prime = 100007;
    private static int multiplier = 31;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrencesRabinKarp(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        for (int i = 0; i + m <= n; ++i) {
	    boolean equal = true;
	    for (int j = 0; j < m; ++j) {
		if (s.charAt(j) != t.charAt(i + j)) {
		     equal = false;
 		    break;
		}
	    }
            if (equal)
                occurrences.add(i);
	}
        return occurrences;
    }
    
    private static List<Integer> getOccurrencesRabinKarp(Data input) {
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        
        int pHash = polyHash(s, prime, multiplier);
        int[] H = precomputeHashes(t, m, prime, multiplier);
        for(int i =0; i < H.length; i++) {
        	if(pHash != H[i]) {
        		continue;
        	}
        	if(t.substring(i, i + m).equals(s)) {
        		occurrences.add(i);
        	}
        }
        return occurrences;
    	
    }
    //Haoyun: we can't use hashCode in java because we want to use the improved Rabin Karp
    static int polyHash(String s, int p, int x) {
    	int hash = 0;
    	for(int i = s.length() - 1; i >= 0; i--) {
    		hash = ((hash * x + s.charAt(i)) % p + p) % p ;
    	}
    	return hash;
    }
    
    static int[] precomputeHashes(String t, int length, int p, int x) {
    	int[] H = new int[t.length() - length + 1];
    	String s = t.substring(t.length() - length, t.length());
    	H[t.length() - length] = polyHash(s, p, x);
    	
    	int y = 1;
    	for(int i = 0; i < length; i++) {
    		y = (y * x % p + p) % p;
    	}
    	for(int i = t.length() - length - 1; i >= 0; i--) {
    		H[i] = ((x * H[i + 1] + t.charAt(i) - y * t.charAt(i + length)) % p + p) % p;
    	}
    	return H;
    }

    static class Data {
        String pattern;
        String text;
        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}

