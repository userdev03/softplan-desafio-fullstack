package com.softplan.processos.features.processosatribuidos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ProcessoAtribuidoRepresentation {

    private Long idAtribuicao;
    private Long idProcesso;
    private String sumula;
    private Long idUsuarioAbertura;
    private LocalDateTime dhAbertura;
    private Long idUsuarioTriador;
    private LocalDateTime dhAtribuicao;
    private Long idUsuarioFinalizador;
    private SimNao possuiParecer;

}
