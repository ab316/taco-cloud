package com.learning.tacocloud.data;

import com.learning.tacocloud.domain.Order;
import com.learning.tacocloud.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    public List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
