

package spring.modern.data.repository.gemfire;

import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.repository.PromotionRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("gemfire")
public interface PromotionGemFireRepository extends PromotionRepository, GemfireRepository<Promotion,String> {
}
