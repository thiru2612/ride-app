package com.rideapp.api.route;

import java.io.IOException;

import org.json.JSONObject;

import com.rideapp.api.modules.DeleteUserService;
import com.rideapp.api.utils.InputValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteUserServlet extends HttpServlet{
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    JSONObject res = new JSONObject();
	    JSONObject queryParams = new JSONObject();
	    queryParams.put("userId", request.getParameter("userId"));
	    queryParams.put("date", request.getParameter("date"));

	    // ✅ Validate input
	    if (!InputValidator.validate("deleteUser", queryParams)) {
	        res.put("message", "Invalid parameters or missing required fields");
	    } else {
	        int userId = queryParams.getInt("userId"); // ✅ Safely gets converted value

	        DeleteUserService delService = new DeleteUserService();
	        boolean isDeleted = delService.deleteUser(userId);

	        res.put("message", isDeleted ? "Deletion of userId "+userId+" is success." : 
	        	"deletion of userId "+userId+" is failed!");
	    }
	    response.getWriter().write(res.toString());
	}
}
