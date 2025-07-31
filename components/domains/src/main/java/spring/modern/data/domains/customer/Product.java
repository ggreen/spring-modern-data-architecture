

package spring.modern.data.domains.customer;

import lombok.Builder;

@Builder
public record Product(String id, String name)
{
}
