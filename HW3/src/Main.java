import java.util.*;
import java.io.*;

class Main {
	public static int firstEligibleChoice(int dist, int[][] stations) {
		int lo = 0, hi = stations.length;
		while (lo < hi) {
			int mid = (lo + hi) / 2;
			if (stations[mid][0] >= dist)
				hi = mid;
			else
				lo = mid + 1;
		}
		return lo;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String[] line = in.readLine().split(" ");
		int n = Integer.parseInt(line[0]);
		int M = Integer.parseInt(line[1]);
		int m = Integer.parseInt(line[2]);
		int[][] stations = new int[n + 1][2];
		for (int i = 1; i < n + 1; i++) {
			String[] l = in.readLine().split(" ");
			stations[i][0] = Integer.parseInt(l[0]); // x_i
			stations[i][1] = Integer.parseInt(l[1]); // cost_i
		}

		int lastStation = firstEligibleChoice(M - m, stations);
		long cost = Integer.MAX_VALUE;
		int minIdx = 0;

		Map<Integer, ArrayList<Integer>> dict = new TreeMap<>();
		dict.put(0, new ArrayList<Integer>());
		ArrayList<Integer> tmp = new ArrayList<>();
		int minIdxJ = -1;
		// Opt[i]: minimum cost solution up to xi with a gas-station at location xi
		long Opt[] = new long[n + 1];
		Opt[0] = 0; // base case
		long minimum = Integer.MAX_VALUE;
		for (int i = 1; i <= n; i++) {
			int idx = firstEligibleChoice(stations[i][0] - m, stations);
			if (idx <= minIdxJ) {
				if (Opt[i - 1] < minimum) {
					minimum = Opt[i - 1];
					minIdxJ = i - 1;
				}
			} else {
				minimum = Integer.MAX_VALUE;
				for (int j = idx; j < i; j++) {
					if (Opt[j] < minimum) {
						minimum = Opt[j];
						minIdxJ = j;
					}
				}
			}
			tmp = (ArrayList<Integer>) (dict.get(stations[minIdxJ][0])).clone();
			tmp.add(stations[i][0]);
			dict.put(stations[i][0], tmp);
			Opt[i] = minimum + stations[i][1];
			if (i >= lastStation) {
				if (Opt[i] < cost) {
					cost = Opt[i];
					minIdx = i;
				}
			}
		}
		BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
		String out = cost + "\n";
		for (int i : dict.get(stations[minIdx][0]))
			out += i + " ";
		out = out.substring(0, out.length() - 1);
		out += "\n";
		log.write(out);
		log.flush();
	}
}
