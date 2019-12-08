package com.learning.tacos.messaging;


import com.learning.tacos.domain.Order;

public interface OrderReceiver {
    Order receiveOrder();
}
