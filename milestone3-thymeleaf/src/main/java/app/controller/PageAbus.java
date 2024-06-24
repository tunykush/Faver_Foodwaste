package app.controller;


import io.javalin.http.Context;
import io.javalin.http.Handler;


/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

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