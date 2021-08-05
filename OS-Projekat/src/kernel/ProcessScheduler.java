package kernel;

import java.util.ArrayList;
import java.util.Queue;

public class ProcessScheduler {

	private static Queue<Process> readyQueue;
	public static ArrayList<Process> allProcesses;
	private static int timeQuantum = 10; // ms

	public static void execute() {
		if (allProcesses.isEmpty()) {
			System.out.println("There are no processes to be executed");
			return;
		}

	}

	private static void updateReadyQueue() {
		if (allProcesses.isEmpty())
			return;
		
	}

	public static void blockProcess(String name) {
		for (Process process : allProcesses)
			if (process.getName().equals(name)) {
				process.block();
				return;
			}
		System.out.println("You entered name of nonexistent process, check and try again");
	}

	public static void unblockProcess(String name) {
		for (Process process : allProcesses)
			if (process.getName().equals(name)) {
				process.unblock();
				return;
			}
		System.out.println("You entered name of nonexistent process, check and try again");
	}

	public static void terminateProcess(String name) {
		for (Process process : allProcesses)
			if (process.getName().equals(name)) {
				process.terminate();
				return;
			}
		System.out.println("You entered name of nonexistent process, check and try again");
	}

	public static String processList() {
		String s = "";
		for (Process process : allProcesses)
			s += process + "\n";
		s += "---------------------------";
		return s;
	}

	public Queue<Process> getReadyQueue() {
		return readyQueue;
	}

	public int getTimeQuantum() {
		return timeQuantum;
	}
}
