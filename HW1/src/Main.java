import java.util.*;
import java.io.*;

class Main {
	// get preference list from the user input
	public static int[][] prefList(int n, BufferedReader in) throws IOException {
		int[][] pref = new int[n][n];
		for (int m = 0; m < n; m++) {
			String[] line = in.readLine().split("\\s+");
			for (int i = 0; i < n; i++) {
				int idx = Integer.parseInt(line[i]);
				pref[m][i] = idx;
			}
		}
		return pref;
	}

	// compute r1
	public static int computeR1(int[][] ManPref, LinkedList<Integer> FreeMan, int[] Next, int[] Current,
			int[][] Ranking) {
		int[] MatchingForMan = new int[Current.length];
		while (FreeMan.size() > 0) {
			int m = FreeMan.removeFirst();
			int w = ManPref[m][Next[m]];
			Next[m] += 1;
			if (Current[w] == -1) { // woman not engaged
				MatchingForMan[m] = w;
				Current[w] = m;
			} else {
				int m_ = Current[w];
				if (Ranking[w][m] < Ranking[w][m_]) { // woman prefers m to m_
					MatchingForMan[m] = w;
					Current[w] = m;
					FreeMan.addFirst(m_);
				} else { // woman prefers m_ to m, m is still a free man
					FreeMan.addFirst(m);
				}
			}
		}
		return Current[0];
	}

	public static int computeR2(int r1, int[][] ManPref, int[][] WomanPref, LinkedList<Integer> FreeMan, int[] Next,
			int[] Current, int[][] Ranking) {
		int[] MatchingForMan = new int[Current.length];
		Next[0] = 1; // m0 will be rejected by the first woman in his list
		while (FreeMan.size() > 0) {
			int m = FreeMan.removeFirst();
			if (Next[m] >= Current.length) {
				continue;
			} else {
				int w = ManPref[m][Next[m]];
				Next[m] += 1;
				if (Current[w] == -1) {
					MatchingForMan[m] = w;
					Current[w] = m;
				} else {
					int m_ = Current[w];
					if (Ranking[w][m] < Ranking[w][m_]) {
						MatchingForMan[m] = w;
						Current[w] = m;
						FreeMan.addFirst(m_);
					} else {
						FreeMan.addFirst(m);
					}
				}
			}
		}
		int W = ManPref[0][0]; // woman W that m0 prefers
		int M = WomanPref[W][0]; // man M that W prefers
		int WW = ManPref[M][0]; // woman WW that M prefers
		if (Current[0] == -1)
			return 1;
		else if (W == WW && Current[W] != M && MatchingForMan[M] != W)
			return 2;
		else
			return 3;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String firstLine = in.readLine();
		int n = Integer.parseInt(firstLine);

		int[][] ManPref = prefList(n, in); // men's preference list
		int[][] WomanPref = prefList(n, in); // women's preference list

		LinkedList<Integer> FreeMan = new LinkedList<Integer>(); // set of free men
		int[] Next = new int[n]; // for each man m the position of next woman he'll propose to
		int[] Current = new int[n]; // the woman w’s current partner m
		for (int i = 0; i < n; i++) {
			FreeMan.add(i);
			Next[i] = 0;
			Current[i] = -1;
		}
		// rank of man m in sorted order of woman w’s preferences; smaller ranking means
		// more preferable
		int[][] Ranking = new int[n][n];
		for (int w = 0; w < n; w++) {
			for (int i = 0; i < n; i++) {
				int man = WomanPref[w][i];
				Ranking[w][man] = i;
			}
		}
		int r1 = computeR1(ManPref.clone(), (LinkedList) FreeMan.clone(), Next.clone(), Current.clone(),
				Ranking.clone());
		int r2 = computeR2(r1, ManPref.clone(), WomanPref.clone(), (LinkedList) FreeMan.clone(), Next.clone(),
				Current.clone(), Ranking.clone());
		BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
		log.write(r1 + "\n" + r2 + '\n');
		log.flush();
	}
}
