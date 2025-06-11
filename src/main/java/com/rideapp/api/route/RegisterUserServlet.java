package com.rideapp.api.route;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;

import com.rideapp.api.modules.CreateUser;
import com.rideapp.api.utils.InputValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterUserServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    StringBuilder jsonBuilder = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        jsonBuilder.append(line);
	    }
	    JSONObject jsonObject = new JSONObject(jsonBuilder.toString());

	    JSONObject responseObject = new JSONObject();
	    if (!InputValidator.validate("RegisterUser", jsonObject)) {
	        responseObject.put("message", "Invalid input format or missing required fields");
	    } else {
	        CreateUser cu = new CreateUser();
	        int userId = cu.createUser(jsonObject.getString("username"), jsonObject.getString("password"),
	                                   jsonObject.getString("contact"), jsonObject.getInt("age"));
	        if (userId != -1) {
	            responseObject.put("userId", userId);
	            responseObject.put("username", jsonObject.getString("username"));
	            responseObject.put("userType", "user");
	            responseObject.put("age", jsonObject.getInt("age"));
	            responseObject.put("message", "User account creation successful");
	        } else {
	            responseObject.put("message", "Registration failed! Please try again");
	        }
	    }

	    response.getWriter().write(responseObject.toString());
	}
}
