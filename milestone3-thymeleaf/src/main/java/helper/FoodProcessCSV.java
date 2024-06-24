package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Stand-alone Java file for processing the database CSV files.
 * <p>
 * You can run this file using the "Run" or "Debug" options
 * from within VSCode. This won't conflict with the web server.
 * <p>
 * This program opens a CSV file from the Food Loss data set
 * and uses JDBC to load up data into the database.
 * <p>
 * To use this program you will need to change:
 * 1. The input file location
 * 2. The output file location
 * <p>
 * This assumes that the CSV files are in the **database** folder.
 * <p>
 * WARNING: This code may take quite a while to run as there could be a lot
 * of SQL insert statments and queries to run!
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */
public class FoodProcessCSV {

   // MODIFY these to load/store to/from the correct locations
   private static final String DATABASE = "jdbc:sqlite:database/foodloss.db";
   private static final String FOOD_CSV_FILE = "database/FoodLoss.csv";
   private static final String CPC_CSV_FILE = "database/CPC.csv";
   private static final int RECORD_PERCENT = 27411/100;
   private static final int START_YEAR = 1966;
   private static final int END_YEAR = 2025;

   public static void main (String[] args) {
      
      // Drops the date, country and class tables then recreates them
      // This only needs to be done once (unless your tables need to be updated and recreated)
      // Comment this out after runnning it the first time
      // ****WARNING**** 
      // ****WARNING**** do not run this again accidentily as it will remove data
      // ****WARNING**** 
      dropTablesAndRecreateTables();


      // Load up the Date table
      // This only needs to be done once
      // Comment this out after runnning it the first time
      loadYears();


      // loads all the 'Class' level codes into class table
      // note it does not load any sub class OR sub sub classes (or divisions, sections, groups)
      // need to update this to handle this (based on your design)
      // Comment this out after runnning it the first time
       loadCpcClass();


      // Load up the Country table
      // This only needs to be done once
      // Comment this out after runnning it the first time
      // Create a copy of this and update to load data into your other tables
       loadCountries();


      // read foodloss csv and check for matching country code and class codes in created tables
      // verifies the loaded data
      // you can also copy this to insert records after a lookup into your other tables
      checkCountryAndClassCodesMatch();

      return;
   }

