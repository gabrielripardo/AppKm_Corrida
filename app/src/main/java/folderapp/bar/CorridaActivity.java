package folderapp.bar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import folderapp.bar.Model.Corrida;


public class CorridaActivity extends AppCompatActivity {
    private Corrida corrida;
    private TextView kmTxV;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrida);

        kmTxV = (TextView) findViewById(R.id.km_max_tv);
        startBtn = (Button) findViewById(R.id.start_btn);

        corrida = MainActivity.Transicao.getCorrida();

        Log.i("%%%%$$$$$$$", " Id:"+ corrida.getId()+" Km Máximo: "+corrida.getMaxKm());

        
        Log.i("Km Máximo = ", String.valueOf(corrida.getMaxKm()));
        kmTxV.setText("KM 0.0/"+corrida.getMaxKm());

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startBtn.getText()!="PAUSE") {
                    startBtn.setBackgroundColor(Color.RED);
                    startBtn.setText("PAUSE");
                    Log.i(">>> Clicou em pausar","PAUSAR");
                }else{
                    startBtn.setText("RESUME");
                    startBtn.setBackgroundColor(Color.BLUE);
                }
            }
        });

    }
}
