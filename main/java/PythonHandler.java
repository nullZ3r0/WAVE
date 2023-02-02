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

    public void run()
    {
        // Check if python based executable actually exists
        if (pyexe.exists() == false)
        {
            System.out.println("This file doesn't exist");
            return;
        }

        try
        {
            // Attempt to run the executable
            String[] commands = {pyexe.getAbsolutePath()};
            process = runtime.exec(commands);

            // Attempt to read the output
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try
            {
                String s = stdInput.readLine();
                while (s != null)
                {
                    System.out.println(s);
                    s = stdInput.readLine();
                }
            }
            catch (IOException e)
            {
                System.out.println("Something went wrong while reading the output: " + pyexe.getAbsolutePath());
                process.destroy();
                process = null;
            }
        }
        catch (IOException e)
        {
            System.out.println("Failed to execute: " + pyexe.getAbsolutePath());
            process.destroy();
            process = null;
        }
    }

    public void end()
    {
        process.destroy();
        process = null;
    }

    public String getFullPath()
    {
        // Check if python based executable actually exists
        if (pyexe.exists())
        {
            return pyexe.getAbsolutePath();
        }
        return "";
    }
}
