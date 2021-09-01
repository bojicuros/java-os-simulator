package memory;

import memory.SecondaryMemory.Pointer;

public class File {
	private String name;
	private int size;  
	private Pointer start;
	private int length; 
	private static byte[] contentFile = new byte[0];

	public File(String name, int size) {
		this.size = size;
		this.name = name;
	}

	public static byte[] part(int index) {
		byte[] part = new byte[Block.getSize()]; 
		int counter = 0;
		for (int i = index * Block.getSize(); i < contentFile.length; i++) {
			part[counter] = contentFile[i];
			if (counter == Block.getSize() - 1)
				break;
			counter++;
		}
		while (counter < Block.getSize() - 1) {
			byte[] b = " ".getBytes();
			part[counter] = b[0];
			counter++;
		}
		return part;
	}

	public Pointer getStart() {
		return start;
	}

	public void setStart(Pointer start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public byte[] getContentFile() {
		return contentFile;
	}

	public void setContentFile(byte[] contentFile) {
		File.contentFile = contentFile;
	}
}
