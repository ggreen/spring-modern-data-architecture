

package spring.modern.data.domains.customer;

import java.util.List;

/*
 { "id" : "nyla",
  "marketingMessage" : "Hi",
    "products": [
    {
      "id": "sku2",
      "name": "Jelly"
    },
    {
      "id": "sku4",
      "name": "Milk"
    }
  ]
 }

 */
public record Promotion(String id, String marketingMessage, List<Product> products)
{
}
