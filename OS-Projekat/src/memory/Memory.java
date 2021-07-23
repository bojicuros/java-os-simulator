package memory;

import java.util.ArrayList;

public class Memory {

	private static int SIZE;
	private static ArrayList<MemoryPartition> partitions = new ArrayList<>();
	private static int occupied;

	public static void init(int size) {
		SIZE = size;
	}
}
