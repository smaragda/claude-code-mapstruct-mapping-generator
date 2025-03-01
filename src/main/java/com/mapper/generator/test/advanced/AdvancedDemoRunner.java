package com.mapper.generator.test.advanced;

import com.mapper.generator.core.MapperGenerator;

/**
 * Runner class to demonstrate mapping between complex classes with differently named properties.
 */
public class AdvancedDemoRunner {
    
    public static void main(String[] args) {
        System.out.println("Advanced MapStruct Mapper Generator Demo");
        System.out.println("=======================================");
        
        MapperGenerator generator = new MapperGenerator();
        
        // Generate mapper between entity and DTO classes that have different property names
        String mapperCode = generator.generateMapper(
                WorkerEntity.class,
                EmployeeDto.class,
                "WorkerToEmployeeMapper"
        );
        
        // Print the generated mapper
        System.out.println(mapperCode);
    }
}