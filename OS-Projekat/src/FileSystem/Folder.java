package FileSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Folder extends File {
	private ArrayList<File> content;
	
	public Folder(String name, Folder parent) {
		super(name, parent, true); 
		content = new ArrayList<File>();
		addFolder();
	}
	public Folder()
	{
		super("", null, true);
		content = new ArrayList<File>();
	}
	public Folder addFolder() 
	{
		if (this.getParentFolder() != null) {
			ArrayList<File> list = parentFolder.getContent();
			list.add(this);
			parentFolder.modified = LocalDateTime.now();
			return this;
		}
		System.out.println("Folder must have parent.");// throw exception
		return null;
	}

	public File addFile(String name, Folder parent) {
		if (parent != null) {
			ArrayList<File> list = parent.getContent();
			File newFile = new File(name, parent, false);
			list.add(newFile);
			parent.modified = LocalDateTime.now();
			return newFile;
		}
		System.out.println("File must have parent.");// throw exception

		return null;
	}

	public File addFile(String name, Folder parent, byte[] content) {
		if (parent != null) {
			ArrayList<File> list = parent.getContent();
			File newFile = new File(name, parent, false, content);
			list.add(newFile);
			parent.modified = LocalDateTime.now();
			return newFile;
		}
		System.out.println("File must have parent.");// throw exception

		return null;
	}
	
	public ArrayList<File> getContent() {
		return content;
	}

}
