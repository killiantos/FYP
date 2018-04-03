import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import core.MyEnv;
import simbad.gui.Simbad;
public class MyProg {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException, JsonSyntaxException, JsonIOException, InvocationTargetException, NoSuchMethodException, SecurityException {
//        Simbad frame = new Simbad(new MyEnv("test.maze") ,false);
        Simbad frame = new Simbad(new MyEnv("astra.json") ,false);
    }
}