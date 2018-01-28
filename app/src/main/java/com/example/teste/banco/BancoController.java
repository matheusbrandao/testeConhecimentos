package com.example.teste.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by matheus on 23/01/2018.
 */

public class BancoController {

    private SQLiteDatabase db;

    private String tabela;
    private String[] colunas;

    public BancoController(Context context){
        db = Banco.getDB(context);
    }

    public long insert(ContentValues contentValues){
        long id = db.insert(tabela, "", contentValues);
        return id;
    }

    public long update(ContentValues contentValues, String where, String[] whereArgs){
        int id = db.update(tabela, contentValues, where, whereArgs);
        return id;
    }

    protected Cursor getCursor(String where, String[] whereArg, String order) {

        try {
            return db.query(tabela, getColunas(), where, whereArg, null, null, order);
        } catch(SQLException e) {
            return null;
        }
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }
}
