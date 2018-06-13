package folderapp.bar.Model;

/*
    Data de criação: 29/05/2018
    obs.: Precisa tratar os erros. Quando o usuário digita um id não existente o aplicativo para.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CorridaDAO extends SQLiteOpenHelper{
    private static final int VERSAO_BANCO = 1;
    private static final String BANCO = "bd_km-corrida";

    private static final String TABELA_CORRIDA = "tb_corridas";
    
    private static final String COLUNA_CODIGO = "codigo";
    private static final String COLUNA_COMENTARIO = "comentario";
    private static final String COLUNA_MAXKM = "maxkm";
    private static final String COLUNA_MAXTEMPO = "maxtempo";

    public CorridaDAO(Context context){
        super(context, BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_COLUNA = "CREATE TABLE " + TABELA_CORRIDA + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY, " + COLUNA_COMENTARIO + " TEXT,"
                + COLUNA_MAXKM + " TEXT, " + COLUNA_MAXTEMPO + " TEXT)";

        db.execSQL(QUERY_COLUNA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //No code;
    }
    public void addCorrida(Corrida corrida){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_COMENTARIO, corrida.getComment());
        values.put(COLUNA_MAXKM, corrida.getMaxKm());
        values.put(COLUNA_MAXTEMPO, corrida.getMaxTempo());

        db.insert(TABELA_CORRIDA, null, values);
        db.close();
    }
    public Corrida carregarCorrida(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        //Corrida corrida = new Corrida(0, "", 0.0);

        Cursor cursor = db.query(TABELA_CORRIDA, new String[] {COLUNA_CODIGO, COLUNA_COMENTARIO,
                        COLUNA_MAXKM, COLUNA_MAXTEMPO}, COLUNA_CODIGO + " = ?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        try {
            Corrida corrida = new Corrida(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Float.parseFloat(cursor.getString(2)), cursor.getString(3));
            return corrida;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void apagarCorrida(Corrida corrida){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_CORRIDA, COLUNA_CODIGO + " =?", new String[] { String.valueOf(corrida.getId())});

        db.close();
    }
    public void atualizarCorrida(Corrida corrida){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_COMENTARIO, corrida.getComment());
        values.put(COLUNA_MAXKM, corrida.getMaxKm());
        values.put(COLUNA_MAXTEMPO, corrida.getMaxTempo());

        db.update(TABELA_CORRIDA, values, COLUNA_CODIGO + " = ?",
                new String[] { String.valueOf(corrida.getId())});
    }
    public List<Corrida> listarTodasCorridas(){
        List<Corrida> listaCorridas = new ArrayList<Corrida>();

        String query = "SELECT * FROM " + TABELA_CORRIDA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Corrida corrida = new Corrida();
                corrida.setId(Integer.parseInt(c.getString(0)));
                corrida.setComment(c.getString(1));
                corrida.setMaxKm(Float.parseFloat(c.getString(2)));
                corrida.setMaxTempo(c.getString(3));

                listaCorridas.add(corrida);

            }while(c.moveToNext());
        }
        return listaCorridas;
    }
}
