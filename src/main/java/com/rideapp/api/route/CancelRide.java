package com.rideapp.api.route;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;

import org.json.JSONObject;

import com.rideapp.api.modules.CancelRideService;
import com.rideapp.api.utils.XMLValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CancelRide extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        String userIdParam=request.getParameter("userId");
        int userId=Integer.parseInt(userIdParam);
        // Parse JSON using `JSONObject`
        JSONObject jsonObject = new JSONObject(jsonInput);
        queryParams.put("userId", userId);
        queryParams.put("date", jsonObject.optString("date", ""));

        // Convert JSON to XML dynamically using `cancelRide` as the root element
        String xmlRequest = convertJsonToXml(queryParams, "cancelRide");

        // Validate XML using `validation.xsd`
        boolean isValid = XMLValidator.validateXMLString(xmlRequest, "C:\\Users\\ASUS\\eclipse-workspace\\ride_app_api\\src\\main\\resources\\validation.xsd");
        if (!isValid) {
            responseJson.put("status", "error");
            responseJson.put("message", "Invalid XML Request for cancelRide");
            response.getWriter().write(responseJson.toString());
            return;
        }

        // Process request if validation passes
        String date = queryParams.getString("date");

        // Convert date string to Timestamp if present
        Timestamp t = null;
        if (!date.isEmpty()) {
            try {
                t = Timestamp.valueOf(date);
            } catch (IllegalArgumentException e) {
                response.getWriter().write("{\"error\":\"Invalid date format. Expected: yyyy-MM-dd HH:mm:ss\"}");
                return;
            }
        }

        // Process ride cancellation after validation
        CancelRideService crs = new CancelRideService();
        String resp = crs.cancelRide(userId, t);

        responseJson.put("message", resp);
        response.getWriter().write(responseJson.toString());
    }

    private String convertJsonToXml(JSONObject jsonObject, String rootElement) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<").append(rootElement).append(">");
        xmlBuilder.append("<userId>").append(jsonObject.optInt("userId")).append("</userId>");
        xmlBuilder.append("<date>").append(jsonObject.optString("date")).append("</date>");
        xmlBuilder.append("</").append(rootElement).append(">");
        return xmlBuilder.toString();
    }
}
