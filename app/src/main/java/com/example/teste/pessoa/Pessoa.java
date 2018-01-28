package com.example.teste.pessoa;

import com.example.teste.estado.Estado;

import java.util.Date;

/**
 * Created by matheus on 22/01/2018.
 */

public class Pessoa {

    private Long id;
    private String nome;
    private String cpf;
    private Date dataCadastro;
    private Date dataNascimento;
    private String rg;

    public final static String ID               = "_id";
    public final static String NOME             = "nome";
    public final static String CPF              = "cpf";
    public final static String DATACADASTRO     = "dataCadastro";
    public final static String DATANASCIMENTO   = "dataNascimento";
    public final static String RG               = "rg";

    public final static String TABELA           = "pessoa";
    public final static String[] COLUNAS = new String[]{
            ID,
            NOME,
            CPF,
            DATACADASTRO,
            DATANASCIMENTO,
            RG
    };

    public Pessoa() {
        this.id = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
}
