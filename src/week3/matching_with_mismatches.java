package week3;

import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class matching_with_mismatches {
    public List<Integer> solve(int k, String text, String pattern) {
        ArrayList<Integer> pos = new ArrayList<>();
        for(int i = 0; i + pattern.length() - 1 < text.length(); i++) {
        	String subS = text.substring(i, i + pattern.length());
        	int count = numOfMismatches(k, subS, pattern);
        	if(count <= k) {
        		pos.add(i);
        	}
        }
        return pos;
    }
    
    public int numOfMismatches(int k, String subS, String p) {
    	//Haoyun: the general idea is to loop through all subS's of length p.length() in s
    	//For each subS, we try to find (at most) k mismatches comparing with p
    	//Use hash code to compare. If equal, then we believe no mismatch
    	//If unequal, repeat same procedure for left part and right part of subS
    	if(subS.hashCode() == p.hashCode()) return 0;   
    	
    	int count = 0;
    	if(subS.length() == 1) {
    		return subS.equals(p) ? 0 : 1;
    	}
    	else {
    		
    		String leftSub = subS.substring(0, subS.length() / 2);
    		String leftP = p.substring(0, p.length() / 2);
    		count += numOfMismatches(k, leftSub, leftP);
    		if(count > k) return count;
    	
    		String rightSub = subS.substring(subS.length() / 2, subS.length());
    		String rightP = p.substring(p.length() / 2, p.length());
    		count += numOfMismatches(k, rightSub, rightP);
    		if(count > k) return count;
    	}
    	return count;
    }

    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        in.lines().forEach(line -> {
            StringTokenizer tok = new StringTokenizer(line);
            int k = Integer.valueOf(tok.nextToken());
            String s = tok.nextToken();
            String t = tok.nextToken();
            List<Integer> ans = solve(k, s, t);
            out.format("%d ", ans.size());
            out.println(ans.stream()
                    .map(n -> String.valueOf(n))
                    .collect(Collectors.joining(" "))
            );
            out.flush();
        });
        out.close();
    }
    
    

    static public void main(String[] args) {
        new matching_with_mismatches().run();
    }
}
