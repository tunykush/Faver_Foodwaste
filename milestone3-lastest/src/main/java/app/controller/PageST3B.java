package app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.config.JDBCConnection;
import app.entities.Commodity;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PageST3B implements Handler {

    public static final String URL = "/page3B.html";

    @Override
    public void handle(Context context) throws Exception {
        JDBCConnection connection = new JDBCConnection();

        // Get form parameters
        String commodityCode = context.formParam("commodityCode");

        String similarityMetric = context.formParam("similarityMetric");

        String limit = context.formParam("limit");    
        if (limit == null) limit = "5";
        String sort = context.formParam("sort");
        if (sort == null) sort = "asc";

        
        if (commodityCode == null || similarityMetric == null) {
            commodityCode = ""; 
            similarityMetric = ""; 
        }

        // Retrieve data based on form parameters
        List<Commodity> cpcCodeList = connection.getCpcCodes();
        List<Map<String, Object>> similarGroups = null;

       
        if (!commodityCode.isEmpty() && !similarityMetric.isEmpty()) {
            //System.out.println(similarityMetric);
            similarGroups = connection.getSimilarGroups(commodityCode, similarityMetric,limit,sort);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("cpcCodes", cpcCodeList);
        model.put("similarityMetric",similarityMetric);
        model.put("similarGroups", similarGroups);

        context.render("/templates/page3B.html", model);
    }
}


