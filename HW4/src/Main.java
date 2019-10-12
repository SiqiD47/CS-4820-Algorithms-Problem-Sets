import java.io.*;

class Main {
	public static long SortCount(int[] nums, int l, int r) {
		long countA = 0, countB = 0, countmerge = 0;
		if (l < r) {
			int pivot = (l + r) / 2;
			countA = SortCount(nums, l, pivot);
			countB = SortCount(nums, pivot + 1, r);
			countmerge = MergeCount(nums, l, pivot, r);
		}
		return countA + countB + countmerge;
	}

	public static long MergeCount(int[] A, int l, int pivot, int r) {
		long count = 0;
		int n1 = pivot - l + 1, n2 = r - pivot;
		int[] L = new int[n1];
		int[] R = new int[n2];
		for (int i = 0; i < n1; i++)
			L[i] = A[l + i];
		for (int i = 0; i < n2; i++)
			R[i] = A[pivot + 1 + i];
		int i = 0, j = 0, k = l;
		while (i < n1 && j < n2) {
			if (L[i] > R[j] + 1) {
				count += n1 - i;
				j++;
			} else
				i++;
		}
		i = j = 0;
		while (i < n1 && j < n2) {
			if (L[i] <= R[j])
				A[k++] = L[i++];
			else {
				A[k++] = R[j++];
			}
		}
		while (i < n1)
			A[k++] = L[i++];
		while (j < n2)
			A[k++] = R[j++];
		return count;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String firstLine = in.readLine();
		int n = Integer.parseInt(firstLine); // length of list
		String[] line = in.readLine().split(" ");
		int[] nums = new int[n];
		for (int i = 0; i < n; i++) {
			nums[i] = Integer.parseInt(line[i]);
		}
		long ans;
		if (n <= 1)
			ans = 0;
		else
			ans = SortCount(nums, 0, nums.length - 1);
		BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
		log.write(ans + "\n");
		log.flush();
	}
}
