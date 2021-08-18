import java.util.ArrayList;
//import kernel.Process;
public class PartitionMemory 
{
    private int[] data;
    private int positionInMemory=-1;
    private int id;
    private int size;
    private Process process;
    private static ArrayList<PartitionMemory> allPartitons; 
    public PartitionMemory(Process process)
    {
        this.process=process;
        size=process.getSize();
        id=process.getPid();
        data=process.getData();
        allPartitons.add(this); 
    }
    public PartitionMemory(int[] data, int id)
    {
        this.data=data;
        this.id=id;
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
    public static PartitionMemory getPartitionByProcess(Process process)
    {
        for (PartitionMemory partitionMemory : allPartitons) 
        {
            if(partitionMemory.getProcess().equals(process))
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
    public Process getProcess() {
        return process;
    }
}
