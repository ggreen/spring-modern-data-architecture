
package spring.modern.data.analytics.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderEntity {
    private String productId;
    private int quantity;
}
