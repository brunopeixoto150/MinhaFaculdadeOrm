package Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Bruno on 29/01/2015.
 */
@DatabaseTable(tableName = "configuracoes")
public class Configuracoes implements Serializable {
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @DatabaseField(columnName = "name")
    private String nome;
    @DatabaseField(columnName = "faculdade")
    private String faculdade;
    @DatabaseField(columnName = "curso")
    private String curso;

    public Configuracoes() {
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(String faculdade) {
        this.faculdade = faculdade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
