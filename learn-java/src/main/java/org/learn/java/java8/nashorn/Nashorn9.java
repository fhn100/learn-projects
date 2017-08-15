package org.learn.java.java8.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

/**
 * @author Benjamin Winterberg
 */
public class Nashorn9 {

    public static void main(String[] args) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
        String path = Nashorn9.class.getResource("/java8/nashorn/nashorn9.js").getPath();
        engine.eval(new FileReader(path));

        long t0 = System.nanoTime();

        double result = 0;
        for (int i = 0; i < 1000; i++) {
            double num = (double) engine.invokeFunction("testPerf");
            result += num;
        }

        System.out.println(result > 0);

        long took = System.nanoTime() - t0;
        System.out.format("Elapsed time: %d ms", TimeUnit.NANOSECONDS.toMillis(took));
    }
}
