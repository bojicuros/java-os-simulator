package kernel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import shell.Shell;

public class Process {

	private int pid; // Process identifier
	private String name;
	private String filePath;
	private ProcessState state;
	private ArrayList<String> instructions;
	private int size;
	private int startAdress; // Address of program instructions in main memory
	private int[] valuesOfRegisters; // To remember values of registers when switching to next process
	private int pcValue = -1; // To remember PC value when switching to next process

	public Process(String filePath) {
		this.pid = ProcessScheduler.allProcesses.size();
		state = ProcessState.READY;
		this.filePath = filePath;
		name = fileName(filePath);
		instructions = new ArrayList<>();
		valuesOfRegisters = new int[4];
		readFile();
		size = instructions.size();
		System.out.println("Program " + name + " (PID = " + pid + ") is loaded and sent in the background");
		ProcessScheduler.allProcesses.add(this);
		ProcessScheduler.readyQueue.add(this);
	}

	public void readFile() { // cita asemblerske instrukcije i u listu instructions upisuje masinske
								// instrukcije
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				String machineInstruction = Shell.asemblerToMachineInstruction(line);
				instructions.add(machineInstruction);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String fileName(String filePath) {
		String[] parse = filePath.split("/");
		return parse[parse.length - 1];
	}

	public void block() {
		if (this.state == ProcessState.READY) {
			this.state = ProcessState.BLOCKED;
			ProcessScheduler.readyQueue.remove(this);
		}
	}

	public void unblock() {
		if (this.state == ProcessState.BLOCKED) {
			this.state = ProcessState.READY;
			ProcessScheduler.readyQueue.add(this);
		}
	}

	public void terminate() {
		if (this.state == ProcessState.READY) {
			this.state = ProcessState.TERMINATED;
			ProcessScheduler.readyQueue.remove(this);
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

	public String getFilePath() {
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

}