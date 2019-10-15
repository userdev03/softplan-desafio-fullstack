package com.softplan.processos.common;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

public abstract class AbstractController<E, R, ID> {

    @Autowired
    protected PagingAndSortingRepository<E, ID> repository;

    @Autowired
    protected Validator validator;

    @Autowired
    private ModelMapper modelMapper;

    private Class<E> entityClass;
    private Class<R> representationClass;

    public AbstractController(Class<E> entityClass, Class<R> representationClass) {
        this.entityClass = entityClass;
        this.representationClass = representationClass;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<R> findById(@PathVariable("id") ID id) {
        E entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new ResponseEntity(entityToRepresentation(entity), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<R>> findAllPaged(Pageable pageable) {
        Page<R> page = repository.findAll(pageable).map(entity -> entityToRepresentation(entity));
        return new ResponseEntity(page, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<R> save(@RequestBody R representation) {
        E entity = representationToEntity(representation);
        validate(entity);
        E entitySaved = repository.save(entity);
        return new ResponseEntity(entityToRepresentation(entitySaved), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<R> update(@PathVariable("id") ID id, @RequestBody R representation) {
        repository.findById(id).orElseThrow(EntityNotFoundException::new);
        E entity = representationToEntity(representation);
        validate(entity);
        E entityUpdated = repository.save(entity);
        return new ResponseEntity(entityToRepresentation(entityUpdated), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity remove(@PathVariable("id") ID id) {
        E entityToRemove = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.delete(entityToRemove);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    protected void validate(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    protected E representationToEntity(R dto) {
        return modelMapper.map(dto, entityClass);
    }

    protected R entityToRepresentation(E entity) {
        return modelMapper.map(entity, representationClass);
    }

}
