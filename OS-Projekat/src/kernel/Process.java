package kernel;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import memory.MemoryManager;
import shell.Shell;

public class Process {

	private int pid; // Process identifier
	private String name;
	private Path filePath;
	private ProcessState state;
	private ArrayList<String> instructions;
	private int size;
	private int startAdress; // Address of program instructions in main memory
	private int[] valuesOfRegisters; // To remember values of registers when switching to next process
	private int pcValue = -1; // To remember PC value when switching to next process

	public Process(String program) {
		if (new File(Shell.tree.getCurrentFolder().getAbsolutePath() + "\\" + program).exists()) {
			this.pid = ProcessScheduler.allProcesses.size();
			state = ProcessState.READY;
			filePath = Paths.get(Shell.tree.getCurrentFolder().getAbsolutePath() + "\\" + program);
			name = program;
			valuesOfRegisters = new int[4];
			instructions = new ArrayList<>();
			readFile();
			size = instructions.size();
			System.out.println("Program " + name + " (PID = " + pid + ") is loaded and sent in the background");
			ProcessScheduler.allProcesses.add(this);
			ProcessScheduler.readyQueue.add(this);
		} else {
			System.out.println("Program " + program + " doesnt exist in this directory");
		}
	}

	public void block() {
		if (this.state == ProcessState.RUNNING) {
			this.state = ProcessState.BLOCKED;
			if (ProcessScheduler.readyQueue.contains(this))
				ProcessScheduler.readyQueue.remove(this);
		}
	}

	public void unblock() {
		if (this.state == ProcessState.BLOCKED) {
			this.state = ProcessState.READY;
			System.out.println("Process " + this.getName() + " is unblocked");
			ProcessScheduler.readyQueue.add(this);
		}
	}

	public void terminate() {
		if (this.state == ProcessState.READY || this.state == ProcessState.RUNNING) {
			this.state = ProcessState.TERMINATED;
			if (ProcessScheduler.readyQueue.contains(this))
				ProcessScheduler.readyQueue.remove(this);
		} else if (this.state == ProcessState.BLOCKED) {
			MemoryManager.removeProcess(this);
			this.state = ProcessState.TERMINATED;
		}
	}

	public int getPid() {
		return pid;
	}

	public String getName() {
		return name;
	}

	public ProcessState getState() {
		return state;
	}

	public void setState(ProcessState state) {
		this.state = state;
	}

	public Path getFilePath() {
		return filePath;
	}

	public int[] getValuesOfRegisters() {
		return valuesOfRegisters;
	}

	public void setValuesOfRegisters(int[] valuesOfRegisters) {
		for (int i = 0; i < valuesOfRegisters.length; i++)
			this.valuesOfRegisters[i] = valuesOfRegisters[i];
	}

	public int getPcValue() {
		return pcValue;
	}

	public void setPcValue(int pcValue) {
		this.pcValue = pcValue;
	}

	public int getStartAdress() {
		return startAdress;
	}

	public void setStartAdress(int startAdress) {
		this.startAdress = startAdress;
	}

	public ArrayList<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(ArrayList<String> instructions) {
		this.instructions = instructions;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void readFile() { // cita asemblerske instrukcije iz sekundarne memorije i u listu instructions
								// upisuje masinske instrukcije
		String content = Shell.memory.readFile(Shell.memory.getFile(name));
		String[] commands = content.split("\\n");
		for (String command : commands) {
			if (!command.equals(commands[commands.length - 1]))
				command = command.substring(0, command.length() - 1);
			else {
				if (command.length() > 4)
					command = command.substring(0, 4);
			}
			String machineInstruction = Shell.asemblerToMachineInstruction(command);
			instructions.add(machineInstruction);
		}
	}
}