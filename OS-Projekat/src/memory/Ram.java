package memory;

public class Ram {
	
	private static final int CAPACITY = 1024;
	private static int[] ram = new int[CAPACITY];
	private static int occupied = 0;

	public static void initialize() {
		for (int i = 0; i < CAPACITY; i++) {
			ram[i] = -1;
		}
	}

	public static boolean setAt(int index, int value) {
		if (isOcupied(index))
			return false;
		ram[index] = value;
		occupied++;
		return true;
	}

	public static boolean setAt(int index) {
		return setAt(index, 1);
	}

	public static boolean removeSequence(int start, int size) {
		if (size + start >= CAPACITY)
			return false;
		for (int i = start; i < size + start; i++) {
			if (isOcupied(i)) {
				ram[i] = -1;
				occupied--;
			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean setSequence(int start, int[] data) {
		if (start + data.length >= CAPACITY)
			return false;
		for (int i = start; i < data.length + start; i++) {
			if (!isOcupied(i)) {
				setAt(i, data[i - start]);
			} else
				return false;
		}
		return true;
	}

	public static boolean setSequence(int start, int size) {
		int[] data = new int[size];
		for (int i = 0; i < data.length; i++) {
			data[i] = 1;
		}
		return setSequence(start, data);
	}

	public static int getAt(int i) {
		return ram[i];
	}

	public static boolean removeAt(int i) {
		if (isOcupied(i)) {
			ram[i] = -1;
			occupied--;
			return true;
		}
		return false;
	}

	public static boolean isOcupied(int i) {
		return ram[i] != -1;
	}

	public static int getOccupiedSpace() {
		return occupied;
	}

	public static int getFreeSpace() {
		return CAPACITY - occupied;
	}

	public static int getCapacity() {
		return CAPACITY;
	}

}