package com.example.teste.configuracao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.teste.estado.Estado;
import com.example.teste.estado.EstadoDAO;
import com.example.teste.banco.BancoController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matheus on 23/01/2018.
 */

public class ConfiguracaoDAO extends BancoController {

    int idxID;
    int idxEstado;
    EstadoDAO estadoDAO;

    public ConfiguracaoDAO(Context context) {
        super(context);

        setTabela(Configuracao.TABELA);
        setColunas(Configuracao.COLUNAS);

        estadoDAO = new EstadoDAO(context);
    }

    private void carregarIdx(Cursor c) {
        idxID 						= c.getColumnIndex(Configuracao.ID);
        idxEstado 					= c.getColumnIndex(Configuracao.ESTADO);
    }

    private Configuracao carregar(Cursor c) {
        Configuracao configuracao = new Configuracao();
        configuracao.setId								(c.getLong(idxID));
        configuracao.setEstado						    (estadoDAO.buscar(c.getLong(idxEstado)));

        return configuracao;
    }

    public void salvar(Configuracao configuracao) {
        ContentValues values = new ContentValues();

        if (configuracao.getEstado() instanceof Estado)
            values.put(Configuracao.ESTADO, configuracao.getEstado().getId());

        if (configuracao.getId() == 0 || configuracao.getId() == null){
            Long id = insert(values);
            configuracao.setId(id);
        } else
            update(values, Configuracao.ID+"=?", new String[]{configuracao.getId().toString()});
    }

    public Configuracao buscar(long value) {
        Cursor c = getCursor(Configuracao.ID+"=?",new String[]{String.valueOf(value)},"");
        Configuracao pessoa = null;
        if (c.moveToFirst()) {
            carregarIdx(c);
            do {
                pessoa = carregar(c);
            } while(c.moveToNext());
        }
        c.close();
        return pessoa;
    }
}
