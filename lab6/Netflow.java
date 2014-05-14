import java.io.*;
import java.util.*;

public class Netflow {
	ArrayList<String> nodes;
	int[][] capacity;

	public Netflow() throws Exception {
		read("/usr/local/cs/edaf05/lab6/rail.txt");
	}

	public void read(String filePath) throws Exception {
		Scanner in = new Scanner(new File(filePath));
		int n = in.nextInt(); // nodes
		in.nextLine();

		nodes = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			nodes.add(in.nextLine());
		}
		int m = in.nextInt(); // arcs

		capacity = new int[n][n];



		LinkedList<LinkedList<Integer>> neighbours = new LinkedList<LinkedList<Integer>>();
		for(int i = 0; i < n; i++) {
			neighbours.add(new LinkedList<Integer>());
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			int c = in.nextInt();
			in.nextLine();
			if (c == -1) { c = Integer.MAX_VALUE; }
			neighbours.get(a).add(b);
			neighbours.get(b).add(a);
			capacity[a][b] = c;
			capacity[a][b] = c;
		}
	}

	public int prims() {
		int weight = 0;
		HashSet<String> visited = new HashSet<String>();
		int source = 0;
	return 0;
	}




	public static void main(String[] args) throws Exception {
		new Netflow();
	}





}
