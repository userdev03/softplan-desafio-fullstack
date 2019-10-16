package com.softplan.processos.common;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

public abstract class AbstractController<E, R, ID> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    protected Validator validator;

    private Class<E> entityClass;
    private Class<R> representationClass;

    public AbstractController(Class<E> entityClass, Class<R> representationClass) {
        this.entityClass = entityClass;
        this.representationClass = representationClass;
    }

    protected E representationToEntity(R dto) {
        return modelMapper.map(dto, entityClass);
    }

    protected R entityToRepresentation(E entity) {
        return modelMapper.map(entity, representationClass);
    }

    protected void validate(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

}
