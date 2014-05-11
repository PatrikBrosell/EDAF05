import java.util.*;
import java.io.*;
import java.lang.*;

public class Alignment {
	int[][] matrix;
	public int delta;


	public Alignment(String fix) throws Exception {
		int[][] A;
		readMatrix("/usr/local/cs/edaf05/lab5/BLOSUM62.txt");
		ArrayList<String> list = readInput("/usr/local/cs/edaf05/lab5/HbB_FASTAs.in");

		if(fix.equals("fix")) {
//			list.set(10, "Sea-Cucumber XGGTLAIQAQGDLTLAQKKIVRKTWHQLMRNKTSFVTDVFIRIFAYDPSAQNKFPQMAGMSASQLRSSRQMQAHAIRVSSIMSEYVEELDSDILPELLATLARTHDLNKVGADHYNLFAKVL"/*MEALQAELGSDFNEKTRD"*/);

			list.set(10,"Sea-Cucumber XGGTLAIQAQGDLTLAQKKIVRKTWHQLMRNKTSFVTDVFIRIFAYDPSAQNKFPQMAGMSASQLRSSRQMQAHAIRVSSIMSEYVEELDSDILPELLATLARTHDLNKVGADHYNLFAKVLMEALQAELGSDFNEKTRDAWAKAFSVVQAVLLVKHG");


		}

		for(int i = 0; i < list.size(); i++) {
			for(int j = i+1; j < list.size(); j++) {
				String first = list.get(i);
				String second = list.get(j);
//				System.out.println(first);
//				System.out.println(second);
				A = alignment(first.split(" ")[1], second.split(" ")[1]);
				System.out.println(first.split(" ")[0] + "--" + second.split(" ")[0] + ": " + A[A.length-1][A[0].length-1]);
				traceback(A, first.split(" ")[1], second.split(" ")[1]);
			}
		}





/*		A = alignment("KQRK", "KQRIKAAKABK"); // -8 points
		traceback(A, "KQRK", "KQRIKAAKABK");

		A = alignment("KQRK", "KAK"); // 5 points
		traceback(A, "KQRK", "KAK");

		A = alignment("KQRIKAAKABK", "KAK");//  -18 points
		traceback(A, "KQRIKAAKABK", "KAK");
*/
	}

	public ArrayList<String> readInput(String filePath) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		Scanner in = new Scanner(new File(filePath));
		String line = in.nextLine();
		while(in.hasNext()) {
			sb = new StringBuilder();
			if(line.startsWith(">")) {
				sb.append(line.split("\\s+")[0].replaceAll(">", ""));
				sb.append(" ");
				line = in.nextLine();
				while(!line.startsWith(">")) {
					sb.append(line);
					if(!in.hasNext()) {
						break;
					}
					line = in.nextLine();
				}
			list.add(sb.toString());
			}
		}


		//sort stuff

		String[] strings = new String[13];
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).startsWith("Human ")) {
				strings[0] = list.get(i);
			}
			else if(list.get(i).startsWith("Spider ")) {
				strings[1] = list.get(i);
			}
			else if(list.get(i).startsWith("Cow ")) {
				strings[2] = list.get(i);
			}
			else if(list.get(i).startsWith("Horse ")) {
				strings[3] = list.get(i);
			}
			else if(list.get(i).startsWith("Trout ")) {
				strings[4] = list.get(i);
			}
			else if(list.get(i).startsWith("Pig ")) {
				strings[5] = list.get(i);
			}
			else if(list.get(i).startsWith("Human-sickle ")) {
				strings[6] = list.get(i);
			}
			else if(list.get(i).startsWith("Deer ")) {
				strings[7] = list.get(i);
			}
			else if(list.get(i).startsWith("Lamprey ")) {
				strings[8] = list.get(i);
			}
			else if(list.get(i).startsWith("Rockcod ")) {
				strings[9] = list.get(i);
			}
			else if(list.get(i).startsWith("Sea-Cucumber ")) {
				strings[10] = list.get(i);
			}
			else if(list.get(i).startsWith("Gull")) {
				strings[11] = list.get(i);
			}
			else if(list.get(i).startsWith("Gorilla")) {
				strings[12] = list.get(i);
			}
		}
		list = new ArrayList<String>(Arrays.asList(strings));
