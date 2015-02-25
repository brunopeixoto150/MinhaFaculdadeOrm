package Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;


import com.example.bruno.minhafaculdade.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import Models.Disciplina;

/**
 * Created by Bruno on 26/01/2015.
 */
public class DisciplinaDao {

    private DbHelper dbHelper;

    public DisciplinaDao(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void insertDisciplina(Disciplina disciplina){

        Log.w("Disciplina Insert", disciplina.getNome());
        ContentValues cv = new ContentValues();


        cv.put("nome", disciplina.getNome());


        cv.put("media", disciplina.getMedia());

        cv.put("precisa", disciplina.getPrecisa());
        cv.put("ementa", disciplina.getEmenta());
        cv.put("professor", disciplina.getProfessor());
        cv.put("ano", disciplina.getAno());
        cv.put("semestre", disciplina.getSemestre());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.insert("disciplina", null, cv);
        db.close();


    }

    public List<Disciplina> selectTodasAsDisciplinas() throws ParseException {

        Log.w("Disciplina selectTodasAsDisciplinas", "");
        List<Disciplina> listDisciplinas = new ArrayList<Disciplina>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sqlSelectTodasDisciplinas = "SELECT * FROM disciplina";

        Cursor c = db.rawQuery(sqlSelectTodasDisciplinas, null);

        if(c.moveToFirst()){
            do{
                Disciplina disciplina = new Disciplina();
                disciplina.setId(c.getInt(0));
                disciplina.setNome(c.getString(1));
                disciplina.setMedia(c.getString(2));
                disciplina.setPrecisa(c.getString(3));
                disciplina.setEmenta(c.getString(4));
                disciplina.setProfessor(c.getString(5));
                disciplina.setAno(c.getInt(6));
                disciplina.setSemestre(c.getInt(7));

                listDisciplinas.add(disciplina);

            }while(c.moveToNext());
        }
        db.close();
        for(int i = 0 ;i < listDisciplinas.size(); i++) {
            ProvaDao provaDao = new ProvaDao(dbHelper);
            listDisciplinas.get(i).setProva1(provaDao.selectProvaIdDisciplina(listDisciplinas.get(i).getNome(), listDisciplinas.get(i).getAno(), listDisciplinas.get(i).getSemestre(), 1));
            listDisciplinas.get(i).setProva2(provaDao.selectProvaIdDisciplina(listDisciplinas.get(i).getNome(), listDisciplinas.get(i).getAno(), listDisciplinas.get(i).getSemestre(), 2));
        }
        Log.w("Disciplina selectTodasAsDisciplinas", ""+listDisciplinas.size());

        return listDisciplinas;
    }

    public void deleteDisciplina(int id){
        Log.w("Disciplina deleteId", ""+id);
        String sqlDeleteDisciplina = "DELETE FROM disciplina WHERE id = " + id;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(sqlDeleteDisciplina);
        db.close();


    }

    public void deleteDisciplina(String nome, int ano, int periodo){
        Log.w("Disciplina delete", ""+nome);
        String sqlDeleteDisciplina = "DELETE FROM disciplina WHERE nome = '" + nome + "' AND ano = " + ano +" AND semestre = " + periodo;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(sqlDeleteDisciplina);
        db.close();

    }

    public Disciplina selectDisciplina(int id) throws ParseException {
        Log.w("Disciplina selectId", ""+id);
        String sqlSelectDisciplina = "SELECT * FROM disciplina WHERE id = " + id;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(sqlSelectDisciplina, null);

        Disciplina disciplina = new Disciplina();
        if(c.moveToFirst()){

            disciplina.setId(c.getInt(0));
            disciplina.setNome(c.getString(1));
            disciplina.setMedia(c.getString(2));
            disciplina.setPrecisa(c.getString(3));
            disciplina.setEmenta(c.getString(4));
            disciplina.setProfessor(c.getString(5));
            disciplina.setAno(c.getInt(6));
            disciplina.setSemestre(c.getInt(7));
        }
        db.close();
        if (disciplina.getNome() != null && !disciplina.getNome().equals("")){
            ProvaDao provaDao = new ProvaDao(dbHelper);
            disciplina.setProva1(provaDao.selectProvaIdDisciplina(disciplina.getNome(), disciplina.getAno(), disciplina.getSemestre(), 1));
            disciplina.setProva2(provaDao.selectProvaIdDisciplina(disciplina.getNome(), disciplina.getAno(), disciplina.getSemestre(), 2));

            return disciplina;
        }
        return null;
    }

    public Disciplina selectDisciplina(String nome, int ano, int periodo) throws ParseException {
        Log.w("Disciplina select", ""+nome);
        String sqlSelectDisciplina = "SELECT * FROM disciplina WHERE nome = '" + nome + "' AND ano = " + ano + " AND semestre = " + periodo;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(sqlSelectDisciplina, null);

        Disciplina disciplina = new Disciplina();
        if (c.moveToFirst()) {

            disciplina.setId(c.getInt(0));
            disciplina.setNome(c.getString(1));
            disciplina.setMedia(c.getString(2));
            disciplina.setPrecisa(c.getString(3));
            disciplina.setEmenta(c.getString(4));
            disciplina.setProfessor(c.getString(5));
            disciplina.setAno(c.getInt(6));
            disciplina.setSemestre(c.getInt(7));
        }

        if (disciplina.getNome() != null && !disciplina.getNome().equals("")){
            ProvaDao provaDao = new ProvaDao(dbHelper);
            disciplina.setProva1(provaDao.selectProvaIdDisciplina(disciplina.getNome(), disciplina.getAno(), disciplina.getSemestre(), 1));
            disciplina.setProva2(provaDao.selectProvaIdDisciplina(disciplina.getNome(), disciplina.getAno(), disciplina.getSemestre(), 2));

            return disciplina;
        }
        db.close();
        return null;
    }

    public void updateDisciplina(Disciplina newDisciplina, Disciplina oldDisciplina) {
        Log.w("Disciplina update", ""+oldDisciplina.getNome());
        

        String sqlUpdateDisciplina = "UPDATE disciplina SET professor = '" + newDisciplina.getProfessor() + "' , ementa = '" + newDisciplina.getEmenta() + "' , media = '" + newDisciplina.getMedia() + "' WHERE nome = '" + oldDisciplina.getNome() + "' AND ano = " + oldDisciplina.getAno() + " AND semestre = " + oldDisciplina.getSemestre();
        Toast.makeText(dbHelper.getContext(),sqlUpdateDisciplina,Toast.LENGTH_LONG);
        Log.i("Minha Faculdade",sqlUpdateDisciplina);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(sqlUpdateDisciplina);
        db.close();

        ProvaDao provaDao = new ProvaDao(dbHelper);
        provaDao.updateProva(newDisciplina,oldDisciplina);
    }

    public Disciplina selectDisciplinaNome(String nome) throws ParseException {
        Log.w("Disciplina selectNome", ""+nome);
        String sqlSelectDisciplina = "SELECT * FROM disciplina WHERE nome = '" + nome + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(sqlSelectDisciplina, null);

        Disciplina disciplina = new Disciplina();
        if(c.moveToFirst()){

            disciplina.setId(c.getInt(0));
            disciplina.setNome(c.getString(1));
            disciplina.setMedia(c.getString(2));
            disciplina.setPrecisa(c.getString(3));
            disciplina.setEmenta(c.getString(4));
            disciplina.setProfessor(c.getString(5));
            disciplina.setAno(c.getInt(6));
            disciplina.setSemestre(c.getInt(7));
        }
        db.close();
        if (disciplina.getNome() != null && !disciplina.getNome().equals("")){
            ProvaDao provaDao = new ProvaDao(dbHelper);
            disciplina.setProva1(provaDao.selectProvaIdDisciplina(disciplina.getNome(), disciplina.getAno(), disciplina.getSemestre(), 1));
            disciplina.setProva2(provaDao.selectProvaIdDisciplina(disciplina.getNome(), disciplina.getAno(), disciplina.getSemestre(), 2));

            return disciplina;
        }
        return null;
    }

    public String calculaMedia(Disciplina disciplina){
        ConfiguracoesDao configuracoesDao = new ConfiguracoesDao(dbHelper);
        if(configuracoesDao.selectConfiguracoes() != null && configuracoesDao.selectConfiguracoes().getFaculdade().equals(dbHelper.getContext().getResources().getString(R.string.estacio))) {
            double media = (((disciplina.getProva1().getNota()) + (disciplina.getProva2().getNota())) / 2);
            if(media>=6 && disciplina.getProva1().getNota()>=4 && disciplina.getProva2().getNota()>=4)
                disciplina.setMedia(media + " Aprovado!");
            else if(disciplina.getProva1().getNota()<4 && disciplina.getProva2().getNota()<4){
                disciplina.setMedia(media + " Reprovado!");
            } else{
                    if(disciplina.getProva1().getNota()>disciplina.getProva2().getNota()) {
                        double precisa = 12 - disciplina.getProva1().getNota();
                        if(precisa<10){
                            if(precisa<4)
                                precisa = 4;
                            disciplina.setMedia(media + " precisando tirar " + precisa + " na Av3.");
                        }else
                            disciplina.setMedia(media + " Reprovado!");
                    }else{
                        double precisa = 12 - disciplina.getProva2().getNota();
                        if(precisa<10)
                            disciplina.setMedia(media + " precisando tirar " + precisa+" na Av3.");
                        else
                            disciplina.setMedia(media + " Reprovado!");
                    }
            }
        }else {
            double media = (((disciplina.getProva1().getNota() * 2) + (disciplina.getProva2().getNota() * 3)) / 5);
            if(media>=5)
                disciplina.setMedia(media + " Aprovado!");
            else{
                if(media<3)
                    disciplina.setMedia(media + " Reprovado!");
                else{
                    double precisa = 10 - media;
                    disciplina.setMedia(media + " precisando tirar "+precisa+" na final.");
                }
            }
        }
        return disciplina.getMedia();
    }
}
