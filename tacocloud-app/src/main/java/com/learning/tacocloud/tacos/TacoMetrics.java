package com.learning.tacocloud.tacos;

import com.learning.tacocloud.domain.Taco;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

@Component
public class TacoMetrics extends AbstractRepositoryEventListener<Taco> {
    private MeterRegistry meterRegistry;

    @Autowired
    public TacoMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    protected void onAfterCreate(Taco taco) {
        for (var ingredient : taco.getIngredients()) {
            meterRegistry.counter("tacocloud", "ingredient", ingredient.getId()).increment();
        }
    }
}
