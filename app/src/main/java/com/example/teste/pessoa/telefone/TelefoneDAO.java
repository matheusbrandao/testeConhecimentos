package com.example.teste.pessoa.telefone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.teste.banco.BancoController;
import com.example.teste.configuracao.Configuracao;
import com.example.teste.estado.Estado;
import com.example.teste.estado.EstadoDAO;
import com.example.teste.pessoa.Pessoa;
import com.example.teste.pessoa.PessoaDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matheus on 23/01/2018.
 */

public class TelefoneDAO extends BancoController {

    int idxID;
    int idxNumero;
    int idxPessoa;
    PessoaDAO pessoaDAO;

    public TelefoneDAO(Context context) {
        super(context);

        setTabela(Telefone.TABELA);
        setColunas(Telefone.COLUNAS);

        pessoaDAO = new PessoaDAO(context);
    }

    private void carregarIdx(Cursor c) {
        idxID 						= c.getColumnIndex(Telefone.ID);
        idxNumero					= c.getColumnIndex(Telefone.NUMERO);
        idxPessoa					= c.getColumnIndex(Telefone.NUMERO);
    }

    private Telefone carregar(Cursor c) {
        Telefone telefone = new Telefone();
        telefone.setId								(c.getLong(idxID));
        telefone.setNumero							(c.getString(idxNumero));
        telefone.setPessoa						    (pessoaDAO.buscar(c.getLong(idxPessoa)));

        return telefone;
    }

    public void salvar(Telefone telefone) {
        ContentValues values = new ContentValues();

        values.put(Telefone.NUMERO, telefone.getNumero());

        if (telefone.getPessoa() instanceof Pessoa)
            values.put(Telefone.PESSOA, telefone.getPessoa().getId());

        if (telefone.getId() == 0 || telefone.getId() == null){
            Long id = insert(values);
            telefone.setId(id);
        } else
            update(values, Configuracao.ID+"=?", new String[]{telefone.getId().toString()});
    }

    public List<Telefone> buscar() {
        Cursor c = getCursor(null,null, "nome");
        List<Telefone> lista = new ArrayList<Telefone>();
        if (c.moveToFirst()) {
            carregarIdx(c);
            do {
                Telefone pessoa = carregar(c);
                lista.add(pessoa);
            } while(c.moveToNext());
        }
        c.close();
        return lista;
    }

    public Telefone buscar(long value) {
        Cursor c = getCursor(Configuracao.ID+"=?",new String[]{String.valueOf(value)},"");
        Telefone pessoa = null;
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
