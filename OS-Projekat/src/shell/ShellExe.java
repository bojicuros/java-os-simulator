package shell;

import kernel.ProcessScheduler;
import kernel.Process;

public class ShellExe {

	public static void ls() {

	}

	public static void cd(String par) {

	}

	public static void md(String par) {

	}

	public static void dd(String par) {

	}

	public static void rd(String par1, String par2) {

	}

	public static void load(String par) {
		new Process(par);
	}

	public static void exe() {
		ProcessScheduler.start();
	}

	public static void lspr() {
		System.out.println("All processes:");
		for (Process process : ProcessScheduler.allProcesses) {
			System.out.println("  " + process);
		}
	}

	public static void trmpr(String par) {

	}

	public static void blpr(String par) {

	}

	public static void ublpr(String par) {

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
		help += "LSPR \t\t List of processes.\n";
		help += "TRMPR \t\t Terminate process.\n";
		help += "BLPR \t\t Blocks process.\n";
		help += "UBLPR \t\t Unblocks process.\n";
		help += "CLEAR \t\t Clears terminal.\n";
		help += "EXIT \t\t Closes program.";

		System.out.println(help);
	}

	public static void exit() {
		System.exit(1);
	}

}