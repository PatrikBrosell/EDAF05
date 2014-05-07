import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.*;

public class SpammingUSA {
	private HashMap<String, LinkedList<Edge>> map;

	private SpammingUSA() {
		map = new HashMap<String, LinkedList<Edge>>();
	}

	public static void main(String[] args) throws Exception {
		SpammingUSA usa = new SpammingUSA();
		usa.read("/usr/local/cs/edaf05/lab3/USA-highway-miles.in");
		System.out.println(usa.prims());
	}

	private void read(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filePath));
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			if (!line.contains("--")) {
				line = line.replace("\"", "");
				line = line.trim();
				map.put(line, new LinkedList<Edge>());
			}

			if (line.contains("--")) {
				String from = line.split("--")[0];
				from = from.replace("\"", "");
				from = from.trim();
				String to = line.split("--")[1];
				to = to.split(" \\[")[0];
				to = to.replace("\"", "");
				to = to.trim();
				String weight = line.split("\\[")[1];
				weight = weight.replace("]", "");
				weight = weight.trim();
				int integerWeight = Integer.parseInt(weight);
				Edge edge1 = new Edge(from, to, integerWeight);
				LinkedList<Edge> temp = map.get(from);
				temp.add(edge1);
				Edge edge2 = new Edge(to, from, integerWeight);
				LinkedList<Edge> temp2 = map.get(to);
				temp2.add(edge2);
			}
		}
	}

	private int prims() {
		int weight = 0;
		HashSet<String> visited = new HashSet<String>();
		String firstCity = map.keySet().iterator().next();
		visited.add(firstCity);
		PriorityQueue<Edge> edgeQueue = new PriorityQueue<Edge>();
		edgeQueue.addAll(map.get(firstCity));
		while (!edgeQueue.isEmpty()) {
			Edge tempEdge = edgeQueue.poll();
			if (!visited.contains(tempEdge.to())) {
				weight = weight + tempEdge.weight();
				visited.add(tempEdge.to());
				edgeQueue.addAll(map.get(tempEdge.to()));
			}
		}
		return weight;
	}
}
