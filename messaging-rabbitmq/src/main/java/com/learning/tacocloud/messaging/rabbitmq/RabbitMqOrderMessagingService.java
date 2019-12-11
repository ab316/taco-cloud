package com.learning.tacocloud.messaging.rabbitmq;

import com.learning.tacocloud.domain.Order;
import com.learning.tacocloud.messaging.OrderMessagingService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqOrderMessagingService implements OrderMessagingService {

    private final RabbitTemplate rabbit;

    @Autowired
    public RabbitMqOrderMessagingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Override
    public void sendOrder(Order order) {
        rabbit.convertAndSend("tacocloud.order", order, RabbitMqOrderMessagingService::addHeader);
    }

    private static Message addHeader(Message message) {
        var properties = message.getMessageProperties();
        properties.setHeader("X_ORDER_SOURCE", "WEB");
        return message;
    }
}
