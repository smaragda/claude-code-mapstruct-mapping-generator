package com.mapper.generator.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class to track and detect cyclic dependencies during type processing.
 */
public class CyclicDependencyTracker {
    
    private final Set<String> processing = new HashSet<>();
    
    /**
     * Starts tracking a class for cyclic dependency detection.
     * 
     * @param className The fully qualified class name to track
     * @return true if the class can be processed, false if it would create a cycle
     */
    public boolean startProcessing(String className) {
        if (processing.contains(className)) {
            return false; // Cycle detected
        }
        processing.add(className);
        return true;
    }
    
    /**
     * Stops tracking a class after processing is complete.
     * 
     * @param className The fully qualified class name to stop tracking
     */
    public void finishProcessing(String className) {
        processing.remove(className);
    }
}