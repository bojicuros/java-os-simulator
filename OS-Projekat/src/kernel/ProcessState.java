package kernel;

public enum ProcessState {
	READY, RUNNING, BLOCKED, TERMINATED, DONE;

	public static String state(ProcessState state) {
		if (state == READY)
			return "READY";
		else if (state == RUNNING)
			return "RUNNING";
		else if (state == BLOCKED)
			return "BLOCKED";
		else if (state == TERMINATED)
			return "TERMINATED";
		else if (state == DONE)
			return "DONE";
		return "";
	}
}