import java.util.*;
import java.io.*;
import java.lang.*;

public class Rails {
	private static int[][] cm;
	private static int[][] orginalet;
	private static int[][] flow;
	private static HashMap<Integer, LinkedList<Integer>> neighbors;

	public static void main(String[] args) throws Exception {
		parse("/usr/local/cs/edaf05/lab6/rail.txt");
		System.out.println(doStuff());

		ArrayList<Integer> cut = new ArrayList<Integer>();
		ArrayList<Integer> nocut = new ArrayList<Integer>();
		for (int i = 1; i < cm.length; i++) {
			if(BFS(i) != null) {
					cut.add(i);
			} else {
				nocut.add(i);
			}
		}
		
		for(Integer i : cut) {
			for(Integer j : nocut) {
				if(neighbors.get(i).contains(j)) {
					System.out.println(i + " " + j + " " + orginalet[i][j]);
				}
			}
		}
	}

	public static void parse(String filePath) throws Exception {

		Scanner in = new Scanner(new File(filePath));
		int n = in.nextInt();
		flow = new int[n][n];
		cm = new int[n][n];
		orginalet = new int[n][n];
		neighbors = new HashMap<Integer, LinkedList<Integer>>();
		for(int i = 0; i < n; i++) {
			neighbors.put(i, new LinkedList<Integer>());
		}


		for (int i = 0; i <= n + 1; i++) {
			in.nextLine();
		}

		while (in.hasNext()) {
			int i = in.nextInt();
			int j = in.nextInt();
			int c = in.nextInt();
			in.nextLine();
			orginalet[i][j] = c;
			orginalet[j][i] = c;
			cm[i][j] = c;
			cm[j][i] = c;
			neighbors.get(i).add(j);
			neighbors.get(j).add(i);
		}
	}

	public static int doStuff() {
		int f = 0;
		ArrayList<Integer> p = BFS(54);
		while (p != null) {
			f = f + augment(p);
			p = BFS(54);
		}
		return f;
	}

	public static int augment(ArrayList<Integer> p) {
		int b = Integer.MAX_VALUE;
		for (int i = 0; i < p.size() - 1; i++) {
			if (cm[p.get(i + 1)][p.get(i)] < b) {
				b = cm[p.get(i + 1)][p.get(i)];
			}
		}
		for (int i = 0; i < p.size() - 1; i++) {
			flow[p.get(i + 1)][p.get(i)] += b;

			cm[p.get(i)][p.get(i + 1)] += b;
			cm[p.get(i + 1)][p.get(i)] -= b;
		}
		return b;
	}

	public static ArrayList<Integer> BFS(int sink) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		HashSet<Integer> v = new HashSet<Integer>();
		LinkedList<Integer> q = new LinkedList<Integer>();
		int start = 0;
		v.add(start);
		q.add(start);
		while (!q.isEmpty()) {
			int t = q.poll();
			if (t == sink) {
				break;
			}
			for (Integer i : neighbors.get(t)) {
				if (cm[t][i] != 0) {
					if (!v.contains(i)) {
						map.put(i, t);
						v.add(i);
						q.add(i);
					}
				}
			}
		}
		int node = Integer.MAX_VALUE;
		try {
			node = map.get(sink);
		} catch (Exception e) {
			return null;
		}
		ArrayList<Integer> p = new ArrayList<Integer>();
		while (node != 0) {
			p.add(node);
			node = map.get(node);
		}
		return p;

	}
}
