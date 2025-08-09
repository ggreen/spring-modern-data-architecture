package spring.modern.data.analytics.consumers;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.ai.ollama.management.PullModelStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Profile("ollama") // Only load this config if "ollama" is in active profiles
public class OllamaConfig {

    @Bean
    public ChatClient ollamaChatClient() {
        OllamaApi api =  OllamaApi.builder()
                .baseUrl("http://localhost:11434")
                .build();

        OllamaChatModel model = OllamaChatModel.builder()
                .ollamaApi(api)
                .modelManagementOptions(
                        ModelManagementOptions
                        .builder()
                        .pullModelStrategy(PullModelStrategy.ALWAYS)
                        .build())
                .defaultOptions(OllamaOptions.builder()
                        .model("llama3").format("json").build())
                .build();

        return ChatClient.builder(model).build();
    }
}