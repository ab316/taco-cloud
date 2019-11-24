package com.learning.tacos.domain.data;

import com.learning.tacos.domain.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
