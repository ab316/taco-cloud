package com.learning.tacos.data;

import com.learning.tacos.domain.Order;
import com.learning.tacos.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    public List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
