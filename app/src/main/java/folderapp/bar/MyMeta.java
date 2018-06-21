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
    private FloatingActionButton btnFSalvar, btnFUpdate, btnFCorrer, btnFIncrement, btnFDecrement;
    private Button btnIncremMin, btnDecremMin;
    private TextView tVKm, tVMinutos, tVHoras, tVTempoReg, tVHorario, tVCalendar;
    private TextInputEditText tIETComment;
    private CorridaDAO db;
    // private AppCompatActivity activity;
    private float km = 0;
    private DecimalFormat dc;
    private int minu;
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
            v = inflater.inflate(R.layout.fragment_meta, container, false);

            FloatingActionButton btnIncrement, btnDecrement;

            TextView tVTitle = (TextView) v.findViewById(R.id.tipo_tV);
            tVTitle.setText("Minha Meta");

            btnFSalvar = (FloatingActionButton) v.findViewById(R.id.salvar_btnF);
            btnFCorrer = (FloatingActionButton) v.findViewById(R.id.correr_btnF);
            tVKm = (TextView) v.findViewById(R.id.km_tV); //No xml é EditText mais foi referenciado como TextView.
            tVMinutos = (TextView) v.findViewById(R.id.minutos_eT);
            tVHoras = (TextView) v.findViewById(R.id.horas_eT);
            tIETComment = (TextInputEditText) v.findViewById(R.id.comment_tIET);
            btnIncremMin = (Button) v.findViewById(R.id.incremMin_btn);
            btnDecremMin = (Button) v.findViewById(R.id.decremMin_btn);
            btnFIncrement = (FloatingActionButton) v.findViewById(R.id.increment_btnF);
            btnFDecrement = (FloatingActionButton) v.findViewById(R.id.decrement_btnF);

            //

            //Faz o set nos TextViews

            tVKm.setText(String.valueOf(corrida.getMaxKm()));
            
            //Deve pegar o tempo e transformar em horas, futuramente deverá ser do tipo int
            String minutos = String.valueOf(corrida.getMaxTempo());
            //Deverá mudar futuramente
            tVMinutos.setText(minutos);
            
            
            
            tIETComment.setText(String.valueOf(corrida.getComment()));


            btnFIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acrescenta
                    km = (Float) km + 0.1f;
                    tVKm.setText(Float.toString(Float.parseFloat(dc.format(km))));
                }
            });

            btnFDecrement.setOnClickListener(new View.OnClickListener() {
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
                    minu++;
                    tVMinutos.setText(String.valueOf(minu));
                }
            });

            btnDecremMin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minu = minu - 1;
                    tVMinutos.setText(String.valueOf(minu));
                }
            });
            

    
            btnFSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float km = Float.parseFloat(String.valueOf(tVKm.getText()));
                    String mi = String.valueOf(String.valueOf(tVMinutos.getText()));
                    String co = String.valueOf(String.valueOf(tIETComment.getText()));

                    corrida.setMaxKm(Float.parseFloat(String.valueOf(tVKm.getText())));
                    corrida.setTempo(String.valueOf(tVMinutos.getText()));
                    corrida.setComment(String.valueOf(tIETComment.getText()));


                    db.atualizarCorrida(corrida);
                    //Toast.makeText(Home.this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "Meta atualizada com sucesso", Toast.LENGTH_SHORT).show();

                    MainActivity.Transicao.abrirView(getActivity(), Metas.newInstance());
                }
            });

            btnFCorrer.setOnClickListener(new View.OnClickListener() {
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

            btnFUpdate = (FloatingActionButton) v.findViewById(R.id.update_btnF);
            tVHorario = (TextView) v.findViewById(R.id.horario_tV);
            tVTempoReg = (TextView) v.findViewById(R.id.tempo_reg_tV);
            tVCalendar = (TextView) v.findViewById(R.id.calendar_tV);
            tIETComment = (TextInputEditText) v.findViewById(R.id.comment_upd_tIET);
            tVKm = (TextView) v.findViewById(R.id.meta_km_tV);
            tVMinutos = (TextView) v.findViewById(R.id.max_tempo_tV);
            
            corrida = db.carregarCorridaFinalizada(corrida.getId());

            tVTempoReg.setText(corrida.getTempo());
            tVHorario.setText(corrida.getHorario());
            tVCalendar.setText(corrida.getdiaMesAno());
            tVKm.setText(String.valueOf(corrida.getMaxKm()));
            tVMinutos.setText(corrida.getMaxTempo());
            tIETComment.setText(corrida.getComment());

            ///Implementar o botão de correr novamente;

            //Essa é parte em que ele grava o cometário no bd porém ele guarda taodas as informações do objeto novamente, causando redundância no banco.
             btnFUpdate.setOnClickListener(new View.OnClickListener() {
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
