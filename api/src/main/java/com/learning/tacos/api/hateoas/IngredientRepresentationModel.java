package com.learning.tacos.api.hateoas;

import com.learning.tacos.domain.Ingredient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "ingredient", collectionRelation = "ingredients")
@Getter
@EqualsAndHashCode(callSuper = false)
public class IngredientRepresentationModel extends RepresentationModel<IngredientRepresentationModel> {
    private final String name;
    private final Ingredient.Type type;

    public IngredientRepresentationModel(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
