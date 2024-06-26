package app.controller;

import java.sql.SQLException;
import java.util.List;


import app.config.JDBCConnection;
import app.entities.SimilarityScore;
import app.entities.SimilarityData;
import app.entities.Date;
import io.javalin.http.Context;
import io.javalin.http.Handler;


public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    @Override
    public void handle(Context context) throws Exception {
        renderPage(context, null);
    }

    public static void handleFormSubmission(Context context) throws SQLException {
        String location = context.formParam("location");
        String year = context.formParam("year");
        String similarityType = context.formParam("similarity-type");
        String similarityLevel = context.formParam("similarity-level");
        int numResults = Integer.parseInt(context.formParam("num-results"));

        List<SimilarityScore> results = getSimilarityScores(location, year, similarityType, similarityLevel, numResults);

        renderPage(context, results);
    }

    private static void renderPage(Context context, List<SimilarityScore> results) throws SQLException {
        JDBCConnection connection = new JDBCConnection();
        List<String> locations = connection.getAllLocations();
        List<Date> years = connection.getAllYears();

        context.attribute("locations", locations);
        context.attribute("years", years);
        context.attribute("results", results);

        context.render("/templates/page3A.html");
    }

    public static List<SimilarityScore> getSimilarityScores(String location, String year, String similarityType, String similarityLevel, int numResults) throws SQLException {
        JDBCConnection connection = new JDBCConnection();
        List<SimilarityData> dataList = connection.getSimilarityData(year, location, similarityType);
        List<SimilarityScore> similarityScores = connection.calculateSimilarityScores(dataList, similarityLevel, similarityType);

        return similarityScores.subList(0, Math.min(numResults, similarityScores.size()));
    }
}