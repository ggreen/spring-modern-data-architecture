

package spring.modern.data.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    private final String customerIdAttribId;
    private final String customerId;

    public IndexController(
            @Value("${retail.customer.attribute.name:id}")
            String customerIdAttribId,
            @Value("${retail.customer.id}")
            String customerId) {
        this.customerIdAttribId = customerIdAttribId;
        this.customerId = customerId;
    }

    @RequestMapping("/")
    public String homePage(Model model)
    {
        model.addAttribute(customerIdAttribId,customerId);
        return "index";
    }
}
