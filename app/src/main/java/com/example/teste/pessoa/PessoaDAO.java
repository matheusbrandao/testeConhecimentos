package com.example.teste.pessoa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.teste.banco.BancoController;
import com.example.teste.banco.BancoSQLiteHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by matheus on 23/01/2018.
 */

public class PessoaDAO extends BancoController {

    int idxID;
    int idxNome;
    int idxCpf;
    int idxDataCadastro;
    int idxDataNascimento;
    int idxRG;

    public PessoaDAO(Context context) {
        super(context);

        setTabela(Pessoa.TABELA);
        setColunas(Pessoa.COLUNAS);
    }

    private void carregarIdx(Cursor c) {
        idxID 						= c.getColumnIndex(Pessoa.ID);
        idxNome 					= c.getColumnIndex(Pessoa.NOME);
        idxCpf						= c.getColumnIndex(Pessoa.CPF);
        idxDataCadastro				= c.getColumnIndex(Pessoa.DATACADASTRO);
        idxDataNascimento			= c.getColumnIndex(Pessoa.DATANASCIMENTO);
        idxRG			            = c.getColumnIndex(Pessoa.RG);
    }

    private Pessoa carregar(Cursor c) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId								(c.getLong(idxID));
        pessoa.setNome								(c.getString(idxNome));
        pessoa.setCpf						        (c.getString(idxCpf));
        pessoa.setRg						        (c.getString(idxRG));

        long data = c.getLong(idxDataNascimento);
        Date dataNascimento = new Date(data);
        if (dataNascimento instanceof Date)
            pessoa.setDataNascimento(dataNascimento);

        data = c.getLong(idxDataCadastro);
        Date dataCadastro = new Date(data);
        if (dataCadastro instanceof Date)
            pessoa.setDataCadastro(dataCadastro);

        return pessoa;
    }

    public void salvar(Pessoa pessoa) {
        ContentValues values = new ContentValues();
        values.put(Pessoa.CPF, pessoa.getCpf());
        values.put(Pessoa.NOME, pessoa.getNome());
        values.put(Pessoa.RG, pessoa.getRg());

        if (pessoa.getDataCadastro() instanceof Date)
            values.put(Pessoa.DATACADASTRO, pessoa.getDataCadastro().getTime());
        if (pessoa.getDataNascimento() instanceof Date)
            values.put(Pessoa.DATANASCIMENTO, pessoa.getDataNascimento().getTime());

        if (pessoa.getId() == 0 || pessoa.getId() == null){
            Long id = insert(values);
            pessoa.setId(id);
        } else
            update(values, Pessoa.ID+"=?", new String[]{pessoa.getId().toString()});

    }

    public List<Pessoa> buscar() {
        Cursor c = getCursor(null,null, "nome");
        List<Pessoa> lista = new ArrayList<Pessoa>();
        if (c.moveToFirst()) {
            carregarIdx(c);
            do {
                Pessoa pessoa = carregar(c);
                lista.add(pessoa);
            } while(c.moveToNext());
        }
        c.close();
        return lista;
    }

    public List<Pessoa> buscar(String nome, Date dataCadastro, Date dataNascimento) {
        String filtro = "";
        String and = "";
        String order = Pessoa.NOME;

        if (!nome.equals("")) {
            filtro = filtro + and + Pessoa.NOME + "='" + nome + "'";
            and = " and ";
        }

        if (dataCadastro instanceof Date){
            Calendar cal = Calendar.getInstance();

            cal.setTimeInMillis(dataCadastro.getTime());

            int ano = cal.get(Calendar.YEAR);
            int mes = cal.get(Calendar.MONTH);
            int dia = cal.get(Calendar.DAY_OF_MONTH);

            Date dataInicial = getDataHora(ano, mes, dia, 0, 0, 0);
            Date datafinal = getDataHora(ano, mes, dia, 23, 59, 59);
            filtro = filtro + and + Pessoa.DATACADASTRO + ">=" + dataInicial.getTime()
                    + " and " + Pessoa.DATACADASTRO + "<=" + datafinal.getTime() ;
            and = " and ";
        }

        if (dataNascimento instanceof Date){
            Calendar cal = Calendar.getInstance();

            cal.setTimeInMillis(dataNascimento.getTime());

            int ano = cal.get(Calendar.YEAR);
            int mes = cal.get(Calendar.MONTH);
            int dia = cal.get(Calendar.DAY_OF_MONTH);

            Date dataInicial = getDataHora(ano, mes, dia, 0, 0, 0);
            Date datafinal = getDataHora(ano, mes, dia, 23, 59, 59);
            filtro = filtro + and + Pessoa.DATANASCIMENTO + ">=" + dataInicial.getTime()
                    + " and " + Pessoa.DATANASCIMENTO + "<=" + datafinal.getTime() ;
            //and = " and ";
        }

        Cursor c = getCursor(filtro,null,order);
        List<Pessoa> lista = new ArrayList<Pessoa>();
        if (c.moveToFirst()) {
            carregarIdx(c);
            do {
                Pessoa pessoa = carregar(c);
                lista.add(pessoa);
            } while(c.moveToNext());
        }
        c.close();
        return lista;
    }

    public Pessoa buscar(long value) {
        Cursor c = getCursor(Pessoa.ID+"=?",new String[]{String.valueOf(value)},"");
        Pessoa pessoa = null;
        if (c.moveToFirst()) {
            carregarIdx(c);
            do {
                pessoa = carregar(c);
            } while(c.moveToNext());
        }
        c.close();
        return pessoa;
    }

    public static Date getDataHora(int ano, int mes, int dia, int hora, int minutos, int segundos) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, dia);
        cal.set(Calendar.MONTH, mes);
        cal.set(Calendar.YEAR, ano);
        cal.set(Calendar.HOUR_OF_DAY, hora);
        cal.set(Calendar.MINUTE, minutos);
        cal.set(Calendar.SECOND, segundos);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();

    }
}
