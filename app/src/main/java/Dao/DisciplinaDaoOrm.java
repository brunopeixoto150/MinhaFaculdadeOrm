package Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.bruno.minhafaculdade.R;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.support.ConnectionSource;

import java.lang.reflect.GenericArrayType;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Models.Disciplina;
import Models.Prova;

/**
 * Created by Bruno on 26/01/2015.
 */
public class DisciplinaDaoOrm extends BaseDaoImpl<Disciplina, Integer> {

    private Context context;


    public DisciplinaDaoOrm(ConnectionSource connectionSource, Context context) throws SQLException {
        super(Disciplina.class);
        setConnectionSource(connectionSource);
        this.context = context;
        initialize();
    }


    public Disciplina selectDisciplina(String nome, int ano, int semestre){
        Disciplina disciplina = new Disciplina();
        String query = "SELECT id, nome, ano, semestre, media, precisa, ementa, professor FROM disciplina WHERE nome = '" + nome + "' AND ano = " + ano + " AND semestre = " + semestre;
        try {
            GenericRawResults<Disciplina> raw = queryRaw(query, new RawRowMapper<Disciplina>() {
                @Override
                public Disciplina mapRow(String[] columnNames, String[] results) throws SQLException {
                    Disciplina disciplina = new Disciplina();
                    disciplina.setId(Integer.parseInt(results[0]));
                    disciplina.setNome(results[1]);
                    disciplina.setAno(Integer.parseInt(results[2]));
                    disciplina.setSemestre(Integer.parseInt(results[3]));
                    disciplina.setMedia(results[4]);
                    disciplina.setPrecisa(results[5]);
                    disciplina.setEmenta(results[6]);
                    disciplina.setProfessor(results[7]);

                    return disciplina;
                }
            });
            return raw.getFirstResult();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String calculaMedia(Disciplina disciplina){
        try {
        ConfiguracoesDaoOrm configuracoesDao = new ConfiguracoesDaoOrm(connectionSource);
            double prova1 = 0.0, prova2 = 0.0;

            for(int i=0; i<disciplina.getProvas().size() ; i++){
                if(i==0)
                    prova1 = ((Prova) disciplina.getProvas().toArray()[i]).getNota();
                if(i==1)
                    prova2 = ((Prova) disciplina.getProvas().toArray()[i]).getNota();
            }

            if(configuracoesDao.queryForAll() != null && configuracoesDao.queryForId(1).getFaculdade().equals(context.getString(R.string.estacio))) {
                double media;

                media = (prova1 + prova2) / 2;
                if(media>=6 && prova1>=4 && prova2>=4)
                    disciplina.setMedia(media + " Aprovado!");
                else if(prova1<4 && prova2<4){
                    disciplina.setMedia(media + " Reprovado!");
                } else{
                    if(prova1>prova2) {
                        double precisa = 12 - prova1;
                        if(precisa<10){
                            if(precisa<4)
                                precisa = 4;
                            disciplina.setMedia(media + " precisando tirar " + precisa + " na Av3.");
                        }else
                            disciplina.setMedia(media + " Reprovado!");
                    }else{
                        double precisa = 12 - prova2;
                        if(precisa<10)
                            disciplina.setMedia(media + " precisando tirar " + precisa+" na Av3.");
                        else
                            disciplina.setMedia(media + " Reprovado!");
                    }
                }
            }else {
                double media = (((prova1 * 2) + (prova2 * 3)) / 5);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplina.getMedia();
    }
}
