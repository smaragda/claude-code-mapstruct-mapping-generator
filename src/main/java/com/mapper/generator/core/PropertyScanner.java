package com.mapper.generator.core;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to scan and extract property information from a given class.
 */
public class PropertyScanner {
    
    /**
     * Extracts all properties (fields with getters and potentially setters) from a class.
     * 
     * @param clazz The class to scan for properties
     * @return A list of PropertyInfo objects representing the properties of the class
     */
    public static List<PropertyInfo> extractProperties(Class<?> clazz) {
        List<PropertyInfo> properties = new ArrayList<>();
        Map<String, Method> getters = new HashMap<>();
        Map<String, Method> setters = new HashMap<>();
        
        // Scan all public methods
        for (Method method : clazz.getMethods()) {
            // Skip methods from Object class
            if (method.getDeclaringClass() == Object.class) {
                continue;
            }
            
            // Skip static methods
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            
            String methodName = method.getName();
            
            // Find getter methods
            if ((methodName.startsWith("get") && methodName.length() > 3) || 
                (methodName.startsWith("is") && method.getReturnType() == boolean.class && methodName.length() > 2)) {
                
                // Extract property name from method name
                String prefix = methodName.startsWith("get") ? "get" : "is";
                String propertyName = methodName.substring(prefix.length());
                propertyName = Introspector.decapitalize(propertyName);
                
                // Ensure it's a valid getter (no parameters)
                if (method.getParameterCount() == 0 && method.getReturnType() != void.class) {
                    getters.put(propertyName, method);
                }
            }
            
            // Find setter methods
            if (methodName.startsWith("set") && methodName.length() > 3) {
                // Extract property name from method name
                String propertyName = methodName.substring(3);
                propertyName = Introspector.decapitalize(propertyName);
                
                // Ensure it's a valid setter (one parameter)
                if (method.getParameterCount() == 1) {
                    setters.put(propertyName, method);
                }
            }
        }
        
        // Match getters with their corresponding setters
        for (Map.Entry<String, Method> entry : getters.entrySet()) {
            String propertyName = entry.getKey();
            Method getter = entry.getValue();
            Method setter = setters.get(propertyName);
            
            // We only consider properties with both getters and setters
            if (setter != null) {
                // Ensure setter parameter type matches getter return type
                if (setter.getParameterTypes()[0].equals(getter.getReturnType())) {
                    properties.add(new PropertyInfo(propertyName, getter, setter));
                }
            }
        }
        
        return properties;
    }
}