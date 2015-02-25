package Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bruno.minhafaculdade.R;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Models.Disciplina;
import Models.Prova;

/**
 * Created by bruno on 27/01/15.
 */
public class ProvaDaoOrm extends BaseDaoImpl<Prova, Integer> {

    private DbHelper dbHelper;
    private SimpleDateFormat format;


    public ProvaDaoOrm(ConnectionSource connectionSource) throws SQLException {
        super(Prova.class);
        setConnectionSource(connectionSource);

        initialize();

    }

    public ProvaDaoOrm(ConnectionSource connectionSource,DbHelper dbHelper) throws SQLException {
        super(Prova.class);

        this.dbHelper = dbHelper;
        this.format = new SimpleDateFormat(dbHelper.getContext().getResources().getString(R.string.tipo_data));
        setConnectionSource(connectionSource);
        initialize();
    }

    public void insertProva(Prova prova){
        Log.w("Prova insert", "" + prova.getDisciplina().getSemestre());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv =new ContentValues();


        cv.put("nota", prova.getNota());
        cv.put("tipo", prova.getTipo());
        cv.put("status", prova.isStatus());
        cv.put("nome_dis", prova.getDisciplina().getNome());
        cv.put("ano_dis", prova.getDisciplina().getAno());
        cv.put("semestre_dis", prova.getDisciplina().getSemestre());
        if(prova.getData() != null)
            cv.put("data", format.format(prova.getData()));
        else
            cv.put("data", "");
        db.insert("prova", null, cv);

        db.close();

    }

    public List<Prova> selectTodasAsProvas() throws ParseException {
        Log.w("Prova selectAll", "" );
        List<Prova> listProvas = new ArrayList<Prova>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sqlSelectTodosProvas = "SELECT * FROM prova";

        Cursor c = db.rawQuery(sqlSelectTodosProvas, null);

        if(c.moveToFirst()){
            do{
                Prova prova = new Prova();
                prova.setId(c.getInt(0));
                prova.setStatus(c.getInt(1)==1);
                prova.setTipo(c.getInt(2));
                prova.setNota(c.getDouble(1));


                if(!c.getString(7).equals(""))
                    prova.setData(format.parse(c.getString(7)));
                else
                    prova.setData(null);
                //DisciplinaDao disciplinaDao = new DisciplinaDao(dbHelper);
                //Disciplina disciplina = disciplinaDao.selectDisciplina(c.getString(4), c.getInt(5), c.getInt(6));
                //prova.setDisciplina(disciplina);
                listProvas.add(prova);
            }while(c.moveToNext());
        }

        db.close();
        Log.w("Prova selectAll", "" + listProvas.size());
        return listProvas;
    }
    public List<Prova> selectTodasAsProvasComDatas() throws ParseException {
        Log.w("Prova selectAllDatas", "" );
        List<Prova> listProvas = new ArrayList<Prova>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sqlSelectTodosProvas = "SELECT * FROM prova WHERE data <> '' AND status = 0";

        Cursor c = db.rawQuery(sqlSelectTodosProvas, null);

        if(c.moveToFirst()){
            do{
                Prova prova = new Prova();
                prova.setId(c.getInt(0));
                prova.setNota(c.getDouble(1));
                prova.setTipo(c.getInt(2));
                prova.setStatus(c.getInt(3)==1);
                Disciplina disciplina = new Disciplina();
                disciplina.setNome(c.getString(4));
                disciplina.setAno(c.getInt(5));
                disciplina.setSemestre(c.getInt(6));
                prova.setDisciplina(disciplina);
                if(!c.getString(7).equals(""))
                    prova.setData(format.parse(c.getString(7)));
                else
                    prova.setData(null);
                //DisciplinaDao disciplinaDao = new DisciplinaDao(dbHelper);
                //Disciplina disciplina = disciplinaDao.selectDisciplina(c.getString(4), c.getInt(5), c.getInt(6));
                //prova.setDisciplina(disciplina);
                listProvas.add(prova);
            }while(c.moveToNext());
        }

        db.close();
        Log.w("Prova selectAllDatas", "" + listProvas.size());
        return listProvas;
    }

