package spring.modern.data.analytics.service.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.stereotype.Service;
import spring.modern.data.domains.customer.CustomerIdentifier;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.domains.customer.reviews.Sentiment;
import spring.modern.data.domains.customer.reviews.SentimentResponse;

import java.util.Collections;
import java.util.List;

/**
 * @author gregory green
 * @param chatClient the chat client
 * @param advisor the Vector RAG advisor
 */
@Service
@Slf4j
public record ChatModelService(ChatClient chatClient,
                               QuestionAnswerAdvisor advisor) implements AiService {


    /*
    You are a marketing expert, create a short marketing
            messaging less than 70 characters for the product for the products [Jelly,Peanut]
            to a person named nyl, respond only with the marketing message
     */
    private static final String promotionPrompt = """
            You are a marketing expert, create a short marketing 
            messaging less than 70 characters for the product for the products [{products}] 
            to a person named {customer}, respond only with the marketing message
            """;

    @Override
    public Promotion createPromotion(CustomerIdentifier customerIdentifier, List<Product> recommends) {

        var marketingResponse = chatClient
                .prompt()
                .user(u -> u.text(promotionPrompt)
                        .param("customer", customerIdentifier.customerId())
                        .param("products",toProductNames(recommends)))
                        .advisors(advisor)
                        .call();

        var marketingMsg = marketingResponse.content();

        return Promotion.builder().marketingMessage(marketingMsg)
                .products(recommends)
                .id(customerIdentifier.customerId()).build();
    }

    /**
     * Convert product to names
     * @param recommends the list of products
     * @return the list of names
     */
    List<String> toProductNames(List<Product> recommends) {
        if(recommends == null)
            return Collections.emptyList();

        return recommends.stream().map(Product::name).toList();
    }

    @Override
    public Sentiment analyze(String text) {
        String prompt = """
            Analyze the sentiment of this text: "{text}".
            Respond in JSON format with response field value with Positive, or Negative
            """;

        var startTime = System.currentTimeMillis();
        var  sentiment = chatClient.prompt()
                .user(u -> u.text(prompt)
                        .param("text", text))
                .advisors(advisor)
                .call()
                .entity(SentimentResponse.class);
        var endTime = System.currentTimeMillis();
        log.info("AI Model took {}ms to response",endTime-startTime);
        log.info("text: {}, Sentiment:{}",text, sentiment);

        if(sentiment == null)
            return null;
        return sentiment.response();
    }
}
