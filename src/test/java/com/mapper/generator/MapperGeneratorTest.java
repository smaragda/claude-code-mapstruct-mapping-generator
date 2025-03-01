package com.mapper.generator;

import com.mapper.generator.core.MapperGenerator;
import com.mapper.generator.test.SourceExample;
import com.mapper.generator.test.TargetExample;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapperGeneratorTest {
    
    @Test
    public void testGenerateMapper() {
        MapperGenerator generator = new MapperGenerator();
        String mapperCode = generator.generateMapper(
                SourceExample.class, 
                TargetExample.class, 
                "SourceExampleToTargetExampleMapper"
        );
        
        assertNotNull(mapperCode);
        
        // Print the generated code first to debug
        System.out.println("Generated mapper code:");
        System.out.println(mapperCode);
        
        // Check for expected content in the generated mapper
        assertTrue(mapperCode.contains("public interface SourceExampleToTargetExampleMapper"));
        assertTrue(mapperCode.contains("TargetExample mapSourceExampleToTargetExample(SourceExample sourceExample)"));
        
        // With the improved similarity algorithm, there might be fewer matches,
        // but they should be more accurate. Let's check for the obvious match.
        assertTrue(mapperCode.contains("@Mapping(source = \"name\", target = \"fullName\")"));
    }
}