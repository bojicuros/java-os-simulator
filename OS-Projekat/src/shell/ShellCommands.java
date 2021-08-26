package shell;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.ArrayList;

public class ShellCommands {

	private static String st;
	private static String command;
	private static ArrayList<String> commandList = new ArrayList<String>();
	private static int iter;

	public static String returnCommand() {
		st = "";
		commandList.add(command);
		iter = commandList.size();
		command = command.toLowerCase();

		String commands[];
		commands = command.split(" ");

		switch (commands[0]) {

		case "ls": // a show directory
			if (commands.length == 1) {
				st = "Show directory\n";
				ShellExe.ls();
			} else
				st = errorWithParameters();
			break;

		case "cd": // change directory
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.cd(parameter);
			} else
				st = errorWithParameters();
			break;

		case "md": // make directory
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.md(parameter);
			} else
				st = errorWithParameters();
			break;

		case "dd": // delete dir
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.dd(parameter);
			} else
				st = errorWithParameters();
			break;

		case "rd": // rename dir
			if (commands.length == 3) {
				String parameter1 = commands[1];
				String parameter2 = commands[2];
				ShellExe.rd(parameter1, parameter2);
			} else
				st = errorWithParameters();
			break;

		case "load": // load
			if (commands.length == 3) {
				String parameter1 = commands[1];
				String parameter2 = commands[2];
				ShellExe.load(parameter1, parameter2);
			} else
				st = errorWithParameters();
			break;

		case "exe": // load
			ShellExe.exe();
			break;

		case "lspr": // list of processes
			if (commands.length == 1) {
				st=ShellExe.lspr();
			} else
				st = errorWithParameters();
			break;

		case "trmpr": // terminate process
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.trmpr(parameter);
			} else
				st = errorWithParameters();
			break;

		case "blpr": // block process
			if (commands.length == 2) {
				String parameter = commands[1];
				st = "blokiraj proces";
				ShellExe.blpr(parameter);
			} else
				st = errorWithParameters();
			break;

		case "ublpr": // ublock process
			if (commands.length == 2) {
				String parameter = commands[1];
				st = "unblokiraj proces";
				ShellExe.ublpr(parameter);
			} else
				st = errorWithParameters();
			break;

		case "clear": // clear terminal
			if (commands.length == 1) {
				ShellExe.clear();
			} else
				st = errorWithParameters();
			break;

		case "exit": // exit
			if (commands.length == 1) {
				ShellExe.exit();
			} else
				st = errorWithParameters();
			break;

		case "help": // help
			if (commands.length == 1) {
				st = ShellExe.help();
				
			} else
				st = errorWithParameters();
			break;

		default:
			st = "That command dont exist. Try again\n";
		}
		return st;
	}

	public static String previous() {
		String rez = "";
		if (!commandList.isEmpty()) {
			if (iter >= 0) {
				iter--;
				if (iter <= 0)
					iter = 0;
				rez = commandList.get(iter);
			}
		}
		return rez;
	}

	public static String next() {
		String rez = "";
		if (!commandList.isEmpty())
			if (iter < commandList.size() - 1) {
				iter++;
				if (iter > commandList.size() - 1)
					iter = commandList.size() - 1;
				rez = commandList.get(iter);
			}
		return rez;
	}

	public static void readACommand(PipedInputStream inp, int len) {
		command = "";
		char c;

		for (int i = 0; i < len; i++) {
			try {
				c = (char) inp.read();
				command += c;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error while reading a command");
			}
		}
	}

	private static String errorWithParameters() {
		String s = "Parameters for command are incorrect!\n";
		return s;
	}
}