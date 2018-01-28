package com.example.teste.estado;

/**
 * Created by matheus on 22/01/2018.
 */

public class Estado {

    private Long id;
    private String uf;
    private String descricao;

    public final static String ID               = "_id";
    public final static String UF               = "UF";
    public final static String DESCRICAO        = "descricao";

    public final static String TABELA           = "estado";
    public final static String[] COLUNAS = new String[]{
            ID,
            UF,
            DESCRICAO
    };

    public Estado() {
        this.id = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return uf + " - " + descricao;
    }
}
