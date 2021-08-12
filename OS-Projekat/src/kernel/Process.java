package kernel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import shell.Shell;

public class Process implements Comparable<Process> {

	private int pid; // Process identifier
	private String name;
	private String filePath;
	private ProcessState state;
	private ArrayList<String> instructions;
	private long arrivalTime;
	private int startAdress; // Address of program instructions in main memory
	private int[] valuesOfRegisters; // To remember values of registers when switching to next process
	private int pcValue = -1; // To remember PC value when switching to next process

	public Process(int pid, String filePath) {
		this.pid = pid;
		state = ProcessState.READY;
		this.filePath = filePath;
		name = fileName(filePath);
		instructions = new ArrayList<>();
		readFile();
		ProcessScheduler.allProcesses.add(this);
		ProcessScheduler.readyQueue.add(this);
	}

	public void readFile() {
		ArrayList<String> instructions = new ArrayList<>();
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

	public int loadIntoRAM() {
		// vraca adresu odakle pocinje proces u ramu
		return -1;
	}

	public void instructionsToRAM() {
		for (int i = 0; i < instructions.size(); i++) {
			/* RAM.setI(startAdress+i, Integer.parseInt(instructions.get(i))) */
		}
	}

	private String fileName(String filePath) {
		String[] parse = filePath.split("/");
		return parse[parse.length - 1];
	}

	public void block() {
		this.state = ProcessState.BLOCKED;
	}

	public void unblock() {
		this.state = ProcessState.RUNNING;
	}

	public void terminate() {
		this.state = ProcessState.TERMINATED;
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

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long time) {
		arrivalTime = time;
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

	@Override
	public String toString() {
		return "Process [pid=" + pid + ", name=" + name + ", state=" + state + ", cpuUsage=" + ", arrivalTime="
				+ arrivalTime + "]";
	}

	@Override
	public int compareTo(Process o) {
		return (int) (this.arrivalTime - o.arrivalTime);
	}

}