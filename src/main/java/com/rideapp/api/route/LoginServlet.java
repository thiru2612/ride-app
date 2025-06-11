package com.rideapp.api.route;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;

import com.rideapp.api.modules.LoginUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ✅ Read JSON request
        StringBuilder jsonBuilder = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        String jsonInput = jsonBuilder.toString();
        System.out.println("Received JSON: " + jsonInput);
        JSONObject jsonObject = new JSONObject(jsonInput);
        
        String username=jsonObject.optString("username");
        String password=jsonObject.optString("password");

        JSONObject responseObject=new JSONObject();
        if(username.equals("")||username==null||
            	password.equals("")||password==null) {
            	responseObject.put("message","missing a input attribute");
        }
        else {
	        LoginUser lu=new LoginUser();
	        int userId=lu.login(username, password);
	        if(userId!=-1) {
	        	responseObject.put("userId",userId);
	        	responseObject.put("username",username);
	        	responseObject.put("message","Login Successful");
	        }
	        else {
				responseObject.put("message","Login failed!user does not exist");
			}
        }
        response.getWriter().write(responseObject.toString());
    }
}