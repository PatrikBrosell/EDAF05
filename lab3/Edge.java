

public class Edge implements Comparable {
	private String from;
	private String to;
	private int weight;

	public Edge(String from, String to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public int weight() {
		return weight;
	}

	public String to() {
		return to;
	}

	public String from() {
		return from;
	}
	
	public String toString() {
		return from + "--" + to + "[" + weight + "]";
	}

	@Override
	public int compareTo(Object o) {
		return  weight - ((Edge) o).weight();
	}
}
