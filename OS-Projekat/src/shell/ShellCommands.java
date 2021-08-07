package shell;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.ArrayList;

public class ShellCommands {

	private static String commandText;
	private static String command;
	private static ArrayList<String> commandList = new ArrayList<String>();
	private static int iter;

	public static String returnCommand() {
		commandText = "";
		commandList.add(command);
		iter = commandList.size();
		command = command.toLowerCase();

		String commands[];
		commands = command.split(" ");

		switch (commands[0]) {

		case "dir": // a show directory
			if (commands.length == 1) {
				commandText = "Show directory\n";
				ShellExe.showDir();
			} else
				commandText = errorWithParameters();
			break;

		case "goto": // change directory
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.go(parameter);
			} else
				commandText = errorWithParameters();
			break;

		case "md": // make directory
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.md(parameter);
			} else
				commandText = errorWithParameters();

			break;

		case "dd": // delete dir
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.dd(parameter);
			} else
				commandText = errorWithParameters();
			break;

		case "rd": // rename dir
			if (commands.length == 3) {
				String parameter1 = commands[1];
				String parameter2 = commands[2];
				ShellExe.rd(parameter1, parameter2);
			} else
				commandText = errorWithParameters();
			break;

		case "exe": // execute
			if (commands.length == 3) {
				String parameter1 = commands[1];
				String parameter2 = commands[2];
				ShellExe.exe(parameter1, parameter2);
			} else
				commandText = errorWithParameters();
			break;

		case "lspr": // list of processes
			if (commands.length == 1) {
				ShellExe.lspr();
				commandText = "Lista procesa";
			} else
				commandText = errorWithParameters();
			break;

		case "trmpr": // terminate process
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.trmpr(parameter);
			} else
				commandText = errorWithParameters();

			break;

		case "blpr": // block process
			if (commands.length == 2) {
				String parameter = commands[1];
				commandText = "blokiraj proces";
				ShellExe.blpr(parameter);
			} else
				commandText = errorWithParameters();
			break;

		case "ublpr": // unblock process
			if (commands.length == 2) {
				String parameter = commands[1];
				commandText = "unblokiraj proces";
				ShellExe.ublpr(parameter);
			} else
				commandText = errorWithParameters();
			break;

		case "clear": // clear terminal
			ShellExe.clear();
			System.out.println("clear");
			break;

		case "exit": // exit
			if (commands.length == 1) {
				ShellExe.exit();
			} else
				commandText = errorWithParameters();
			break;

		case "help": // help
			if (commands.length == 1) {
				ShellExe.help();
			} else
				commandText = errorWithParameters();
			break;

		default:
			commandText = "That command dont exist. Try again\n";
		}
		return commandText;
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