    public List<Prova> selectTodasAsProvasComDatasOrm() throws ParseException {
        Log.w("Prova selectAllDatas", "" );




        String query = "SELECT * FROM prova WHERE data <> '' AND status = 0";

        try {
            GenericRawResults<Prova> raw = queryRaw(query, new RawRowMapper<Prova>() {
                @Override
                public Prova mapRow(String[] columnNames, String[] results) throws SQLException {
                    Prova prova = new Prova();
                    prova.setId(Integer.parseInt(results[0]));
                    prova.setStatus(Integer.parseInt(results[1])==1);
                    prova.setTipo(Integer.parseInt(results[2]));
                    prova.setNota(Double.parseDouble(results[3]));
                    if(results[4].equals(""))
                        try {
                            prova.setData(format.parse(results[4]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    else
                        prova.setData(null);

                    Disciplina disciplina = new Disciplina();
                    disciplina.setId(Integer.parseInt(results[5]));
                    prova.setDisciplina(disciplina);

                    return prova;
                }
            });
            return raw.getResults();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    public Prova selectProvaIdDisciplina(String nome, int ano, int periodo, int tipo) throws ParseException {
        Log.w("Prova select", "" + nome);
        String sqlSelectProvaIdDisciplina = "SELECT * FROM prova WHERE nome_dis = '" + nome + "' AND ano_dis = " + ano +" AND semestre_dis = " + periodo + " AND tipo = " + tipo;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(sqlSelectProvaIdDisciplina, null);

        Prova prova = new Prova();
        if(c.moveToFirst()){


            prova.setId(c.getInt(0));
            prova.setNota(c.getDouble(1));
            prova.setTipo(c.getInt(2));
            prova.setStatus(c.getInt(3)==1);
            if(!c.getString(7).equals("")) {
                prova.setData(format.parse(c.getString(7)));
            }else
                prova.setData(null);
            //DisciplinaDao disciplinaDao = new DisciplinaDao(dbHelper);
            //Disciplina disciplina = disciplinaDao.selectDisciplina(nome, ano, periodo);
            //prova.setDisciplina(disciplina);
            Log.w("Prova select", "" + nome);
            return prova;

        }
        db.close();

        return prova;
    }

    public void deleteProva(String nome, int ano, int periodo){
        Log.w("Prova Delete", "" + nome);
        String sqlDeleteProva = "DELETE FROM prova WHERE nome_dis = '" + nome + "' AND ano_dis = " + ano +" AND semestre_dis = " + periodo;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(sqlDeleteProva);
        db.close();

    }

    public void updateProva(Disciplina newDisciplina, Disciplina oldDisciplina) {
        Log.w("Prova update", "" + oldDisciplina.getNome());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int status = 0;
        if(newDisciplina.getProva1().isStatus())
            status=1;
            String sqlUpdateProva1 = "UPDATE prova SET";
                    sqlUpdateProva1 += " nota = " + newDisciplina.getProva1().getNota() + " , status = "+status;
                    if(newDisciplina.getProva1().getData() != null) {
                        sqlUpdateProva1 += " , data = '" + format.format(newDisciplina.getProva1().getData()) + "'";
                    }
                    sqlUpdateProva1 += " WHERE nome_dis = '"
                    + oldDisciplina.getNome() + "' AND ano_dis = "
                    + oldDisciplina.getAno() + " AND semestre_dis = "
                    + oldDisciplina.getSemestre() + " AND tipo = 1";
            db.execSQL(sqlUpdateProva1);

        if(newDisciplina.getProva2().isStatus())
            status=1;
        else
            status=0;
            String sqlUpdateProva2 = "UPDATE prova SET nota = " + newDisciplina.getProva2().getNota() + " , status = "+status;
                    if(newDisciplina.getProva2().getData() != null) {
                        sqlUpdateProva2 += " , data = '" + format.format(newDisciplina.getProva2().getData()) + "'";
                    }
                    sqlUpdateProva2 += " WHERE nome_dis = '"
                    + oldDisciplina.getNome() + "' AND ano_dis = "
                    + oldDisciplina.getAno() + " AND semestre_dis = "
                    + oldDisciplina.getSemestre() + " AND tipo = 2";
            db.execSQL(sqlUpdateProva2);




        db.close();


    }
}
