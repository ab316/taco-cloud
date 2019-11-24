package com.learning.tacos.domain.data;

import com.learning.tacos.domain.model.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
