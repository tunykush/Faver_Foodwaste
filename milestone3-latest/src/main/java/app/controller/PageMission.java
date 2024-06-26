package app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.entities.Student;
import app.entities.User;
import app.config.JDBCConnection;
import io.javalin.http.Context;
import io.javalin.http.Handler;



public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {

         Map<String, Object> model = new HashMap<String, Object>();

        JDBCConnection jdbc = new JDBCConnection();

        ArrayList<User> users = jdbc.getUsers();
        ArrayList<Student> students = jdbc.getStudents();
        
        model.put("users", users);
        model.put("students", students);


        context.render("/templates/mission.html", model);
    }



    
}