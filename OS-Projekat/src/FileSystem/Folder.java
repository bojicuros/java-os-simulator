package FileSystem;

import java.util.ArrayList;

public class Folder extends File
{
    private ArrayList<File> content;

    public Folder(String name, Folder parent)
    {
        super(name,parent,true);//ne koristim null za content
        content=new ArrayList<File>();
    }
    public Folder addFolder(String name, Folder parent) //static
    {
        //dovrši - jesam
        if(parent!=null)
        {
            ArrayList<File>list=parent.getContent();
            Folder newFolder=new Folder(name, parent);
            list.add(newFolder);
            return newFolder;
        }
        System.out.println("Folder must have parent.");//throw exception
        return null;
    }
    public File addFile(String name, Folder parent) 
    {
        if(parent!=null)
        {
            ArrayList<File> list=parent.getContent();
            File newFile=new File(name, parent, false);
            list.add(newFile);
            return newFile;
        }
        System.out.println("File must have parent.");//throw exception

        return null;
    }
    public File addFile(String name, Folder parent, byte[] content) 
    {
        if(parent!=null)
        {
            ArrayList<File> list=parent.getContent();
            File newFile=new File(name, parent, false, content);
            list.add(newFile);
            return newFile;
        }
        System.out.println("File must have parent.");//throw exception

        return null;
    }
    
    public ArrayList<File> getContent() 
    {
        return content;
    }
    
}
