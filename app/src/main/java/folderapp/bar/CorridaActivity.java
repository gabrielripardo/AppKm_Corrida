package folderapp.bar;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import folderapp.bar.Model.Corrida;

public class CorridaActivity extends AppCompatActivity {
    private FloatingActionButton btnFIniciar, btnFParar, btnFPausar;
    protected TextView tVGpsKm, tVKmMax, tVtempoMax;
    protected ProgressBar mProgressBar;
    private Chronometer cronometro;
    private Button btnSimular;
    private Corrida corrida;
    protected static final int TIMER_RUNTIME = 10000;
    private long milliseconds;
    protected int metros;
    private double totalMetros;
    protected boolean mbActive;
    protected GPS gps;
    protected DecimalFormat decF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrida);

        corrida = MainActivity.Transicao.getCorrida();

        gps = new GPS(this);

        milliseconds = 0;
       // cronometro.setText(DateFormat.format("kk:mm:ss",0));

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

        //Marca as horas em que a corrida foi iniciada
        TextClock clock;
        clock = new TextClock(getApplicationContext());
        clock.setFormat12Hour("hh:mm:ss");
        String horario = String.valueOf(clock.getText());
        corrida.setHorario(horario);

        btnSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                iniciarBarra(false);
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
                        while (mbActive && (waited < TIMER_RUNTIME)) { //TIMER_RUNTIME = 10000
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
                        mbActive = false;
                        decF = new DecimalFormat("0.000");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() { gps.createLocationRequest();

                                gps.mLocationCallback = new LocationCallback() {
                                    int waited = 0;
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        if (locationResult == null) {
                                            return;
                                        }
                                        for (Location location : locationResult.getLocations()) {
                                            gps.someDistances(location);

                                            //if(location.getSpeed()>0) {;
                                                if (waited >= TIMER_RUNTIME) {
                                                    tVGpsKm.setText(String.valueOf(corrida.getMaxKm()));

                                                    if (gps.totalKms > corrida.getMaxKm()) {
                                                        metros = (int) (corrida.getMaxKm() * 1000);
                                                    } else {
                                                        metros = (int) (gps.totalKms * 1000);
                                                    }
                                                    gps.stopLocationUpdate();
                                                    salvarCorrida();
                                                    break;
                                                }
                                                int varProgress = (int) ((TIMER_RUNTIME * gps.currentKm * 1000) / (corrida.getMaxKm() * 1000));
                                                waited += varProgress / 2;
                                                updateProgress(waited);
                                                tVGpsKm.setText(String.valueOf(Float.parseFloat(decF.format(gps.totalKms))));
                                                Log.i("Km Current", "var:waited = " + waited);
                                                Log.i("Location Current:", "\nLongitude = " + location.getLongitude() + "\nLatitude" + location.getLongitude() + "\nSpeed: " + location.getSpeed());
                                                }
                                        //}
                                    }
                                };
                                gps.startLocationUpdates();
                            }
                        });
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
        int minus = Integer.parseInt(String.valueOf(cronometro.getText().charAt(3))+String.valueOf(cronometro.getText().charAt(4)));

        /////No momento o que tá sendo guardado são os segundos.
        corrida.setMinutosTempo(minus, 0);

        Log.i("Miliseconds", "Chronometter - Milliseconds: " + milliseconds+" getBase: "+cronometro.getBase());

        MainActivity.Transicao.setCorrida(corrida);
        startActivity(new Intent(CorridaActivity.this, FinishCorrida.class));
    }
}
