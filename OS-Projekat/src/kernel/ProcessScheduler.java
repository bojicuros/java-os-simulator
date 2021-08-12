package kernel;

import java.util.ArrayList;
import java.util.Queue;

import shell.Shell;

public class ProcessScheduler {

	public static Queue<Process> readyQueue;
	public static ArrayList<Process> allProcesses;
	private static int timeQuantum = 10; // ms

	public static void start() {
		while (!readyQueue.isEmpty()) {
			Process next = readyQueue.poll();
			executeProcess(next);
			if (next.getState() != ProcessState.BLOCKED || next.getState() != ProcessState.TERMINATED
					|| next.getState() != ProcessState.DONE) {
				next.setState(ProcessState.READY);
				Shell.saveValues(next);
				readyQueue.add(next);
			}
		}
		System.out.println("There are no processes to be executed");
	}

	public static void executeProcess(Process process) {
		long startTime = System.currentTimeMillis();
		if (process.getPcValue() == -1) { // we need to start process
			System.out.println("Process " + process.getName() + " started to execute");
			int startAdress = process.loadIntoRAM();
			process.setStartAdress(startAdress);
			Shell.PC = process.getStartAdress();
			process.setState(ProcessState.RUNNING);
			execute(process, startTime);
		} else { // we need to continue process
			System.out.println("Process " + process.getName() + " is executing again");
			Shell.loadValues(process);
			execute(process, startTime);
		}
	}

	private static void execute(Process process, long startTime) {
		while (process.getState() == ProcessState.RUNNING && startTime - System.currentTimeMillis() < timeQuantum) {
			int temp = 0; // RAM.getI(PC)
			String instruction = Shell.fromIntToInstruction(temp);
			Shell.IR = instruction;
			Shell.executeMachineInstruction(process);
		}
		if (process.getState() == ProcessState.BLOCKED)
			System.out.println("Process " + process.getName() + " is blocked");
		else if (process.getState() == ProcessState.TERMINATED)
			System.out.println("You have terminated process " + process.getName());
		else if (process.getState() == ProcessState.DONE)
			System.out.println("Process " + process.getName() + " is done");
	}

	public static void loadProcess(String filePath) {
		Process newProcess = new Process(allProcesses.size(), filePath);
		newProcess.setArrivalTime(System.currentTimeMillis());
		allProcesses.add(newProcess);
		newProcess.readFile();
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
