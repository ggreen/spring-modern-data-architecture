

package spring.modern.data.caching.consumers;

import nyla.solutions.core.patterns.integration.Publisher;
import spring.modern.data.repository.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spring.modern.data.domains.customer.Promotion;

import java.util.function.Consumer;

/**
 *
 * @param promotionRepository the key/value repository to save
 */
@Component
@Slf4j
public record SavePromotionConsumer(PromotionRepository promotionRepository, Publisher<Promotion> promotionPublisher) implements Consumer<Promotion> {

    @Override
    public void accept(Promotion promotion) {
        log.info("Saving Promotion: {}",promotion);
        promotionRepository.save(promotion);

        log.info("Publish Promotion: {}",promotion);
        promotionPublisher.send(promotion);

    }
}
