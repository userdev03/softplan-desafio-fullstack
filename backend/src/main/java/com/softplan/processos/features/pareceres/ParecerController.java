package com.softplan.processos.features.pareceres;

import com.softplan.processos.common.AbstractController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/pareceres")
public class ParecerController extends AbstractController<Parecer, ParecerRepresentation, Long> {

    public ParecerController() {
        super(Parecer.class, ParecerRepresentation.class);
    }

    @Override
    @PostMapping
    public ResponseEntity<ParecerRepresentation> save(@RequestBody ParecerRepresentation parecer) {
        Parecer entity = representationToEntity(parecer);
        entity.setDhParecer(LocalDateTime.now());
        return super.save(entityToRepresentation(entity));
    }

}
