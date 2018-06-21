package folderapp.bar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextClock;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;


public class CorridaActivity extends AppCompatActivity {
    private Corrida corrida;
    private TextView kmTxV, tVtempoMax;
    private FloatingActionButton btnFIniciar, btnFParar, btnFPausar;
    private Chronometer cronometro;
    private long milliseconds;
    private CorridaDAO bd = new CorridaDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrida);

        corrida = MainActivity.Transicao.getCorrida();
        Log.i("%%%%$$$$$$$", " Id:"+ corrida.getId()+" Km Máximo: "+corrida.getMaxKm());

        milliseconds = 0;

        btnFIniciar = (FloatingActionButton) findViewById(R.id.iniciar_btnF);
        btnFParar = (FloatingActionButton) findViewById(R.id.parar_btnF);
        btnFPausar = (FloatingActionButton) findViewById(R.id.pausar_btnF);
        cronometro = (Chronometer) findViewById(R.id.cronometro);
        kmTxV = (TextView) findViewById(R.id.km_max_tv);
        tVtempoMax = (TextView) findViewById(R.id.tempoMax_tV);

        kmTxV.setText("KM 0.0/"+corrida.getMaxKm());
        tVtempoMax.setText(corrida.getMaxTempo());

        btnFIniciar.setEnabled(true);
        btnFParar.setEnabled(false);
        btnFPausar.setEnabled(false);

        //Marca a data de largada
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        corrida.setdiaMesAno(dateString);
        Log.i("####### Data #######", corrida.getdiaMesAno());


        //Marca as horas em que a corrida foi iniciada
        TextClock clock;
        clock = new TextClock(getApplicationContext());
        clock.setFormat12Hour("HH:mm");
        String horario = String.valueOf(clock.getText());
        corrida.setHorario(horario);

        Log.i("###############", "Horário: " + corrida.getHorario());

        btnFIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFIniciar.setImageResource(R.drawable.ic_play_off);

                btnFIniciar.setEnabled(false);
                btnFPausar.setImageResource(R.drawable.ic_pause);
                btnFPausar.setEnabled(true);
                btnFParar.setEnabled(true);

                cronometro.setBase(SystemClock.elapsedRealtime() - milliseconds);
                cronometro.start();
            }
        });
        btnFParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                corrida.setFinalizada(true);

                //Comportamento dos botões
                btnFParar.setImageResource(R.drawable.ic_stop_off);
                btnFIniciar.setEnabled(false);
                btnFParar.setEnabled(false);
                btnFPausar.setEnabled(false);

                //Cronometro deve parar e guardar seu valor no objeto Corrida
                cronometro.stop();
                corrida.setTempo(String.valueOf(cronometro.getText()));

                //Pegar todas as informações da corrida e guradar no BD.
                Log.i("###############","Id: "+String.valueOf(corrida.getId())+"Tempo de corrida: "+corrida.getTempo()+
                " Meta de Km: "+corrida.getMaxKm()+" Tempo máximo: "+corrida.getMaxTempo()+
                        " Horário do início da largada: "+corrida.getHorario()+
                        " Data: "+ corrida.getdiaMesAno()+
                                " Finalizada: "+corrida.isFinalizada());
                //A ideia é quando é usuário acionar esse botão, o tempo será armazenado no BD e será aberta uma view.

                bd.atualizarCorridaFinalizada(corrida);

                startActivity(new Intent(CorridaActivity.this, MainActivity.class));

                /*Fragment fragmentSelect = MyMeta.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragmentSelect);
                transaction.commit();
                */
            }
        });
        btnFPausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFPausar.setBackgroundColor(Color.parseColor("#2e2e2e"));
                btnFPausar.setImageResource(R.drawable.ic_pause_off);
               // btnFPausar.setBackgroundColor(Color.YELLOW);
                btnFPausar.setEnabled(false);
                btnFIniciar.setImageResource(R.drawable.ic_play);
                btnFIniciar.setEnabled(true);
                milliseconds = SystemClock.elapsedRealtime() - cronometro.getBase();
                cronometro.stop();
            }
        });
    }
}
