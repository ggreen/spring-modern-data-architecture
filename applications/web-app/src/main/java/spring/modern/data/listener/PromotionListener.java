/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.listener;

import spring.modern.data.domains.customer.Promotion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Component
public class PromotionListener
        implements Consumer<Promotion>
{
    private final SimpMessagingTemplate messageTemple;
    private static Logger logger = LoggerFactory.getLogger(PromotionListener.class);

    public PromotionListener(SimpMessagingTemplate messageTemple)
    {
        this.messageTemple = messageTemple;
    }

    public void accept(Promotion promotion)
    {
        String user = promotion.id();

        String destination = "/topic/customerPromotions/"+user;
        logger.info("Sending to destination: {} ",destination);
         messageTemple.convertAndSend(destination, promotion);
        logger.info("SENT promotion: {} ",promotion);

    }
}
