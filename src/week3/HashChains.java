package week3;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    //Haoyun: I commented out the following line
    //private List<String> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;
    
    //TODO: A is an array of lists
    private LinkedList<String>[] A;
    //Haoyun: you can NOT now have = new LinkedList[bucketCount];
    //have to wait till bucket count is read in
    //Haoyun: you can NOT use for loop out of any method!!
    
//    for(int i = 0; i < A.length; i++) {
//    	A[i] = new LinkedList<String>();
//    }
    

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;//Here used ASCII code
        return (int)hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
         out.flush();
    }

    private void processQuery(Query query) {
    	int hS = 0;
    	if(query.s != null) {
    		hS = hashFunc(query.s);
    		//System.out.println(hS);
    	}
        switch (query.type) {
            case "add":
                if (!A[hS].contains(query.s))
                    A[hS].addFirst(query.s); //Haoyun: .add(0, query.s) may also works
                break;
            case "del":
                if (A[hS].contains(query.s))
                    A[hS].remove(query.s); 
                //Haoyun: remove can take parameters
                //for both int for index, and an object 
                //like (Integer)10 for object
                break;
            case "find":
                writeSearchResult(A[hS].contains(query.s));
                break;
            case "check":
            	Iterator<String> iterator = A[query.ind].iterator();
            	while(iterator.hasNext()) {
            		out.print(iterator.next() + " ");
            	}
//                for (String cur : A[hS])
                    //if (hashFunc(cur) == query.ind)
                    //    out.print(cur + " ");
                	//out.print(cur + " ");
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                 out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        //elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        
        A= new LinkedList[bucketCount];
        for(int i = 0; i < A.length; i++) {
      	  A[i] = new LinkedList<String>();
        }
        
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
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
