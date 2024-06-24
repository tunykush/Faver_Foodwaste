package app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.entities.Commodity;
import app.entities.Date;
import app.config.JDBCConnection;
import app.dto.Task2B;
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

public class PageST2B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2B.html";
    List<String> foodGroupList = null;

    @Override
    public void handle(Context context) throws Exception {
        JDBCConnection connection = new JDBCConnection();
        String fromYear = context.formParam("fromYear", "1900");
        String toYear = context.formParam("toYear", "3000");
        String orderBy = context.formParam("orderBy", "asc");
        String activity = context.formParam("activity");
        String foodsupplystage = context.formParam("foodsupplystage");
        String causeofloss = context.formParam("causeofloss");
        String sortOrder = context.formParam("sortOrder", "asc");
        System.out.println("sortOrder: " + sortOrder);
        boolean leastOne = false;
        if (!context.formParams("foodGroup").isEmpty()) {
            foodGroupList = context.formParams("foodGroup");
            leastOne = foodGroupList.size() > 1;
        }

        ArrayList<Task2B> event = connection.getAllFoodLossEvent(fromYear, toYear, foodGroupList, sortOrder,
                activity != null, foodsupplystage != null, causeofloss != null, sortOrder);
        ArrayList<Date> years = connection.getAllYears();
        ArrayList<Commodity> commodities = connection.getAllCommodity();
        HashMap<String, Object> model = new HashMap<String, Object>();

        model.put("activity", activity);
        model.put("foodsupplystage", foodsupplystage);
        model.put("causeofloss", causeofloss);
        model.put("foodLoss", event);
        model.put("years", years);
        model.put("orderBy", orderBy);
        model.put("fromYear", fromYear);
        model.put("toYear", toYear);
        model.put("foodGroupList", foodGroupList);
        model.put("commodities", commodities);
        model.put("leastOne", leastOne);

        context.render("/templates/page2B.html", model);
    }

}