   // Drops and recreates empty date, country and class tables
   // Add additional create statements to create the rest of your tables
      public static void dropTablesAndRecreateTables() {
      // JDBC Database Object
      Connection connection = null;
      Scanner s = new Scanner(System.in);
      String response = null;

      System.out.println("\nWARNING: existing tables will be dropped and recreated\nAre you sure? (y/n)");
      response = s.nextLine();
      while(!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n"))
      {
         response = s.nextLine();
      }
      if(response.equalsIgnoreCase("n")){
         System.out.println("aborting");
         System.out.println("Comment out 'dropTablesAndRecreateTables();' to avoid deleting tables and run again");
         System.exit(0);
      }
      // Like JDBCConnection, we need some error handling.
      try {
         connection = DriverManager.getConnection(DATABASE);

         
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();

         // Create Insert Statement
         String query = null;
         query = "PRAGMA foreign_keys = OFF";
         System.out.println("Executing: \n" + query);
         statement.execute(query);
         query = "DROP TABLE IF EXISTS Country";
         System.out.println("Executing: \n" + query);
         statement.execute(query);
         query = "DROP TABLE IF EXISTS Date";
         System.out.println("Executing: \n" + query);
         statement.execute(query);
         query = "DROP TABLE IF EXISTS Class";
         System.out.println("Executing: \n" + query);
         statement.execute(query);
         query = "PRAGMA foreign_keys = ON";
         System.out.println("Executing: \n" + query);
         statement.execute(query);

         query =  "CREATE TABLE Country ( " + "\n" +
                  "     m49Code            TEXT NOT NULL," + "\n" +
                  "     countryName        TEXT NOT NULL," + "\n" +
                  "     PRIMARY KEY (m49Code)" + "\n" +
                  ")";
                      
         System.out.println("Executing: \n" + query);
         statement.execute(query);

         query =  "CREATE TABLE Date (" + "\n" +
                  "      year              INTEGER NOT NULL," + "\n" +
                  "      PRIMARY KEY (year)" + "\n" +
                  ")";
         System.out.println("Executing: \n" + query);
         statement.execute(query);

         query =  "CREATE TABLE Class (" + "\n" +
                  "      classNo           TEXT NOT NULL," + "\n" +
                  "      className         TEXT NOT NULL," + "\n" +
                  "      PRIMARY KEY (classNo)" + "\n" +
                  ")";
         System.out.println("Executing: \n" + query);
         statement.execute(query);
         System.out.println("\ndropped and recreated tables\npress enter to continue");
         System.in.read();

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void loadYears() {
      // JDBC Database Object
      Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         connection = DriverManager.getConnection(DATABASE);

         for (int i = START_YEAR; i != END_YEAR; ++i) {
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();

            // Create Insert Statement
            String query = "INSERT into Date VALUES ("
                           + i
                           + ")";

            // Execute the INSERT
            System.out.println("Executing: \n" + query);
            statement.execute(query);
         }
         System.out.println("\ninserted all years\npress enter to continue");
         System.in.read();

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // loads all the 'Class' level codes into class table
   // note it does not load any sub class OR sub sub classes (or divisions, sections, groups)
   // need to update this to handle this (based on your design)
   public static void loadCpcClass() {
      // JDBC Database Object
      Connection connection = null;
      PreparedStatement statement = null;
      BufferedReader reader = null;
      String line;

      // We need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         reader = new BufferedReader(new FileReader(CPC_CSV_FILE));

         // Read the first line of "headings"
         String header = reader.readLine();
         System.out.println("Heading row\n" + header + "\n");

         // Setup JDBC
         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         //read CSV file line by line, stop if not more lines
         while ((line = reader.readLine())!=null) {
            //System.out.println(row);
            // split the line up by commas (ignoring commas within quoted fields)
            String[] splitline = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            // Get all of the columns in order (and remove double quuotes)
            // Note you will need to do the same in the FoodLoss.csv to match
            String cpcGroupSectionDivision = (splitline[ClassFields.GROUP_SECTION_DIVISION]).replaceAll("^\"|\"$", "");
            String cpcClass = (splitline[ClassFields.CLASS]).replaceAll("^\"|\"$", "");
            String cpcSubClass = (splitline[ClassFields.SUBCLASS]).replaceAll("^\"|\"$", "");
            String cpcDescription = (splitline[ClassFields.DESCRIPTION]).replaceAll("^\"|\"$", "");

            // only dealing with cpc codes at the class level
            // you will need to update code to also insert sub classes and sub classes (and division/section/group)
            // insert if record has a class code but no sub class code (its a class only)
            if(!cpcClass.equals("") && cpcSubClass.equals(""))
            {
                  // Create Insert Statement
               String myStatement = " INSERT INTO Class (classNo, className) VALUES (?, ?)";
               statement= connection.prepareStatement(myStatement );
               statement.setString(1, cpcClass);
               statement.setString(2, cpcDescription);
               System.out.println(statement.toString());
               statement.executeUpdate();
            }
         }
         System.out.println("\ninserted all class level cpc code\npress enter to continue");
         System.in.read();

      } catch (Exception e) {
         e.printStackTrace();
      }
      finally {
         if(reader!=null) {
            try{
            reader.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }

   public static void loadCountries() {
      // JDBC Database Object
      Connection connection = null;
      PreparedStatement statement = null;
      HashMap<String, String> countries = new HashMap<String, String>();
      BufferedReader reader = null;
      String line;

      // We need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         reader = new BufferedReader(new FileReader(FOOD_CSV_FILE));

         // Read the first line of "headings"
         String header = reader.readLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         //read CSV file line by line, stop if not more lines
         while ((line = reader.readLine())!=null) {

            // split the line up by commas (ignoring commas within quoted fields)
            String[] splitline = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            // Get all of the columns in order
            String m49Code = splitline[CountryFields.M49CODE];
            String countryName = splitline[CountryFields.COUNTRYNAME];

            // Note: the rest of the attributes are not used, but you can copy this method 
            // and modify to load appropriate attributes into your other tables using insert statements

            // String regionName = splitline[CountryFields.REGIONAME];
            // String cpcCode = splitline[CountryFields.CPCCODE];
            // // remove double quotes from commodity
            // String commodity = splitline[CountryFields.COMMODITY].replaceAll("^\"|\"$", "");
            // String year = splitline[CountryFields.YEAR];
            // String lossPercentage = splitline[CountryFields.LOSSPERCENT];
            // String activity = splitline[CountryFields.ACTIVITY];
            // String foodSupplyStage = splitline[CountryFields.SUPPLYSTAGE];
            // String causeOfLoss = splitline[CountryFields.LOSSCAUSE];

            // check that the country code does not already exists by trying to insert into a hashmap data structure
            if(countries.put(m49Code, countryName) == null){
               //doesn't exists - insert it
               // Create Insert Statement
               String myStatement = " INSERT INTO Country (m49Code, countryName) VALUES (?, ?)";
               statement= connection.prepareStatement(myStatement );
               statement.setString(1, m49Code);
               statement.setString(2, countryName);
               System.out.println(statement.toString());
               statement.executeUpdate();
            }
         }
         System.out.println("\ninserted all countries\npress enter to continue");
         System.in.read();

      } catch (Exception e) {
         e.printStackTrace();
      }
      finally {
         if(reader!=null) {
            try{
            reader.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }


   public static void checkCountryAndClassCodesMatch() {
      // JDBC Database Object
      Connection connection = null;
      Statement statement = null;
      BufferedReader reader = null;
      String line;
      String query = "";
      int row = 1;

      // We need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         reader = new BufferedReader(new FileReader(FOOD_CSV_FILE));

         System.out.println("begining check of country and 'class' level cpc code\nthis will take some time\npress enter to continue");
         System.in.read();

         // Read the first line of "headings"
         String header = reader.readLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         //read CSV file line by line, stop if not more lines
         while ((line = reader.readLine())!=null) {
            //System.out.println(row);

            // split the line up by commas (ignoring commas within quoted fields)
            String[] splitline = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

           // Get all of the columns in order
           String m49Code = splitline[CountryFields.M49CODE];
           String countryName = splitline[CountryFields.COUNTRYNAME];

           // Note: the rest of the attributes are not used, but you can copy this method 
           // and modify to do a lookup and then load appropriate attributes into your other tables
           // String regionName = splitline[CountryFields.REGIONAME];
           String cpcCode = splitline[CountryFields.CPCCODE];
           // // remove double quotes from commodity
           // String commodity = splitline[CountryFields.COMMODITY].replaceAll("^\"|\"$", "");
           // String year = splitline[CountryFields.YEAR];
           // String lossPercentage = splitline[CountryFields.LOSSPERCENT];
           // String activity = splitline[CountryFields.ACTIVITY];
           // String foodSupplyStage = splitline[CountryFields.SUPPLYSTAGE];
           // String causeOfLoss = splitline[CountryFields.LOSSCAUSE];

            statement = connection.createStatement();
            query =  "SELECT *" + "\n" + 
                     "FROM country" + "\n" +  
                     "WHERE m49Code = \"" + m49Code + "\" ";
            //System.out.println("Looking up: \n" + query);

            ResultSet results = statement.executeQuery(query);

            // where any matching rows found?
            if(!results.isBeforeFirst())
            {
               System.out.println("Country code not Found in country table!");
               System.out.println("Double check that all the codes have been loaded into country");
               System.out.println("You may need to run loadCountries() again");
               return;
            }
            else if(results.next()) {
               countryName = results.getString("countryName");
            }

            if(cpcCode.length() <= ClassFields.CLASS_LENGTH) {
               query =  "SELECT *" + "\n" + 
                        "FROM Class" + "\n" +  
                        "WHERE ClassNo = \"" + cpcCode + "\" ";
               // System.out.println("Looking up: \n" + query);

               results = statement.executeQuery(query);

               // where any matching rows found?
               if(!results.isBeforeFirst())
               {
                  System.out.println("Class level code not found in class table!");
                  System.out.println("Double check that all the class codes have been loaded into class");
                  System.out.println("You may need to run loadCpcClass() again");
                  return;
               }
               else if(results.next()) {
                  countryName = results.getString("className");
               }
            }

            /*
             *  you can insert code here to insert records based on the values found
             */

            row++;
            if(row%RECORD_PERCENT==0){
               System.out.println((row/RECORD_PERCENT) + "% of records checked");
            }
         }

         System.out.println("\nAll Country codes and 'Class' level cpc codes found");
         System.out.println("press enter to continue");
         System.in.read();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Query being run when exception occured:");
         System.out.println(query);
      }
      finally {
         if(reader!=null) {
            try{
            reader.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }
}