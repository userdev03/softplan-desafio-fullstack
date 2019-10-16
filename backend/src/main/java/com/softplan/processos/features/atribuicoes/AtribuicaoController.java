package com.softplan.processos.features.atribuicoes;

import com.softplan.processos.common.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/atribuicoes")
public class AtribuicaoController extends AbstractController<Atribuicao, AtribuicaoRepresentation, Long> {

    @Autowired
    private AtribuicaoRepository repository;

    public AtribuicaoController() {
        super(Atribuicao.class, AtribuicaoRepresentation.class);
    }

    @PostMapping
    public ResponseEntity<AtribuicaoRepresentation> save(@RequestBody AtribuicaoRepresentation atribuicao) {
        Atribuicao entity = representationToEntity(atribuicao);
        entity.setDhAtribuicao(LocalDateTime.now());
        validate(entity);
        Atribuicao entitySaved = repository.save(entity);
        return new ResponseEntity(entityToRepresentation(entitySaved), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public AtribuicaoRepresentation findById(@PathVariable("id") Long id) {
        Atribuicao entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return entityToRepresentation(entity);
    }

}
