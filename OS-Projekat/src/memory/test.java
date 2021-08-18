import java.util.ArrayList;
import java.util.Random;

public class test 
{
    public static ArrayList<Process> listProcess;
    public static int[] generateArray(int size)
    {
        int[] res=new int[size];
        Random rnd=new Random();
        for (int i = 0; i < size; i++) {
            res[i]=rnd.nextInt(100);
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
    public static void loadProcess(int n, MemoryManager memoryManager) 
    {
        for (int i = 0; i < n; i++) 
        {       
            Random r=new Random();

            Process p=new Process(i, generateArray(r.nextInt(100)+20));
            int position=memoryManager.loadProcess(p);
            listProcess.add(p);
            System.out.println("Proces: "+i+" na poziciji:"+position);
            System.out.println("Zauzeto: "+Ram.getOccupiedSpace());
        }    
    }
    public static void printPartitions(ArrayList<PartitionMemory> list) 
    {
        for (PartitionMemory partitionMemory : list) 
        {
            System.out.println("Partition: "+partitionMemory.getId()+" ["+partitionMemory.getPositionInMemory()+" - "+(partitionMemory.getPositionInMemory()+partitionMemory.getSize())+"]");    
        }    
    }
    
    public static void main(String[] args) 
    {
        MemoryManager memoryManager=new MemoryManager();
        listProcess=new ArrayList<>();
        /*
        loadProcess(10, memoryManager);
        printPartitions(memoryManager.getPartitionsInRam());
        memoryManager.removeProcess(listProcess.get(3));
        memoryManager.removeProcess(listProcess.get(5));
        memoryManager.removeProcess(listProcess.get(8));
        */

        Process p1=new Process(1, generateArray(300));
        memoryManager.loadProcess(p1);
        listProcess.add(p1);
        Process p2=new Process(2, generateArray(300));
        memoryManager.loadProcess(p2);
        listProcess.add(p2);
        Process p3=new Process(3, generateArray(300));
        memoryManager.loadProcess(p3);
        listProcess.add(p3);

        printPartitions(memoryManager.getPartitionsInRam());

        memoryManager.removeProcess(listProcess.get(1));
        printPartitions(memoryManager.getPartitionsInRam());
        Process p4=new Process(4, generateArray(400));
        memoryManager.loadProcess(p4);
        listProcess.add(p4);
        printPartitions(memoryManager.getPartitionsInRam());
        int[] d=memoryManager.readProcess(p3);
        

        /*
        loadProcess(2, memoryManager);
        printPartitions(memoryManager.getPartitionsInRam());
        loadProcess(10, memoryManager);
        */

    }    
}
