package assembler;

import kernel.ProcessState;
import shell.Shell;

public class Operations {

	public static final String halt = "0000";
	public static final String mov = "0001";
	public static final String store = "0010";
	public static final String add = "0011";
	public static final String sub = "0100";
	public static final String mul = "0101";
	public static final String jmp = "0110";
	public static final String jmpl = "0111";
	public static final String jmpg = "1000";
	public static final String jmpe = "1001";
	public static final String load = "1010";
	public static final String jmpd = "1011";
	public static final String dec = "1100";
	public static final String inc = "1101";

	public static Register R1 = new Register("R1", Constants.R1, 0);
	public static Register R2 = new Register("R2", Constants.R2, 0);
	public static Register R3 = new Register("R3", Constants.R3, 0);
	public static Register R4 = new Register("R4", Constants.R4, 0);
	public static Register R5 = new Register("R5", Constants.R5, 0);

	public static int store(String reg, String value) {
		Register r = getRegister(reg);
		if (r != null) {
			// ...
		}
		return -1;
	}

	public static void load(String reg, String adr) {
		// ...
	}

	// postavlja vrijednost registra1 na registar2
	public static void mov(String reg1, String reg2) {
		Register r1 = getRegister(reg1);
		Register r2 = getRegister(reg2);
		if (r1 != null && r2 != null) {
			r1.value = r2.value;
		}
	}

	// sabira vrijednosti 1. i 2. registra i cuva je na 1. registru, ako je nemoguce
	// vraca -1
	public static int add(String reg1, String value) {
		Register r1 = getRegister(reg1);
		if (value.length() == 8) { // vrijednost
			if (r1 != null) {
				r1.value += Integer.parseInt(value, 2);
				return r1.value;
			}
		} else if (value.length() == 4) { // registar
			Register r2 = getRegister(value);
			if (r1 != null && r2 != null) {
				r1.value += r2.value;
				return r1.value;
			}
		}
		return -1;
	}

	// oduzima vrijednosti 1. i 2. registra i cuva je na 1. registru, ako je
	// nemoguce vraca -1
	public static int sub(String reg1, String value) {
		Register r1 = getRegister(reg1);
		if (value.length() == 8) { // vrijednost
			if (r1 != null) {
				r1.value -= Integer.parseInt(value, 2);
				return r1.value;
			}
		} else if (value.length() == 4) { // registar
			Register r2 = getRegister(value);
			if (r1 != null && r2 != null) {
				r1.value -= r2.value;
				return r1.value;
			}
		}
		return -1;
	}

	// mnozi vrijednosti 1. i 2. registra i cuva je na 1. registru, ako je nemoguce
	// vraca -1
	public static int mul(String reg1, String value) {
		Register r1 = getRegister(reg1);
		if (value.length() == 8) { // vrijednost
			if (r1 != null) {
				r1.value *= Integer.parseInt(value, 2);
				return r1.value;
			}
		} else if (value.length() == 4) { // registar
			Register r2 = getRegister(value);
			if (r1 != null && r2 != null) {
				r1.value *= r2.value;
				return r1.value;
			}
		}
		return -1;
	}

	public static void jmp(String adr) {
		int temp = Integer.parseInt(adr, 2);
		Shell.PC = Shell.currentlyExecuting.getStartAdress() + temp;
	}

	public static boolean jmpl(String reg, String val, String adr) {
		Register r = getRegister(reg);
		if (r != null && r.value < Integer.parseInt(val, 2)) {
			int temp = Integer.parseInt(adr, 2);
			Shell.PC = Shell.currentlyExecuting.getStartAdress() + temp;
			return true;
		}
		return false;
	}

	public static boolean jmpg(String reg, String val, String adr) {
		Register r = getRegister(reg);
		if (r != null && r.value > Integer.parseInt(val, 2)) {
			int temp = Integer.parseInt(adr, 2);
			Shell.PC = Shell.currentlyExecuting.getStartAdress() + temp;
			return true;
		}
		return false;
	}

	public static boolean jmpe(String reg, String val, String adr) {
		Register r = getRegister(reg);
		if (r != null && r.value == Integer.parseInt(val, 2)) {
			int temp = Integer.parseInt(adr, 2);
			Shell.PC = Shell.currentlyExecuting.getStartAdress() + temp;
			return true;
		}
		return false;
	}

	public static boolean jmpd(String reg, String val, String adr) {
		Register r = getRegister(reg);
		if (r != null && r.value != Integer.parseInt(val, 2)) {
			int temp = Integer.parseInt(adr, 2);
			Shell.PC = Shell.currentlyExecuting.getStartAdress() + temp;
			return true;
		}
		return false;
	}

	public static void inc(String reg){
		Register r = getRegister(reg);
		r.value += 1;
	}

	public static void dec(String reg){
		Register r = getRegister(reg);
		r.value -= 1;
	}

	public static void halt() {
		Shell.currentlyExecuting.setState(ProcessState.DONE);
	}

	// vraca registar na osnovu adrese registra
	private static Register getRegister(String adr) {
		switch (adr) {
		case Constants.R1:
			return R1;
		case Constants.R2:
			return R2;
		case Constants.R3:
			return R3;
		case Constants.R4:
			return R4;
		case Constants.R5:
			return R5;
		default:
			return null;
		}
	}

	public static void printRegisters() {
		System.out.println("R1 value - [ " + R1.value + " ]");
		System.out.println("R2 value - [ " + R2.value + " ]");
		System.out.println("R3 value - [ " + R3.value + " ]");
		System.out.println("R4 value - [ " + R4.value + " ]");
		System.out.println("R5 value - [ " + R5.value + " ]");
	}

	public static void clearRegisters() {
		R1.value = 0;
		R2.value = 0;
		R3.value = 0;
		R4.value = 0;
		R5.value = 0;
	}

}