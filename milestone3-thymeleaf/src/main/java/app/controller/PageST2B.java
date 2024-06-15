package app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.entities.Commodity;
import app.entities.Date;
import app.entities.FoodLossEvent;
import app.config.JDBCConnection;
import app.dto.Task2B;
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

public class PageST2B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2B.html";

    @Override
    public void handle(Context context) throws Exception {
        // Makes Javalin render the webpage
        JDBCConnection connection = new JDBCConnection();
        String fromYear = context.queryParam("fromYear", "1900");
        String toYear = context.queryParam("toYear", "3000");
        String sortDir = context.queryParam("sortDir", "asc");
        String orderBy = context.queryParam("orderBy", "year");
        String foodGroup = context.queryParam("foodGroup", "null");
        ArrayList<Task2B> event = connection.getAllFoodLossEvent(fromYear,toYear,sortDir,foodGroup,orderBy);
        ArrayList<Date> years = connection.getAllYears();
        ArrayList<Commodity> commodities = connection.getAllCommodity();
        HashMap<String, Object> model = new HashMap<String,Object>();
        model.put("foodLoss", event);
        model.put("years",years);
        model.put("sortDir", sortDir);
        model.put("orderBy", orderBy);
        model.put("fromYear", fromYear);
        model.put("toYear", toYear);
        model.put("foodGroup", foodGroup);
        model.put("commodities", commodities);
        // DO NOT MODIFY THIS
        context.render("/templates/page2B.html", model);
    }

}
