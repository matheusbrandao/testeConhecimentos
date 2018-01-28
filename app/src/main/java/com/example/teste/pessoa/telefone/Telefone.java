package com.example.teste.pessoa.telefone;

import com.example.teste.estado.Estado;
import com.example.teste.pessoa.Pessoa;

/**
 * Created by matheus on 22/01/2018.
 */

public class Telefone {

    private Long id;
    private String numero;
    private Pessoa pessoa;

    public final static String ID               = "_id";
    public final static String NUMERO           = "numero";
    public final static String PESSOA           = "pessoa";

    public final static String TABELA           = "telefone";
    public final static String[] COLUNAS = new String[]{
            ID,
            NUMERO,
            PESSOA
    };

    public Telefone() {
        this.id = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
