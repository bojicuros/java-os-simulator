package assembler;

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

     //prevodi u masinski kod
     public static String MachineCodeConversion(String command){
          //npr: STORE R1,5
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
               case "JMPL": mc += jmpl; break;
               case "JMPG": mc += jmpg; break;
               case "JMPE": mc += jmpe; break;
               case "LOAD": mc += load; break;
          }

          if(arr[0].equals("HALT")){
               return mc;
          } else if(arr[0].equals("JMP")){   //npr: JMP 5(adr)
               mc += toBinary(arr[1]);
               return mc;
          } else if(arr[0].equals("JMPL") || arr[0].equals("JMPG") || arr[0].equals("JMPE")){   //npr.: JMPL R1,1(value),6(adr)
               switch(arr[1]){     //+registar
                    case "R1": mc += Constants.R1; break;
                    case "R2": mc += Constants.R2; break;
                    case "R3": mc += Constants.R3; break;
                    case "R4": mc += Constants.R4; break;
                    case "R5": mc += Constants.R5; break;
               }
               mc += toBinary(arr[2]);  //+vrijendost
               mc += toBinary(arr[3]);  //+adresa
               return mc;
          } else if(arr[2].equals("R1") || arr[2].equals("R2") || arr[2].equals("R3") || arr[2].equals("R4") ||
               arr[2].equals("R5")){ //ako su oba argumenta registri (MOV,ADD,SUB,MUL)
                    switch(arr[1]){
                         case "R1": mc += Constants.R1; break;
                         case "R2": mc += Constants.R2; break;
                         case "R3": mc += Constants.R3; break;
                         case "R4": mc += Constants.R4; break;
                         case "R5": mc += Constants.R5; break;
                    }
                    switch(arr[2]){
                         case "R1": mc += Constants.R1; break;
                         case "R2": mc += Constants.R2; break;
                         case "R3": mc += Constants.R3; break;
                         case "R4": mc += Constants.R4; break;
                         case "R5": mc += Constants.R5; break;
                    }
                    return mc;
          } else {
               switch(arr[1]){   //+registar
                    case "R1": mc += Constants.R1; break;
                    case "R2": mc += Constants.R2; break;
                    case "R3": mc += Constants.R3; break;
                    case "R4": mc += Constants.R4; break;
                    case "R5": mc += Constants.R5; break;
               }
               mc += toBinary(arr[2]); //+vrijednost
               return mc;
          }
          
     }

     //iz dekadnog u binarni
     private static String toBinary(String s){
          int num = Integer.parseInt(s);
          int binary[] = new int[10];
          int index = 0;
          int counter = 0;
          while(num > 0){
            binary[index++] = num % 2;
            num = num / 2;
            counter++;
          }
          
          String bin = "";
          counter = 8 - counter;
          for(int i = 0 ; i < counter ; i++)
               bin += "0";
          for(int i = index - 1 ; i >= 0 ; i--){
               bin += binary[i];
          }
          return bin;
          
     }


     public static void executeMachineCode(String command){
          String operation = command.substring(0, 4);

          if(operation == halt){
               //halt()
          } else if(operation == mov){
               String r1 = command.substring(4, 8);
               String r2 = command.substring(8,12);
               mov(r1,r2);
          } else if(operation == store){
               String r1 = command.substring(4, 8);
               String val2 = command.substring(8,16);
               store(r1,val2);
          } else if(operation == add){
               if(command.length() == 12){   //oba registra
                    String r1 = command.substring(4, 8);
                    String r2 = command.substring(8,12);
                    add(r1,r2);
               } else if(command.length() == 16){    //registar pa vrijednost
                    String r1 = command.substring(4, 8);
                    String val2 = command.substring(8,16);
                    add(r1,val2);
               }
          } else if(operation == sub){
               if(command.length() == 12){   //oba registra
                    String r1 = command.substring(4, 8);
                    String r2 = command.substring(8,12);
                    sub(r1,r2);
               } else if(command.length() == 16){    //registar pa vrijednost
                    String r1 = command.substring(4, 8);
                    String val2 = command.substring(8,16);
                    sub(r1,val2);
               }
          } else if(operation == mul){
               if(command.length() == 12){   //oba registra
                    String r1 = command.substring(4, 8);
                    String r2 = command.substring(8,12);
                    mul(r1,r2);
               } else if(command.length() == 16){    //registar pa vrijednost
                    String r1 = command.substring(4, 8);
                    String val2 = command.substring(8,16);
                    mul(r1,val2);
               }
          } else if(operation == jmp){
               String adr = command.substring(4, 12);
               jmp(adr);
          } else if(operation == jmpl){
               String reg = command.substring(4, 8);
               String val = command.substring(8, 16);
               String adr = command.substring(16, 24);
               jmpl(reg,val,adr);
          } else if(operation == jmpg){
               String reg = command.substring(4, 8);
               String val = command.substring(8, 16);
               String adr = command.substring(16, 24);
               jmpg(reg,val,adr);
          } else if(operation == jmpe){
               String reg = command.substring(4, 8);
               String val = command.substring(8, 16);
               String adr = command.substring(16, 24);
               jmpe(reg,val,adr);
          } else if(operation == load){
               String r1 = command.substring(4, 8);
               String adr = command.substring(8,16);
               load(r1,adr);
          }


     }


     public static int store(String reg, String value){
          Register r = getRegister(reg);
          if(r != null){
               //...
          }
          return -1;
     }

     public static void load(String reg, String adr){
          //...
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
     public static int add(String reg1, String value){  
          Register r1 = getRegister(reg1);
          if(value.length() == 8){ //vrijednost
               if(r1 != null){
                    r1.value += Integer.parseInt(value);
                    return r1.value;
               }
          } else if(value.length() == 4){ //registar
               Register r2 = getRegister(value);
               if(r1 != null && r2 != null){
                    r1.value += r2.value;
                    return r1.value;
               }
          }
          return -1;
     }

     //oduzima vrijednosti 1. i 2. registra i cuva je na 1. registru, ako je nemoguce vraca -1
     public static int sub(String reg1, String value){
          Register r1 = getRegister(reg1);
          if(value.length() == 8){ //vrijednost
               if(r1 != null){
                    r1.value -= Integer.parseInt(value);
                    return r1.value;
               }
          } else if(value.length() == 4){ //registar
               Register r2 = getRegister(value);
               if(r1 != null && r2 != null){
                    r1.value -= r2.value;
                    return r1.value;
               }
          }
          return -1;
     }

     //mnozi vrijednosti 1. i 2. registra i cuva je na 1. registru, ako je nemoguce vraca -1
     public static int mul(String reg1, String value){
          Register r1 = getRegister(reg1);
          if(value.length() == 8){ //vrijednost
               if(r1 != null){
                    r1.value *= Integer.parseInt(value);
                    return r1.value;
               }
          } else if(value.length() == 4){ //registar
               Register r2 = getRegister(value);
               if(r1 != null && r2 != null){
                    r1.value *= r2.value;
                    return r1.value;
               }
          }
          return -1;
     }

     public static void jmp(String adr){
          //pc 
     }

     public static void jmpl(String reg, String val, String adr){
          Register r = getRegister(reg);
          if(r != null && r.value == Integer.parseInt(val)){}
               //pc
     } 

     public static void jmpg(String reg, String val, String adr){
          Register r = getRegister(reg);
          if(r != null && r.value == Integer.parseInt(val)){}
               //pc
     } 

     public static void jmpe(String reg, String value, String adr){
          Register r = getRegister(reg);
          if(r != null) {} //&& r.value == Memory.getValue(value)
               //pc
     }

     public static void halt(){}

     
     
}
