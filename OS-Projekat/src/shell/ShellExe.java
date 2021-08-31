package shell;

import kernel.Process;
import kernel.ProcessScheduler;

public class ShellExe {

	public static void ls() {
		Shell.fileSystem.printContent();
	}

	public static void cd(String par) {
		Shell.fileSystem.changeFolder(par);
	}

	public static void md(String par) {
		Shell.fileSystem.addFolder(par);
	}

	public static void dd(String par) {
		Shell.fileSystem.deleteFile(par);
	}

	public static void rd(String name, String newName) {
		// Shell.fileSystem.renameFile(name, newName);
	}

	public static void load(String par) {
		new Process(par);
	}

	public static void exe() {
		ProcessScheduler.start();
	}

	public static void pr() {
		ProcessScheduler.listOfProcesses();
	}

	public static void trm(String par) {
		ProcessScheduler.terminateProcess(Integer.parseInt(par));
	}

	public static void block(String par) {
		ProcessScheduler.blockProcess(Integer.parseInt(par));
	}

	public static void unblock(String par) {
		ProcessScheduler.unblockProcess(Integer.parseInt(par));
	}

	public static void clear() {
		GUI.clearTerminal();
	}

	public static void help() {
		String help;

		help = "LS \t\t Displays a list of files and subdirectories in a directory.\n";
		help += "CD \t\t Changes dir.\n";
		help += "MD \t\t Make dir.\n";
		help += "DD \t\t Delete dir.\n";
		help += "RD \t\t Rename dir.\n";
		help += "LOAD \t\t Load and send procces in the background. \n";
		help += "EXE \t\t Start executing processes. \n";
		help += "PR \t\t List of processes.\n";
		help += "TRM \t\t Terminate process.\n";
		help += "BLOCK \t\t Blocks process.\n";
		help += "UBLOCK \t\t Unblocks process.\n";
		help += "CLEAR \t\t Clears terminal.\n";
		help += "EXIT \t\t Closes program.";

		System.out.println(help);
	}

	public static void exit() {
		System.exit(1);
	}

}