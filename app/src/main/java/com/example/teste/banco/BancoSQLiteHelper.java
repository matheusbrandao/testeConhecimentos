package com.example.teste.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.teste.configuracao.Configuracao;
import com.example.teste.estado.Estado;
import com.example.teste.pessoa.Pessoa;

/**
 * Created by matheus on 22/01/2018.
 */

public class BancoSQLiteHelper extends SQLiteOpenHelper {

    private String[] scriptSQLCreate;

    public BancoSQLiteHelper(Context context, String name, int version, String[] scriptSQLCreate) {
        super(context, name, null, version);

        this.scriptSQLCreate = scriptSQLCreate;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int qtdeScripts = scriptSQLCreate.length;
        for (int i = 0; i < qtdeScripts; i++) {
            db.execSQL(scriptSQLCreate[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Pessoa.TABELA);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Configuracao.TABELA);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Estado.TABELA);
        onCreate(sqLiteDatabase);
    }

}
