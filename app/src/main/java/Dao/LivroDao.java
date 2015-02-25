package Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Models.Livro;

/**
 * Created by Bruno on 26/01/2015.
 */
public class LivroDao {
    private DbHelper dbHelper;

    public LivroDao(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void insertLivro(Livro livro){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv =new ContentValues();


        cv.put("titulo", livro.getTitulo());
        cv.put("autor", livro.getAutor());
        cv.put("paginas", livro.getPaginas());

        db.insert("livro", null, cv);

        db.close();
    }

    public List<Livro> selectTodosOsLivros(){

        List<Livro> listLivros = new ArrayList<Livro>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sqlSelectTodosLivros = "SELECT * FROM livro";

        Cursor c = db.rawQuery(sqlSelectTodosLivros, null);

        if(c.moveToFirst()){
            do{
                Livro livro = new Livro();
                livro.setId(c.getInt(0));
                livro.setTitulo(c.getString(1));
                livro.setAutor(c.getString(2));
                livro.setPaginas(c.getInt(3));

                listLivros.add(livro);
            }while(c.moveToNext());
        }

        db.close();
        return listLivros;
    }
}
