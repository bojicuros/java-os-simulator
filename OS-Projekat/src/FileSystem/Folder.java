package FileSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Folder extends File {
	private ArrayList<File> content;
	
	public Folder(String name, Folder parent) {
		super(name, parent, true); 
		content = new ArrayList<File>();
	}
	public Folder()	//kreira root folder
	{
		super();
		content = new ArrayList<File>();
	}
	
	public File addFile(File file) {	//dodaje fajl u folders
		content.add(file);
		modified = LocalDateTime.now();
		return this;
	}
	public ArrayList<File> getContent() {
		return content;
	}

}
