package com.mapper.generator.core;

import com.mapper.generator.util.CyclicDependencyTracker;
import com.mapper.generator.util.StringSimilarity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The main class that generates MapStruct mapper interfaces based on provided source and target classes.
 */
public class MapperGenerator {
    
    private final CyclicDependencyTracker cyclicDependencyTracker = new CyclicDependencyTracker();
    private final Set<String> processedMappers = new HashSet<>();
    private final Map<String, String> nestedMapperInterfaces = new HashMap<>();
    
    /**
     * Generates a MapStruct mapper interface for converting between source and target classes.
     * 
     * @param sourceClass The source class
     * @param targetClass The target class
     * @param mapperName The name for the generated mapper interface
     * @return String containing the full mapper interface code
     */
    public String generateMapper(Class<?> sourceClass, Class<?> targetClass, String mapperName) {
        nestedMapperInterfaces.clear();
        processedMappers.clear();
        
        StringBuilder mainMapper = new StringBuilder();
        
        // Generate the package statement (using the target class's package)
        mainMapper.append("package ").append(targetClass.getPackage().getName()).append(";\n\n");
        
        // Generate imports
        Set<String> imports = new HashSet<>();
        imports.add("org.mapstruct.Mapper");
        imports.add("org.mapstruct.Mapping");
        imports.add("org.mapstruct.ReportingPolicy");
        
        // Add source and target class imports if needed
        if (!sourceClass.getPackage().getName().equals(targetClass.getPackage().getName())) {
            imports.add(sourceClass.getName());
        }
        
        for (String importClass : imports) {
            mainMapper.append("import ").append(importClass).append(";\n");
        }
        mainMapper.append("\n");
        
        // Generate the mapper interface declaration
        mainMapper.append("/**\n")
                .append(" * Mapper interface for converting between ")
                .append(sourceClass.getSimpleName())
                .append(" and ")
                .append(targetClass.getSimpleName())
                .append(".\n")
                .append(" */\n")
                .append("@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)\n")
                .append("public interface ").append(mapperName).append(" {\n\n");
        
        // Generate the main mapping method with mappings
        String sourceParam = uncapitalize(sourceClass.getSimpleName());
        mainMapper.append("    /**\n")
                .append("     * Maps a ").append(sourceClass.getSimpleName())
                .append(" to a ").append(targetClass.getSimpleName()).append(".\n")
                .append("     */\n");
        
        // Generate mappings between properties
        List<PropertyInfo> sourceProps = PropertyScanner.extractProperties(sourceClass);
        List<PropertyInfo> targetProps = PropertyScanner.extractProperties(targetClass);
        
        List<String> sourceNames = sourceProps.stream().map(PropertyInfo::getName).collect(Collectors.toList());
        List<String> targetNames = targetProps.stream().map(PropertyInfo::getName).collect(Collectors.toList());
        
        Map<String, String> propertyMatches = StringSimilarity.matchProperties(sourceNames, targetNames);
        
        List<String> mappingAnnotations = new ArrayList<>();
        
        for (Map.Entry<String, String> match : propertyMatches.entrySet()) {
            String sourcePropName = match.getKey();
            String targetPropName = match.getValue();
            
            // Find the PropertyInfo objects for this match
            PropertyInfo sourceProp = findProperty(sourceProps, sourcePropName);
            PropertyInfo targetProp = findProperty(targetProps, targetPropName);
            
            if (sourceProp != null && targetProp != null) {
                // Check if we need to create a nested mapper for complex types
                if (!sourceProp.isPrimitive() && !targetProp.isPrimitive()) {
                    // Handle collection/array types
                    Class<?> sourceType = sourceProp.isCollection() || sourceProp.isArray() || sourceProp.isMap() 
                            ? sourceProp.getComponentType() : sourceProp.getType();
                    Class<?> targetType = targetProp.isCollection() || targetProp.isArray() || targetProp.isMap() 
                            ? targetProp.getComponentType() : targetProp.getType();
                    
                    // Generate nested mapper if needed and not a cycle
                    String nestedMapperName = sourceType.getSimpleName() + "To" + targetType.getSimpleName() + "Mapper";
                    String mapperKey = sourceType.getName() + " -> " + targetType.getName();
                    
                    if (!processedMappers.contains(mapperKey) && 
                        !sourceType.equals(sourceClass) && !targetType.equals(targetClass) &&
                        !sourceType.equals(targetType) &&
                        cyclicDependencyTracker.startProcessing(mapperKey)) {
                        
                        try {
                            String nestedMapper = generateMapper(sourceType, targetType, nestedMapperName);
                            nestedMapperInterfaces.put(nestedMapperName, nestedMapper);
                            processedMappers.add(mapperKey);
                        } finally {
                            cyclicDependencyTracker.finishProcessing(mapperKey);
                        }
                    }
                }
                
                // Skip mapping annotation if property names match exactly
                if (!sourcePropName.equals(targetPropName)) {
                    mappingAnnotations.add("    @Mapping(source = \"" + sourcePropName + "\", target = \"" + targetPropName + "\")");
                }
            }
        }
        
        // Add mapping annotations if any
        if (!mappingAnnotations.isEmpty()) {
            mainMapper.append(String.join("\n", mappingAnnotations)).append("\n");
        }
        
        // Add the method signature
        mainMapper.append("    ").append(targetClass.getSimpleName())
                .append(" map").append(sourceClass.getSimpleName()).append("To").append(targetClass.getSimpleName())
                .append("(").append(sourceClass.getSimpleName()).append(" ").append(sourceParam).append(");\n");
        
        // Close the interface
        mainMapper.append("}\n");
        
        // Include all nested mapper interfaces
        StringBuilder fullOutput = new StringBuilder(mainMapper);
        for (Map.Entry<String, String> entry : nestedMapperInterfaces.entrySet()) {
            fullOutput.append("\n\n").append(entry.getValue());
        }
        
        return fullOutput.toString();
    }
    
    private PropertyInfo findProperty(List<PropertyInfo> properties, String name) {
        for (PropertyInfo property : properties) {
            if (property.getName().equals(name)) {
                return property;
            }
        }
        return null;
    }
    
    private String uncapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + (str.length() > 1 ? str.substring(1) : "");
    }
}