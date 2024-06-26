package app.config;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import app.dto.Task2B;
import app.entities.Commodity;
import app.entities.Country;
import app.entities.Date;
import app.entities.FoodLossEvent;
import app.entities.SimilarityData;
import app.entities.SimilarityScore;
import app.entities.Student;
import app.entities.User;

import java.util.Map;
import java.util.stream.Collectors;


public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:milestone3-lastest/database/foodloss.db";

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
            String query = "SELECT *  FROM commodity WHERE group_code='' ORDER BY Descriptor";

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

    public ArrayList<String> getLandingData() {
            ArrayList<String> data = new ArrayList<>();
            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DATABASE);
                Statement statementYr = connection.createStatement();
                Statement statementLoss = connection.createStatement();
                Statement statementComm = connection.createStatement();
                String yrQuery = "SELECT MIN(year) as minYear, MAX(year) as maxYear from FoodLossEvent";
                String lossQuery = "SELECT MAX(loss_percentage) as maxLoss from FoodLossEvent";
                String commQuery = "SELECT commodity, loss_percentage, year from FoodLossEvent WHERE loss_percentage = (SELECT MAX(loss_percentage) from FoodLossEvent)";
                ResultSet yrRs = statementYr.executeQuery(yrQuery);
                ResultSet lRs = statementLoss.executeQuery(lossQuery);
                ResultSet cRs = statementComm.executeQuery(commQuery);

                while(yrRs.next()) {
                    String min = yrRs.getString("minYear");
                    String max = yrRs.getString("maxYear");

                    data.add(min);
                    data.add(max);
                }

                while(lRs.next()) {
                    String loss = lRs.getString("maxLoss");

                    data.add(loss);
                }

                while(cRs.next()) {
                    String comm = cRs.getString("commodity");
                    String yr = cRs.getString("year");

                    data.add(comm);
                    data.add(yr);
                }
                
            } catch(SQLException e) {
                System.err.println(e);
            }

            return data;
    }

    public ArrayList<Task2B> getAllFoodLossEvent(String fromYear, String toYear,
            List<String> foodGroupList, String orderBy, boolean ac, boolean fs, boolean ca,String orderByString) {
        ArrayList<Task2B> foodLossEvents = new ArrayList<Task2B>();

        if (foodGroupList == null)
            return foodLossEvents;

        String filter = "", f2 = "", filter1 = "";
        if (ac) {
            filter += ",activity";
            filter1 += ",activity as activity1";
            f2 += " and s.activity=e.activity1";
        }
        if (fs) {
            filter += ",food_supply_stage";
            filter1 += ",food_supply_stage as food_supply_stage1";
            f2 += " and s.food_supply_stage=e.food_supply_stage1";
        }
        if (ca) {
            filter += ",cause_of_loss";
            filter1 += ",cause_of_loss as cause_of_loss1";
            f2 += " and s.cause_of_loss=e.cause_of_loss1";
        }

        String fGStr = "";
        fGStr = "foodgroup= '" + foodGroupList.get(0) + "'";
        for (int i = 1; i < foodGroupList.size(); i++) {
            fGStr += " or foodgroup= '" + foodGroupList.get(i) + "'";
        }

        
        Connection connection = null;

        try {
           
            connection = DriverManager.getConnection(DATABASE);

           
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);


            String query = "with com as (";
            query += " select c1.cpc_code as cpc_code,c2.descriptor as foodgroup from commodity c1 join commodity c2 on c1.group_code=c2.cpc_code)";
            query += " select (endd-start)/start*100 as diff,* from";
            query += " (SELECT foodgroup,avg(loss_percentage) as start " + filter;
            query += " FROM (FoodLossEvent f join com c on f.cpc_code=c.cpc_code)";
            query += " WHERE (year= '" + fromYear + "') and (" + fGStr + ")";
            query += " GROUP BY foodgroup " + filter + ") s";
            
            query += " full outer join";
            query += " (SELECT foodgroup as foodgroup1,avg(loss_percentage) as endd " + filter1;
            query += " FROM (FoodLossEvent f join com c on f.cpc_code=c.cpc_code)";
            query += " WHERE (year= '" + toYear + "') and (" + fGStr + ")";
            query += " GROUP BY foodgroup " + filter + ") e";
            query += " on s.foodgroup=e.foodgroup1" + f2;
            query += " order by diff " + orderByString + "";

            ResultSet results = statement.executeQuery(query);
            
            while (results.next()) {
                String commodity = results.getString("foodgroup") != null ? results.getString("foodgroup")
                        : results.getString("foodgroup1");
                double start = results.getDouble("start");
                double end = results.getDouble("endd");
                Double lossPercentage = results.getDouble("diff");
                String activity = ac ? (results.getString("activity") != null ? results.getString("activity")
                        : results.getString("activity1")) : null;
                String foodSupplyStage = fs ? results.getString("food_supply_stage") : null;
                String causeOffLoss = ca ? results.getString("cause_of_loss") : null;
                
                Task2B foodLossEvent = new Task2B(commodity, start, end, lossPercentage, activity, foodSupplyStage,
                        causeOffLoss);

                
                foodLossEvents.add(foodLossEvent);
            }

           
            statement.close();
        } catch (SQLException e) {
            
            System.err.println(e.getMessage());
        } finally {
            
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
               
                System.err.println(e.getMessage());
            }
        }

        
        return foodLossEvents;
    }

    public List<Map<String, Object>> getSimilarGroups(String commodityCode,
                                                      String similarityMetric, String limit, String sort) {
        List<Map<String, Object>> results = new ArrayList<>();

        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        try {
            connection = DriverManager.getConnection(DATABASE);

            
            String sql = "";

            if(similarityMetric.equals("avg")) {
                sql+="with t as (";
                sql+="       select t1.groupp,loss/waste as val from ( ";
                sql+="           select";
                sql+="        groupp,max(loss) as loss";
                sql+="       from (";
                sql+="        select";
                sql+="            cpc_code,substr(cpc_code,1,3) as groupp, avg(loss_percentage) as loss";
                sql+="        from";
                sql+="            foodlossevent";
                sql+="        where";
                sql+="            lostorwaste=1 or lostorwaste=2";
                sql+="         group by";
                sql+="             cpc_code";
                sql+="         )";
                sql+="         group by groupp";
                sql+="         ) t1";
                sql+="         join";
                sql+="         (";
                sql+="         select";
                sql+="         groupp,max(waste) as waste";
                sql+="         from (";
                sql+="         select";
                sql+="             cpc_code,substr(cpc_code,1,3) as groupp, avg(loss_percentage) as waste";
                sql+="         from";
                sql+="             foodlossevent";
                sql+="         where";
                sql+="             lostorwaste=0 or lostorwaste=2";
                sql+="         group by";
                sql+="             cpc_code";
                sql+="         )";
                sql+="         group by groupp";
                sql+="         ) t2 on t1.groupp=t2.groupp";
                sql+="         )";
                sql+="         select descriptor,val,diff from (";
                sql+="         select *,abs(val-(select val from t where groupp=substr('"+commodityCode+"',1,3))) as diff";
                sql+="         from t";
                sql+="         where groupp<>substr('"+commodityCode+"',1,3)";
                sql+="         order by diff";
                sql+="         limit "+limit;
                sql+="         ) res join commodity on commodity.cpc_code=res.groupp";
                sql+="         order by diff "+sort;
            } else {
                sql+=" with t as (";
                sql+="    select ";
                sql+="        groupp,"+similarityMetric+"(aa) as val ";
                sql+="    from ( ";
                sql+="    select ";
                sql+="        cpc_code,substr(cpc_code,1,3) as groupp,avg(loss_percentage) as aa ";
                sql+="    from ";
                sql+="        foodlossevent ";
                sql+="    group by ";
                sql+="        cpc_code ";
                sql+="    ) ";
                sql+="    group by ";
                sql+="        groupp ";
                sql+="    ),res as ( ";
                sql+="    select ";
                sql+="        *,abs(val-(select val from t where groupp=substr('"+commodityCode+"',1,3))) as diff ";
                sql+="    from ";
                sql+="        t ";
                sql+="    where ";
                sql+="        groupp<>substr('"+commodityCode+"',1,3) ";
                sql+="    order by ";
                sql+="        diff ";
                sql+="    limit "+limit;
                sql+="    ) ";
                sql+="    select c.descriptor,res.val,res.diff from res join commodity c on res.groupp=c.cpc_code ";
                sql+="    order by diff "+sort;
            }

            

            preparedStatement = connection.prepareStatement(sql);

           
            resultSet = preparedStatement.executeQuery();

           
            while (resultSet.next()) {
                DecimalFormat df = new DecimalFormat("#,###.###");
                Map<String, Object> row = new HashMap<>();
                row.put("group_name", resultSet.getString("descriptor"));
                row.put("value", df.format(resultSet.getDouble("val")));
                row.put("similarity_score", df.format(resultSet.getDouble("diff")));
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    public List<Commodity> getCpcCodes() {
        List<Commodity> cpcCodes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE);

          
            String sql = """
                        WITH CommodityGroup AS (
                            SELECT DISTINCT c.group_code
                            FROM Commodity c
                        ), GroupMetrics AS (
                            SELECT
                                c.group_code,
                                AVG(f.loss_percentage) AS avg_loss,
                                MAX(f.loss_percentage) AS max_loss,
                                MIN(f.loss_percentage) AS min_loss
                            FROM FoodLossEvent f
                            JOIN Commodity c ON f.cpc_code = c.cpc_code
                            WHERE c.group_code IN (SELECT group_code FROM CommodityGroup)
                            GROUP BY c.group_code
                        ), SelectedGroupMetrics AS (
                            SELECT
                                g.group_code,
                                g.avg_loss,
                                g.max_loss,
                                g.min_loss
                            FROM GroupMetrics g
                            JOIN CommodityGroup cg ON g.group_code = cg.group_code
                        ), SimilarGroups AS (
                            SELECT
                                gm.group_code,
                                gm.avg_loss,
                                gm.max_loss,
                                gm.min_loss,
                                ABS(gm.avg_loss - sg.avg_loss) AS avg_diff,
                                ABS(gm.max_loss - sg.max_loss) AS max_diff,
                                ABS(gm.min_loss - sg.min_loss) AS min_diff
                            FROM GroupMetrics gm
                            CROSS JOIN SelectedGroupMetrics sg
                        )
                        SELECT DISTINCT c.cpc_code, c.descriptor, c.group_code
                        FROM Commodity c
                        JOIN SimilarGroups s ON c.group_code = s.group_code
                        ORDER BY c.cpc_code;
                    """;

            preparedStatement = connection.prepareStatement(sql);

           
            resultSet = preparedStatement.executeQuery();

            
            while (resultSet.next()) {
                String cpcCode = resultSet.getString("cpc_code");
                String descriptor = resultSet.getString("descriptor");
                String groupCode = resultSet.getString("group_code");
                Commodity commodity = new Commodity(cpcCode, descriptor, groupCode);

                cpcCodes.add(commodity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        } finally {
            
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
               
            }
        }

        return cpcCodes;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        Connection connection = null;

        try {
           
            connection = DriverManager.getConnection(DATABASE);

           
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

           
            ResultSet results = statement.executeQuery("SELECT * FROM Persona");

           
            while (results.next()) {
                String background = results.getString("background");
                String needs = results.getString("needs");
                String imageFilePath = results.getString("imageFilePath");

                User user = new User(background, needs, imageFilePath);

                users.add(user);
            }

         
            statement.close();
        } catch (SQLException e) {
           

            System.err.println(e.getMessage());
        } finally {
      
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
             
                System.err.println(e.getMessage());
            }
        }

        return users;
    }

     public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();

        Connection connection = null;

        try {
            
            connection = DriverManager.getConnection(DATABASE);

           
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30); 

           
            ResultSet results = statement.executeQuery("SELECT * FROM Student");

           
            while (results.next()) {
                String name = results.getString("name");
                String studentID = results.getString("studentID");

                Student student = new Student(name, studentID);

                students.add(student);
            }

           
            statement.close();
        } catch (SQLException e) {
           

            System.err.println(e.getMessage());
        } finally {
          
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
              
                System.err.println(e.getMessage());
            }
        }

        return students;
    }

    public List<FoodLossEvent> getFoodLossEvents(List<String> country, String startYear, String endYear, String sortField, String sortOrder) {
        Connection connection = null;
    List<FoodLossEvent> events = new ArrayList<>();

    if (country == null || country.isEmpty()) {
        return events; 
    }

    try {
        connection = DriverManager.getConnection(DATABASE);

      
        StringBuilder lossQuery = new StringBuilder("SELECT t1.country, t1.start_loss, t1.activity, t1.commodity, t1.cause_of_loss, t1.food_supply_stage, t2.end_loss, t2.activity, t2.commodity, t2.cause_of_loss, t2.food_supply_stage FROM (");
        lossQuery.append("SELECT country, avg(loss_percentage) as start_loss, activity, commodity, cause_of_loss, food_supply_stage FROM FoodLossEvent WHERE country IN (");

        for (int i = 0; i < country.size(); i++) {
            if (i > 0) {
                lossQuery.append(",");
            }
            lossQuery.append("?");
        }

        lossQuery.append(") AND year = ? GROUP BY country) as t1 ");
        lossQuery.append("JOIN (SELECT country, avg(loss_percentage) as end_loss, activity, commodity, cause_of_loss, food_supply_stage FROM FoodLossEvent WHERE country IN (");

        for (int i = 0; i < country.size(); i++) {
            if (i > 0) {
                lossQuery.append(",");
            }
            lossQuery.append("?");
        }

        lossQuery.append(") AND year = ? GROUP BY country) as t2 ");
        lossQuery.append("ON t1.country = t2.country");
        lossQuery.append(" ORDER BY ").append(sortField).append(" ").append(sortOrder);

        
        
        PreparedStatement statement = connection.prepareStatement(lossQuery.toString());

        
        int index = 1;
        for (String c : country) {
            statement.setString(index++, c);
        }
        statement.setString(index++, startYear);

        
        for (String c : country) {
            statement.setString(index++, c);
        }
        statement.setString(index, endYear);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String countryName = resultSet.getString("country");
            String startLoss = resultSet.getString("start_loss");
            String endLoss = resultSet.getString("end_loss");
            String activity = resultSet.getString("activity");
            String commodity = resultSet.getString("commodity");
            String causeOfLoss = resultSet.getString("cause_of_loss");
            String supplyStage = resultSet.getString("food_supply_stage");

            if(isEmptyString(activity)) activity = null;
            if(isEmptyString(commodity)) commodity = null;
            if(isEmptyString(causeOfLoss)) causeOfLoss = null;
            if(isEmptyString(supplyStage)) supplyStage = null;

            double dStartLoss = 0;
            double dEndLoss = 0;
            if (startLoss != null && endLoss != null) {
                String increaseOrDecrease = null;
                 dStartLoss = Double.parseDouble(startLoss);
                 dEndLoss = Double.parseDouble(endLoss);

                double lossDiff = Math.abs(((dStartLoss - dEndLoss) / dStartLoss) * 100);

                String formattedLossDiff = String.format("%.3f%%", lossDiff);
                if(lossDiff > 0) {
                    increaseOrDecrease = "↑ ";
                } else if(lossDiff < 0) {
                    increaseOrDecrease = "↓ ";  
                } else {
                    increaseOrDecrease = "";
                }
                
                FoodLossEvent event = new FoodLossEvent(Arrays.asList(countryName), String.format("%.3f%%", dStartLoss), String.format("%.3f%%", dEndLoss), increaseOrDecrease + formattedLossDiff, startYear, endYear, commodity, activity, supplyStage, causeOfLoss);
                events.add(event);
            } else {
                FoodLossEvent event = new FoodLossEvent(Arrays.asList(countryName), String.format("%.3f%%", dStartLoss), String.format("%.3f%%", dEndLoss), "N/A", startYear, endYear,  commodity, activity, supplyStage, causeOfLoss);
                events.add(event);
            }
        }
    } catch (SQLException e) {
        System.err.println(e);
    } finally {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    return events;
}

