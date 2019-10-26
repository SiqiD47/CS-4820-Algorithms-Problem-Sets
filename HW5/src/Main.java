import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.*;
import java.util.*;

class Main {
	int n; // number of candidates
	int k; // number of recruiters

	// provided data structures (already filled in)
	ArrayList<ArrayList<Integer>> neighbors;
	int[] recruiterCapacities;
	int[] preliminaryAssignment;

	// provided data structures (you need to fill these in)
	boolean existsValidAssignment;
	int[] validAssignment;
	int[] bottleneckRecruiters;

	// reading the input
	void input() {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(System.in));

			String text = reader.readLine();
			String[] parts = text.split(" ");

			n = Integer.parseInt(parts[0]);
			k = Integer.parseInt(parts[1]);
			neighbors = new ArrayList<ArrayList<Integer>>(n + k);
			recruiterCapacities = new int[n + k];
			preliminaryAssignment = new int[n];

			for (int j = 0; j < n + k; j++) {
				neighbors.add(new ArrayList<Integer>());
			}
			for (int i = 0; i < n; i++) {
				text = reader.readLine();
				parts = text.split(" ");
				int numRecruiters = Integer.parseInt(parts[0]);
				for (int j = 0; j < numRecruiters; j++) {
					int recruiter = Integer.parseInt(parts[j + 1]);
					neighbors.get(i).add(recruiter);
					neighbors.get(recruiter).add(i);
				}
			}

			text = reader.readLine();
			parts = text.split(" ");
			for (int j = 0; j < k; j++) {
				recruiterCapacities[n + j] = Integer.parseInt(parts[j]);
			}

			text = reader.readLine();
			parts = text.split(" ");
			for (int i = 0; i < n - 1; i++) {
				preliminaryAssignment[i] = Integer.parseInt(parts[i]);
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// writing the output
	void output() {
		try {
			PrintWriter writer = new PrintWriter(System.out);

			if (existsValidAssignment) {
				writer.println("Yes");
				for (int i = 0; i < n - 1; i++) {
					writer.print(validAssignment[i] + " ");
				}
				writer.println(validAssignment[n - 1]);
			} else {
				writer.println("No");
				for (int j = 0; j < n + k - 1; j++) {
					writer.print(bottleneckRecruiters[j] + " ");
				}
				writer.println(bottleneckRecruiters[n + k - 1]);
			}

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Main() {
		input();

		// Fill these in as instructed in the problem statement.
		existsValidAssignment = false;
		validAssignment = new int[n];
		bottleneckRecruiters = new int[n + k];

		// YOUR CODE STARTS HERE
		int[] current = new int[n + k]; // record current flow into each recruiter node
		for (int i = 0; i < n - 1; i++) {
			current[preliminaryAssignment[i]]++;
		}

		ArrayList<Integer> recruiters = neighbors.get(n - 1);
		for (int i : recruiters) {
			if (current[i] + 1 <= recruiterCapacities[i]) {
				existsValidAssignment = true;
				for (int j = 0; j < n - 1; j++)
					validAssignment[j] = preliminaryAssignment[j];
				validAssignment[n - 1] = i;
				break;
			}
		}
		// either need to re-arrange or need to increase recuriter's capacity
		

		// YOUR CODE ENDS HERE

		output();
	}

	// Strings in Args are the name of the input file followed by
	// the name of the output file.
	public static void main(String[] Args) {
		new Main();
	}
}
