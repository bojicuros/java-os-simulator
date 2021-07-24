package FileSystem;

public class Main 
{
    public static void main(String[] args) 
    {
        FileSystem fs=new FileSystem();
        fs.addFolder("f1");
        fs.addFolder("f2");
        fs.addFile("file1");
        fs.printCurrentFolder();
        fs.printContent();
        fs.changeFolder("f2");
        fs.addFolder("f2_1");
        fs.addFile("file2_1");
        fs.printCurrentFolder();
        fs.printContent();
        fs.deleteFile("file2_1");
        fs.printContent();
        fs.changeFolder("..");
        fs.printCurrentFolder();
        fs.printContent();
        fs.changeFolder("/f2/f2_1");
        fs.addFile("f2_1_1");
        fs.addFolder("f2_1_1");
        fs.printCurrentFolder();
        fs.printContent();
        fs.renameFolder("f2_1_1", "preimenovan");
        fs.printContent();
        
    }
}
