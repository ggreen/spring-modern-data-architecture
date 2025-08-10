

package spring.modern.data.source.functions;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.exception.TooManyRowsException;
import nyla.solutions.core.io.csv.CsvReader;
import nyla.solutions.core.util.Organizer;
import org.springframework.stereotype.Component;
import spring.modern.data.domains.customer.CustomerIdentifier;
import spring.modern.data.domains.customer.order.CustomerOrder;
import spring.modern.data.domains.customer.order.ProductOrder;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


@Slf4j
@Component
public class CsvToCustomerOrder implements Function<String, CustomerOrder> {

    private final int orderCol = 0;
    private final int customerCol  = 1;
    private final int productIdCol = 2;
    private final int quantityCol = 3;

    /**
     * Example CSV
     *
     * "3","c1","pc","5"
     * "3","c1","pb","3"
     * @param csv the function argument
     * @return
     */
    @SneakyThrows
    @Override
    public CustomerOrder apply(String csv) {

      log.info("csv:\n {}",csv);

      var csvReader = new CsvReader(new StringReader(csv));

      var productOrders = new ArrayList(csvReader.size());

      String productId,quantityText,rowOrderIdText =
              null,rowCustomerId = null;

        String customerId = null;
        String orderId = null;

        for (List<String> row : csvReader){
          rowOrderIdText = Organizer.organize(row).getByIndex(orderCol);
          if(orderId != null && !orderId.equals(rowOrderIdText))
              throw new TooManyRowsException("Cannot process multiple orderId(s) ("+orderId+","+rowOrderIdText+")");

          orderId = rowOrderIdText;

          rowCustomerId = Organizer.organize(row).getByIndex(customerCol);
          if(customerId != null && !customerId.equals(rowCustomerId))
              throw new TooManyRowsException("Cannot process multiple id(s) ("+customerId+","+rowCustomerId+")");

          customerId = rowCustomerId;

          productId = Organizer.organize(row).getByIndex(productIdCol);
          quantityText = Organizer.organize(row).getByIndex(quantityCol);

          productOrders.add(new ProductOrder(productId, Integer.valueOf(quantityText)));
        }

      return new CustomerOrder(Long.valueOf(rowOrderIdText),
              new CustomerIdentifier(rowCustomerId),
              productOrders);
    }
}
