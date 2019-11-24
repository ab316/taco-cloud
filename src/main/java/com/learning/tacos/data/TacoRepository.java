package com.learning.tacos.data;

import com.learning.tacos.model.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
