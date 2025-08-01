

package spring.modern.data.controller;

import nyla.solutions.core.patterns.integration.Publisher;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.repository.PromotionRepository;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller for promotion data
 * @param repository the promotion repository
 * @param promotionPublisher the publisher of promotions
 */
@RestController
@RequestMapping("promotions")
public record PromoteController(PromotionRepository repository, Publisher<Promotion> promotionPublisher)
{

    private static final String channel = "default";

    @PostMapping("promotion")
    public void savePromotion(@RequestBody Promotion promotion)
    {
        repository.save(promotion);
    }

    @PostMapping("promotion/publish")
    public void publishPromotion(@RequestBody Promotion promotion)
    {
        promotionPublisher.send(promotion);
    }

    @GetMapping("promotion/{id}")
    public Promotion getPromotion(@PathVariable String id)
    {
        return repository.findById(id).orElse(null);
    }
}
