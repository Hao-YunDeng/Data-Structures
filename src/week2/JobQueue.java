package week2;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            int bestWorker = 0;
            for (int j = 0; j < numWorkers; ++j) {
                if (nextFreeTime[j] < nextFreeTime[bestWorker])
                    bestWorker = j;
            }
            assignedWorker[i] = bestWorker;
            startTime[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
        }
    }
    
    //Haoyun: The idea is that, make a priority queue for the threads (workers)
    //each worker is a long[2] with long[0] the ID and long[1] the ready time
    //Continually remove a worker for using it, 
    //and add back with an updated ready time after the job done 
    //comparator for the queue: sort with time; if equal, use ID
    
    //Now, just loop through the job array and for each job pick up (remove) a worker
    //and note down its time
    //and then update, put back
    
    static class workerComparator implements Comparator<long[]> {
    	@Override
    	public int compare(long[] worker1, long[] worker2) {
    		if(worker1[1] != worker2[1]) {
    			return (int) (worker1[1] - worker2[1]);
    		}
    		else {
    			return (int) (worker1[0] - worker2[0]);
    		}
    		
    	}
    }
    
    private void assignJobsFast() {
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        
        //Haoyun: Java has its own priority queue. Default is min-heap
        //PriorityQueue(int initialCapacity, Comparator<E> comparator): 
        //Creates a PriorityQueue with the specified initial capacity
        //that orders its elements according to the specified comparator.
        
        PriorityQueue<long[]> pQueue = new PriorityQueue<>(numWorkers, new workerComparator());
        
        for(int i = 0; i < numWorkers; i++) {
        	long[] worker = {i, 0};
        	pQueue.add(worker);
        }
        
        for(int i = 0; i < jobs.length; i++) {
        	long[] worker = pQueue.poll();
        	assignedWorker[i] = (int)worker[0];
        	startTime[i] = worker[1];
        	
        	worker[1] = worker[1] + jobs[i];
        	pQueue.add(worker);
        }
        
    }
    
    

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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
