package com.learning.tacos.api;

import com.learning.tacos.api.util.BasePathAwareLinks;
import com.learning.tacos.data.TacoRepository;
import com.learning.tacos.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
public class RecentTacosController {

    private TacoRepository tacoRepository;

    private BasePathAwareLinks basePathAwareLinks;

    @Autowired
    public RecentTacosController(TacoRepository tacoRepository, BasePathAwareLinks basePathAwareLinks) {
        this.tacoRepository = tacoRepository;
        this.basePathAwareLinks = basePathAwareLinks;
    }

    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PersistentEntityResource>> recentTacos(
            PersistentEntityResourceAssembler assembler) {
        PageRequest page = PageRequest.of(0, 12, Sort.by(Taco.COLUMN_CREATED_AT).descending());
        var tacos = tacoRepository.findAll(page);

        var recentTacos = new CollectionModel<>(tacos.map(assembler::toModel));
        recentTacos.add(
                basePathAwareLinks.underBasePath(
                        linkTo(methodOn(RecentTacosController.class).recentTacos(null)))
                        .withRel("recents")
        );

        return ResponseEntity.ok(recentTacos);
    }
}
