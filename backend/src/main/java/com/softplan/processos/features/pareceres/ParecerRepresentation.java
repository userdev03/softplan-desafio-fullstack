package com.softplan.processos.features.pareceres;

import com.softplan.processos.features.atribuicoes.AtribuicaoRepresentation;
import com.softplan.processos.features.processos.Processo;
import com.softplan.processos.features.processos.ProcessoRepresentation;
import com.softplan.processos.features.usuarios.UsuarioRepresentation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ParecerRepresentation {

    private Long id;
    private ProcessoRepresentation processo;
    private AtribuicaoRepresentation atribuicao;
    private UsuarioRepresentation usuarioParecer;
    private String textoParecer;
    private LocalDateTime dhParecer;

}
