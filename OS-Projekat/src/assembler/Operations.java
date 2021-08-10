package assembler;

public class Operations {

     public static final String mov = "0000";
     public static final String halt = "0001";
     public static final String store = "0010";
     public static final String add = "0011";
     public static final String sub = "0100";
     public static final String mul = "0101";
     public static final String jmp = "0110";
     public static final String jmpt = "0111";
	public static final String jmpf = "1000";
	public static final String jmpe = "1001";


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
               case Constants.R5: return R4;
               default: return null;
          }
     }

     //prevodi u masinski kod
     public static String MachineCodeConversion(String command){
          //npr: STORE R1,5
          //prva 4 bita su za operaciju, druga 3 za adresu, i ostatak za vrijednost (ako postoji)
          String mc = "";
          String arr[] = command.split("[ |,]");

          //prevodjenje operacije
          switch(arr[0]){
               case "MOV": mc += mov; break;
               case "HALT": mc += halt; break;
               case "STORE": mc += store; break;
               case "ADD": mc += add; break;
               case "SUB": mc += sub; break;
               case "MUL": mc += mul; break;
               case "JMP": mc += jmp; break;
               case "JMPT": mc += jmpt; break;
               case "JMPF": mc += jmpf; break;
               case "JMPE": mc += jmpe; break;
          }

          if(arr[0].equals("HALT")){
               return mc;
          } else if(arr[0].equals("JMP")){
               mc += toBinary(arr[1]);
               return mc;
          } else if(arr[0].equals("JMPT") || arr[0].equals("JMPF")){
               switch(arr[1]){
                    case "R1": mc += "000"; break;
                    case "R2": mc += "001"; break;
                    case "R3": mc += "010"; break;
                    case "R4": mc += "011"; break;
                    case "R5": mc += "101"; break;
               }
               mc += toBinary(arr[2]);
               return mc;
          } else if(arr[0].equals("JMPE")){
               //...
          } else if(arr[2].equals("R1") || arr[2].equals("R2") || arr[2].equals("R3") || arr[2].equals("R4") ||
               arr[2].equals("R5")){ //ako su oba argumenta registri
                    switch(arr[1]){
                         case "R1": mc += "000"; break;
                         case "R2": mc += "001"; break;
                         case "R3": mc += "010"; break;
                         case "R4": mc += "011"; break;
                         case "R5": mc += "101"; break;
                    }
                    switch(arr[2]){
                         case "R1": mc += "000"; break;
                         case "R2": mc += "001"; break;
                         case "R3": mc += "010"; break;
                         case "R4": mc += "011"; break;
                         case "R5": mc += "101"; break;
                    }
                    return mc;
          } else {
               switch(arr[1]){
                    case "R1": mc += "000"; break;
                    case "R2": mc += "001"; break;
                    case "R3": mc += "010"; break;
                    case "R4": mc += "011"; break;
                    case "R5": mc += "101"; break;
               }
               mc += toBinary(arr[2]);
               return mc;
          }

          return mc;
     }

     //iz dekadnog u binarni
     private static String toBinary(String s){
          int num = Integer.parseInt(s);
          int binary[] = new int[10];
          int index = 0;
          while(num > 0){
            binary[index++] = num % 2;
            num = num / 2;
          }
          
          String bin = "";
          for(int i = index - 1 ; i >= 0 ; i--){
               bin += binary[i];
          }
          return bin;
          
     }

     public static int store(String reg, String value){
          Register r = getRegister(reg);
          if(r != null){
               //...
          }
          return -1;
     }

     //postavlja vrijednost registra1 na registar2
     public static void mov(String reg1, String reg2){
          Register r1 = getRegister(reg1);
          Register r2 = getRegister(reg2);
          if(r1 != null && r2 != null){
               r1.value = r2.value; 
               //r1.value ) Memory.getRam(r2.value);
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

     public static int jmp(int adr){
          return adr;
     }

     public static int jmpt(String reg, int adr){
          Register r = getRegister(reg);
          if(r != null && r.value == 1)
               return adr;
          else
               return -1;
     } 

     public static int jmpf(String reg, int adr){
          Register r = getRegister(reg);
          if(r != null && r.value == 0)
               return adr;
          else
               return -1;
     } 

     public static int jmpe(String reg, String value, int adr){
          Register r = getRegister(reg);
          if(r != null)  //&& r.value == Memory.getValue(value)
               return adr;
          else
               return -1;
     }

     public static void halt(){}

     
     
}
