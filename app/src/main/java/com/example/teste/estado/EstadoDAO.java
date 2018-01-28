package com.example.teste.estado;

import android.content.Context;
import android.database.Cursor;

import com.example.teste.banco.BancoController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matheus on 23/01/2018.
 */

public class EstadoDAO extends BancoController {

    int idxID;
    int idxUF;
    int idxDescricao;

    public EstadoDAO(Context context) {
        super(context);

        setTabela(Estado.TABELA);
        setColunas(Estado.COLUNAS);
    }

    private void carregarIdx(Cursor c) {
        idxID 						= c.getColumnIndex(Estado.ID);
        idxUF 					    = c.getColumnIndex(Estado.UF);
        idxDescricao 				= c.getColumnIndex(Estado.DESCRICAO);
    }

    private Estado carregar(Cursor c) {
        Estado estado = new Estado();
        estado.setId								(c.getLong(idxID));
        estado.setUf								(c.getString(idxUF));
        estado.setDescricao							(c.getString(idxDescricao));

        return estado;
    }

    public List<Estado> buscar() {
        Cursor c = getCursor(null,null, Estado.UF);
        List<Estado> lista = new ArrayList<Estado>();
        if (c.moveToFirst()) {
            carregarIdx(c);
            do {
                Estado estado = carregar(c);
                lista.add(estado);
            } while(c.moveToNext());
        }
        c.close();
        return lista;
    }

    public Estado buscar(long value) {
        Cursor c = getCursor(Estado.ID+"=?",new String[]{String.valueOf(value)},"");
        Estado estado = null;
        if (c.moveToFirst()) {
            carregarIdx(c);
            do {
                estado = carregar(c);
            } while(c.moveToNext());
        }
        c.close();
        return estado;
    }
}
