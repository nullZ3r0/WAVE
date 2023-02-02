import java.nio.file.*;
import java.io.*;

public class PythonHandler
{
    File pyexe;

    public PythonHandler(String _filepath)
    {
        pyexe = new File(_filepath);
    }

    public String getFullPath ()
    {
        if (pyexe != null)
        {
            return pyexe.getAbsolutePath();
        }
        return "";
    }
}
