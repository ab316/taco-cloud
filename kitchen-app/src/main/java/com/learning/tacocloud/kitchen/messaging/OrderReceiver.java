package com.learning.tacocloud.kitchen.messaging;


import com.learning.tacocloud.domain.Order;

public interface OrderReceiver {
    Order receiveOrder();
}
