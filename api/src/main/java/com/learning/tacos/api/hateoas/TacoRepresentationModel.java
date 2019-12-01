package com.learning.tacos.api.hateoas;

import com.learning.tacos.domain.Taco;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;

@Relation(value = "taco", collectionRelation = "tacos")
@Getter
@EqualsAndHashCode(callSuper = false)
public class TacoRepresentationModel extends RepresentationModel<TacoRepresentationModel> {

    private static final IngredientResourceAssembler ingredientAssembler = new IngredientResourceAssembler();

    private final String name;
    private final Instant createdAt;
    private final CollectionModel<IngredientRepresentationModel> ingredients;

    public TacoRepresentationModel(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
        this.ingredients = ingredientAssembler.toCollectionModel(taco.getIngredients());
    }
}
