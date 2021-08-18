import java.util.Random;

public class test 
{
    public static int[] generateArray(int size)
    {
        int[] res=new int[size];
        Random rnd=new Random();
        for (int i : res) 
        {
            i=rnd.nextInt(100);
        }
        return res;
    }
    public static void printArray(int[] arr)
    {
        System.out.print("[");
        for (int i : arr) {
            System.out.print(i+", ");
        }
        System.out.print("]");
    }
    public static void main(String[] args) 
    {
        System.out.println("Početak 0");
        MemoryManager memoryManager=new MemoryManager();
        System.out.println("Početak 1");

        Process p1=new Process(4, 1, generateArray(10));
        System.out.println("Početak 2");

        memoryManager.loadProcess(p1);
        System.out.println("Početak 3");

        System.out.println(Ram.getOccupiedSpace());
        System.out.println("Početak 4");

    }    
}
