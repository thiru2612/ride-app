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

public class RouteById extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    JSONObject res = new JSONObject();
	    JSONObject queryParams = new JSONObject();
	    queryParams.put("routeId", request.getParameter("routeId"));

	    // ✅ Validate input
	    if (!InputValidator.validate("getRouteHistory", queryParams)) {
	        res.put("message", "Invalid parameters or missing required fields");
	    } else {
	        int routeId = queryParams.getInt("routeId"); // ✅ Safely gets converted value

	        RouteHistoryService historyService = new RouteHistoryService();
	        List<RouteHistory> rideHistoryList = historyService.getRouteHistory(routeId);

	        res.put("data", rideHistoryList.isEmpty() ? "No rides found" : new JSONArray(rideHistoryList));
	    }
	    response.getWriter().write(res.toString());
    }
}