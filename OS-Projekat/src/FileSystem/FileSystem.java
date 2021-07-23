import java.time.format.DateTimeFormatter;

public class FileSystem
{
    private Folder root;
    private Folder current;
    public FileSystem()
    {
        root=new Folder("", null);
        current=root;
    }
    public boolean addFolder(String name) 
    {
        return current.addFolder(name, current)!=null;  
    }
    public boolean addFile(String name)
    {
        return current.addFile(name, current)!=null;
    }
    public boolean addFile(String name, byte[] content) 
    {
        return current.addFile(name, current, content)!=null;
    }
    public boolean changeFolder(String command) 
    {
        if(command.compareTo("..")==0 && !current.equals(root))
        {
            if(current.equals(root))
                return true;
            current=current.parentFolder;
            return true;
        }
        else 
        {
            if(command.charAt(0)=='/')
            {
                current=root;
                if(command.compareTo("/")==0)
                {
                    return true;
                }
                String currentFolderName="";
                for (int i = 1; i < command.length(); i++) 
                {
                    if(command.charAt(i)=='/')
                    {   
                        if(!openFolder(currentFolderName))
                        {
                            return false;
                        }
                        currentFolderName="";
                    }
                    else
                    {
                        currentFolderName+=command.charAt(i);
                    }
                }
                if(!openFolder(currentFolderName))
                {
                    return false;
                }
                return true;
            }
            return openFolder(command);
        }
    }
    
    public boolean renameFolder(String currentName, String newName) 
    {
        for (File f : current.getContent()) 
        {
            if(f.getName().compareTo(currentName)==0)
            {
                f.setName(newName);
                return true;            
            }
        }
        return false;
    }
    public boolean deleteFile(String name) 
    {
        for (File f : current.getContent()) 
        {
            if(f.getName().compareTo(name)==0)
            {
                current.getContent().remove(f);
                f=null;
                return true;            
            }
        }    
        return false;
    }
    private boolean openFolder(String name) 
    {
        for (File f : current.getContent()) 
        {
            if(f.getName().compareTo(name)==0 && f instanceof Folder)
            {
                current=(Folder)f;
                return true;            
            }
        }
        return false;
    }
            
    public void printCurrentFolder()
    {
        System.out.println("Current folder: "+current.getName());
    }
    public void printContent() 
    {
        System.out.println("Content of: "+current.getName());
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        System.out.println("Type\tName\tSize\tCreated\t\t\tModified");
        for (File file : current.getContent()) 
        {
            System.out.println(((file instanceof Folder)?"folder":"file:")+"\t"+file.getName()+"\t"+file.getSize()+"\t"+dtf.format(file.getCreated())+"\t"+dtf.format(file.getModified()));
        }
    }
}