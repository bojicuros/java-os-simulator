package kernel;

public class Process {

	private String pid; // Process identifier
	private String name;
	private ProcessState state;
	private int cpuUsage;
	private int counter = 0;

	public Process(String name) {
		this.name = name;
		counter++;
		this.pid = "prcs" + counter;
		state = ProcessState.READY;

	}

	public Process(Process p) {
		this.pid = p.pid;
		this.name = p.name;
		this.state = p.state;
		this.cpuUsage = p.cpuUsage;
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProcessState getStanje() {
		return state;
	}

	public void setStanje(ProcessState stanje) {
		this.state = stanje;
	}

	public int getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(int cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	@Override
	public String toString() {
		return "Process [pid=" + pid + ", name=" + name + ", stanje=" + state + ", cpuUsage=" + cpuUsage + "]";
	}

}
