package shell;

import assembler.Operations;
import fileSystem.FileSystem;
import kernel.Process;
import kernel.ProcessScheduler;
import memory.MemoryManager;
import memory.Ram;
import memory.SecondaryMemory;

public class ShellExe {

	public static void ls() {
		FileSystem.listFiles();
	}

	public static void cd(String par) {
		FileSystem.changeDirectory(par);
	}

	public static void md(String par) {
		FileSystem.makeDirectory(par);
	}

	public static void dd(String par) {
		FileSystem.deleteDirectory(par);
	}

	public static void df(String par) {
		FileSystem.deleteFile(par);
	}

	public static void rd(String name, String newName) {
		FileSystem.renameDirectory(name, newName);
	}

	public static void memM() {
		Ram.printRAM();
	}

	public static void memR() {
		Operations.printRegisters();
	}

	public static void memS() {
		SecondaryMemory.printMemoryAllocationTable();
	}

	public static void mem() {
		MemoryManager.printMemory();
	}

	public static void load(String par) {
		new Process(par);
	}

	public static void exe() {
		new ProcessScheduler().start();
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
		help += "MEM \t\t Show RAM, registers and memory allocation table.\n";
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