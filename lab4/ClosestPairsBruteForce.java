import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;

public class ClosestPairsBruteForce {
	private static ArrayList<Double> numberListA;
	private static ArrayList<Double> numberListB;
	private static String name;
	private static String comment;
	private static String type;
	private static String dimension;
	private static String edgeWeightType;
	private static String closestPair;
	private static double closestDistance;

	public ClosestPairsBruteForce() {
	}

	private static void read(String filePath) throws Exception {
		Scanner scan = new Scanner(new File(filePath));
		boolean readComments = true;
		while(scan.hasNext() && readComments) {
			String line = scan.nextLine();
			String info = "";
			if (line.split(":").length == 2) {
				info = line.split(":")[1];
			}
			info = info.trim();
			if (line.split(":")[0].contains("NAME")) {
				name = info;
			}
			if (line.split(":")[0].contains("COMMENT")) {
				comment = info;
			}
			if (line.split(":")[0].contains("TYPE")) {
				type = info;
			}
			if (line.split(":")[0].contains("DIMENSION")) {
				dimension = info;
			}
			if (line.split(":")[0].contains("EDGE_WEIGHT_TYPE")) {
				edgeWeightType = info;
			}
			if (line.split(":")[0].contains("NODE_COORD_SECTION")) {
				readComments = false;
			}
		}
		while(scan.hasNext() && !readComments) {
			String line = scan.nextLine();
			line = line.trim();
			if (line.contains("EOF")) {
				break;
			}
			String[] data = line.split("\\s+");
			Double numberOne = Double.parseDouble(data[1]);
			Double numberTwo = Double.parseDouble(data[2]);
			numberListA.add(numberOne);
			numberListB.add(numberTwo);
		}
	}

	public static void algorithm() {
		closestDistance = Double.MAX_VALUE;
		closestPair = "";
		for (int i = 0; i < numberListA.size(); i++) {
			for (int j = 0; j < numberListB.size(); j++) {
				double x1 = numberListA.get(i);
				double x2 = numberListA.get(j);
				double y1 = numberListB.get(i);
				double y2 = numberListB.get(j);
				double newDistance = Math.sqrt( (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) );
					if ((newDistance < closestDistance) && (j != i))  {
						closestDistance = newDistance;
					}
			}
		}
	}

	public static String format(double number) {
//		System.out.println("DETTA NUMMER IN: " + number);
		String inTal = Double.toString(number);
//		System.out.println("DETTA AR NUMRET SOM STRING: " + inTal);
		String helTalsDel = inTal.split("\\.")[0];
//		System.out.println("HELTALSDEL : " + helTalsDel);
		int numberOfDigits = helTalsDel.length();
		if (helTalsDel.equals("0")) {
			numberOfDigits = 0;
		}
		if (inTal.split("\\.")[0].startsWith("0") && inTal.split("\\.")[1].startsWith("0")) {
			numberOfDigits = -1;
		}
//		System.out.println("HELTALSDEL LANGDEN: " + numberOfDigits);
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(15 - numberOfDigits);
		df.setRoundingMode(RoundingMode.HALF_DOWN);
		String formattedNumber = df.format(number);
		formattedNumber = formattedNumber.replaceAll(",",".");
		return formattedNumber;
	}


	public static void main(String[] args) throws Exception {
		File folder = new File(args[0]);
		File[] listOfFiles = folder.listFiles();
		Arrays.sort(listOfFiles);
		for (File file : listOfFiles) {
			if (file.isFile() && !file.getName().contains("close")) {
				numberListA = new ArrayList<Double>();
				numberListB = new ArrayList<Double>();
				read(file.getPath());
				algorithm();
//				System.out.println();
				System.out.println("../data/" + file.getName() + ": " + numberListA.size() + " " + format(closestDistance));
//				System.out.println();
			}
		}
	}
}
