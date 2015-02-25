package Models;

import com.j256.ormlite.*;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bruno on 27/01/15.
 */
@DatabaseTable(tableName = "prova")
public class Prova implements Serializable {
    @DatabaseField(generatedId = true ,columnName = "id")
    private int id;
    @DatabaseField(columnName = "status")
    private boolean status;
    @DatabaseField(columnName = "tipo")
    private int tipo;
    @DatabaseField(columnName = "nota")
    private Double nota;
    @DatabaseField(columnName = "data")
    private Date data;

    @DatabaseField(foreign = true , foreignAutoRefresh = true)
    private Disciplina disciplina;

    public Prova() {
        status = false;
    }

    public Prova(int tipo, Double nota, boolean status) {
        this.tipo = tipo;
        this.nota = nota;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
