package com.learning.tacocloud.kitchen.messaging.artemis;

import com.learning.tacocloud.kitchen.messaging.OrderReceiver;
import com.learning.tacocloud.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class ArtemisOrderReceiver implements OrderReceiver {

    private JmsTemplate jms;

    @Autowired
    public ArtemisOrderReceiver(JmsTemplate jms) {
        this.jms = jms;
    }

    @Override
    public Order receiveOrder() {
        return (Order) jms.receiveAndConvert("tacocloud.order.queue");
    }
}
