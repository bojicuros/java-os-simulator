package FileSystem;

import java.time.LocalDateTime;

public class File {
	protected String name;
	protected String path;
	protected int size;
	protected LocalDateTime created;
	protected LocalDateTime modified;
	protected Folder parentFolder;
	protected byte[] contentFile = null;
	protected String contentString=null;

	public File()
	{
		this.name = "";
		path = "//";
		created = LocalDateTime.now();
		modified = LocalDateTime.now();
		parentFolder = null;
	}
	public File(String name, Folder parent, boolean isFolder) {
		this.name = name;
		if (parent == null) {
			path = name + (isFolder ? "//" : "");
		} else {
			path = parent.getPath() + name + (isFolder ? "//" : "");
		}
		created = LocalDateTime.now();
		modified = LocalDateTime.now();
		parentFolder = parent;
		parent.addFile(this);
		
	}

	public File(String name, Folder parent, boolean isFolder, byte[] content)// Object[] content
	{
		this(name, parent, isFolder);
		this.contentFile = content;
		this.size = contentFile.length;
	}
	public File(String name, Folder parent, String contentString)
	{
		this(name, parent, false);
		this.contentString=contentString;
	}
	public String getName() {
		return name;
	}

	public byte[] getContentFile() {
		return contentFile;
	}

	public String getPath() {
		return path;
	}

	public int getSize() {
		return size;
	}

	public Folder getParentFolder() {
		return parentFolder;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setName(String name) {
		this.name = name;
		modified = LocalDateTime.now();
	}

	public void setSize(int size) {
		this.size = size;
	}


	public boolean save() {
//		SecondaryMemory.saveFile(this);
		return false;
	}

	public void delete() {
//		SecondaryMemory.deleteFile(this);
		
	}

	public void printFileBlocks() {
//		String rez = "File "+name+", size: " + size + "\n";
//		rez += "Is located on blocks: ";
//		for (Block b : blocks)
//			rez += b.getAdress() + ", ";
//		System.out.println(rez);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof File))
			return false;
		File f = (File) o;
		return f.getName().compareTo(name) == 0;
	}
}