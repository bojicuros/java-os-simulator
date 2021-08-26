package shell;

import kernel.ProcessScheduler;

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

	public static void load(String par1, String par2) {

	}

	public static void exe() {

	}

	public static String lspr() {
		String answer = ProcessScheduler.processList();
		return answer;
	}

	public static String trmpr(String par) {
		ProcessScheduler.terminateProcess(par);
		String answer = par + " has been terminated";
		return answer;
	}

	public static String blpr(String par) {
		ProcessScheduler.blockProcess(par);
		String answer = par + " has been blocked";
		return answer;
	}

	public static String ublpr(String par) {
		ProcessScheduler.unblockProcess(par);
		String answer = par + " has been blocked";
		return answer;
	}

	public static void clear() {
		GUI.clearTerminal();
	}

	public static String help() {
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
		help += "EXIT \t\t Closes program.\n";

		return help;
	}

	public static void exit() {
		System.exit(1);
	}

}
