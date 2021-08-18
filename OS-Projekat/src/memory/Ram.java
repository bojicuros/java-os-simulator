public class Ram
{
    private static final int CAPACITY=1024;
    private static int[] ram=new int[CAPACITY];
    private static int occupied=0;
/*  //null = nothing = 0
    public static void initialize() 
    {
        
    }
    */
    public static void initialize() 
    {
        for (int i : ram) 
        {
            ram[i]=-1;
        }    
    }
    public static boolean setAt(int i, int value)
    {
        if(isOcupied(i))
            return false;
        ram[i]=value;
        occupied++;
        return true;
    }
    public static boolean setAt(int i) 
    {
        return setAt(i, 1);
    }
    
    public static boolean removeSequence(int start, int size) 
    {
        for(int i=start;i<CAPACITY || i<size+start;i++)
        {
            if(isOcupied(i))
            {
                ram[i]=-1;
                occupied--;
            }
            else
            {
                return false;
            }
        }    
        return true;
    }
    public static boolean setSequence(int start, int[] data) 
    {
        //int current=start;
        for(int i=start; i<CAPACITY || i<data.length+start;i++) 
        {
            if(!isOcupied(i))
            {
                setAt(i, data[i-start]);
            }
                
            else
                return false; 
        }   
        return true;
    }
    public static boolean setSequence(int start, int size) 
    {
        for(int i=start; i<CAPACITY || i<size+start;i++) 
        {
            if(!isOcupied(i))
            {
                setAt(i);
            }
            else
                return false; 
        }   
        return true;
    }
    public static int getAt(int i) 
    {
        return ram[i];
    }
    public static boolean removeAt(int i) 
    {
        if(isOcupied(i))
        {
            ram[i]=-1;
            occupied--;
            return true;
        }
        return false;
    }
    public static boolean isOcupied(int i) 
    {
        return ram[i]!=-1;
    }
    public static int getOccupiedSpace()
    {
        return occupied;
    }
    public static int getFreeSpace() 
    {
        return  CAPACITY-occupied;
    }
    public static int getCapacity() {
        return CAPACITY;
    }
    
}