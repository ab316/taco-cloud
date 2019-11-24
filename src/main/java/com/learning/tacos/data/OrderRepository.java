package com.learning.tacos.data;

import com.learning.tacos.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
