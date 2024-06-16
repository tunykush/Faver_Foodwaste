package app.config;

import java.util.ArrayList;

import app.dto.Task2B;
import app.entities.Commodity;
import app.entities.Country;
import app.entities.Date;
import app.entities.FoodLossEvent;
import app.entities.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:Faver_Foodwaste/milestone3-thymeleaf/database/foodloss.db";


    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the Countries in the database.
     * 
     * @return
     *         Returns an ArrayList of Country objects
     */
    public ArrayList<Country> getAllCountries() {
        // Create the ArrayList of Country objects to return
        ArrayList<Country> countries = new ArrayList<Country>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Country";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String m49Code = results.getString("m49code");
                String name = results.getString("countryName");

                // Create a Country Object
                Country country = new Country(m49Code, name);

                // Add the Country object to the array
                countries.add(country);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return countries;
    }

    public ArrayList<Commodity> getAllCommodity() {
        // Create the ArrayList of Country objects to return
        ArrayList<Commodity> commodities = new ArrayList<Commodity>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT *  FROM commodity ORDER BY Descriptor";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String cpcCode = results.getString("cpc_code");
                String descriptor = results.getString("descriptor");
                String groupCode = results.getString("group_code");

                // Create a Country Object
                Commodity commodity = new Commodity(cpcCode, descriptor, groupCode);

                // Add the Country object to the array
                commodities.add(commodity);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return commodities;
    }

    public ArrayList<Date> getAllYears() {
        // Create the ArrayList of Country objects to return
        ArrayList<Date> years = new ArrayList<Date>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Date";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                Integer yearString = results.getInt("year");

                // Create a Country Object
                Date year = new Date(yearString);

                // Add the Country object to the array
                years.add(year);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return years;
    }
    // TODO: Add your required methods here

    public ArrayList<Task2B> getAllFoodLossEvent(String fromYear, String toYear, 
        String sortDir, String foodGroup, String orderBy) {
        // Create the ArrayList of Country objects to return
        ArrayList<Task2B> foodLossEvents = new ArrayList<Task2B>();

        boolean applyCommodityFilter = foodGroup != "null";
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT f.commodity , f.Activity, f.year, f.food_supply_stage, avg(f.loss_percentage) AS loss_percentage, f.cause_off_loss "+
            "FROM FoodLossEvent f "+
            "WHERE year BETWEEN '"+fromYear+"'"+" and "+"'"+toYear+"' "+
            
            (applyCommodityFilter ? "AND f.commodity = '"+foodGroup+"' " : "") +
            
            "GROUP BY f.commodity, f.Activity, f.year, f.food_supply_stage, f.cause_off_loss "+ 
            "ORDER BY " + orderBy + " " + sortDir + " ;";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String commodity = results.getString("commodity");
                Integer year = results.getInt("year");
                Double lossPercentage = results.getDouble("loss_percentage");
                String activity = results.getString("activity");
                String foodSupplyStage = results.getString("food_supply_stage");
                String causeOffLoss = results.getString("cause_off_loss");

                // Create a Country Object
                Task2B foodLossEvent = new Task2B(commodity, year, lossPercentage, activity, foodSupplyStage, causeOffLoss);

                // Add the Country object to the array
                foodLossEvents.add(foodLossEvent);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return foodLossEvents;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        System.out.println("a");
        Connection connection = null;

try {
    // Connect to JDBC database
    connection = DriverManager.getConnection(DATABASE);
    System.out.println("b");

    // Prepare a new SQL Query & Set a timeout
    Statement statement = connection.createStatement();
    statement.setQueryTimeout(30); // set timeout to 30 sec.
    System.out.println("a");

    // Execute the query
    ResultSet results = statement.executeQuery("SELECT * FROM Persona");

    // Process all of the results
    while (results.next()) {
        String background = results.getString("background");
        String needs = results.getString("needs");
        String imageFilePath = results.getString("imageFilePath");

        User user = new User(background, needs, imageFilePath);

        users.add(user);
    }

    // Close the statement because we are done with it
    statement.close();
} catch (SQLException e) {
    // If there is an error, let's just print the error
    System.out.println("err");
    System.err.println(e.getMessage());
} finally {
    // Safety code to cleanup
    try {
        if (connection != null) {
            connection.close();
        }
    } catch (SQLException e) {
        // Connection close failed.
        System.err.println(e.getMessage());
    }
}


        return users;
    }
}
