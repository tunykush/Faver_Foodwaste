package app.controller;


import io.javalin.http.Context;
import io.javalin.http.Handler;


public class PageAbus implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/aboutus.html";
    @Override
    public void handle(Context context) throws Exception {
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.render("/templates/aboutus.html");
    }

}