package com.example.teste.configuracao;

import com.example.teste.estado.Estado;

/**
 * Created by matheus on 22/01/2018.
 */

public class Configuracao {

    private Long id;
    private Estado estado;

    public final static String ID               = "_id";
    public final static String ESTADO           = "estado";

    public final static String TABELA           = "configuracao";
    public final static String[] COLUNAS = new String[]{
            ID,
            ESTADO
    };

    public Configuracao() {
        this.id = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
