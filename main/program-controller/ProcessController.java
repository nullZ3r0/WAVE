import java.io.IOException;
import java.util.ArrayList;

public class ProcessController
{
    public static ArrayList<Process> processArrayList = new ArrayList<>();

    public static void startProcess(String filePath)
    {
        try
        {
            ProcessBuilder processBuilder = new ProcessBuilder(filePath);
            Process process = processBuilder.start();
            processArrayList.add(process);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void endAll()
    {
        for (Process process : processArrayList)
        {
            if (process.isAlive())
            {
                process.destroyForcibly();
            }
        }
    }
}
