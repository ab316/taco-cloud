package com.learning.tacos.api;

import com.learning.tacos.data.OrderRepository;
import com.learning.tacos.domain.Order;
import com.learning.tacos.messaging.OrderMessagingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@RequestMapping(path = "/orders", produces = "application/hal+json")
@Slf4j
public class OrdersApiController {

    private final OrderRepository orderRepository;
    private final OrderMessagingService orderMessagingService;

    @Autowired
    public OrdersApiController(OrderRepository orderRepository, OrderMessagingService orderMessagingService) {
        this.orderRepository = orderRepository;
        this.orderMessagingService = orderMessagingService;
    }

    @PostMapping
    public ResponseEntity<EntityModel<PersistentEntityResource>> postOrder(
            @RequestBody Order order,
            PersistentEntityResourceAssembler assembler) {
        Order newOrder = orderRepository.save(order);
        log.info("Order [{}] sent to the kitchen", newOrder.getId());
        orderMessagingService.sendOrder(newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EntityModel<>(assembler.toModel(newOrder)));
    }
}
