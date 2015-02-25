package com.example.bruno.minhafaculdade;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Dao.DisciplinaDaoOrm;
import Dao.OrmDbHelper;
import Dao.ProvaDaoOrm;
import Models.Disciplina;
import Models.Prova;

/**
 * Created by bruno on 03/02/15.
 */
public class OrmMainActivity extends Activity {
    private OrmDbHelper ormDbHelper;
    private DisciplinaDaoOrm disciplinaDao;
    private ProvaDaoOrm provaDao;
    private List<Disciplina> disciplinaList;
    private Disciplina disciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_disciplina);
        ormDbHelper = new OrmDbHelper(this);
        disciplinaList = new ArrayList<Disciplina>();

        disciplina = new Disciplina();
        disciplina.setNome("Português");
        disciplina.setAno(2012);
        disciplina.setSemestre(1);
        disciplina.setProvas(Arrays.asList(new Prova(1,5.0,true), new Prova(2,6.0,true)));
        disciplinaList.add(disciplina);

        disciplina = new Disciplina();
        disciplina.setNome("Calculo I");
        disciplina.setAno(2012);
        disciplina.setSemestre(1);
        disciplina.setProvas(Arrays.asList(new Prova(1,5.0,true), new Prova(2,4.0,true)));
        disciplinaList.add(disciplina);

        try {
            disciplinaDao = new DisciplinaDaoOrm(ormDbHelper.getConnectionSource(),this);
            provaDao = new ProvaDaoOrm(ormDbHelper.getConnectionSource());
            for(Disciplina disciplina1: disciplinaList){
                int result = disciplinaDao.create(disciplina1);
                if(result == 1)
                    for(Prova prova: disciplina1.getProvas()){
                        prova.setDisciplina(disciplina1);
                        provaDao.create(prova);
                    }
            }

            //GET ALL LINES
            Log.w("Script","GET ALL LINES");
            disciplinaList = disciplinaDao.queryForAll();
            for (Disciplina disciplina1: disciplinaList){
                Log.w("Script","Name: "+disciplina1.getNome()+"\nId: "+disciplina1.getId()+"\n Provas: "+disciplina1.getProvas().size());
            }


            Log.w("Script","GET  LINE");
            Disciplina disciplina1 = new Disciplina();
            disciplina1 = disciplinaDao.selectDisciplina("Calculo I",disciplina.getAno(), disciplina.getSemestre());
            Log.w("Disciplina", "Nome: "+disciplina1.getNome() +" Ano: "+disciplina1.getAno()+" Semestre: "+disciplina1.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
