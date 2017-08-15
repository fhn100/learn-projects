package org.learn.java.java8.nashorn;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * Using Backbone Models from Nashorn.
 *
 * @author Benjamin Winterberg
 */
public class Nashorn6 {

    public static void main(String[] args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        String path = Nashorn6.class.getResource("/java8/nashorn/nashorn6.js").getPath();
        engine.eval(new FileReader(path));

        Invocable invocable = (Invocable) engine;

        Product product = new Product();
        product.setName("Rubber");
        product.setPrice(1.99);
        product.setStock(1337);

        ScriptObjectMirror result = (ScriptObjectMirror)
                invocable.invokeFunction("calculate", product);
        System.out.println(result.get("name") + ": " + result.get("valueOfGoods"));
    }

    public static void getProduct(ScriptObjectMirror result) {
        System.out.println(result.get("name") + ": " + result.get("valueOfGoods"));
    }

}