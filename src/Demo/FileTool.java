package Demo;

import java.io.File;
import java.util.ArrayList;


public class FileTool {
    public static ArrayList<String> refreshFileList(String strPath)
    {
        ArrayList<String> filelist = new ArrayList<String>();
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files == null)
        {
            return filelist;
        }
        for (int i = 0; i < files.length; i++)
        {
            if (files[i].isDirectory())
            {
                refreshFileList(files[i].getAbsolutePath());
            }
            else
            {
                String strFileName = files[i].getAbsolutePath().toLowerCase();
                //System.out.println(strFileName);
                filelist.add(files[i].getAbsolutePath());
            }
        }
        return filelist;
    }

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        ArrayList<String>  test = refreshFileList("D:\\cat_dog_data");
        System.out.println(test.get(1));
        System.out.println(test.get(2));
        System.out.println(test.get(3));
    }

}
