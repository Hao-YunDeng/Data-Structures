package week3;

import java.util.*;
import java.io.*;

public class common_substring {   
    public class Answer {
        int i, j, len;
        Answer(int i, int j, int len) {
            this.i = i;
            this.j = j;
            this.len = len;
        }
    }

    public Answer solve(String s, String t) {
        Answer ans = new Answer(0, 0, 0);
        for (int i = 0; i < s.length(); i++)
            for (int j = 0; j < t.length(); j++)
                for (int len = 0; i + len <= s.length() && j + len <= t.length(); len++)
                    if (len > ans.len && s.substring(i, i + len).equals(t.substring(j, j + len)))
                        ans = new Answer(i, j, len);
        return ans;
    }
    
    public Answer solveFast(String s, String t) {
    	//Haoyun: in this problem we use hashmap method because it's fast to check .containsKey
    	// we just use Java's hashcode
    	Answer ans = new Answer(0, 0, 0);
    	//we use binary search to find the longest common length
    	int low = 0, high = Math.min(s.length(), t.length());
    	//This is the possible range of common length k
    	while(low <= high) {
    		int k = (low + high) / 2;
    		//we now check if there is a common substring of length k
    		//First, precompute hash values of all substrings of length k for s
    		HashMap<Long, Integer> hashSubS = new HashMap<>();
    		for(int i = 0; i + k - 1 < s.length(); i++) {
    			long hash = s.substring(i, i + k).hashCode();
    			hashSubS.put(hash,i);    			
    		}
    		
    		//Then we loop through t's substrings of length k, 
    		//break if same hashCode in hashSubS
    		//Need an extra flag for binary search
    		boolean found = false;
    		for(int i = 0; i + k - 1 < t.length(); i++) {
    			long hash = t.substring(i, i + k).hashCode();
    			if(hashSubS.containsKey(hash)) {
    				ans.i = hashSubS.get(hash);
    				ans.j = i;
    				ans.len = k;
    				found = true;
    				break;
    			}
    		}
    		if(found) low = k + 1;
    		else high = k - 1;
    	}
    	return ans;
    }
    


    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        in.lines().forEach(line -> {
            StringTokenizer tok = new StringTokenizer(line);
            String s = tok.nextToken();
            String t = tok.nextToken();
            Answer ans = solveFast(s, t);
            out.format("%d %d %d\n", ans.i, ans.j, ans.len);
            out.flush();
        });
        out.close();
    }

    static public void main(String[] args) {
        new common_substring().run();
    }
}
