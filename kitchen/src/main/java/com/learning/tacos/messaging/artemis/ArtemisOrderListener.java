package com.learning.tacos.messaging.artemis;

import com.learning.tacos.kitchen.KitchenUI;
import com.learning.tacos.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ArtemisOrderListener {
    private KitchenUI kitchenUI;

    @Autowired
    public ArtemisOrderListener(KitchenUI kitchenUI) {
        this.kitchenUI = kitchenUI;
    }

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(Order order) {
        kitchenUI.displayOrder(order);
    }
}
