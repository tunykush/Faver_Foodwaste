package app.controller;

import io.javalin.http.Handler;
import app.config.JDBCConnection;
import app.entities.Country;
import app.entities.Date;
import app.entities.FoodLossEvent;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PageST2A implements Handler {

    public static final String URL = "/page2A.html";

    @Override
    public void handle(Context context) throws Exception {
        JDBCConnection connection = new JDBCConnection();
        List<String> country = context.formParams("country");
        String startYear = context.formParam("start-year");
        String endYear = context.formParam("end-year");
        String sortField = context.formParam("sort-field");
        String sortOrder = context.formParam("sort-order");

        List<Country> countries = connection.getAllCountries();
        List<Date> years = connection.getAllYears();

        HashMap<String, Object> model = new HashMap<>();
        ArrayList<String> dates = new ArrayList<>(Arrays.asList(startYear, endYear));

        model.put("countries", countries);
        model.put("years", years);
        model.put("dates", dates);

        if (country != null && startYear != null && endYear != null) {
            List<FoodLossEvent> events = connection.getFoodLossEvents(country, startYear, endYear, sortField, sortOrder);
            model.put("events", events);
        
            
        }

        context.render("/templates/page2A.html", model);
    }
}