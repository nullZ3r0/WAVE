import java.io.File;
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
            process.destroyForcibly();
        }
    }

    public static void defaultClean()
    {
        System.out.println("[i] Applying duct tape");
        // This will end all processes that share any of the names of the default processes
        // Definitely not be a duct tage solution to lingering pyhelper_voice processes :)

        endCameraProcess();
        endVoiceProcess();
    }

    public static void endCameraProcess()
    {
        //System.out.println("[i] Stopping pyhelper_gestures processes");
        // This will end all processes that are named pyhelper_gestures
        String end = ".exe";

        try
        {
            // Execute taskkill command to kill processes with the given name
            Process process1 = Runtime.getRuntime().exec("taskkill /F /IM pyhelper_gestures"+end);

            // Wait for the command to finish
            process1.waitFor();
        }
        catch (IOException | InterruptedException e)
        {

        }
    }

    public static void endVoiceProcess()
    {
        //System.out.println("[i] Stopping pyhelper_voice processes");
        // This will end all processes that are named pyhelper_gestures
        String end = ".exe";

        try
        {
            // Execute taskkill command to kill processes with the given name
            Process process1 = Runtime.getRuntime().exec("taskkill /F /IM pyhelper_voice"+end);

            // Wait for the command to finish
            process1.waitFor();
        }
        catch (IOException | InterruptedException e)
        {

        }
    }

    public static void startDefaultProcesses(Boolean start)
    {
        if (start)
        {
            startDefaultProcesses();
        }
    }

    public static void startDefaultProcesses()
    {
        System.out.println("[i] Starting Sub Programs");
        File currentDir = new File(System.getProperty("user.dir"));
        String pyhelperVoicePath = "main\\resources\\python\\pyhelper_voice.exe";
        File pyhelperVoiceFile = new File(currentDir, pyhelperVoicePath);
        String pyhelperGesturesPath = "main\\resources\\python\\pyhelper_gestures.exe";
        File pyhelperGesturesFile = new File(currentDir, pyhelperGesturesPath);

        if (pyhelperGesturesFile.exists())
        {
            System.out.println("[path] " + pyhelperGesturesFile.getAbsolutePath());
            ProcessController.startProcess(pyhelperGesturesFile.getAbsolutePath());
        }
        else
        {
            System.out.println("[!] pyhelper_gestures.exe could not be found!");
        }

        if (pyhelperVoiceFile.exists())
        {
            System.out.println("[path] " + pyhelperVoiceFile.getAbsolutePath());
            ProcessController.startProcess(pyhelperVoiceFile.getAbsolutePath());
        }
        else
        {
            System.out.println("[!] pyhelper_voice.exe could not be found!");
        }
    }
}
