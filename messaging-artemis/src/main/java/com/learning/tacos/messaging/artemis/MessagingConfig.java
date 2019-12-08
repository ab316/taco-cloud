package com.learning.tacos.messaging.artemis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.tacos.domain.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessagingConfig {

    @Bean
    public MappingJackson2MessageConverter messageConverter(ObjectMapper objectMapper) {
        var messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        messageConverter.setTypeIdPropertyName("_typeId");

        Map<String, Class<?>> typeIdsMapping = new HashMap<>();
        typeIdsMapping.put("order", Order.class);
        messageConverter.setTypeIdMappings(typeIdsMapping);

        return messageConverter;
    }
}
