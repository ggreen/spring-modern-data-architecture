package spring.modern.data.analytics.consumers.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.vectorstore.VectorStore;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveVectorContextConsumerTest {


    @Mock
    private VectorStore vectorStore;
    private SaveVectorContextConsumer subject;
    private final static String context  = "Hi";

    @BeforeEach
    void setUp() {
        subject = new SaveVectorContextConsumer(vectorStore);
    }

    @Test
    void saveRagContext() {

        subject.accept(context);

        verify(vectorStore).add(any());
    }

    @Test
    void nullValue() {

        subject.accept(null);

        verify(vectorStore,never()).add(any());
    }

    @Test
    void emptyValue() {

        subject.accept("");

        verify(vectorStore,never()).add(any());
    }

}