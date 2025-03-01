package com.mapper.generator;

import com.mapper.generator.core.MapperGenerator;

/**
 * Main application class for the MapStruct mapper generator.
 */
public class MapperGeneratorApp {
    
    /**
     * Application entry point.
     * 
     * @param args Command line arguments: 
     *             args[0] = fully qualified source class name
     *             args[1] = fully qualified target class name
     *             args[2] = (optional) mapper interface name
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java -jar mapper-generator.jar <source-class> <target-class> [mapper-name]");
            System.err.println("Example: java -jar mapper-generator.jar com.example.SourceDto com.example.TargetDto");
            System.exit(1);
        }
        
        String sourceClassName = args[0];
        String targetClassName = args[1];
        String mapperName = args.length > 2 ? args[2] : generateMapperName(sourceClassName, targetClassName);
        
        try {
            // Load classes using reflection
            Class<?> sourceClass = Class.forName(sourceClassName);
            Class<?> targetClass = Class.forName(targetClassName);
            
            // Generate the mapper
            MapperGenerator generator = new MapperGenerator();
            String mapperCode = generator.generateMapper(sourceClass, targetClass, mapperName);
            
            // Output the generated code
            System.out.println(mapperCode);
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Class not found - " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error generating mapper: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Generates a default mapper name based on source and target class names.
     */
    private static String generateMapperName(String sourceClassName, String targetClassName) {
        String sourceSimpleName = sourceClassName.substring(sourceClassName.lastIndexOf('.') + 1);
        String targetSimpleName = targetClassName.substring(targetClassName.lastIndexOf('.') + 1);
        return sourceSimpleName + "To" + targetSimpleName + "Mapper";
    }
}