package com.mapper.generator.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class for string similarity calculations and property matching.
 */
public class StringSimilarity {

    // Minimum similarity threshold for considering a match
    private static final double SIMILARITY_THRESHOLD = 0.4;
    // Bonus for exact word matches
    private static final double EXACT_WORD_BONUS = 0.4;

    /**
     * Calculates normalized Levenshtein similarity between two strings.
     * Returns a value between 0 (completely different) and 1 (identical).
     */
    public static double levenshteinSimilarity(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return 0.0;
        }
        
        // If strings are identical, return perfect score
        if (str1.equals(str2)) {
            return 1.0;
        }
        
        // Calculate base Levenshtein similarity
        int distance = StringUtils.getLevenshteinDistance(str1, str2);
        int maxLength = Math.max(str1.length(), str2.length());
        
        // Prevent division by zero
        if (maxLength == 0) {
            return 1.0;
        }
        
        // Convert distance to similarity (0 to 1)
        double similarity = 1.0 - ((double) distance / maxLength);
        
        // Add bonus for matching specific words - this helps with properties like "firstName" and "childName"
        // by identifying the common "Name" part
        String[] words1 = str1.split("(?<=[a-z])(?=[A-Z])|_");
        String[] words2 = str2.split("(?<=[a-z])(?=[A-Z])|_");
        
        double wordMatchBonus = 0.0;
        for (String word1 : words1) {
            for (String word2 : words2) {
                if (word1.equalsIgnoreCase(word2) && word1.length() > 2) {  // Only consider significant words
                    wordMatchBonus += EXACT_WORD_BONUS * ((double) word1.length() / maxLength);
                }
            }
        }
        
        // Cap total similarity at 1.0
        return Math.min(1.0, similarity + wordMatchBonus);
    }
    
    /**
     * Finds the best matches between source and target property names based on Levenshtein similarity.
     * Returns a map from source property to matched target property.
     */
    public static Map<String, String> matchProperties(List<String> sourceProps, List<String> targetProps) {
        List<PropertyMatch> allMatches = new ArrayList<>();
        
        // Calculate similarity for each source-target pair
        for (String source : sourceProps) {
            for (String target : targetProps) {
                double similarity = levenshteinSimilarity(source, target);
                
                // Only consider matches above the threshold
                if (similarity >= SIMILARITY_THRESHOLD) {
                    allMatches.add(new PropertyMatch(source, target, similarity));
                }
            }
        }
        
        // Sort by similarity (highest first)
        allMatches.sort(Comparator.comparing(PropertyMatch::getSimilarity).reversed());
        
        // Assign best matches first (greedy algorithm)
        List<String> assignedSources = new ArrayList<>();
        List<String> assignedTargets = new ArrayList<>();
        
        return allMatches.stream()
                .filter(match -> !assignedSources.contains(match.getSource()) 
                              && !assignedTargets.contains(match.getTarget()))
                .peek(match -> {
                    assignedSources.add(match.getSource());
                    assignedTargets.add(match.getTarget());
                })
                .collect(Collectors.toMap(
                    PropertyMatch::getSource,
                    PropertyMatch::getTarget
                ));
    }
    
    /**
     * Helper class to store property matches with their similarity score.
     */
    private static class PropertyMatch {
        private final String source;
        private final String target;
        private final double similarity;
        
        public PropertyMatch(String source, String target, double similarity) {
            this.source = source;
            this.target = target;
            this.similarity = similarity;
        }
        
        public String getSource() {
            return source;
        }
        
        public String getTarget() {
            return target;
        }
        
        public double getSimilarity() {
            return similarity;
        }
    }
}