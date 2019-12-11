package com.learning.tacocloud.kitchen.messaging.rabbitmq;

import com.learning.tacocloud.domain.Order;
import com.learning.tacocloud.kitchen.KitchenUI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("rabbitmq-profile")
@Slf4j
public class RabbitMqOrderListener {
    private KitchenUI kitchenUI;

    @Autowired
    public RabbitMqOrderListener(KitchenUI kitchenUI) {
        this.kitchenUI = kitchenUI;
        log.info("RabbitMQ listener started");
    }

    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrder(Order order) {
        kitchenUI.displayOrder(order);
    }
}
