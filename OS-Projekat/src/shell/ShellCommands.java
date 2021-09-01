package shell;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class ShellCommands {

	private static String command;
	private static ArrayList<String> commandList = new ArrayList<String>();
	private static int iter;

	public static void returnCommand() {
		commandList.add(command);
		iter = commandList.size();
		command = command.toLowerCase();
		String commands[];
		commands = command.split(" ");

		switch (commands[0]) {

		case "ls": // a show directory
			if (commands.length == 1) {
				ShellExe.ls();
			} else
				errorWithParameters();
			break;

		case "cd": // change directory
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.cd(parameter);
			} else
				errorWithParameters();
			break;

		case "md": // make directory
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.md(parameter);
			} else
				errorWithParameters();
			break;

		case "dd": // delete dir
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.dd(parameter);
			} else
				errorWithParameters();
			break;

		case "rd": // rename dir
			if (commands.length == 3) {
				String parameter1 = commands[1];
				String parameter2 = commands[2];
				ShellExe.rd(parameter1, parameter2);
			} else
				errorWithParameters();
			break;

		case "mem": // print memory
			if (commands.length == 1) {
				ShellExe.mem();
			} else
				errorWithParameters();
			break;

		case "load": // load
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.load(parameter);
			} else
				errorWithParameters();
			break;

		case "exe": // load
			ShellExe.exe();
			break;

		case "pr": // list of processes
			if (commands.length == 1) {
				ShellExe.pr();
			} else
				errorWithParameters();
			break;

		case "trm": // terminate process
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.trm(parameter);
			} else
				errorWithParameters();
			break;

		case "block": // block process
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.block(parameter);
			} else
				errorWithParameters();
			break;

		case "unblock": // ublock process
			if (commands.length == 2) {
				String parameter = commands[1];
				ShellExe.unblock(parameter);
			} else
				errorWithParameters();
			break;

		case "clear": // clear terminal
			if (commands.length == 1) {
				ShellExe.clear();
			} else
				errorWithParameters();
			break;

		case "exit": // exit
			if (commands.length == 1) {
				ShellExe.exit();
			} else
				errorWithParameters();
			break;

		case "help": // help
			if (commands.length == 1) {
				ShellExe.help();
			} else
				errorWithParameters();
			break;

		default:
			System.out.println("That command doesn't exist!");
		}

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

	public static void setOut(OutputStream out) {
		System.setOut(new PrintStream(out, true));
	}

	private static void errorWithParameters() {
		String s = "Parameters for command are incorrect!\n";
		System.out.println(s);
	}
}