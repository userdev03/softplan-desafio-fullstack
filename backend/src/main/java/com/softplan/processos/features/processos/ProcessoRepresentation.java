package com.softplan.processos.features.processos;

import com.softplan.processos.features.usuarios.UsuarioRepresentation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ProcessoRepresentation {

    private Long id;
    private String sumula;
    private LocalDateTime dhAbertura;
    private UsuarioRepresentation usuarioAbertura;

}
