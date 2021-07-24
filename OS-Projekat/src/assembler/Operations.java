package assembler;

public class Operations {

     public static final String mov = "0000";
     public static final String halt = "0001";
     public static final String store = "0010";
     public static final String add = "0011";
     public static final String sub = "0100";
     public static final String mul = "0101";
     public static final String jmp = "0101";


     public static Register R1 = new Register("R1", Constants.R1, 0);
	public static Register R2 = new Register("R2", Constants.R2, 0);
	public static Register R3 = new Register("R3", Constants.R3, 0);
	public static Register R4 = new Register("R4", Constants.R4, 0);
	public static Register R5 = new Register("R5", Constants.R5, 0);

     //vraca registar na osnovu adrese registra
     public static Register getRegister(String adr){
          switch(adr){
               case Constants.R1: return R1;
               case Constants.R2: return R2;
               case Constants.R3: return R3;
               case Constants.R4: return R4;
               case Constants.R5: return R5;
               default: return null;
          }
     }

     //prebacuje vrijednost registra2 na registar1
     public static void mov(String reg1, String reg2){
          Register r1 = getRegister(reg1);
          Register r2 = getRegister(reg2);
          if(r1 != null && r2 != null){
               r1.value = r2.value; 
          }
     }

     //sabira vrijednosti 1. i 2. registra i cuva je na 1. registru, ako je nemoguce vraca -1
     public static int add(String reg1, String reg2){
          Register r1 = getRegister(reg1);
          Register r2 = getRegister(reg2);
          if(r1 != null && r2 != null){
               r1.value += r2.value;
               return r1.value;
          } else {
               return -1;
          }
     }

     //oduzima vrijednosti 1. i 2. registra i cuva je na 1. registru, ako je nemoguce vraca -1
     public static int sub(String reg1, String reg2){
          Register r1 = getRegister(reg1);
          Register r2 = getRegister(reg2);
          if(r1 != null && r2 != null){
               r1.value -= r2.value;
               return r1.value;
          } else {
               return -1;
          }
     }

     //mnozi vrijednosti 1. i 2. registra i cuva je na 1. registru, ako je nemoguce vraca -1
     public static int mul(String reg1, String reg2){
          Register r1 = getRegister(reg1);
          Register r2 = getRegister(reg2);
          if(r1 != null && r2 != null){
               r1.value *= r2.value;
               return r1.value;
          } else {
               return -1;
          }
     }

     public static void jmp(int d){
          //...
     }
     
}
