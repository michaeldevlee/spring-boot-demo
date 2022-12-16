package com.example.employee.sys.common;

import com.example.employee.sys.exceptions.InternalServerException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    
    private Utils() {}

    public static String getJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new InternalServerException("There's a problem converting this object to a JSON string: " + obj);
        }
    }  
}
