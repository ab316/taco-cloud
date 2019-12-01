package com.learning.tacos.api.hateoas;

import com.learning.tacos.api.DesignApiController;
import com.learning.tacos.domain.Taco;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoRepresentationModel> {

    public TacoResourceAssembler() {
        super(DesignApiController.class, TacoRepresentationModel.class);
    }

    @Override
    protected TacoRepresentationModel instantiateModel(Taco taco) {
        return new TacoRepresentationModel(taco);
    }

    @Override
    public TacoRepresentationModel toModel(Taco taco) {
        return createModelWithId(taco.getId(), taco);
    }
}
