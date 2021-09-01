package assembler;

public class Register {

     public final String name;
     public final String address;
     public int value;

     public Register(String name, String address, int value){
          this.name = name;
          this.address = address;
          this.value = value;
     }
}