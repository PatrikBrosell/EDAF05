import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;

public class WordLadders {
	HashMap<String, HashSet<String>> graph;

	public WordLadders() {

	}

	public static void main(String[] args) throws Exception {
		WordLadders w = new WordLadders();
		w.readgraph(args[0]);
		ArrayList<Integer> distances = w.readInputfile(args[1]);
		for(int i = 0; i < distances.size(); i++) {
			System.out.println(distances.get(i));
		}
	}

	public void readgraph(String filePath) throws Exception {
		graph = new HashMap<String, HashSet<String>>();
		BufferedReader scanner = new BufferedReader(new FileReader(filePath));
		String word = scanner.readLine();
		
		while (word != null) {
			for (int i = 0; i < 5; i++) {
				String subWord;
				subWord = word.replaceFirst(word.substring(i, i + 1), "");
				char[] characters = subWord.toCharArray();
				Arrays.sort(characters);
				subWord = String.valueOf(characters);
				if (graph.get(subWord) != null) {
					graph.get(subWord).add(word);
				} 
					else {
					graph.put(subWord, new HashSet<String>());
					graph.get(subWord).add(word);
					}
				}
			word = scanner.readLine();
			}

		}
	
	public int BFS(String from, String to) {
		ArrayList<HashSet<String>> layers = new ArrayList<HashSet<String>>();
		HashSet<String> layer0 = new HashSet<String>();
		layer0.add(from);
		layers.add(layer0);
		HashSet<String> checked = new HashSet<String>();
		checked.add(from);
		int distance = 0;
		while (!layers.get(distance).isEmpty()) {
			if(layers.get(distance).contains(to)) {
				return distance;
			} else  {
				distance++;
				layers.add(new HashSet<String>());
				for (String str : layers.get(distance - 1)) {
					str = str.substring(1, 5);
					char[] characters = str.toCharArray();
					Arrays.sort(characters);
					str = String.valueOf(characters);
					for (String s : graph.get(str)) {
						if (s.equals(to)) {return distance; }
						if(!checked.contains(s)) {
							layers.get(distance).add(s);
						}
						checked.add(s);
					}
				}
			}
		}
		return -1;
	}
	
	public ArrayList<Integer> readInputfile(String filePath) throws FileNotFoundException {
		ArrayList<Integer> distances = new ArrayList<Integer>();
		Scanner scanner = new Scanner(new File(filePath));
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] words = line.split(" ");
			distances.add(BFS(words[0], words[1]));
		}
		return distances;
	}
}
