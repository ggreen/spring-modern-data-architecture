

package spring.modern.data.controller;

import nyla.solutions.core.patterns.integration.Publisher;
import org.junit.jupiter.api.BeforeEach;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.repository.PromotionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static nyla.solutions.core.util.Organizer.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromoteControllerTest
{
    @Mock
    private PromotionRepository repository;
    private final Promotion promotion = new Promotion("1",
            "new stuff",
            toList(new Product("3L","productName")));

    @Mock
    private Publisher<Promotion> publisher;
    private PromoteController subject;

    @BeforeEach
    void setUp() {
        subject = new PromoteController(repository,publisher);
    }

    @Test
    void given_promote_When_savePromotion_Then_getPromote_returns_Saved()
    {
        when(repository.findById(anyString())).thenReturn(Optional.of(promotion));
        System.out.println(promotion);

        subject.savePromotion(promotion);
        assertEquals(promotion,subject.getPromotion(promotion.id()));
        verify(repository).findById(anyString());
    }

    @Test
    void publishPromotion() {

        subject.publishPromotion(promotion);

        verify(publisher).send(promotion);
    }
}