package com.softplan.processos.features.processosatribuidos;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/processos-atribuidos")
public class ProcessoAtribuidoController {

    @Autowired
    private ProcessoAtribuidoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Page<ProcessoAtribuidoRepresentation>> findByUsuario(@RequestParam(value = "usuario") Long idUsuario,
                                                                               @RequestParam(value = "possuiParecer", defaultValue = "N") String possuiParecer,
                                                                               Pageable pageable) {
        PageRequest pageRequest = PageRequest
                .of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by("dhAtribuicao")));

        Page<ProcessoAtribuidoRepresentation> processosPage = repository
                .findByIdUsuarioFinalizadorAndPossuiParecer(idUsuario, SimNao.of(possuiParecer), pageRequest)
                .map(entity -> entityToRepresentation(entity));

        return new ResponseEntity(processosPage, HttpStatus.OK);
    }

    private ProcessoAtribuidoRepresentation entityToRepresentation(ProcessoAtribuido entity) {
        return modelMapper.map(entity, ProcessoAtribuidoRepresentation.class);
    }

}
