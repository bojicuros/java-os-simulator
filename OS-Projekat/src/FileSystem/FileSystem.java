package FileSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.FileWriter;

public class FileSystem {
	private Folder root;
	private Folder current;
	private Folder programFilesFolder;
	public ArrayList<java.io.File> programFiles;

	public FileSystem() {
		root = new Folder();
		current = root;
		programFilesFolder = new Folder("ProgramFiles", root); // dodaje u root ProgramFiles folder
	}

	public boolean addFolder(String name) {
		Folder f = new Folder(name, current);
		return f != null;
	}

	public void loadExternFiles(String path) {
		programFiles = new ArrayList<>();
		java.io.File folder = new java.io.File(path);
		java.io.File[] listOfFiles = folder.listFiles();
		for (java.io.File file : listOfFiles) {
			String nameOfFOlder = path.substring(path.lastIndexOf('\\') + 1);
			programFiles.add(file);
			if (file.isDirectory()) {
				if (nameOfFOlder.compareTo("ProgramFiles") == 0) {
					new Folder(file.getName(), programFilesFolder);
				} else {
					File subfolder = getFileByName(programFilesFolder, file.getName());// trazi folder u program files
					if (subfolder == null) // ako ga ne nadje, kreira ga u ProgramFiles
					{
						Folder missingFolder = new Folder(nameOfFOlder, programFilesFolder);
						new Folder(file.getName(), missingFolder);
					} else // ako ga nadje u njemu kreira
					{
						new Folder(file.getName(), (Folder) subfolder);
					}

				}
			} else {
				if (nameOfFOlder.compareTo("ProgramFiles") == 0) {
					new File(file.getName(), programFilesFolder, false);
				} else {
					File subfolder = getFileByName(programFilesFolder, file.getName());
					if (subfolder == null) {
						Folder missingFolder = new Folder(nameOfFOlder, programFilesFolder);
						new Folder(file.getName(), missingFolder);
					} else {
						new File(file.getName(), (Folder) subfolder, false);
					}
				}
			}
		}
	}

	public boolean createProgramFile(String name, String path, String content) {

		java.io.File file = new java.io.File(path + "\\" + name);
		if (path.substring(path.lastIndexOf('\\') + 1).compareTo("ProgramFiles") == 0) {
			new File(name, programFilesFolder, content);
		} else {
			Folder parentFolder = (Folder) getFileByName(programFilesFolder,
					path.substring(path.lastIndexOf('\\') + 1));
			new File(name, parentFolder, content);
		}
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();
			programFiles.add(file);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean createProgramFolder(String path, String name) {
		java.io.File folder = new java.io.File(path + "\\" + name);
		if (!folder.exists()) {
			folder.mkdirs();
			new Folder(name, programFilesFolder);
			return true;
		}
		return false;
	}

	public java.io.File getProgramFile(String name, String folderPath) { // nakon sto se pozove loadExtern files, vraca
																			// datoteku sa proslijedjenim imenom
		loadExternFiles(folderPath);
		for (java.io.File file : programFiles) {
			if (file.getName().compareTo(name) == 0)
				return file;
		}
		return null;
	}

	public boolean addFile(String name) {
		return new File(name, current, false) != null;
	}

	public boolean addFile(String name, byte[] content) {
		return new File(name, current, false, content) != null;
	}

	public boolean changeFolder(String command) {
		if (command.compareTo("..") == 0 && !current.equals(root)) { // vraca korak unazad
			if (current.equals(root))
				return true;
			current = current.parentFolder;
			return true;
		} else {
			if (command.charAt(0) == '/') { // trazi prema apsolutnoj putnji
				current = root;
				if (command.compareTo("/") == 0) {
					return true;
				}
				String currentFolderName = "";
				for (int i = 1; i < command.length(); i++) {
					if (command.charAt(i) == '/') {
						if (!openFolder(currentFolderName)) {
							return false;
						}
						currentFolderName = "";
					} else {
						currentFolderName += command.charAt(i);
					}
				}
				if (!openFolder(currentFolderName)) {
					return false;
				}
				return true;
			}
			return openFolder(command);
		}
	}

	public boolean renameFolder(String currentName, String newName) {
		for (File f : current.getContent()) {
			if (f.getName().compareTo(currentName) == 0) {
				f.setName(newName);
				f.modified = LocalDateTime.now();
				return true;
			}
		}
		return false;
	}

	public boolean deleteFile(String name) {
		for (File f : current.getContent()) {
			if (f.getName().compareTo(name) == 0) {
				current.getContent().remove(f);
				current.modified = LocalDateTime.now();
				f = null;
				return true;
			}
		}
		return false;
	}

	private boolean openFolder(String name) {
		for (File f : current.getContent()) {
			if (f.getName().compareTo(name) == 0 && f instanceof Folder) {
				current = (Folder) f;
				return true;
			}
		}
		return false;
	}

	public File getFileByName(Folder parent, String name) {
		for (File f : parent.getContent()) {
			if (name.compareTo(f.getName()) == 0) {
				return f;
			}
		}
		return null;
	}

	public void printCurrentFolder() {
		System.out.println("Current folder: " + current.getName());
	}

	public void printContent() {
		System.out.println("Content of: " + current.getName());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		System.out.println("Type\tName\t\tSize\tCreated\t\t\tModified");
		for (File file : current.getContent()) {
			System.out.println(((file instanceof Folder) ? "folder" : "file") + "\t" + file.getName() + "\t"
					+ file.getSize() + "\t" + dtf.format(file.getCreated()) + "\t" + dtf.format(file.getModified()));
		}
	}
}