package com.softplan.processos.features.pareceres;

import com.softplan.processos.common.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/pareceres")
public class ParecerController extends AbstractController<Parecer, ParecerRepresentation, Long> {

    @Autowired
    private ParecerRepository repository;

    public ParecerController() {
        super(Parecer.class, ParecerRepresentation.class);
    }

    @PostMapping
    public ResponseEntity<ParecerRepresentation> save(@RequestBody ParecerRepresentation parecer) {
        Parecer entity = representationToEntity(parecer);
        entity.setDhParecer(LocalDateTime.now());
        validate(entity);
        Parecer entitySaved = repository.save(entity);
        return new ResponseEntity(entityToRepresentation(entitySaved), HttpStatus.CREATED);
    }

}
