import java.io.FileNotFoundException;
import java.io.IOException;

import core.MyEnv;
import simbad.gui.Simbad;
public class MyProg {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException {
        Simbad frame = new Simbad(new MyEnv("test.maze") ,false);
    }
}