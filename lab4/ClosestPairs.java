import java.lang.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.math.RoundingMode;

public class ClosestPairs {
	static ArrayList<Point> points = new ArrayList<Point>();
	static ArrayList<Point> listx = new ArrayList<Point>();
	static ArrayList<Point> listy = new ArrayList<Point>();

	public static void main(String[] args) throws FileNotFoundException {
		File folder = new File(args[0]);
		File[] listOfFiles = folder.listFiles();
		Arrays.sort(listOfFiles);
		for (File file : listOfFiles) {
			if (!file.getName().contains("close")) {
				points = new ArrayList<Point>();
				parse(file.getAbsolutePath());
//				long start = System.nanoTime();
				double result = fixStuffz(points);
				System.out.print("../data/" + file.getName() + ": " + points.size() + " " + format(result));
				System.out.println(/*"Time: " + (System.nanoTime() - start)/1000000*/);
			}
		}
	}

	public static double fixStuffz(List<Point> px) {
		if (px.size() <= 3) {
			double dizt = Double.MAX_VALUE;
			for(int i = 0; i < px.size(); i++) {
				for(int j = i; j < px.size(); j++) {
					if(i != j) {
						double newDizt = px.get(i).distanceTo(px.get(j));
						if (newDizt < dizt) {
							dizt = newDizt;
						}
					}
				}
			}
		return dizt;
		}
		List<Point> qx = px.subList(0, px.size() / 2);
		List<Point> rx = px.subList(px.size() / 2, px.size());
		double l = qx.get(qx.size()-1).x;
		double d1 = fixStuffz(qx);
		double d2 = fixStuffz(rx);
		double delta = Math.min(d1, d2);
		ArrayList<Point> pxNew = new ArrayList<Point>();
		for (Point p : px) {
			if ((l - p.x) < delta || (l + p.x) < delta) {
				pxNew.add(p);
			}
		}
		Collections.sort(pxNew, new Point()); // sortera efter y-koordinat
		for (int i = 0; i < pxNew.size(); i++) {
			for (int j = i+1; j <= 16; j++) {
				if (!(j >= pxNew.size())) {
					double d = pxNew.get(i).distanceTo(pxNew.get(j));
					if (delta > d) {
						delta = d;
					}
				}
			}
		}
		return delta;
	}

	public static void parse(String filePath) throws FileNotFoundException {
		Scanner in = new Scanner(new File(filePath));
		String line = in.nextLine().trim();
		while (!line.startsWith("NODE_COORD_SECTION")) {
			line = in.nextLine();
		}
		while (in.hasNext()) {
			line = in.nextLine().trim();
			if (line.contains("EOF")) {
				break;
			}
			int id = Integer.parseInt(line.split("\\s+")[0].trim());
			double x = Double.parseDouble(line.split("\\s++")[1].trim());
			double y = Double.parseDouble(line.split("\\s++")[2].trim());
			Point p = new Point(x, y, id);
			points.add(p);
		}
		Collections.sort(points); // sortera efter x-koordinat
	}

	public static String format(double number) {
		String inTal = Double.toString(number);
		String helTalsDel = inTal.split("\\.")[0];
		int numberOfDigits = helTalsDel.length();
		if (helTalsDel.equals("0")) {
			numberOfDigits = 0;
		}
		if (inTal.split("\\.")[0].startsWith("0") && inTal.split("\\.")[1].startsWith("0")) {
			numberOfDigits = 1;
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(15 - numberOfDigits);
		df.setRoundingMode(RoundingMode.HALF_DOWN);
		String formattedNumber = df.format(number);
		formattedNumber = formattedNumber.replaceAll(",",".");
		return formattedNumber;
	}

	public static class Point implements Comparable<Point>, Comparator<Point> {
		double x, y;
		int id;

		public Point() {
		}

		public Point(double x, double y, int id) {
			this.x = x;
			this.y = y;
			this.id = id;
		}

		public double distanceTo(Point a) {
			return Math.sqrt((x - a.x) * (x - a.x) + (y - a.y) * (y - a.y));

		}

		@Override
		public int compareTo(Point a) {
			if (x - a.x > 0) {
				return 1;
			} else if (x - a.x < 0) {
				return -1;
			} else {
				return 0;
			}
		}

		@Override
		public int compare(Point a, Point b) {
			if (a.y - b.y > 0) {
				return 1;
			} else if (a.y - b.y < 0) {
				return -1;
			} else {
				return 0;
			}
		}

		public String toString() {
			return ("ID: " + id + " koord: " + x + ", " + y + ") ");
		}
	}
}
