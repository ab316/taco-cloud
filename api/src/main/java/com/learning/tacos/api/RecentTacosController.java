package com.learning.tacos.api;

import com.learning.tacos.data.TacoRepository;
import com.learning.tacos.domain.Taco;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RepositoryRestController
public class RecentTacosController {

    private TacoRepository tacoRepository;

    public RecentTacosController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PersistentEntityResource>> recentTacos(PersistentEntityResourceAssembler assembler) {
        PageRequest page = PageRequest.of(0, 12, Sort.by(Taco.COLUMN_CREATED_AT).descending());
        var tacos = tacoRepository.findAll(page);

        var recentTacos = new CollectionModel<>(tacos.map(assembler::toModel));
        recentTacos.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RecentTacosController.class).recentTacos(null))
                .withRel("recent")
        );

        return ResponseEntity.ok(recentTacos);
    }
}
