package spring.modern.data.architecture.valkey.console.controller.conversion;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.stereotype.Component;
import spring.modern.data.architecture.valkey.console.domain.Command;

import java.util.Arrays;

/**
 * @author gregory Green
 */
@Component
public class TextToCommand implements Converter<String, Command> {

    /**
     *
     * @param sourceObject the object to convert
     * @return the command
     */
    @Override
    public Command convert(String sourceObject) {

        if(sourceObject == null || sourceObject.isEmpty())
            return null;
        
        var tokens = sourceObject.split("\\s+");

        String[] parameters = Arrays.copyOfRange(tokens, 1, tokens.length);

        return Command.builder().name(tokens[0])
                .parameters(parameters)
                .build();
    }
}
