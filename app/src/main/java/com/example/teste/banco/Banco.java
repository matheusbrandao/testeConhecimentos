package com.example.teste.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.teste.configuracao.Configuracao;
import com.example.teste.estado.Estado;
import com.example.teste.pessoa.Pessoa;
import com.example.teste.pessoa.telefone.Telefone;

/**
 * Created by matheus on 23/01/2018.
 */

public class Banco {

    private static final String NOME_BANCO 	= "Teste";
    private static final int VERSAO_BANCO = 9;
    private static SQLiteDatabase db;

    public static SQLiteDatabase getDB(Context ctx) {

        try {
            if (db == null) {
                BancoSQLiteHelper dbHelper = new BancoSQLiteHelper(ctx, NOME_BANCO, VERSAO_BANCO, SCRIPT_DATABASE_CREATE);
                db = dbHelper.getWritableDatabase();
            }
        } catch (Exception e){
        }

        return db;
    }

    private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
            "CREATE TABLE " + Pessoa.TABELA + "("
                    + Pessoa.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + Pessoa.NOME + " text,"
                    + Pessoa.CPF + " text,"
                    + Pessoa.DATACADASTRO + " integer,"
                    + Pessoa.DATANASCIMENTO + " integer,"
                    + Pessoa.RG + " text"
                    +");",

            "CREATE TABLE " + Estado.TABELA + "("
                    + Estado.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + Estado.UF + " text,"
                    + Estado.DESCRICAO + " text"
                    +");",

            "insert into " + Estado.TABELA + " (" + Estado.ID + ", " + Estado.UF + ", " + Estado.DESCRICAO
                    + ") values (1,'SC','Santa Catarina');",
            "insert into " + Estado.TABELA + " (" + Estado.ID + ", " + Estado.UF + ", " + Estado.DESCRICAO
                    + ") values (2,'PR','Paraná');",

            "CREATE TABLE " + Configuracao.TABELA + "("
                    + Configuracao.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + Configuracao.ESTADO + " integer"
                    +");",

            "insert into " + Configuracao.TABELA + " (" + Configuracao.ID + ", " + Configuracao.ESTADO
                    + ") values (1,1);",

            "CREATE TABLE " + Telefone.TABELA + "("
                    + Telefone.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + Telefone.PESSOA + " integer,"
                    + Telefone.NUMERO + " text"
                    +");",
    };
}
