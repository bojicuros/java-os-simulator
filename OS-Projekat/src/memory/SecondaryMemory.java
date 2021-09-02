package memory;

import java.util.ArrayList;

public class SecondaryMemory {
	private static int size;
	private static Block[] blocks;
	private static int numberOfBlocks;
	public static ArrayList<FileInMemory> files;

	public SecondaryMemory() {
		size = 2048;
		numberOfBlocks = size / Block.getSize();
		blocks = new Block[numberOfBlocks];
		for (int i = 0; i < numberOfBlocks; i++) {
			Block newBlock = new Block(i);
			blocks[i] = newBlock;
		}
		files = new ArrayList<>();
	}

	public void save(FileInMemory file) {
		int reminder = file.getSize() % Block.getSize();
		int numOfBlocks;
		if (reminder == 0)
			numOfBlocks = file.getSize() / Block.getSize();
		else
			numOfBlocks = (file.getSize() + 4 - reminder) / Block.getSize();
		if (numberOfFreeBlocks() >= numOfBlocks) {
			int counter = 0;
			Pointer first = null;
			for (int i = 0; i < numberOfBlocks; i++)
				if (!blocks[i].isOccupied()) {
					blocks[i].setOccupied(true);
					blocks[i].setContent(FileInMemory.part(counter));
					if (counter == 0) {
						first = new Pointer(blocks[i]);
						file.setStart(first);
						counter++;
					} else {
						Pointer secound = new Pointer(blocks[i]);
						first.next = secound;
						first = secound;
						counter++;
						if (counter == numOfBlocks) {
							file.setLength(counter);
							files.add(file);
							return;
						}
					}
				}
		} else
			System.out.println("Not enough space, cannot create file ");
	}

	public void deleteFile(FileInMemory file) {
		if (!files.contains(file))
			System.out.println("Your file is not in the secondary memory");
		else {
			Pointer pointer = file.getStart();
			file.setStart(null);
			pointer.block.setOccupied(false);
			pointer.block.setContent(null);
			while (pointer.next != null) {
				Pointer help = pointer;
				pointer = pointer.next;
				help.next = null;
				pointer.block.setOccupied(false);
			}
		}
		files.remove(file);
	}

	public String readFile(FileInMemory file) {
		String read = "";
		Pointer pointer = file.getStart();
		byte[] content = pointer.block.getContent();
		for (byte singleByte : content)
			read += (char) singleByte;
		while (pointer.next != null) {
			pointer = pointer.next;
			content = pointer.block.getContent();
			for (byte singleByte : content)
				read += (char) singleByte;
		}
		return read;
	}

	public void updateFile(FileInMemory file) {
		if (!files.contains(file))
			save(file);
		else {
			int reminder = file.getSize() % Block.getSize();
			int numOfBlocks;
			if (reminder == 0)
				numOfBlocks = file.getSize() / Block.getSize();
			else
				numOfBlocks = (file.getSize() + 4 - reminder) / Block.getSize();
			if (numOfBlocks > numberOfBlocksOccupiedByFile(file)) {
				if (numberOfFreeBlocks() < numOfBlocks - numberOfBlocksOccupiedByFile(file))
					System.out
							.println("There is not enough space to update file, old verison of file will be saved.\n");
				else {
					deleteFile(file);
					save(file);
				}
			} else {
				deleteFile(file);
				save(file);
			}
		}
	}

	private static int numberOfFreeBlocks() {
		int counter = 0;
		for (int i = 0; i < numberOfBlocks; i++)
			if (!blocks[i].isOccupied())
				counter++;
		return counter;
	}

	private static int numberOfBlocksOccupiedByFile(FileInMemory file) {
		int counter = 0;
		Pointer pointer = file.getStart();
		counter++;
		while (pointer.next != null) {
			pointer = pointer.next;
			counter++;
		}
		return counter;
	}

	public boolean contains(String fileName) {
		for (FileInMemory f : files)
			if (f.getName().equals(fileName))
				return true;
		return false;
	}

	public FileInMemory getFile(String fileName) {
		for (FileInMemory f : files)
			if (f.getName().equals(fileName))
				return f;
		return null;
	}

	public static int getSize() {
		return size;
	}

	public static Block[] getBlocks() {
		return blocks;
	}

	public ArrayList<FileInMemory> getFiles() {
		return files;
	}

	public static void printMemoryAllocationTable() {
		String line = "------------------------------------------------------------------------------------------";
		System.out.println("Memory Allocation Table:");
		System.out.println(line);
		System.out.println("Name of file\t\tStart block\tLength");
		System.out.println(line);
		for (FileInMemory file : files)
			System.out.println(file.getName() + (file.getName().length() < 16
					? ("\t\t" + file.getStart().block.getAdress() + "\t\t" + file.getLength())
					: ("\t" + file.getStart().block.getAdress() + "\t\t" + file.getLength())));
	}

	protected static class Pointer {
		private Block block;
		private Pointer next;

		private Pointer(Block block) {
			this.block = block;
			this.next = null;
		}

		public String toString() {
			String s = "";
			s += block;
			return s;
		}
	}
}