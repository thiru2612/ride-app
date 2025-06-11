package com.rideapp.api.route;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.rideapp.api.modules.BookRideService;
import com.rideapp.api.utils.XMLValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookRideServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject responseJson = new JSONObject();
        JSONObject queryParams = new JSONObject();

        // Read JSON request
        StringBuilder jsonBuilder = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        String jsonInput = jsonBuilder.toString();
        System.out.println("Received JSON: " + jsonInput);

        //  Parse JSON using `JSONObject`
        JSONObject jsonObject = new JSONObject(jsonInput);
        queryParams.put("userId", jsonObject.optInt("userId", 0));
        queryParams.put("mode", jsonObject.optString("mode", ""));
        queryParams.put("pickup", jsonObject.optInt("pickup", 0));
        queryParams.put("drop", jsonObject.optInt("drop", 0));
        queryParams.put("date", jsonObject.optString("date", ""));
        queryParams.put("paymode", jsonObject.optString("paymode", ""));
        queryParams.put("review", jsonObject.optString("review", "Good"));
        queryParams.put("rating", jsonObject.optInt("rating", 0));
        queryParams.put("tStatus", jsonObject.optString("tStatus", ""));

        //  Convert JSON to XML dynamically using `BookRide` as the root element
        String xmlRequest = convertJsonToXml(queryParams, "BookRide");

        //  Validate XML using `validation.xsd`
        boolean isValid = XMLValidator.validateXMLString(xmlRequest, "C:\\Users\\ASUS\\eclipse-workspace\\ride_app_api\\src\\main\\resources\\validation.xsd");
        if (!isValid) {
            responseJson.put("status", "error");
            responseJson.put("message", "Invalid XML Request for BookRide");
            response.getWriter().write(responseJson.toString());
            return;
        }

        //  Process request if validation passes
        int userId = queryParams.getInt("userId");
        String mode = queryParams.getString("mode");
        int ploc = queryParams.getInt("pickup");
        int dloc = queryParams.getInt("drop");
        String time = queryParams.getString("date");
        String pmode = queryParams.getString("paymode");
        String rev = queryParams.getString("review");
        int rating = queryParams.getInt("rating");
        String tStatus = queryParams.getString("tStatus");

        //  Convert date string to Timestamp if present
        Timestamp t = null;
        if (!time.isEmpty()) {
            try {
                t = Timestamp.valueOf(time);
            } catch (IllegalArgumentException e) {
                response.getWriter().write("{\"error\":\"Invalid date format. Expected: yyyy-MM-dd HH:mm:ss\"}");
                return;
            }
        }

        //  Process ride booking after validation
        BookRideService bs = new BookRideService();
        HashMap<String, Double> res = bs.bookRide(userId, mode, ploc, dloc, t, pmode, rev, rating, tStatus);

        if (res != null) {
            Map.Entry<String, Double> entry = res.entrySet().iterator().next();
            String d = entry.getKey();
            Double amount = entry.getValue();
            
            responseJson.put("userId", userId);
            responseJson.put("message", "Ride added successfully");
            responseJson.put("date", (t != null) ? d : "No specific time provided");
            responseJson.put("amount", amount);
        } else {
            responseJson.put("error", "Failed to book the ride. Please try again.");
        }

        response.getWriter().write(responseJson.toString());
    }

    private String convertJsonToXml(JSONObject jsonObject, String rootElement) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<").append(rootElement).append(">");
        xmlBuilder.append("<userId>").append(jsonObject.optInt("userId")).append("</userId>");
        xmlBuilder.append("<mode>").append(jsonObject.optString("mode")).append("</mode>");
        xmlBuilder.append("<pickup>").append(jsonObject.optInt("pickup")).append("</pickup>");
        xmlBuilder.append("<drop>").append(jsonObject.optInt("drop")).append("</drop>");
        xmlBuilder.append("<paymode>").append(jsonObject.optString("paymode")).append("</paymode>");
        xmlBuilder.append("<rating>").append(jsonObject.optInt("rating")).append("</rating>");
        xmlBuilder.append("<tStatus>").append(jsonObject.optString("tStatus")).append("</tStatus>");
        xmlBuilder.append("<review>").append(jsonObject.optString("review")).append("</review>");
        xmlBuilder.append("<date>").append(jsonObject.optString("date")).append("</date>");
        xmlBuilder.append("</").append(rootElement).append(">");
        return xmlBuilder.toString();
    }
}
