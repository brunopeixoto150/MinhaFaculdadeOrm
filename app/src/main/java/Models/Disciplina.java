package Models;

import android.os.BaseBundle;
import android.support.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by bruno on 23/01/15.
 */
@DatabaseTable(tableName = "disciplina")
public class Disciplina  implements Serializable {
    @DatabaseField(generatedId = true,columnName = "id")
    private int id;
    @DatabaseField(canBeNull = false, columnName = "nome")
    private String nome;
    @DatabaseField(canBeNull = false, columnName = "ano")
    private Integer ano;
    @DatabaseField(canBeNull = false, columnName = "semestre")
    private Integer semestre;


    @DatabaseField(columnName = "media")
    private String media;
    @DatabaseField(columnName = "precisa")
    private String precisa;
    @DatabaseField(columnName = "ementa")
    private String ementa;
    @DatabaseField(columnName = "professor")
    private String professor;
    @ForeignCollectionField
    private Collection<Prova> provas;
    private Prova prova1;
    private Prova prova2;

    public Disciplina() {
        provas = new ArrayList<Prova>();


    }

    public Disciplina(int id, String nome, Prova prova1, Prova prova2, String media, String precisa, String ementa, String professor, int ano, int semestre) {


        this.id = id;
        this.nome = nome;
        this.prova1 = prova1;
        this.prova2 = prova2;
        this.media = media;
        this.precisa = precisa;
        this.ementa = ementa;
        this.professor = professor;
        this.ano = ano;
        this.semestre = semestre;
        this.provas =  new ArrayList<Prova>();
        this.provas.add(prova1);
        this.provas.add(prova2);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public String getNomeTabela() {
        String nome;
        if(this.nome.length()<10)
            nome = this.nome.substring(0,this.nome.length());
        else
            nome = this.nome.substring(0,7);

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Prova getProva1() {
        return prova1;
    }

    public void setProva1(Prova prova1) {
        this.prova1 = prova1;
    }

    public Prova getProva2() {
        return prova2;
    }

    public void setProva2(Prova prova2) {
        this.prova2 = prova2;
    }

    public String getMedia() {
        return this.media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getPrecisa() {
        return precisa;
    }

    public void setPrecisa(String precisa) {
        this.precisa = precisa;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public String getProfessor() {
        return professor;
    }

    public String getProfessorTabela() {
        String professor;
        if(this.professor.length()<6)
            professor = this.professor.substring(0,this.professor.length());
        else
            professor = this.professor.substring(0,6);

        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Collection<Prova> getProvas() {
        return provas;
    }

    public void setProvas(Collection<Prova> provas) {
        this.provas = provas;
    }

    @Override
    public String toString() {
        return nome;
    }


}
