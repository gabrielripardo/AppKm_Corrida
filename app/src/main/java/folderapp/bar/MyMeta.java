package folderapp.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;

public class MyMeta extends Fragment{
    Context contexto;
    private Button btnVoltar, btnSalvar, btnCorrer;
    private TextView tVKm, tVMaxTempo, tVTempoReg, tVHorario, tVCalendar;
    private TextInputEditText tIETComment;
    private CorridaDAO db;
    // private AppCompatActivity activity;
    private float km = 0;
    private DecimalFormat dc;
    private int minutos = 0;
    private Corrida corrida;

    public static MyMeta newInstance() {
        MyMeta fragment = new MyMeta();
        return fragment;

    }
    @Override
    public void onAttach(Context context){
        this.contexto = context;
        super.onAttach(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new CorridaDAO(getActivity());
        dc = new DecimalFormat("0.00");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Recupera o objeto corrida
        corrida = MainActivity.Transicao.getCorrida();


        View v;

        if(!corrida.isFinalizada()) {
            v = inflater.inflate(R.layout.fragment_my_meta, container, false);

            FloatingActionButton btnIncrement, btnDecrement, btnIncremMin, btnDecremMin;
            btnVoltar = (Button) v.findViewById(R.id.voltar_btn);
            btnCorrer = (Button) v.findViewById(R.id.correr_btn);
            tVKm = (TextView) v.findViewById(R.id.km_tV); //No xml é EditText mais foi referenciado como TextView.
            tVMaxTempo = (TextView) v.findViewById(R.id.minutos_eT);
            tIETComment = (TextInputEditText) v.findViewById(R.id.comment_tIET);
            btnSalvar = (Button) v.findViewById(R.id.salvar_btn);
            btnIncrement = (FloatingActionButton) v.findViewById(R.id.increment_btn);
            btnDecrement = (FloatingActionButton) v.findViewById(R.id.decrement_btn);
            btnIncremMin = (FloatingActionButton) v.findViewById(R.id.incremMin_btn);
            btnDecremMin = (FloatingActionButton) v.findViewById(R.id.decremMin_btn);

            //Faz o set nos TextViews

            tVKm.setText(String.valueOf(corrida.getMaxKm()));
            tVMaxTempo.setText(String.valueOf(corrida.getMaxTempo()));
            tIETComment.setText(String.valueOf(corrida.getComment()));

            btnIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acrescenta
                    km = (Float) km + 0.1f;
                    tVKm.setText(Float.toString(Float.parseFloat(dc.format(km))));
                }
            });

            btnDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Diminui
                    km = (Float) km - 0.1f;
                    tVKm.setText(Float.toString(Float.parseFloat(dc.format(km))));
                }
            });

            btnIncremMin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minutos = minutos + 1;
                    tVMaxTempo.setText(String.valueOf(minutos));
                }
            });

            btnDecremMin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minutos = minutos - 1;
                    tVMaxTempo.setText(String.valueOf(minutos));
                }
            });

            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float km = Float.parseFloat(String.valueOf(tVKm.getText()));
                    String mi = String.valueOf(String.valueOf(tVMaxTempo.getText()));
                    String co = String.valueOf(String.valueOf(tIETComment.getText()));

                    corrida.setMaxKm(Float.parseFloat(String.valueOf(tVKm.getText())));
                    corrida.setMaxTempo(String.valueOf(tVMaxTempo.getText()));
                    corrida.setComment(String.valueOf(tIETComment.getText()));

                    db.atualizarCorrida(corrida);
                    //Toast.makeText(Home.this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "Meta atualizada com sucesso", Toast.LENGTH_SHORT).show();
                }
            });
            btnVoltar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.Transicao.abrirView(getActivity(), Metas.newInstance());
                }
            });
            btnCorrer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrida.setComment(String.valueOf(tIETComment.getText()));
                    MainActivity.Transicao.setCorrida(corrida);
                    Intent intent = new Intent(getActivity(), CorridaActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            v = inflater.inflate(R.layout.fragment_my_meta_finalizada, container, false);

            btnCorrer = (Button) v.findViewById(R.id.salvar_btn);
            tVHorario = (TextView) v.findViewById(R.id.horario_tV);
            tVTempoReg = (TextView) v.findViewById(R.id.tempo_reg_tV);
            tVCalendar = (TextView) v.findViewById(R.id.calendar_tV);
            tIETComment = (TextInputEditText) v.findViewById(R.id.comment_upd_tIET);
            tVKm = (TextView) v.findViewById(R.id.meta_km_tV);
            tVMaxTempo = (TextView) v.findViewById(R.id.max_tempo_tV);
            
            corrida = db.carregarCorridaFinalizada(corrida.getId());

            tVTempoReg.setText(corrida.getTempo());
            tVHorario.setText(corrida.getHorario());
            tVCalendar.setText(corrida.getdiaMesAno());
            tVKm.setText(String.valueOf(corrida.getMaxKm()));
            tVMaxTempo.setText(corrida.getMaxTempo());
            tIETComment.setText(corrida.getComment());

            //Essa é parte em que ele grava o cometário no bd porém ele guarda taodas as informações do objeto novamente, causando redundância no banco.
            btnCorrer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corrida.setComment(String.valueOf(tIETComment.getText()));
                    db.atualizarCorridaFinalizada(corrida);
                    Toast.makeText(getContext(), "Comentário salvado com sucesso!", Toast.LENGTH_LONG).show();
                }
            });
            
        }
        Log.i("%%%%$$$$$$###########", "Tempo: "+corrida.getTempo());
        return v;
    }
}
