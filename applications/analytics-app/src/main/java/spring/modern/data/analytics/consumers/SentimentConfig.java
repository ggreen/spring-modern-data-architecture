package spring.modern.data.analytics.consumers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.modern.data.analytics.consumers.service.SentimentService;
import spring.modern.data.domains.customer.reviews.Sentiment;

@Configuration
@Slf4j
public class SentimentConfig {

    @Bean
    QuestionAnswerAdvisor advisor(VectorStore vectorStore){
        return new QuestionAnswerAdvisor(vectorStore);
    }


    @Bean
    SentimentService sentimentService(ChatModel chatModel, QuestionAnswerAdvisor advisor)
    {

        return text -> {
            String prompt = """
            Analyze the sentiment of this text: "{text}".
            Respond with only one word: Positive, or Negative
            """;
            var  sentiment = ChatClient.create(chatModel).prompt()
                    .user(u -> u.text(prompt)
                            .param("text", text))
                    .advisors(advisor)
                    .call()
                    .entity(Sentiment.class);
            log.info("text: {}, Sentiment:{}",text, sentiment);

            return sentiment;
        };

    }
}
