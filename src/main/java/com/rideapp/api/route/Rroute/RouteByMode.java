package com.rideapp.api.route.Rroute;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rideapp.api.entity.RouteHistory;
import com.rideapp.api.modules.RouteHistoryService;
import com.rideapp.api.utils.InputValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RouteByMode extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    JSONObject res = new JSONObject();
	    JSONObject queryParams = new JSONObject();
	    queryParams.put("routeId", request.getParameter("routeId"));
	    queryParams.put("mode", request.getParameter("mode"));

	    // ✅ Validate input
	    if (!InputValidator.validate("getRoutebyMode", queryParams)) {
	        res.put("message", "Invalid parameters or missing required fields");
	    } else {
	        int routeId = queryParams.getInt("routeId"); // ✅ Safely gets converted value
	        String mode = queryParams.getString("mode");
	       
	        RouteHistoryService historyService = new RouteHistoryService();
	        List<RouteHistory> rideHistoryList = historyService.getRouteHistory(routeId, mode);

	        res.put("data", rideHistoryList.isEmpty() ? "No rides found" : new JSONArray(rideHistoryList));
	    }
	    response.getWriter().write(res.toString());
    }
}