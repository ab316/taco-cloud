package com.learning.tacos.messaging;

import com.learning.tacos.domain.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
