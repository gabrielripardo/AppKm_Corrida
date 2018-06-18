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

public class CorridaDAO extends SQLiteOpenHelper{
    private static final int VERSAO_BANCO = 1;
    private static String BANCO = "bd_kmcorridaapp";

    private static final String TABELA_CORRIDA = "tb_corridas";
    
    private static final String COLUNA_CODIGO = "codigo";
    private static final String COLUNA_COMENTARIO = "comentario";
    private static final String COLUNA_MAXKM = "maxkm";
    private static final String COLUNA_MAXTEMPO = "maxtempo";
    private static final String COLUNA_TEMPO = "tempo";
    private static final String COLUNA_FINALIZADA = "finalizada";
    private static final String COLUNA_HORARIO = "horario";
    private static final String COLUNA_CALENDAR = "calendar";
    private static final String COLUNA_KMPercorrido = "kmpercorrido";


    public CorridaDAO(Context context){
        super(context, BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_COLUNA = "CREATE TABLE " + TABELA_CORRIDA + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY, " + COLUNA_COMENTARIO + " TEXT,"
                + COLUNA_MAXKM + " TEXT, " + COLUNA_MAXTEMPO + " TEXT," + COLUNA_TEMPO +" TEXT,"+ COLUNA_FINALIZADA +" INTEGER,"+ COLUNA_HORARIO+" TEXT,"+ COLUNA_CALENDAR+ " TEXT,"+ COLUNA_HORARIO+"TEXT,"+
                COLUNA_KMPercorrido+" TEXT)";

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
        values.put(COLUNA_FINALIZADA, 0);

        db.insert(TABELA_CORRIDA, null, values);
        db.close();
    }
    /*
    public void addCorridaFinalizada(Corrida corrida){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_COMENTARIO, corrida.getComment());
        values.put(COLUNA_MAXKM, corrida.getMaxKm());
        values.put(COLUNA_MAXTEMPO, corrida.getMaxTempo());
        values.put(COLUNA_TEMPO, corrida.getTempo());
        int finalizada = 0;
        if(corrida.isFinalizada()==true)
            finalizada = 1;
        values.put(COLUNA_FINALIZADA, finalizada);

        db.insert(TABELA_CORRIDA, null, values);
        db.close();
    }*/
    public Corrida carregarCorrida(Corrida corrObj){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_CORRIDA, new String[] {COLUNA_CODIGO, COLUNA_COMENTARIO,
                        COLUNA_MAXKM, COLUNA_MAXTEMPO, COLUNA_FINALIZADA}, COLUNA_CODIGO + " = ?",
                new String[] {String.valueOf(corrObj.getId())}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        try {
            Corrida corrida = new Corrida(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Float.parseFloat(cursor.getString(2)), cursor.getString(3));

            if(corrObj.isFinalizada())
                if(Integer.parseInt(cursor.getString(4))==1){
                    corrida.setFinalizada(true);
                }


            return corrida;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Corrida carregarCorridaFinalizada(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_CORRIDA, new String[] {COLUNA_CODIGO, COLUNA_COMENTARIO,
                        COLUNA_MAXKM, COLUNA_MAXTEMPO, COLUNA_TEMPO, COLUNA_FINALIZADA, COLUNA_HORARIO, COLUNA_CALENDAR}, COLUNA_CODIGO + " = ?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        try {
            //Criação de objeto e popularização
            Corrida corrida = new Corrida(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Float.parseFloat(cursor.getString(2)), cursor.getString(3));
            corrida.setTempo(cursor.getString(4));
            if(Integer.parseInt(cursor.getString(5))==1){
                corrida.setFinalizada(true);
            }
            corrida.setHorario(cursor.getString(6));
            corrida.setdiaMesAno(cursor.getString(7));

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
        values.put(COLUNA_TEMPO, corrida.getTempo());
        int finalizada = 0;
        if(corrida.isFinalizada())
            finalizada = 1;
        values.put(COLUNA_FINALIZADA, finalizada);

        db.update(TABELA_CORRIDA, values, COLUNA_CODIGO + " = ?",
                new String[] { String.valueOf(corrida.getId())});
    }
    public void atualizarCorridaFinalizada(Corrida corrida){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_COMENTARIO, corrida.getComment());
        values.put(COLUNA_MAXKM, corrida.getMaxKm());
        values.put(COLUNA_MAXTEMPO, corrida.getMaxTempo());
        values.put(COLUNA_TEMPO, corrida.getTempo());
        values.put(COLUNA_HORARIO, corrida.getHorario());
        values.put(COLUNA_CALENDAR, corrida.getdiaMesAno());

        int finalizada = 0;
        if(corrida.isFinalizada())
            finalizada = 1;
        values.put(COLUNA_FINALIZADA, finalizada);

        db.update(TABELA_CORRIDA, values, COLUNA_CODIGO + " = ?",
                new String[] { String.valueOf(corrida.getId())});
    }
    public List<Corrida> listarTodasCorridas(){
        List<Corrida> listaCorridas = new ArrayList<Corrida>();

        String query = "SELECT * FROM " + TABELA_CORRIDA+" ORDER BY "+COLUNA_CODIGO+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Corrida corrida = new Corrida();
                corrida.setId(Integer.parseInt(c.getString(0)));
                corrida.setComment(c.getString(1));
                corrida.setMaxKm(Float.parseFloat(c.getString(2)));
                corrida.setMaxTempo(c.getString(3));
                if(c.getString(5)!=null) {
                    if (Integer.parseInt(c.getString(5)) == 1)
                        corrida.setFinalizada(true);
                }

                listaCorridas.add(corrida);
                Log.i("$$$$$$$$$$$$$$$$", "DIRETO DO BANCO $$$$$$$$$$$$$$$$$$$$$$ Finalizada? "+ String.valueOf(corrida.isFinalizada())+" Tempo: "+corrida.getTempo());

            }while(c.moveToNext());
        }
        return listaCorridas;
    }
}
