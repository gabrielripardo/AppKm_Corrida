package folderapp.bar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;

public class FinishCorrida extends AppCompatActivity {
    private Corrida c;
    private FloatingActionButton btnFNext;
    private FloatingActionButton btnFRestart;
    private MediaPlayer mediaPlayer;
    private ImageView iVMedalha;
    private TextView tVTipo, tVGanhou;
    private CorridaDAO bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_corrida);

        bd = new CorridaDAO(this);
        c = MainActivity.Transicao.getCorrida();

        mediaPlayer = MediaPlayer.create(FinishCorrida.this, R.raw.game_of_thrones_8bits);
        btnFNext = (FloatingActionButton) findViewById(R.id.next_btnF);
        btnFRestart = (FloatingActionButton) findViewById(R.id.restart_btnF);
        iVMedalha = (ImageView) findViewById(R.id.medalha_iV);
        tVTipo = (TextView) findViewById(R.id.tipoMedalha_tV);
        tVGanhou = (TextView) findViewById(R.id.ganhou_tV);

        mediaPlayer.start();

        salvarCorridaDb();

        btnFNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinishCorrida.this, MainActivity.class));
            }
        });
        btnFRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinishCorrida.this, CorridaActivity.class));
            }
        });
    }

    private int verificarResultado(){
        int medalha;
        int numM;
        if(c.getKm() >= c.getMaxKm()) {
            medalha = R.drawable.ouro;
            tVGanhou.setText("Ouro");
            numM = 1;
        }else if(c.getKm() > (0.5*c.getMaxKm()) && c.getKm() < (c.getMaxKm())){ // 50 por cento da meta e menor que a meta
            medalha = R.drawable.prata;
            tVGanhou.setText("Prata");
            numM = 2;
        }else{ //Bronze
            medalha = R.drawable.bronze;
            tVGanhou.setText("Bronze");
            numM = 3;
        }
        iVMedalha.setBackgroundResource(medalha);
        tVTipo.setText("KM máximo: "+String.valueOf(c.getMaxKm())+" Km Percoriddo: "+String.valueOf(c.getKm())+"Valor da medalha: "+medalha);
        return numM;
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    public void salvarCorridaDb(){
        c.setMedalha(verificarResultado());
        if(c.getId()!=0) {
            //Pegar todas as informações da c e guradar no BD.
            Log.i("###############", "Id: " + String.valueOf(c.getId()) + "Tempo de c: " + c.getTempo() +
                    " Meta de Km: " + c.getMaxKm() + " Tempo máximo: " + c.getMaxTempo() +
                    " Horário do início da largada: " + c.getHorario() +
                    " Data: " + c.getdiaMesAno() +
                    " Finalizada: " + c.isFinalizada());
            bd.atualizarCorridaFinalizada(c);
        }else{
            bd.addCorrida(c);
        }
    }
}
