package com.softplan.processos.features.atribuicoes;

import com.softplan.processos.features.processos.ProcessoRepresentation;
import com.softplan.processos.features.usuarios.UsuarioRepresentation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AtribuicaoRepresentation {

    private Long id;
    private ProcessoRepresentation processo;
    private UsuarioRepresentation usuarioTriador;
    private UsuarioRepresentation usuarioFinalizador;
    private LocalDateTime dhAtribuicao;

}
