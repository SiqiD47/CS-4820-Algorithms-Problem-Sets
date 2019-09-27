import java.util.*;
import java.io.*;

class Main {
	public static Map<Integer, ArrayList<Integer>> dict = new TreeMap<>(); // maintain the list of elements in each set

	// implement the Kruskal's algorithm and compute s1, s2, s3
	public static int[] Kruskal(Map<Integer, ArrayList<Integer>> Edges, int[] ComponentName) {
		int[] ans = new int[3]; // s1, s2, s3
		UpdateDict(ComponentName);
		while (dict.size() > 3) {
			ArrayList<Integer> e = ((TreeMap<Integer, ArrayList<Integer>>) Edges).pollFirstEntry().getValue();
			int v0 = e.get(0), v1 = e.get(1);
			Union(v0, v1, ComponentName);
		}
		ans[0] = ((TreeMap<Integer, ArrayList<Integer>>) dict).pollFirstEntry().getValue().size();
		ans[1] = ((TreeMap<Integer, ArrayList<Integer>>) dict).pollFirstEntry().getValue().size();
		ans[2] = ((TreeMap<Integer, ArrayList<Integer>>) dict).pollFirstEntry().getValue().size();
		Arrays.sort(ans);
		return ans;
	}

	// maintain the list of elements in each set
	public static void UpdateDict(int[] ComponentName) {
		for (int m = 0; m < ComponentName.length; m++) {
			if (dict.containsKey(ComponentName[m]))
				dict.get(ComponentName[m]).add(m);
			else {
				ArrayList<Integer> val = new ArrayList<>();
				val.add(m);
				dict.put(ComponentName[m], val);
			}
		}
	}

	// retrieve the set name containing node i
	public static int Find(int i, int[] ComponentName) {
		return ComponentName[i];
	}

	public static void Union(int a, int b, int[] ComponentName) {
		int nameSetA = Find(a, ComponentName), nameSetB = Find(b, ComponentName);
		if (nameSetA != nameSetB) { // can union
			ArrayList<Integer> setA = dict.get(nameSetA); // get element in set A
			ArrayList<Integer> setB = dict.get(nameSetB); // get element in set B
			if (setA.size() >= setB.size()) {
				for (int s : setB) {
					ComponentName[s] = nameSetA; // inherit the name from the larger of the two
					dict.remove(nameSetB);
					dict.get(nameSetA).add(s);
				}
			} else {
				for (int s : setA) {
					ComponentName[s] = nameSetB; // inherit the name from the larger of the two
					dict.remove(nameSetA);
					dict.get(nameSetB).add(s);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String firstLine = in.readLine();
		int n = Integer.parseInt(firstLine);
		String secondLine = in.readLine();
		int m = Integer.parseInt(secondLine);
		Map<Integer, ArrayList<Integer>> Edges = new TreeMap<>();
		for (int i = 0; i < m; i++) {
			String[] line = in.readLine().split(" ");
			int cost = Integer.parseInt(line[2]); // w0
			ArrayList<Integer> nodes = new ArrayList<>();
			nodes.add(Integer.parseInt(line[0])); // v0
			nodes.add(Integer.parseInt(line[1])); // v1
			Edges.put(cost, nodes);
		}
		int[] ComponentName = new int[n]; // ComponentName[v] for a node v is the name of component containing node v
		for (int i = 0; i < n; i++)
			ComponentName[i] = i;

		int[] ans = Kruskal(Edges, ComponentName);
		BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
		log.write(ans[0] + "\n" + ans[1] + '\n' + ans[2] + '\n');
		log.flush();
	}
}
