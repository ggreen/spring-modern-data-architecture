package spring.modern.data.service;

import lombok.RequiredArgsConstructor;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.repository.ProductRepository;

import java.util.List;

@RequiredArgsConstructor
public class QueryProductServiceByFindAllStream implements  QueryProductService{
    private final ProductRepository productRepository;

    @Override
    public List<Product> findByNameContaining(String name) {
        if(name ==null || name.isEmpty())
            return null;

        var nameLower = name.toLowerCase();
        return productRepository.findAll().stream().filter(p -> p.name() != null && p.name().toLowerCase().contains(nameLower)).toList();
    }
}

