/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.analytics.repository;

import spring.modern.data.analytics.consumers.repository.jdbc.ProductGreenplumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import spring.modern.data.domains.customer.Product;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductGreenplumRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ProductGreenplumRepository subject;
    private double confidence = 0;
    private String frequentBoughtSql = "";
    private String findCustomerFavoritesByCustomerIdAndTopCountSql = "";
    private String insertSql ="";

    @BeforeEach
    void setUp() {
        subject = new ProductGreenplumRepository(jdbcTemplate,
                namedParameterJdbcTemplate,
                confidence,
                frequentBoughtSql,
                findCustomerFavoritesByCustomerIdAndTopCountSql,
                insertSql);
    }

    @Test
    void given_products_when_save_then_save_to_database() {
        Product product = new Product("id","name");
        List<Product> expected = asList(product);

        this.subject.saveProducts(expected);

        verify(namedParameterJdbcTemplate,times(2)).update(anyString(),any(Map.class));
    }
}