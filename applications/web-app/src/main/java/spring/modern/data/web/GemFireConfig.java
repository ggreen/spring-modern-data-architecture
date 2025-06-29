/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.web;

import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.events.CacheListerConsumerBridge;
import spring.modern.data.repository.gemfire.CustomerFavoriteGemFireRepository;
import spring.modern.data.repository.gemfire.ProductGemFireRepository;
import spring.modern.data.repository.gemfire.PromotionGemFireRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.CacheListener;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.client.Interest;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import java.util.function.Consumer;


@Profile("gemfire")
@EnableGemfireRepositories(basePackageClasses = {CustomerFavoriteGemFireRepository.class,
        PromotionGemFireRepository.class, ProductGemFireRepository.class})
@EnableSecurity
@Configuration
@Slf4j
@ClientCacheApplication(subscriptionEnabled = true)
public class GemFireConfig {


    public GemFireConfig(){
        log.info("Created");
    }

    @Bean("CustomerFavorites")
    public ClientRegionFactoryBean<String, CustomerFavorites> customerFavorites(ClientCache gemFireCache){
        var factory = new ClientRegionFactoryBean<String, CustomerFavorites>();
        factory.setName("CustomerFavorites");
        factory.setCache(gemFireCache);
        factory.setDataPolicy(DataPolicy.EMPTY);
        return factory;
    }

    @Bean("Product")
    public ClientRegionFactoryBean<String, Product> product(ClientCache gemFireCache){
        var factory = new ClientRegionFactoryBean<String, Product>();
        factory.setName("Product");
        factory.setCache(gemFireCache);
        factory.setDataPolicy(DataPolicy.EMPTY);
        return factory;
    }

    @Bean
    CacheListener<String, Promotion> listener(Consumer<Promotion> promotionConsumer){
        return new CacheListerConsumerBridge(promotionConsumer);
    }

    @Bean("Promotion")
    public ClientRegionFactoryBean<String, Promotion> promotion(ClientCache gemFireCache,
                                                                CacheListener<String, Promotion> listener){
        var factory = new ClientRegionFactoryBean<String, Promotion>();
        factory.setName("Promotion");
        factory.setCache(gemFireCache);
        factory.setDataPolicy(DataPolicy.EMPTY);
        CacheListener<String, Promotion>[] listeners = new CacheListener[]{listener};
        factory.setCacheListeners(listeners);
        Interest<String> interest = Interest.newInterest(Interest.ALL_KEYS);
        Interest<String>[] interests = new Interest[]{interest};
        factory.setInterests(interests);
        return factory;
    }
}
