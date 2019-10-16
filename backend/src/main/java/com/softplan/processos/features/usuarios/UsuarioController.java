package com.softplan.processos.features.usuarios;

import com.softplan.processos.common.AbstractController;
import com.softplan.processos.common.PasswordBuilder;
import com.softplan.processos.exceptions.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioController extends AbstractController<Usuario, UsuarioRepresentation, Long> {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioController() {
        super(Usuario.class, UsuarioRepresentation.class);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UsuarioRepresentation> login(@RequestBody UsuarioRepresentation usuario) {
        Usuario usuarioLogin = repository.findByEmailAndSenha(usuario.getEmail(),
                PasswordBuilder.toHash(usuario.getSenha()));
        if (nonNull(usuarioLogin)) {
            return new ResponseEntity(entityToRepresentation(usuarioLogin), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/consulta", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UsuarioRepresentation> findAllByPerfil(@RequestParam(value = "perfil") Perfil perfil) {
        List<UsuarioRepresentation> usuariosRepresentation = repository.findByPerfil(perfil)
                .stream().map(usuario -> entityToRepresentation(usuario))
                .collect(Collectors.toList());
        return usuariosRepresentation;
    }

    @GetMapping(value = "/{id}")
    public UsuarioRepresentation findById(@PathVariable("id") Long id) {
        Usuario entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return entityToRepresentation(entity);
    }

    @GetMapping
    public Page<UsuarioRepresentation> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(entity -> entityToRepresentation(entity));
    }

    @PostMapping
    public ResponseEntity<UsuarioRepresentation> save(@RequestBody UsuarioRepresentation usuario) {
        Usuario entity = representationToEntity(usuario);
        entity.setSenha(nonNull(entity.getSenha()) ? PasswordBuilder.toHash(entity.getSenha()) : null);
        validate(entity);
        Usuario entitySaved = repository.save(entity);
        return new ResponseEntity(entityToRepresentation(entitySaved), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity remove(@PathVariable("id") Long id) {
        Usuario entityToRemove = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.delete(entityToRemove);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    protected void validate(Usuario entity) {
        super.validate(entity);
        boolean emailInUse = repository.existsByEmail(entity.getEmail());
        if (emailInUse) {
            throw new DomainException(String.format("O email informado %s já está em uso.", entity.getEmail()));
        }
    }

}
