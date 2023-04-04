import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProgramController
{
    public static void startProcess(String filePath)
    {
        Runtime runtime = Runtime.getRuntime();

        try
        {
            runtime.exec("main/resources/python/HandGestures.exe");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