//		System.out.println(list.toString());





		return list;
	}

	public int[][] readMatrix(String filePath) throws Exception {
		Scanner in = new Scanner(new File(filePath));
		String line = in.nextLine();
		while(line.startsWith("#")) {
			line = in.nextLine();
		}
		line = line.replace("*","");
		line = line.trim();

		String[] strIndexes = line.split("\\s+");

		char[] charIndexes = new char[strIndexes.length];
		for(int i = 0; i < strIndexes.length; i++) {
			charIndexes[i] = strIndexes[i].charAt(0);
		}

		matrix = new int[26][26];

		while(in.hasNextLine() && !line.startsWith("#")) {
			line = in.nextLine();
			line = line.trim();
			if(line.startsWith("*")) {
				break;
			}
			String[] splitLine = line.split("\\s+");

			for(int i = 0; i < charIndexes.length; i++) {
				int temp = Integer.parseInt(splitLine[i+1]);
				matrix[charIndexes[i]-65][splitLine[0].charAt(0)-65] = temp;
			}
		}
		return matrix;


	}

	public int[][] alignment(String x, String y) {
		delta = -4;
		int[][] A = new int[x.length()+1][y.length()+1];

		for(int i = 0; i <= x.length(); i++) {
			A[i][0] = delta*i;
		}
		for(int j = 0; j <= y.length(); j++) {
			A[0][j] = delta*j;
		}


		for(int i = 1; i <= x.length(); i++) {
			for(int j = 1; j <= y.length(); j++) {
				int a = matrix[x.charAt(i-1)-65][y.charAt(j-1)-65] + A[i -1][j -1];
				int b = delta + A[i-1][j];
				int c = delta + A[i][j-1];

				A[i][j] = Math.max(Math.max(a,b),c);
			}
		}
//	System.out.println(A[x.length()][y.length()]);
	return A;
	}

	public void traceback(int[][] A, String x, String y) {
		StringBuilder stringA = new StringBuilder();
		StringBuilder stringB = new StringBuilder();
		int m = x.length();
		int n = y.length();
		while(m > 0 && n > 0) {
			int score = A[m][n];
			int scoreLeft = A[m-1][n];
			int scoreDiagonal = A[m-1][n-1];
			int scoreUp = A[m][n-1];
			if(score == scoreDiagonal + matrix[x.charAt(m-1)-65][y.charAt(n-1)-65]) {
				// match or missmatch
				stringA = stringA.insert(0, x.charAt(m-1));
//				System.out.println(stringA);
				stringB = stringB.insert(0, y.charAt(n-1));
//				System.out.println(stringB);
				n--;
				m--;
			} else if (score == scoreLeft + delta ) {
				// gap in sequence x
//				System.out.println("gap y");

				stringA = stringA.insert(0, x.charAt(m-1));
				stringB = stringB.insert(0, '-');
				m--;
			} else if (score == scoreUp + delta) {
				// gap in sequence y

//				System.out.println("gap x");
				stringA = stringA.insert(0, '-');
				stringB = stringB.insert(0, y.charAt(n-1));
				n--;
			}
		}

		while(m > 0) {
			stringA = stringA.insert(0, x.charAt(m-1));
			stringB = stringB.insert(0, '-');
			m--;
		}

		while(n > 0) {
			stringA = stringA.insert(0, '-');
			stringB = stringB.insert(0, y.charAt(n-1));
			n--;
		}

		System.out.println(stringA.toString());
		System.out.println(stringB.toString());






	}


	public static void main(String[] args) throws Exception {
		new Alignment(args[0]);
	}
}
