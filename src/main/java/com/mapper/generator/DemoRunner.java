package com.mapper.generator;

import com.mapper.generator.core.MapperGenerator;
import com.mapper.generator.test.SourceExample;
import com.mapper.generator.test.TargetExample;

/**
 * Demo class to show how to use the mapper generator programmatically.
 */
public class DemoRunner {
    
    public static void main(String[] args) {
        System.out.println("MapStruct Mapper Generator Demo");
        System.out.println("==============================");
        
        MapperGenerator generator = new MapperGenerator();
        
        // Generate mapper between the example classes
        String mapperCode = generator.generateMapper(
                SourceExample.class,
                TargetExample.class,
                "SourceToTargetMapper"
        );
        
        // Print the generated mapper
        System.out.println(mapperCode);
        
        System.out.println("\nTo use from command line:");
        System.out.println("java -jar mapper-generator.jar com.mapper.generator.test.SourceExample com.mapper.generator.test.TargetExample");
    }
}