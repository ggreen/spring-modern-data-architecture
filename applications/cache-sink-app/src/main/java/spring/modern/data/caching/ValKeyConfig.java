///*
// *
// *  * Copyright 2023 VMware, Inc.
// *  * SPDX-License-Identifier: GPL-3.0
// *
// */

package spring.modern.data.caching;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.integration.Publisher;
import nyla.solutions.core.util.Text;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.repository.valkey.CustomerFavoriteValKeyRepository;

import java.util.Calendar;

/**
 * RedisConfig
 *
 * @author Gregory Green
 */
@Configuration
@EnableRedisRepositories(basePackageClasses = CustomerFavoriteValKeyRepository.class)
@Profile("valkey")
@Slf4j
public class ValKeyConfig
{
    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public RedisSerializer redisSerializer()
    {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Value("${retail.valkey.publish.channel:promotions}")
    private String publishChannel;

    /**
     * Type safe representation of application.properties
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory, RedisSerializer redisSerializer)
    {
        var template = new RedisTemplate();
        template.setDefaultSerializer(redisSerializer);
        template.setConnectionFactory(connectionFactory);

        return template;
    }


    @Bean
    Publisher<Promotion> promotionPublisher(RedisOperations<String,Promotion> redisOperations, ObjectMapper objectMapper)
    {
        return promotion -> {
            // send message through RedisOperations
            var numberOfClients = redisOperations.convertAndSend(publishChannel, promotion);

            log.info("Published to numberOfClients:{} ",numberOfClients);
        };

    }
    @Bean
    ApplicationContextAware listener(RedisTemplate<String,String> redisTemplate)
    {
        return context -> {
            redisTemplate.opsForValue().set(applicationName,
                    Text.formatDate(Calendar.getInstance().getTime()));
        };
    }

}
