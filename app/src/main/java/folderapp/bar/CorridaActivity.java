package folderapp.bar;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import folderapp.bar.Model.Corrida;


public class CorridaActivity extends AppCompatActivity {
    private Corrida corrida;
    private TextView kmTxV;
    private Button btnIniciar, btnParar, btnPausar;
    private Chronometer cronometro;
    private long milliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrida);

        corrida = MainActivity.Transicao.getCorrida();
        Log.i("%%%%$$$$$$$", " Id:"+ corrida.getId()+" Km Máximo: "+corrida.getMaxKm());

        milliseconds = 0;

        btnIniciar = (Button) findViewById(R.id.iniciar_btn);
        btnParar = (Button) findViewById(R.id.parar_btn);
        btnPausar = (Button) findViewById(R.id.pausar_btn);
        cronometro = (Chronometer) findViewById(R.id.cronometro);
        kmTxV = (TextView) findViewById(R.id.km_max_tv);

        kmTxV.setText("KM 0.0/"+corrida.getMaxKm());

        btnIniciar.setEnabled(true);
        btnParar.setEnabled(false);
        btnPausar.setEnabled(false);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnIniciar.setBackgroundColor(Color.GREEN);
                btnIniciar.setEnabled(false);
                btnPausar.setEnabled(true);
                btnParar.setEnabled(true);

                cronometro.setBase(SystemClock.elapsedRealtime() - milliseconds);
                cronometro.start();
            }
        });
        btnParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnParar.setBackgroundColor(Color.RED);
                btnIniciar.setEnabled(false);
                btnParar.setEnabled(false);
                btnPausar.setEnabled(false);
                cronometro.stop();
                //A ideia é quando é usuário acionar esse botão, o tempo será armazenado no BD e será aberta uma view.
            }
        });
        btnPausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPausar.setBackgroundColor(Color.YELLOW);
                btnPausar.setEnabled(false);
                btnIniciar.setEnabled(true);
                milliseconds = SystemClock.elapsedRealtime() - cronometro.getBase();
                cronometro.stop();
            }
        });



    }
}
