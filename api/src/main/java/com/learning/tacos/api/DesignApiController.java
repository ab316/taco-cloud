package com.learning.tacos.api;

import com.learning.tacos.api.hateoas.TacoRepresentationModel;
import com.learning.tacos.api.hateoas.TacoResourceAssembler;
import com.learning.tacos.data.TacoRepository;
import com.learning.tacos.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignApiController {

    private TacoRepository tacoRepository;

    @Autowired
    EntityLinks entityLinks;

    @Autowired
    public DesignApiController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacoRepresentationModel> tacoById(@PathVariable("id") Long id) {
        return tacoRepository.findById(id)
                .map(taco -> new ResponseEntity<>(new TacoRepresentationModel(taco), HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/recent")
    public CollectionModel<TacoRepresentationModel> recentTacos() {
        Pageable page = PageRequest.of(0, 12, Sort.by(Taco.COLUMN_CREATED_AT).descending());
        List<Taco> tacos = tacoRepository.findAll(page).getContent();

        var tacoCollectionModel = new TacoResourceAssembler().toCollectionModel(tacos);
        tacoCollectionModel.add(
                WebMvcLinkBuilder
                .linkTo(methodOn(DesignApiController.class).recentTacos())
                .withRel("recents")
        );

        return tacoCollectionModel;
    }

    @PostMapping(path = "/new", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TacoRepresentationModel createTaco(@Valid @RequestBody Taco taco) {
        Taco newTaco = tacoRepository.save(taco);
        return new TacoRepresentationModel(newTaco);
    }
}
