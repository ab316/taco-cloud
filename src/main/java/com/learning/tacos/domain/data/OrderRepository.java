package com.learning.tacos.domain.data;

import com.learning.tacos.domain.model.Order;
import com.learning.tacos.security.data.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    public List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
