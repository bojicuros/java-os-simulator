import java.time.LocalDateTime;

public class File 
{
    protected String name;
    protected String path;
    protected int size=0;
    protected LocalDateTime created;
    protected LocalDateTime modified;
    protected Folder parentFolder;
    protected byte[] contentFile=null;

    public File(String name, Folder parent, boolean isFolder)
    {
        this.name=name;
        if(parent==null)
        {
            path=name+(isFolder?"//":"");
        }
        else
        {
            path=parent.getPath()+name+(isFolder?"//":"");
        }
        created=LocalDateTime.now();
        modified=LocalDateTime.now();
        parentFolder=parent;
    }
    public File(String name, Folder parent, boolean isFolder, byte[] content)//Object[] content
    {
        this(name, parent, isFolder);
        this.contentFile=content;
        this.size=contentFile.length;
    }
    public String getName() {
        return name;
    }
    public byte[] getContentFile() {
        return contentFile;
    }
    public String getPath() {
        return path;
    }
    public int getSize() {
        return size;
    }
    public Folder getParentFolder() {
        return parentFolder;
    }
    public LocalDateTime getCreated() {
        return created;
    }
    public LocalDateTime getModified() {
        return modified;
    }
    public void setName(String name) {
        this.name = name;
        modified=LocalDateTime.now();
    }
    @Override
    public boolean equals(Object o)
    {
        if(o==this)
            return true;
        if(!(o instanceof File))
            return false;
        File f=(File)o;
        return f.getName().compareTo(name)==0;
    }
}
