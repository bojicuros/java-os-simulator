package kernel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import assembler.Operations;
import memory.MemoryManager;
import memory.Ram;
import shell.Shell;

public class ProcessScheduler {

	public static Queue<Process> readyQueue;
	public static ArrayList<Process> allProcesses;
	private static int timeQuantum = 1; // ms

	public static void getReady() {
		readyQueue = new LinkedList<>();
		allProcesses = new ArrayList<>();
	}

	public static void start() { // pokrece izvrsavanja svih procesa
		while (!readyQueue.isEmpty()) {
			Process next = readyQueue.poll();
			executeProcess(next);
			if (next.getState() != ProcessState.BLOCKED && next.getState() != ProcessState.TERMINATED
					&& next.getState() != ProcessState.DONE) {
				next.setState(ProcessState.READY);
				readyQueue.add(next);
			}
		}
		System.out.println("There are no processes to be executed");
	}

	private static void executeProcess(Process process) {
		Shell.currentlyExecuting = process;
		if (process.getPcValue() == -1) { // we need to start process
			System.out.println("Process " + process.getName() + " started to execute");
			int startAdress = Shell.manager.loadProcess(process);
			process.setStartAdress(startAdress);
			Shell.base = startAdress;
			Shell.limit = process.getInstructions().size();
			Shell.PC = 0;
			process.setState(ProcessState.RUNNING);
			execute(process, System.currentTimeMillis());
		} else { // we need to continue process
			System.out.println("Process " + process.getName() + " is executing again");
			int startAdress = Shell.manager.loadProcess(process);
			process.setStartAdress(startAdress);
			Shell.base = startAdress;
			Shell.limit = process.getInstructions().size();
			Shell.loadValues();
			process.setState(ProcessState.RUNNING);
			execute(process, System.currentTimeMillis());
		}
	}

	private static void execute(Process process, long startTime) {
		while (process.getState() == ProcessState.RUNNING && System.currentTimeMillis() - startTime < timeQuantum) {
			int temp = Ram.getAt(Shell.PC + Shell.base);
			String instruction = Shell.fromIntToInstruction(temp);
			Shell.IR = instruction;
			Shell.executeMachineInstruction();
		}

		if (process.getState() == ProcessState.BLOCKED) {
			System.out.println("Process " + process.getName() + " is blocked");
			Shell.saveValues();
		} else if (process.getState() == ProcessState.TERMINATED) {
			System.out.println("You have terminated process " + process.getName());
			MemoryManager.removeProcess(process);
		} else if (process.getState() == ProcessState.DONE) {
			System.out.println("Process " + process.getName() + " is done");
			Operations.printRegisters();
			MemoryManager.removeProcess(process);
		} else { // process is switched by process scheduler
			Shell.saveValues();
		}
		Operations.clearRegisters();
	}

	public static void blockProcess(Integer pid) {
		for (Process process : allProcesses)
			if (process.getPid() == pid) {
				process.block();
				return;
			}
		System.out.println("You entered name of nonexistent process, check and try again");
	}

	public static void unblockProcess(Integer pid) {
		for (Process process : allProcesses)
			if (process.getPid() == pid) {
				process.unblock();
				return;
			}
		System.out.println("You entered name of nonexistent process, check and try again");
	}

	public static void terminateProcess(Integer pid) {
		for (Process process : allProcesses)
			if (process.getPid() == pid) {
				process.terminate();
				return;
			}
		System.out.println("You entered name of nonexistent process, check and try again");
	}

	public static void listOfProcesses() {
		System.out.println("PID\tProgram\t\tSize\tState\t\tCurrent occupation of memory");
		for (Process process : allProcesses)
			System.out.println(process.getPid() + "\t" + process.getName() + "\t " + process.getSize() + "\t"
					+ ProcessState.state(process.getState()) + "\t\t" + MemoryManager.memoryOccupiedByProcess(process));
	}

	public Queue<Process> getReadyQueue() {
		return readyQueue;
	}

	public int getTimeQuantum() {
		return timeQuantum;
	}
}
