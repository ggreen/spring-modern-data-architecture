package spring.modern.data.analytics.service.ai;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import spring.modern.data.domains.customer.CustomerIdentifier;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.domains.customer.reviews.Sentiment;
import spring.modern.data.domains.customer.reviews.SentimentResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatModelServiceTest {

    @Mock
    private QuestionAnswerAdvisor questionAnswerAdvisor;

    @Mock
    private ChatClient chatClient;
    @Mock
    private ChatClient.ChatClientRequestSpec clientResponseSpec;

    private ChatModelService subject;
    private final static CustomerIdentifier customerId = JavaBeanGeneratorCreator.of(CustomerIdentifier.class).create();
    private final static List<Product> recommendations = new ArrayList<>(JavaBeanGeneratorCreator.of(Product.class)
            .createCollection(1));
    @Mock
    private ChatClient.CallResponseSpec callResponseSpec;
    private List<Product> products = new ArrayList<>(JavaBeanGeneratorCreator.of(Product.class).createCollection(2));

    @BeforeEach
    void setUp() {
        subject = new ChatModelService(chatClient, questionAnswerAdvisor);
    }

    @Test
    void createPromotion() {

        String expectedMsg = "hello";
        when(chatClient.prompt()).thenReturn(clientResponseSpec);
        when(clientResponseSpec.user(any(Consumer.class))).thenReturn(clientResponseSpec);
        when(clientResponseSpec.advisors(any(Advisor.class))).thenReturn(clientResponseSpec);
        when(clientResponseSpec.call()).thenReturn(callResponseSpec);
        when(callResponseSpec.content()).thenReturn(expectedMsg);

        Promotion expected = Promotion.builder()
                .id(customerId.customerId())
                .marketingMessage(expectedMsg)
                .products(recommendations)
                .build();

        var actual = subject.createPromotion(customerId,recommendations);
        assertEquals(expected, actual);
    }

    @Test
    void analyze() {

        Sentiment expected =  Sentiment.POSITIVE;
        SentimentResponse sentimentResponse = new SentimentResponse(expected);

        String expectedMsg = "hello";
        when(chatClient.prompt()).thenReturn(clientResponseSpec);
        when(clientResponseSpec.user(any(Consumer.class))).thenReturn(clientResponseSpec);
        when(clientResponseSpec.advisors(any(Advisor.class))).thenReturn(clientResponseSpec);
        when(clientResponseSpec.call()).thenReturn(callResponseSpec);
        when(callResponseSpec.entity(any(Class.class))).thenReturn(sentimentResponse);

        String text = "Hello";
        var actual = subject.analyze(text);

        assertEquals(expected, actual);

    }

    @Test
    void toProductNames() {
        List<String> expected = this.products.stream().map(Product::name).toList();

        var actual = subject.toProductNames(this.products);

        assertEquals(expected, actual);
    }

    @Test
    void toProductNamesNull() {

        assertEquals(Collections.emptyList(),subject.toProductNames(null));

    }

    @Test
    void toProductNamesEmpty() {

        assertEquals(Collections.emptyList(),subject.toProductNames(Collections.emptyList()));

    }
}