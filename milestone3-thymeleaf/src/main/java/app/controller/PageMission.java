package app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.entities.User;
import app.config.JDBCConnection;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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

public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission";

    @Override
    public void handle(Context context) throws Exception {

         Map<String, Object> model = new HashMap<String, Object>();

        JDBCConnection jdbc = new JDBCConnection();

        ArrayList<User> users = jdbc.getUsers();
        
        model.put("users", users);


        context.render("/templates/mission.html", model);
    }



    
}