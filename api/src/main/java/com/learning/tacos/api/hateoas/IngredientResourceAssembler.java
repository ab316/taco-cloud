package com.learning.tacos.api.hateoas;

import com.learning.tacos.api.IngredientApiController;
import com.learning.tacos.domain.Ingredient;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class IngredientResourceAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientRepresentationModel> {

    public IngredientResourceAssembler() {
        super(IngredientApiController.class, IngredientRepresentationModel.class);
    }

    @Override
    protected IngredientRepresentationModel instantiateModel(Ingredient ingredient) {
        return new IngredientRepresentationModel(ingredient);
    }

    @Override
    public IngredientRepresentationModel toModel(Ingredient ingredient) {
        return createModelWithId(ingredient.getId(), ingredient);
    }
}
