package app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.config.JDBCConnection;
import app.entities.DropDownData;
import app.dto.Task2A;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2A";

    @Override
    public void handle(Context context) throws Exception {
        // Initialize JDBC connection
        JDBCConnection connection = new JDBCConnection();

        // Get data for the dropdowns
        DropDownData dropdownData = connection.getDropdownData();

        // Create a model to pass data to the Thymeleaf template
        Map<String, Object> model = new HashMap<>();
        model.put("countries", dropdownData.getCountries());
        model.put("years", dropdownData.getYears());

        // Render the page
        context.render("/templates/page2A.html", model);
    }

    public void handleFormSubmission(Context context) throws Exception {
        // Initialize JDBC connection
        JDBCConnection connection = new JDBCConnection();

        // Get query parameters from the form submission
        String country = context.formParam("country");
        String startYear = context.formParam("start-year");
        String endYear = context.formParam("end-year");
        String sortDir = context.formParam("sort");

        // Fetch data from the database based on the input parameters
        ArrayList<Task2A> foodLossEvents = connection.getFoodLossEvents(country, startYear, endYear, sortDir);

        
        

        // Convert the result to HTML table rows
        StringBuilder result = new StringBuilder();
        for (Task2A event : foodLossEvents) {
            result.append("<tr>")
                  .append("<td>").append(event.getCountry()).append("</td>")
                  .append("<td>").append(event.getChangeInLoss()).append("</td>")
                  .append("<td>").append(event.getCommodity()).append("</td>")
                  .append("<td>").append(event.getActivity()).append("</td>")
                  .append("<td>").append(event.getFoodSupplyStage()).append("</td>")
                  .append("<td>").append(event.getCauseOfLoss()).append("</td>")
                  .append("</tr>");
        }

        // Return the result as the response
        context.result(result.toString());
    }
}
