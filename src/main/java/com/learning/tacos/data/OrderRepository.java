package com.learning.tacos.data;

import com.learning.tacos.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
