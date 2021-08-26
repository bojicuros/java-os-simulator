package FileSystem;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		FileSystem fs = new FileSystem();
		fs.loadExternFiles("C:\\Users\\PC\\eclipse-workspace\\OS-Projekat\\ProgramFiles");// mora imati ProgramFiles ili
																							// njegov podfolder na kraju
		fs.changeFolder("ProgramFiles");
		fs.printContent();

		// fs.createProgramFolder("D:\\path\\Folder", "NewFolder");//dodati putanju
		// fs.createProgramFile("file.txt", "D:\\path\\NewFolder", "Text u fajlu\nNovi
		// red");//dodati svoju putanju (\\)
		java.io.File f = fs.getProgramFile("petlja.txt",
				"C:\\\\Users\\\\PC\\\\eclipse-workspace\\\\OS-Projekat\\\\ProgramFiles");// dodati path
		try {
			Scanner myReader = new Scanner(f);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				System.out.println(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		/*
		 * fs.addFolder("f1"); fs.addFolder("f2"); fs.addFile("file1");
		 * fs.printCurrentFolder(); fs.printContent(); fs.changeFolder("f2");
		 * fs.addFolder("f2_1"); fs.addFile("file2_1"); fs.printCurrentFolder();
		 * fs.printContent(); fs.deleteFile("file2_1"); fs.printContent();
		 * fs.changeFolder(".."); fs.printCurrentFolder(); fs.printContent();
		 * fs.changeFolder("/f2/f2_1"); fs.addFile("f2_1_1"); fs.addFolder("f2_1_1");
		 * fs.printCurrentFolder(); fs.printContent(); fs.changeFolder("..");
		 * fs.changeFolder(".."); fs.renameFolder("f1", "abc"); fs.printContent();
		 */
	}
}
