package com.rideapp.api.route.ride;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rideapp.api.entity.RideHistory;
import com.rideapp.api.modules.RideHistoryService;
import com.rideapp.api.utils.InputValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RideByDate extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    JSONObject res = new JSONObject();
	    JSONObject queryParams = new JSONObject();
	    queryParams.put("userId", request.getParameter("userId"));
	    queryParams.put("date", request.getParameter("date"));

	    // ✅ Validate input
	    if (!InputValidator.validate("getRidebyDate", queryParams)) {
	        res.put("message", "Invalid parameters or missing required fields");
	    } else {
	        int userId = queryParams.getInt("userId"); // ✅ Safely gets converted value
	        String date = queryParams.getString("date");

	        Date t=null;
	        try {
	        	t=Date.valueOf(date);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error in string to date conversion");
			}
	        RideHistoryService historyService = new RideHistoryService();
	        List<RideHistory> rideHistoryList = historyService.getRideDate(userId, t);

	        res.put("data", rideHistoryList.isEmpty() ? "No rides found" : new JSONArray(rideHistoryList));
	    }
	    response.getWriter().write(res.toString());
	}
}
