package com.learning.tacos.domain.data;

import com.learning.tacos.domain.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
