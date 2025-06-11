package com.rideapp.api.utils;

import java.io.InputStream;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;

public class InputValidator {
    private static JSONObject validationRules;

    // Load validation rules from JSON file on startup
    static {
        try (InputStream inputStream = InputValidator.class.getClassLoader().getResourceAsStream("validation.json");) {
            if (inputStream != null) {
                validationRules = new JSONObject(new JSONTokener(inputStream));
                System.out.println(" Loaded validation rules: " + validationRules.toString(2));  // Debugging output
            } else {
                throw new RuntimeException(" Validation rules file not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  Validate user input based on JSON rules
    public static boolean validate(String apiName, JSONObject userInput) {
        if (!validationRules.has(apiName)) {
            System.out.println("❌ Validation rules for " + apiName + " not found.");
            return false; // API validation rules missing
        }

        JSONObject rules = validationRules.getJSONObject(apiName);
        for (String key : rules.keySet()) {
            List<Object> rule = rules.getJSONArray(key).toList();
            String expectedType = (String) rule.get(0);
            int maxSize = (Integer) rule.get(1);
            int required = (Integer) rule.get(2);

            Object value = userInput.opt(key);
            System.out.println(" Validating field: " + key + " | Value: " + value); // Debugging log

            //  Check required fields
            if (required == 1 && (value == null || value.toString().trim().isEmpty())) {
                System.out.println(" Missing required field: " + key);
                return false;
            }

            //  Handle type mismatches dynamically
            if (value != null) {
                if (expectedType.equals("integer")) {
                    if (value instanceof String) {
                        try {
                            value = Integer.parseInt((String) value); //  Convert safely
                            userInput.put(key, value); //  Update the input JSON
                        } catch (NumberFormatException e) {
                            System.out.println(" Invalid integer format for field: " + key);
                            return false;
                        }
                    } else if (!(value instanceof Integer)) {
                        System.out.println(" Type mismatch: Expected integer for field: " + key);
                        return false;
                    }
                }

                if (expectedType.equals("string") && !(value instanceof String)) {
                    System.out.println(" Type mismatch: Expected string for field: " + key);
                    return false;
                }

                if (expectedType.equals("string") && ((String) value).length() > maxSize) {
                    System.out.println(" Field exceeds max length: " + key + " (" + maxSize + ")");
                    return false;
                }
            }
        }
        return true; //  All validations passed
    }
}