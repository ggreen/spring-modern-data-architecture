package spring.modern.data.analytics;

import org.springframework.ai.model.ollama.autoconfigure.OllamaChatAutoConfiguration;
import org.springframework.ai.model.ollama.autoconfigure.OllamaEmbeddingAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("openai")
@EnableAutoConfiguration(exclude = {
        OllamaEmbeddingAutoConfiguration.class,
        OllamaChatAutoConfiguration.class
})
public class OpenAiConfig {
}
