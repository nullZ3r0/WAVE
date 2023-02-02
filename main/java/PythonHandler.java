import java.nio.file.*;
import java.io.*;

public class PythonHandler
{
    File pyexe;
    Runtime runtime;
    String[] commands;
    Process process;

    public PythonHandler(String _filepath)
    {
        pyexe = new File(_filepath);
        runtime = Runtime.getRuntime();
    }

    public void execute()
    {
        String[] commands = {pyexe.getAbsolutePath()};

        // Attempt to run the python executable
        try
        {
            process = runtime.exec(commands);
        }
        catch (IOException e)
        {
            System.out.println("Error");
            return;
        }

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s = null;
        while (true)
        {
            try
            {
                s = stdInput.readLine();
                if (s == null)
                {
                    break;
                }
                System.out.println(s);
            }
            catch (IOException e)
            {
                break;
            }
        }
    }

    public String getFullPath()
    {
        if (pyexe != null)
        {
            return pyexe.getAbsolutePath();
        }
        return "";
    }
}
