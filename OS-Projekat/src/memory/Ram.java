package memory;

public class Ram {

	private static final int CAPACITY = 128;
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

	public static boolean removeSequence(int start, int size) {
		if (size + start > CAPACITY)
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
		if (start + data.length > CAPACITY)
			return false;
		for (int i = start; i < data.length + start; i++) {
			if (!isOcupied(i)) {
				setAt(i, data[i - start]);
			} else
				return false;
		}
		return true;
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

	public static void printRAM() {
		if (occupied == 0)
			System.out.println("RAM memory isnt occupied");
		else {
			System.out.print("RAM memory:");
			for (int i = CAPACITY - 1; i >= 0; i--) {
				if (isOcupied(i)) {
					for (int j = 0; j <= i; j++) {
						if (j % 10 == 0)
							System.out.println();
						System.out.print(ram[j] + "\t");
					}
					System.out.println();
					break;
				}
			}
		}
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