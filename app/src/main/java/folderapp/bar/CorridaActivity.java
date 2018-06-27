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
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;

import static java.lang.Thread.sleep;


public class CorridaActivity extends AppCompatActivity {
    private Corrida corrida;
    protected TextView tVGpsKm, tVKmMax, tVtempoMax;
    private FloatingActionButton btnFIniciar, btnFParar, btnFPausar;
    private Button btnSimular;
    private Chronometer cronometro;
    private long milliseconds;
    private int metros;
    protected static final int TIMER_RUNTIME = 10000;
    protected boolean mbActive;
    protected ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrida);

        corrida = MainActivity.Transicao.getCorrida();
//        Log.i("%%%%$$$$$$$", " Id:"+ corrida.getId()+" Km Máximo: "+corrida.getMaxKm());

        milliseconds = 0;

        mbActive = true;

        mProgressBar = (ProgressBar) findViewById(R.id.barrinha_pB);
        btnSimular = (Button) findViewById(R.id.simulador_btn);
        btnFIniciar = (FloatingActionButton) findViewById(R.id.iniciar_btnF);
        btnFParar = (FloatingActionButton) findViewById(R.id.parar_btnF);
        btnFPausar = (FloatingActionButton) findViewById(R.id.pausar_btnF);
        cronometro = (Chronometer) findViewById(R.id.cronometro);
        tVKmMax = (TextView) findViewById(R.id.km_max_tv);
        tVGpsKm = (TextView) findViewById(R.id.kmGps_tV);
        tVtempoMax = (TextView) findViewById(R.id.tempoMax_tV);

        tVKmMax.setText(String.valueOf(corrida.getMaxKm()));

        String[] tempoLim = corrida.convetToHoursFormat(corrida.getMaxTempo());

        tVtempoMax.setText(tempoLim[0]+ ":" +tempoLim[1]);

        btnFIniciar.setEnabled(true);
        btnFParar.setEnabled(false);
        btnFPausar.setEnabled(false);
        btnSimular.setEnabled(false);

        //Marca a data de largada
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        corrida.setdiaMesAno(dateString);
        Log.i("####### Data #######", corrida.getdiaMesAno());


        //Marca as horas em que a corrida foi iniciada
        TextClock clock;
        clock = new TextClock(getApplicationContext());
        clock.setFormat12Hour("HH:mm:ss");
        String horario = String.valueOf(clock.getText());
        corrida.setHorario(horario);

        Log.i("###############", "Horário: " + corrida.getHorario());


        btnSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarBarra(true);
            }
        });



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

                btnSimular.setEnabled(true);

            }
        });
        btnFParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comportamento dos botões
                btnFParar.setImageResource(R.drawable.ic_stop_off);
                btnFIniciar.setEnabled(false);
                btnFParar.setEnabled(false);
                btnFPausar.setEnabled(false);

                mbActive = false;
                salvarCorrida();

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
    public void updateProgress(final int timePassed) {
        if (null != mProgressBar) {
            final int progress = mProgressBar.getMax() * timePassed / TIMER_RUNTIME;

            mProgressBar.setProgress(progress);
        }
    }
    public void iniciarBarra(final boolean simular){
        final Thread timerThread = new Thread(){
            @Override
            public void run(){
                try{
                    float percorre = (float) (corrida.getMaxKm()*1000)/10;
                    int waited = 0;
                    if(simular) {
                        while (mbActive && (waited < TIMER_RUNTIME)) { //A cada 200 milisegundo é acresentado o 200 até chegar a 10000
                            sleep(1000);
                            if (mbActive) {

                                waited += 1000;
                                updateProgress(waited);

                                metros += percorre; //a cada 200 milisegundo o robô anda 100 metros

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tVGpsKm.setText(String.valueOf((float)metros/1000));
                                    }
                                });

                            }
                        }
                    }else {
                        //code gps
                        //é viável criar um método ou uma classe para obter os metros do gps
                        //valores de km gerados pelo gps
                    }
                }catch(InterruptedException e){
                    //Tratar exceção
                }finally{
                    if(mbActive) {
                        salvarCorrida();
                    }
                }
            }
        };
        timerThread.start();
    }
    public void salvarCorrida(){

        corrida.setFinalizada(true);

        //A quilometragem percorrida .......
        corrida.setKm((float) metros/1000);

        //Cronometro deve parar e guardar seu valor no objeto Corrida
        cronometro.stop();

        //Converte a String do cronometro em minutos
        // int horas = Integer.parseInt(String.valueOf(cronometro.getText().charAt(0))+String.valueOf(cronometro.getText().charAt(1)));
        int minus = Integer.parseInt(String.valueOf(cronometro.getText().charAt(3))+String.valueOf(cronometro.getText().charAt(4)));

        /////No momento o que tá sendo guardado são os segundos.
        /////O objetivo é obter resultados rápidos para implementar as medalhas ganhadas
        corrida.setMinutosTempo(minus, 0);


        MainActivity.Transicao.setCorrida(corrida);
        startActivity(new Intent(CorridaActivity.this, FinishCorrida.class));
    }
}
