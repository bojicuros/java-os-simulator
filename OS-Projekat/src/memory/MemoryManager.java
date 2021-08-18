import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
//import kernel.Process;
public class MemoryManager 
{
    public static Hashtable<PartitionMemory, Integer> addressesInRam;
    ArrayList<PartitionMemory> loadedPartitions;
    
    public MemoryManager()
    {
        Ram.initialize();
        PartitionMemory.initialize(); //možda nepotrebno
        addressesInRam=new Hashtable<>();

    }
    public int loadProcess(Process process)
    {    
        return loadPartition(new PartitionMemory(process));
        //objedini
    }
    public int readProcess()
    {
        
        return -1;
    }
    public int loadPartition(PartitionMemory partiton) 
    {
        int position=load(partiton.getData());
        
        partiton.setPositionInMemory(position);
        addressesInRam.put(partiton, position);
        return position;
    }
    public boolean loadFile(File file) 
    {
        //nedovršeno kako rasporediti u mapu...
        try 
        {
            Scanner fileReader=new Scanner(file);
            while(fileReader.hasNextLine())
            {
                String line=fileReader.nextLine();
                if(line.compareTo("-1")==0)
                    continue;
                String[] content=line.split(",");
                int[] data=new int[content.length];
                for (int i = 0; i < data.length; i++) 
                {
                    data[i]=Integer.parseInt(content[i]);
                }
                //dodaj u mapu
                load(data);
            }
            fileReader.close();
        } 
        catch (Exception e) 
        {
            //TODO: handle exception
        }
        return true;
    }
    private int load(int[] data) 
    {
        int len=data.length;
        if(len>Ram.getFreeSpace())
        {
            return -1;
        }
        int bestPosition=-1, bestFitSize=-1;
        int currentPosition=0, currentSize=0;
        for(int i=0;i<Ram.getCapacity();i++)
        {
            if(Ram.isOcupied(i) && currentPosition!=-1)
            {
                if(currentSize>=len && currentSize<bestFitSize)
                {
                    bestFitSize=currentSize;
                    bestPosition=currentPosition;
                }
                currentPosition=-1;
                currentSize=0;
            }
            else if(!Ram.isOcupied(i))
            {
                currentSize++;
            }
        }
        if(currentSize>=len && currentSize<bestFitSize)
        {
            bestFitSize=currentSize;
            bestPosition=currentPosition;
        }
        if(bestPosition==-1)
        {
            defragmentation();
            
            if( Ram.setSequence(Ram.getOccupiedSpace(), data))
                return Ram.getOccupiedSpace();
        }

        if(Ram.setSequence(bestPosition, data))
            return bestPosition;
        return -1;
    }
    private void defragmentation() 
    {
        int freePosition=-1;
        boolean avaliablePosition=false;
        for(int i=0; i<Ram.getCapacity();i++)
        {
            if(!Ram.isOcupied(i) && !avaliablePosition)//pronalazi prvu slobodnu poziciju
            {
                freePosition=i;
                avaliablePosition=true;
            }
            else if(Ram.isOcupied(i) && avaliablePosition)
            {
                PartitionMemory partition=PartitionMemory.getPartitionByAddress(i);
                int size=partition.getSize();
                int k=i;//možda je nepotreban
                int j;
                for (j = freePosition; j < i+size; j++) 
                {
                    Ram.setAt(j, Ram.getAt(k));
                    Ram.removeAt(k);
                    k++;
                }
                partition.setPositionInMemory(i);
                addressesInRam.replace(partition, i);
                i=j-1;
                freePosition=i;
                
            }


        }
    }
    public static Hashtable<PartitionMemory, Integer> getAddressesInRam() 
    {
        return addressesInRam;
    }
}
