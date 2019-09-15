import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Framework {
	// get preference list from user input
	public static HashMap<Integer, List<Integer>> getPreferenceList(int n, BufferedReader in) throws IOException {
		HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < n; i++) {
			map.put(i, new ArrayList<Integer>());
			String[] line = in.readLine().split("\\s+");
			for (int j = 0; j < n; j++) {
				try {
					int m = Integer.parseInt(line[j]);
					if (m < 0 || m >= n) {
						System.out.println("Invalid input.");
						System.exit(0);
					}
					map.get(i).add(m);
				} catch (Exception e) {
					System.out.println("Invalid input.");
					System.exit(0);
				}
			}
		}
		return map;
	}

	public static void galeShapleyAlg(HashMap<Integer, List<Integer>> ManPref,
			HashMap<Integer, List<Integer>> WomanPref) {
		// maintain the set of free men as a linked list
		LinkedList<Integer> FreeMan = new LinkedList<Integer>();
		int n = ManPref.size();
		for (int i = 0; i < n; i++) {
			FreeMan.add(i);
		}
		
		
		while (!FreeMan.isEmpty()) {
			int m = FreeMan.getFirst();

			FreeMan.removeFirst();
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String firstLine = in.readLine();
		int n = Integer.parseInt(firstLine);
		// men's preference list
		HashMap<Integer, List<Integer>> ManPref = getPreferenceList(n, in);
		// women's preference list
		HashMap<Integer, List<Integer>> WomanPref = getPreferenceList(n, in);

	}
}
