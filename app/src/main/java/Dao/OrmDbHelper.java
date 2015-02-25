package Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import Models.Configuracoes;
import Models.Disciplina;
import Models.Prova;

public class OrmDbHelper extends OrmLiteSqliteOpenHelper {

    private static final String NOME_BASE = "MinhaFaculdade";
    private static final int VERSAO_BASE = 1;
    private Context context;

    public OrmDbHelper(Context context) {

        super(context, NOME_BASE, null, VERSAO_BASE);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Disciplina.class);
            TableUtils.createTable(connectionSource, Prova.class);
            TableUtils.createTable(connectionSource, Configuracoes.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, Disciplina.class, true);
            TableUtils.dropTable(connectionSource, Prova.class, true);
            TableUtils.dropTable(connectionSource, Configuracoes.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() {
        super.close();
    }

}