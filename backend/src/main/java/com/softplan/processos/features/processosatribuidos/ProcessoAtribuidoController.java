package com.softplan.processos.features.processosatribuidos;

import com.softplan.processos.common.AbstractController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/processos-atribuidos")
public class ProcessoAtribuidoController extends AbstractController<ProcessoAtribuido, ProcessoAtribuidoRepresentation, Long> {

    @Autowired
    private ProcessoAtribuidoRepository repository;

    public ProcessoAtribuidoController() {
        super(ProcessoAtribuido.class, ProcessoAtribuidoRepresentation.class);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<ProcessoAtribuidoRepresentation> findByUsuario(@RequestParam(value = "usuario") Long idUsuario,
                                                               @RequestParam(value = "possuiParecer", defaultValue = "N") String possuiParecer,
                                                               Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by("dhAtribuicao")));

        Page<ProcessoAtribuidoRepresentation> processosPage = repository
                .findByIdUsuarioFinalizadorAndPossuiParecer(idUsuario, SimNao.of(possuiParecer), pageRequest)
                .map(entity -> entityToRepresentation(entity));

        return processosPage;
    }

}
