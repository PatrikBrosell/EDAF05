import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class StableMarriage {
	private String[] names;
	private int totPersons;
	private int[] couples;
	private ArrayList<Integer>[] menPreferences;
	private int[][] womenPreferences;
	private LinkedList<Integer> men;

	@SuppressWarnings("unchecked")
	public void readFile(String filePath) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String string;
		while (scanner.hasNext()) {
			string = scanner.nextLine();
			while (string.startsWith("#")) {
				string = scanner.nextLine();
			}
			if (string.startsWith("n")) {
				String total = string.substring(2, string.length());
				totPersons = 2 * Integer.parseInt(total);
				couples = new int[totPersons];
				womenPreferences = new int[totPersons][totPersons];
				menPreferences = (ArrayList<Integer>[]) new ArrayList[totPersons];
				names = new String[totPersons];
				men = new LinkedList<Integer>();
				for (int i = 0; i < totPersons; i+=2) {
					men.add(i);
				}
				for (int i = 0; i < totPersons; i++) {
					menPreferences[i] = new ArrayList<Integer>();
					couples[i] = -1;
				}
			} else if (string.length() != 0 && !string.contains(":")) {
				names[Integer.parseInt(string.split(" ")[0]) - 1] = string.split(" ")[1];
			}
			else if (string.length() != 0 && string.contains(":")) {
				int arrayIndex = Integer.parseInt(string.split(" ")[0].replace(":", ""));
				string = string.substring(string.indexOf(":") + 2);
				String[] preference = string.split(" ");
				for (int i = 0; i < preference.length; i++) {
					menPreferences[arrayIndex - 1].add(Integer.parseInt(preference[i]) - 1);
					womenPreferences[arrayIndex - 1][Integer.parseInt(preference[i]) - 1] = i;
				}
			}
		}
	}

	public void gs() {
		while (!men.isEmpty()) {
			int man = men.poll();
			int woman = menPreferences[man].remove(0);
			if (couples[woman] == -1) {
				couples[woman] = man;
				couples[man] = woman;
			} else if (womenPreferences[woman][man] < womenPreferences[woman][couples[woman]]) {
				int oldBoyfriend = couples[woman];
				men.push(oldBoyfriend);
				couples[oldBoyfriend] = -1;
				couples[woman] = man;
				couples[man] = woman;
			} else {
				men.addLast(man);
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < couples.length; i = i + 2) {
			sb.append(names[i] + " -- " + names[(couples[i])]);
			if (i != couples.length - 2) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		StableMarriage sb = new StableMarriage();
		sb.readFile(args[0]);
		sb.gs();
		System.out.println(sb.toString());
	}
}
