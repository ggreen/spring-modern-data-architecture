

package spring.modern.data.caching.consumers;

import nyla.solutions.core.patterns.integration.Publisher;
import spring.modern.data.caching.consumers.SavePromotionConsumer;
import spring.modern.data.repository.PromotionRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.domains.customer.Promotion;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SavePromotionConsumerTest {


    @Mock
    private PromotionRepository repository;

    @Mock
    private Publisher<Promotion> publisher;

    private SavePromotionConsumer subject;
    private Promotion expected = JavaBeanGeneratorCreator.of(Promotion.class).create();

    @Test
    void given_Promotion_when_save_then_when_save_then_repository_saves() {


        subject = new SavePromotionConsumer(repository,publisher);

        subject.accept(expected);

        verify(this.repository).save(any(Promotion.class));
        verify(this.publisher).send(any());
    }
}