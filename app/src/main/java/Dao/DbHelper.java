package Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private static final String NOME_BASE = "MinhaFaculdade";
	private static final int VERSAO_BASE = 1;
    private Context context;
	
	public DbHelper(Context context) {

		super(context, NOME_BASE, null, VERSAO_BASE);
        this.context = context;
	}



    @Override
	public void onCreate(SQLiteDatabase db) {
		String sqlCreateTabelaLivro = "CREATE TABLE livro("
									+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
									+ "titulo TEXT,"
									+ "autor TEXT,"
									+ "paginas INTEGER"
									+ ")";
		db.execSQL(sqlCreateTabelaLivro);

        String sqlCreateTabelaDisciplina = "CREATE TABLE disciplina("
                + "id INTEGER,"
                + "nome TEXT NOT NULL,"
                + "media TEXT,"
                + "precisa TEXT,"
                + "ementa TEXT,"
                + "professor TEXT,"
                + "ano INTEGER NOT NULL,"
                + "semestre INTEGER NOT NULL ,"
                + "PRIMARY KEY(nome, ano, semestre)"
                + ")";
        db.execSQL(sqlCreateTabelaDisciplina);

        String sqlCreateTabelaProva = "CREATE TABLE prova("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nota DOUBLE, "
                + "tipo INTEGER, "
                + "status BOOLEAN, "
                + "nome_dis TEXT, "
                + "ano_dis INTEGER, "
                + "semestre_dis INTEGER, "
                + "data TEXT"
                //+ "FOREIGN KEY(nome_dis, ano_dis, semestre_dis) REFERENCES disciplina(nome, ano, semestre)"
                + ")";
        db.execSQL(sqlCreateTabelaProva);

        String sqlCreateTabelaConfguracoes = "CREATE TABLE configuracoes("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "faculdade TEXT, "
                + "nome TEXT, "
                + "curso TEXT "
                + ")";
        db.execSQL(sqlCreateTabelaConfguracoes);


		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		String sqlDropTabelaLivros = "DROP TABLE livro";
		db.execSQL(sqlDropTabelaLivros);

        String sqlDropTabelaProvas = "DROP TABLE prova";
        db.execSQL(sqlDropTabelaProvas);

        String sqlDropTabelaDisciplinas = "DROP TABLE disciplina";
        db.execSQL(sqlDropTabelaDisciplinas);
		
		onCreate(db);
	}

    public Context getContext() {
        return context;
    }
}
