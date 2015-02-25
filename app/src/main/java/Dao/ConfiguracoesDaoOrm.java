package Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import Models.Configuracoes;
import Models.Disciplina;

/**
 * Created by Bruno on 29/01/2015.
 */
public class ConfiguracoesDaoOrm extends BaseDaoImpl<Configuracoes, Integer> {


    protected ConfiguracoesDaoOrm(ConnectionSource connectionSource) throws SQLException {
        super(Configuracoes.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
