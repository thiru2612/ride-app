package com.rideapp.api.route.ride;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rideapp.api.entity.RideHistory;
import com.rideapp.api.modules.RideHistoryService;
import com.rideapp.api.utils.XMLValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RideByMode extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject res = new JSONObject();
        JSONObject queryParams = new JSONObject();
        queryParams.put("userId", request.getParameter("userId"));
        queryParams.put("mode", request.getParameter("mode"));

        // ✅ Convert JSON to XML dynamically using `getRidebyMode` as the root element
        String xmlRequest = convertJsonToXml(queryParams, "getRidebyMode");

        // ✅ Validate XML using `validation.xsd`
        boolean isValid = XMLValidator.validateXMLString(xmlRequest, "C:\\Users\\ASUS\\eclipse-workspace\\ride_app_api\\src\\main\\resources\\validation.xsd");
        if (!isValid) {
            res.put("status", "error");
            res.put("message", "Invalid XML Request for getRidebyMode");
            response.getWriter().write(res.toString());
            return;
        }

        // ✅ Process request if validation passes
        int userId = queryParams.getInt("userId");
        String mode = queryParams.getString("mode");

        RideHistoryService historyService = new RideHistoryService();
        List<RideHistory> rideHistoryList = historyService.getRideMode(userId, mode);

        res.put("status", "success");
        res.put("message", "Ride request validated successfully!");
        res.put("data", rideHistoryList.isEmpty() ? "No rides found" : new JSONArray(rideHistoryList));

        response.getWriter().write(res.toString());
    }

    private String convertJsonToXml(JSONObject jsonObject, String rootElement) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<").append(rootElement).append(">");
        xmlBuilder.append("<userId>").append(jsonObject.optInt("userId")).append("</userId>");
        xmlBuilder.append("<mode>").append(jsonObject.optString("mode")).append("</mode>");
        xmlBuilder.append("</").append(rootElement).append(">");
        return xmlBuilder.toString();
    }
}