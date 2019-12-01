package com.learning.tacos.api;

import com.learning.tacos.data.TacoRepository;
import com.learning.tacos.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        return tacoRepository.findById(id)
                .map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos() {
        Pageable page = PageRequest.of(0, 12, Sort.by(Taco.COLUMN_CREATED_AT).descending());
        return tacoRepository.findAll(page).getContent();
    }

    @PostMapping(path = "/new", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco createTaco(@Valid @RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }
}