public List<String> getAllLocations() {
    List<String> locations = new ArrayList<>();
    String query = "SELECT DISTINCT country FROM FoodLossEvent UNION SELECT DISTINCT region FROM FoodLossEvent";
    
    try (Connection connection = DriverManager.getConnection(DATABASE);
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet rs = statement.executeQuery()) {

        while (rs.next()) {
            String location = rs.getString("country");
            locations.add(location);
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return locations;
}

 public List<SimilarityData> getSimilarityData(String selectedYear, String selectedCountry, String criteria) {
        String query = "";
        if ("common_products".equals(criteria) || "both".equals(criteria)) {
            query = """
                WITH SelectedCountryData AS (
                    SELECT FLE.cpc_code, FLE.loss_percentage
                    FROM FoodLossEvent FLE
                    WHERE FLE.year = ? AND (FLE.country = ? OR FLE.region = ?)
                ),
                ComparisonData AS (
                    SELECT FLE.country, FLE.region, FLE.cpc_code, FLE.loss_percentage
                    FROM FoodLossEvent FLE
                    WHERE FLE.year = ? AND (FLE.country != ? AND FLE.region != ?)
                )
                SELECT CD.country, CD.region, COUNT(DISTINCT CD.cpc_code) AS common_products,
                       COUNT(DISTINCT SC.cpc_code) + COUNT(DISTINCT CD.cpc_code) AS total_products,
                       AVG(CD.loss_percentage) AS average_loss_percentage
                FROM ComparisonData CD
                JOIN SelectedCountryData SC ON CD.cpc_code = SC.cpc_code
                GROUP BY CD.country, CD.region
                ORDER BY common_products DESC;
            """;
        } else if ("overall_percentage".equals(criteria)) {
            query = """
                WITH SelectedCountryData AS (
                    SELECT FLE.loss_percentage
                    FROM FoodLossEvent FLE
                    WHERE FLE.year = ? AND (FLE.country = ? OR FLE.region = ?)
                ),
                ComparisonData AS (
                    SELECT FLE.country, FLE.region, AVG(FLE.loss_percentage) AS average_loss_percentage
                    FROM FoodLossEvent FLE
                    WHERE FLE.year = ? AND (FLE.country != ? AND FLE.region != ?)
                    GROUP BY FLE.country, FLE.region
                )
                SELECT CD.country, CD.region, AVG(CD.average_loss_percentage) AS average_loss_percentage
                FROM ComparisonData CD
                JOIN SelectedCountryData SC ON 1 = 1
                GROUP BY CD.country, CD.region
                ORDER BY average_loss_percentage DESC;
            """;
        } else {
            
            throw new IllegalArgumentException("Invalid criteria: " + criteria);
        }
    
        List<SimilarityData> data = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
    
        try {
            connection = DriverManager.getConnection(DATABASE);
            statement = connection.prepareStatement(query);
    
            statement.setString(1, selectedYear);
            statement.setString(2, selectedCountry);
            statement.setString(3, selectedCountry);
            statement.setString(4, selectedYear);
            statement.setString(5, selectedCountry);
            statement.setString(6, selectedCountry);
    
            rs = statement.executeQuery();
    
            while (rs.next()) {
                String country = rs.getString("country");
                String region = rs.getString("region");
                int commonProducts = 0;
                int totalProducts = 0;
                if ("common_products".equals(criteria) || "both".equals(criteria)) {
                    commonProducts = rs.getInt("common_products");
                    totalProducts = rs.getInt("total_products");
                }
                double averageLossPercentage = rs.getDouble("average_loss_percentage");
    
                data.add(new SimilarityData(country, region, commonProducts, totalProducts, averageLossPercentage));
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    
        return data;
    }

    //EXTRA FUNCTIONS
    boolean isEmptyString(String s) {
        return s.isEmpty();
    }

    public List<SimilarityScore> getSimilarityScores(String location, String year, String similarityType, String similarityLevel, int numResults) {
        List<SimilarityData> dataList = getSimilarityData(year, location, similarityType);
        List<SimilarityScore> similarityScores = calculateSimilarityScores(dataList, similarityLevel, similarityType);

        return similarityScores.subList(0, Math.min(numResults, similarityScores.size()));
    }



    public List<SimilarityScore> calculateSimilarityScores(List<SimilarityData> dataList, String calculationMethod, String criteria) {
        return dataList.stream().map(data -> {
            double absoluteSimilarity = data.getCommonProducts();
            double levelOfOverlap = (double) data.getCommonProducts() / data.getTotalProducts();
            if ("overall_percentage".equals(criteria) || "both".equals(criteria)) {
                absoluteSimilarity = data.getAverageLossPercentage();
                levelOfOverlap = data.getAverageLossPercentage();
            }
            return new SimilarityScore(data.getCountry(), data.getRegion(), absoluteSimilarity, levelOfOverlap);
        }).sorted((s1, s2) -> {
            int cmp = Double.compare(s2.getAbsoluteSimilarity(), s1.getAbsoluteSimilarity());
            if (cmp != 0) return cmp;
            return Double.compare(s2.getLevelOfOverlap(), s1.getLevelOfOverlap());
        }).collect(Collectors.toList());
    }




}
