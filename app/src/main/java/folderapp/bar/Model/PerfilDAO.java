package folderapp.bar.Model;

/*
    Data de criação: 29/05/2018

 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PerfilDAO extends SQLiteOpenHelper{
    private static final int VERSAO_BANCO = 1;
    private static String BANCO = "bd_kmperfilapp";

    private static final String TABELA_PERFIL = "tb_perfis";
    
    private static final String COLUNA_CODIGO = "codigo";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_FOTO = "foto";

    public PerfilDAO(Context context){
        super(context, BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_COLUNA = "CREATE TABLE " + TABELA_PERFIL + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY, " + COLUNA_NOME + " TEXT,"+
                COLUNA_FOTO+" TEXT)";

        db.execSQL(QUERY_COLUNA);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //No code;

    }
    public void addPerfil(Perfil perfil){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, perfil.getNome());
        values.put(COLUNA_FOTO, perfil.getFoto());

        db.insert(TABELA_PERFIL, null, values);
        db.close();
    }

    public Perfil carregarPerfil(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_PERFIL, new String[] {COLUNA_CODIGO, COLUNA_NOME,
                        COLUNA_FOTO}, COLUNA_CODIGO + " = ?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        try {
            Perfil perfil = new Perfil(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));

            return perfil;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void apagarPerfil(Perfil perfil){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_PERFIL, COLUNA_CODIGO + " =?", new String[] { String.valueOf(perfil.getId())});

        db.close();
    }
    public void atualizarPerfil(Perfil perfil){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, perfil.getNome());
        values.put(COLUNA_FOTO, perfil.getFoto());

        db.update(TABELA_PERFIL, values, COLUNA_CODIGO + " = ?",
                new String[] { String.valueOf(perfil.getId())});
    }

    public List<Perfil> listarTodasPerfis(){
        List<Perfil> listaPerfis = new ArrayList<Perfil>();

        String query = "SELECT * FROM " + TABELA_PERFIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Perfil perfil = new Perfil();
                perfil.setId(Integer.parseInt(c.getString(0)));
                perfil.setNome(c.getString(1));
                perfil.setFoto(c.getString(2));

                listaPerfis.add(perfil);
                Log.i("###### Tabela: Perfil", " id: "+perfil.getId()+" | Nome: "+perfil.getNome()+" Foto"+perfil.getFoto());

            }while(c.moveToNext());
        }
        return listaPerfis;
    }
    public boolean isEmpty(){
        List<Perfil> listaPerfis = new ArrayList<Perfil>();

        String query = "SELECT * FROM " + TABELA_PERFIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            return false;
        }
        return true;
    }
    public void deletarRegistros(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABELA_PERFIL);

        db.close();
    }
}
