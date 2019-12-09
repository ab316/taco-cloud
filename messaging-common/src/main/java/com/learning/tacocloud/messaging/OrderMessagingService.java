package com.learning.tacocloud.messaging;

import com.learning.tacocloud.domain.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
