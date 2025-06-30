package spring.modern.data.service;

import spring.modern.data.domains.customer.Product;

import java.util.List;

public interface QueryProductService {
    List<Product> findByNameContaining(String name);
}
