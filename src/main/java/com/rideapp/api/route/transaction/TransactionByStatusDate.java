package com.rideapp.api.route.transaction;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.rideapp.api.entity.TransactionHistory;
import com.rideapp.api.modules.THistoryService;
import com.rideapp.api.utils.InputValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TransactionByStatusDate extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    JSONObject res = new JSONObject();
	    JSONObject queryParams = new JSONObject();
	    queryParams.put("userId", request.getParameter("userId"));
	    queryParams.put("date", request.getParameter("date"));
	    queryParams.put("status", request.getParameter("status"));

	    // ✅ Validate input
	    if (!InputValidator.validate("getTransactionbyStatusDate", queryParams)) {
	        res.put("message", "Invalid parameters or missing required fields");
	    } else {
	        int userId = queryParams.getInt("userId"); // ✅ Safely gets converted value
	        String date = queryParams.getString("date");
	        String status = queryParams.getString("status");
	        
	        Date t=null;
	        try {
	        	t=Date.valueOf(date);
			} catch (Exception e) {
				System.out.println("error converting from string to date");
				e.printStackTrace();
			}

	        THistoryService historyService = new THistoryService();
	        List<TransactionHistory> rideHistoryList = historyService.getTStatusDate(userId,status, t);

	        res.put("data", rideHistoryList.isEmpty() ? "No rides found" : new JSONArray(rideHistoryList));
	    }
	    response.getWriter().write(res.toString());
    }
}