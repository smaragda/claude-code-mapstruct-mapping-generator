# MapStruct Mapper Generator

A Java application that automatically generates MapStruct mapper interfaces by analyzing classes using reflection and matching properties based on Levenshtein distance similarity.

## Features

- Automatically analyzes source and target classes using Java Reflection
- Matches properties based on Levenshtein distance for intelligent property mapping
- Handles nested object types, collections, arrays, and maps
- Detects and prevents cyclic dependencies
- Outputs MapStruct mapper interfaces that can be directly used in your projects

## Requirements

- Java 11 or higher
- Maven for building the project

## Building the Application

```bash
mvn clean package
```

This will create a JAR file with dependencies in the `target` directory.

## Usage

### Command Line

```bash
java -jar target/mapper-generator-1.0-SNAPSHOT-jar-with-dependencies.jar <source-class> <target-class> [mapper-name]
```

Example:

```bash
java -jar target/mapper-generator-1.0-SNAPSHOT-jar-with-dependencies.jar com.example.SourceClass com.example.TargetClass
```

The generated mapper interface will be output to the console, which you can redirect to a file if needed:

```bash
java -jar target/mapper-generator-1.0-SNAPSHOT-jar-with-dependencies.jar com.example.SourceClass com.example.TargetClass > SourceToTargetMapper.java
```

### Programmatic Usage

```java
import com.mapper.generator.core.MapperGenerator;

// Create the generator instance
MapperGenerator generator = new MapperGenerator();

// Generate the mapper interface
String mapperCode = generator.generateMapper(
    SourceClass.class,
    TargetClass.class,
    "SourceToTargetMapper"
);

// Use the generated code
System.out.println(mapperCode);
```

## How It Works

1. The application uses reflection to analyze the source and target classes
2. It extracts all properties (fields with getters and setters) from both classes
3. Properties are matched based on Levenshtein distance similarity between their names
4. For complex types (non-primitives), it recursively generates nested mappers
5. Cyclic dependencies are detected and handled to prevent infinite recursion
6. The final output is a MapStruct mapper interface with appropriate @Mapping annotations

## Example

For classes like:

```java
// Source class
public class Person {
    private String name;
    private int age;
    
    // getters and setters
}

// Target class
public class PersonDto {
    private String fullName;
    private int personAge;
    
    // getters and setters
}
```

The generated mapper would be:

```java
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonToPersonDtoMapper {

    @Mapping(source = "name", target = "fullName")
    @Mapping(source = "age", target = "personAge")
    PersonDto mapPersonToPersonDto(Person person);
}
```

## Notes

- The application requires that the classes being mapped are in the classpath
- Both source and target classes must have proper JavaBean-style getters and setters
- Generated mappers use `ReportingPolicy.IGNORE` for unmapped target properties

## License

MIT License