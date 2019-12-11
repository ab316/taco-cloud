package com.learning.tacocloud.kitchen.messaging.artemis;

import com.learning.tacocloud.kitchen.KitchenUI;
import com.learning.tacocloud.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Profile("artemis-profile")
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
