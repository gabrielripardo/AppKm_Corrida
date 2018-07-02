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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;

public class MyMeta extends Fragment{
    private Context contexto;
    private FloatingActionButton btnFSalvar, btnFUpdate, btnFCorrer, btnFIncrement, btnFDecrement, btnFCorrerAgain;
    private Button btnIncremMin, btnDecremMin, btnIncremHora, btnDecremHora;;
    private TextView tVKmMeta, tVMinutos, tVHoras, tVTempoReg, tVHorario, tVCalendar, tVKm;
    private TextInputEditText tIETComment;
    private CorridaDAO db;
    private Corrida corrida;
    private DecimalFormat dc;
    private int minutos = 0;
    private int horas = 0;
    private float km = 0;


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

        //Referencia uma classe View.
        View v;

        if(!corrida.isFinalizada()) {
            v = inflater.inflate(R.layout.fragment_meta, container, false);

            TextView tVTitle = (TextView) v.findViewById(R.id.tipo_tV);
            tVTitle.setText("Minha Meta");

            btnFSalvar = (FloatingActionButton) v.findViewById(R.id.salvar_btnF);
            btnFCorrer = (FloatingActionButton) v.findViewById(R.id.correr_btnF);
            tVKmMeta = (TextView) v.findViewById(R.id.km_tV); //No xml é EditText mais foi referenciado como TextView.
            tVMinutos = (TextView) v.findViewById(R.id.minutos_eT);
            tVHoras = (TextView) v.findViewById(R.id.horas_eT);
            tIETComment = (TextInputEditText) v.findViewById(R.id.comment_tIET);
            btnIncremMin = (Button) v.findViewById(R.id.incremMin_btn);
            btnDecremMin = (Button) v.findViewById(R.id.decremMin_btn);
            btnIncremHora = (Button) v.findViewById(R.id.incremHora_btn);
            btnDecremHora = (Button) v.findViewById(R.id.decremHora_btn);
            btnFIncrement = (FloatingActionButton) v.findViewById(R.id.increment_btnF);
            btnFDecrement = (FloatingActionButton) v.findViewById(R.id.decrement_btnF);

            km = corrida.getMaxKm();

            String[] tempoMax = corrida.convetToHoursFormat(corrida.getMaxTempo());

            horas = Integer.parseInt(tempoMax[0]);
            minutos = Integer.parseInt(tempoMax[1]);

            //Faz o set nos TextViews
            tVKmMeta.setText(String.valueOf(corrida.getMaxKm()));
            tVHoras.setText(tempoMax[0]);
            tVMinutos.setText(tempoMax[1]);

            tIETComment.setText(String.valueOf(corrida.getComment()));

            //Controle de Percurso
            btnFIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acrescenta
                    km = (Float) km + 0.1f;
                    tVKmMeta.setText(Float.toString(Float.parseFloat(dc.format(km))));
                }
            });

            btnFDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(km >= 0.1f) {
                        // Diminui
                        km = (Float) km - 0.1f;
                        tVKmMeta.setText(Float.toString(Float.parseFloat(dc.format(km))));
                    }
                }
            });

            //Controle de horas
            btnIncremHora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    horas++;
                    tVHoras.setText(String.valueOf(horas));
                }
            });

            btnDecremHora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(horas>=1) {
                        horas--;
                        tVHoras.setText(String.valueOf(horas));
                    }
                }
            });

            //Controle de minutos
            btnIncremMin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minutos++;
                    if(minutos >= 60){
                        horas++;
                        minutos = 0;
                        tVHoras.setText(String.valueOf(horas));
                    }

                    tVMinutos.setText(String.valueOf(minutos));
                }
            });

            btnDecremMin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(minutos >= 1 || horas >=1) {
                        minutos--;
                        if (minutos <= -1) {
                            minutos = 59;
                            horas--;
                        }
                        tVMinutos.setText(String.valueOf(minutos));
                        tVHoras.setText(String.valueOf(horas));
                    }
                }
            });

            btnFSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    salvarObjeto();

                    db.atualizarCorrida(corrida);
                    //Toast.makeText(Home.this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "Meta atualizada com sucesso", Toast.LENGTH_SHORT).show();

                    MainActivity.Transicao.abrirView(getActivity(), Metas.newInstance());
                }
            });

            btnFCorrer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    salvarObjeto();
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
            tVKmMeta = (TextView) v.findViewById(R.id.meta_km_tV);
            tVMinutos = (TextView) v.findViewById(R.id.max_tempo_tV);
            btnFCorrerAgain = (FloatingActionButton) v.findViewById(R.id.correrAgain_btnF);
            ImageView iVTrofeu = (ImageView) v.findViewById(R.id.trofeu_iV);
            tVKm = (TextView) v.findViewById(R.id.km_tV);

            corrida = db.carregarCorridaFinalizada(corrida.getId());

            switch (corrida.getMedalha()){
                case 1:
                    iVTrofeu.setBackgroundResource(R.drawable.ouro_c_finalizada);
                    break;
                case 2:
                    iVTrofeu.setBackgroundResource(R.drawable.prata_c_finalizada);
                    break;
                case 3:
                    iVTrofeu.setBackgroundResource(R.drawable.bronze_c_finalizada);
                    break;
                default:
                    iVTrofeu.setBackgroundResource(R.drawable.ic_about);
            }

            String[] formatTime = corrida.convetToHoursFormat(corrida.getTempo());
          //  corrida.setMinutosMaxTempo(formatTime[0], formatTime[1]);
            tVTempoReg.setText(String.valueOf(corrida.getTempo()));
            tVHorario.setText(corrida.getHorario());
            tVCalendar.setText(corrida.getdiaMesAno());
            tVKmMeta.setText(String.valueOf(corrida.getMaxKm()));
            tVMinutos.setText(String.valueOf(corrida.getMaxTempo()));
            tIETComment.setText(corrida.getComment());
            tVKm.setText(String.valueOf(corrida.getKm()));

            btnFCorrerAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Corrida c = new Corrida();
                    c.setMaxKm(corrida.getMaxKm());
                    c.setMaxTempo(corrida.getMaxTempo());
                    c.setComment(corrida.getComment());
                    db.addCorrida(c);
                    Toast.makeText(getContext(), "Meta replicada, volte para tela de metas!", Toast.LENGTH_LONG).show();
                }
            });

            //Essa é parte em que ele grava o cometário no bd, porém ele guarda taodas as informações do objeto novamente, causando redundância no banco.
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
    public void salvarObjeto(){
        float km = Float.parseFloat(String.valueOf(tVKmMeta.getText()));
        String mi = String.valueOf(String.valueOf(tVMinutos.getText()));
        String co = String.valueOf(String.valueOf(tIETComment.getText()));

        corrida.setMaxKm(Float.parseFloat(String.valueOf(tVKmMeta.getText())));
        corrida.setMinutosMaxTempo(minutos, horas);
        corrida.setComment(String.valueOf(tIETComment.getText()));
    }
}
