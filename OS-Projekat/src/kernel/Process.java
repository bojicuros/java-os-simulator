package kernel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import memory.Memory;
import memory.MemoryPartition;

public class Process implements Comparable<Process> {

	private int pid; // Process identifier
	private String name;
	private String filePath;
	private ProcessState state;
	private int cpuUsage;
	private int size;
	private float arrivalTime;
	private int burstTime; // Time needed for execution
	private ArrayList<String[]> assemblerProgram = new ArrayList<String[]>();

	public Process(int pid, String name, int size, String filePath) {
		this.pid = pid;
		this.name = name;
		state = ProcessState.NEW;
		cpuUsage = 0;
		this.size = size;
		this.filePath = filePath;
	}

	public void readFile() {
		try {
			File myObj = new File(this.filePath);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				String[] data = line.split(" ");
				assemblerProgram.add(data);
			}
			burstTime = Integer.parseInt(assemblerProgram.get(0)[0]);
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.printf("An error occurred.\n");
			e.printStackTrace();
		}
	}

	public void freeMemory() {
		this.terminate();
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

	public int getCpuUsage() {
		return cpuUsage;
	}

	public int getSize() {
		return size;
	}

	public float getArrivalTime() {
		return arrivalTime;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public ArrayList<String[]> getAssemblerProgram() {
		return assemblerProgram;
	}

	@Override
	public String toString() {
		return "Process [pid=" + pid + ", name=" + name + ", state=" + state + ", cpuUsage=" + cpuUsage
				+ ", arrivalTime=" + arrivalTime + "]";
	}

	@Override
	public int compareTo(Process o) {
		return (int) (this.arrivalTime - o.arrivalTime);
	}

}