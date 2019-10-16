package com.softplan.processos.features.processos;

import com.softplan.processos.common.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/processos")
public class ProcessoController extends AbstractController<Processo, ProcessoRepresentation, Long> {

    @Autowired
    private ProcessoRepository repository;

    public ProcessoController() {
        super(Processo.class, ProcessoRepresentation.class);
    }

    @GetMapping(value = "/{id}")
    public ProcessoRepresentation findById(@PathVariable("id") Long id) {
        Processo entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return entityToRepresentation(entity);
    }

    @GetMapping
    public Page<ProcessoRepresentation> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(entity -> entityToRepresentation(entity));
    }

    @PostMapping
    public ResponseEntity<ProcessoRepresentation> save(@RequestBody ProcessoRepresentation processo) {
        Processo entity = representationToEntity(processo);
        entity.setDhAbertura(LocalDateTime.now());
        validate(entity);
        Processo entitySaved = repository.save(entity);
        return new ResponseEntity(entityToRepresentation(entitySaved), HttpStatus.CREATED);
    }

}
