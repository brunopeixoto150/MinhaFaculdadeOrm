package Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import Models.Configuracoes;

/**
 * Created by Bruno on 29/01/2015.
 */
public class ConfiguracoesDao {

    private DbHelper dbHelper;

    public ConfiguracoesDao(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void insertConfiguracoes(Configuracoes configuracoes) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();


        cv.put("faculdade", configuracoes.getFaculdade());
        cv.put("nome", configuracoes.getNome());
        cv.put("curso", configuracoes.getCurso());

        db.insert("configuracoes", null, cv);

        db.close();
    }

    public void updateConfiguracoes(Configuracoes newConfiguracoes) {


        String sqlUpdateConfiguracoes = "UPDATE configuracoes SET faculdade = '" + newConfiguracoes.getFaculdade() + "' , nome = '" + newConfiguracoes.getNome() + "' , curso = '" + newConfiguracoes.getCurso() + "'";
        Log.i("Minha Faculdade", sqlUpdateConfiguracoes);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(sqlUpdateConfiguracoes);
        db.close();


    }

    public Configuracoes selectConfiguracoes(){
        String sqlSelectConfiguracoes = "SELECT * FROM configuracoes";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(sqlSelectConfiguracoes, null);

        Configuracoes configuracoes = new Configuracoes();
        if (c.moveToFirst()) {

            configuracoes.setId(c.getInt(0));
            configuracoes.setFaculdade(c.getString(1));
            configuracoes.setNome(c.getString(2));
            configuracoes.setCurso(c.getString(3));

        }
        db.close();

        if (configuracoes.getId() != 0)
            return configuracoes;
        else
            return null;
    }

    public void deleteConfiguracoes() {
        String sqlDeleteConfiguracoes = "DELETE FROM configuracoes";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(sqlDeleteConfiguracoes);
        db.close();

    }
}
