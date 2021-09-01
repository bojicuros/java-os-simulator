package fileSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import assembler.Operations;
import javafx.scene.control.TreeItem;
import kernel.Process;
import memory.FileInMemory;
import shell.Shell;

public class FileSystem {
	private static File rootFolder;
	private static File currentFolder;
	private TreeItem<File> treeItem;

	public FileSystem(File path) {
		rootFolder = path;
		currentFolder = rootFolder;
		treeItem = new TreeItem<>(rootFolder);
		createTree(treeItem);
	}

	public void createTree(TreeItem<File> rootItem) {
		try (DirectoryStream<Path> directoryStream = Files
				.newDirectoryStream(Paths.get(rootItem.getValue().getAbsolutePath()))) {
			for (Path path : directoryStream) {
				TreeItem<File> newItem = new TreeItem<>(path.toFile());
				newItem.setExpanded(false);
				rootItem.getChildren().add(newItem);
				if (Files.isDirectory(path))
					createTree(newItem);
				else { // Ucitava fajlove u sekundarnu memoriju
					byte[] content = Files.readAllBytes(newItem.getValue().toPath());
					FileInMemory newFile = new FileInMemory(newItem.getValue().getName(), content);
					if (!Shell.memory.contains(newFile))
						Shell.memory.save(newFile);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TreeItem<File> getTreeItem() {
		treeItem = new TreeItem<>(currentFolder);
		createTree(treeItem);
		return treeItem;
	}

	public static void listFiles() {
		System.out.println("Content of: " + currentFolder.getName());
		System.out.println("Type\tName\t\t\tSize");
		for (TreeItem<File> file : Shell.tree.getTreeItem().getChildren()) {
			byte[] fileContent = null;
			try {
				if (!file.getValue().isDirectory())
					fileContent = Files.readAllBytes(file.getValue().toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(file.getValue().isDirectory() ? ("Folder \t" + file.getValue().getName())
					: ("File" + "\t" + file.getValue().getName() + "\t\t" + fileContent.length + " B"));
		}
	}

	public static void changeDirectory(String directory) {
		if (directory.equals("..") && !currentFolder.equals(rootFolder))
			currentFolder = currentFolder.getParentFile();
		else {
			for (TreeItem<File> file : Shell.tree.getTreeItem().getChildren()) {
				if (file.getValue().getName().equals(directory) && file.getValue().isDirectory())
					currentFolder = file.getValue();
			}
		}
	}

	public static void makeDirectory(String directory) {
		File folder = new File(currentFolder.getAbsolutePath() + "\\" + directory);
		if (!folder.exists()) {
			folder.mkdir();
		}
	}

	public static void deleteDirectory(String directory) {
		for (TreeItem<File> file : Shell.tree.getTreeItem().getChildren()) {
			if (file.getValue().getName().equals(directory) && file.getValue().isDirectory())
				file.getValue().delete();
		}
	}

	public static void renameDirectory(String old, String newName) {
		for (TreeItem<File> file : Shell.tree.getTreeItem().getChildren()) {
			if (file.getValue().getName().equals(old) && file.getValue().isDirectory())
				file.getValue().renameTo(new File(currentFolder.getAbsolutePath() + "\\" + newName));
		}
	}

	public static void createFile(Process process) {
		String name = process.getName().substring(0, process.getName().indexOf('.')) + "_output";
		File newFile = new File(process.getFilePath().getParent() + "\\" + name + ".txt");
		try {
			newFile.createNewFile();
			FileWriter fw = new FileWriter(newFile);
			fw.write("Rezultat izvrsavanja: " + Operations.R4.value);
			fw.close();
		} catch (IOException e) {
			System.out.println("Error while creating file");
		}
	}

	public File getCurrentFolder() {
		return currentFolder;
	}
}