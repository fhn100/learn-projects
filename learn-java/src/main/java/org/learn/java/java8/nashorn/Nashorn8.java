package org.learn.java.java8.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.learn.java.java8.lambda.Person;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Benjamin Winterberg
 */
public class Nashorn8 {
    public static void main(String[] args) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
        String path = Nashorn8.class.getResource("/java8/nashorn/nashorn8.js").getPath();
        engine.eval(new FileReader(path));

        engine.invokeFunction("evaluate1");                             // [object global]
        engine.invokeFunction("evaluate2");                             // [object Object]
        engine.invokeFunction("evaluate3", "Foobar");                   // Foobar
        engine.invokeFunction("evaluate3", new Person("John", "Doe"));  // [object global] <- ???????
    }

}
