package com.softplan.processos.features.usuarios;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioRepresentation {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Perfil perfil;

}
