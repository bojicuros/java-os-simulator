import java.util.ArrayList;
//import kernel.Process;
public class PartitionMemory 
{
    private int[] data;
    private int positionInMemory;// DA LI JE POTEBNO
    private int id;
    private int size;
    private static ArrayList<PartitionMemory> allPartitons; // kada se kreira
    //public static ArrayList<PartitionMemory> allPartitions=new ArrayList<>();
    public PartitionMemory(Process process)
    {
        
        size=process.getSize();
        id=process.getPid();
        data=process.getData();// promijeni ime funkcije   
    }
    public static void initialize() 
    {
        allPartitons=new ArrayList<>();    
    }
    public int getPositionInMemory() {
        return positionInMemory;
    }
    public void setPositionInMemory(int positionInMemory) {
        this.positionInMemory = positionInMemory;
    }
    public int[] getData() {
        return data;
    }
    public static PartitionMemory getPartitionByAddress(int address)
    {
        for (PartitionMemory partitionMemory : allPartitons) 
        {
            if(partitionMemory.getPositionInMemory()==address)
                return partitionMemory;
        }
        return null;
    }
    public int getSize() {
        return size;
    }
    public int getId() {
        return id;
    }
}
