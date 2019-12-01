package com.learning.tacos.api;

import com.learning.tacos.api.hateoas.IngredientRepresentationModel;
import com.learning.tacos.data.IngredientRepository;
import com.learning.tacos.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/ingredient", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientApiController {

    private IngredientRepository ingredientRepository;

    @Autowired
    EntityLinks entityLinks;

    @Autowired
    public IngredientApiController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientRepresentationModel> ingredientById(@PathVariable("id") String id) {
        return ingredientRepository.findById(id)
                .map(ingredient -> new ResponseEntity<>(new IngredientRepresentationModel(ingredient), HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/new", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientRepresentationModel createIngredient(@Valid @RequestBody Ingredient ingredient) {
        Ingredient newIngredient = ingredientRepository.save(ingredient);
        return new IngredientRepresentationModel(newIngredient);
    }
}
