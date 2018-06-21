package folderapp.bar;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;

public class Meta extends Fragment{
    Context contexto;
    private Button btnIncremMin, btnDecremMin, btnIncremHora, btnDecremHora;
    private FloatingActionButton btnFSalvar, btnFCorrer, btnFIncrement, btnFDecrement;
    private TextView tVKm, tVMinutos, tVHoras;
    private TextInputEditText tIETComment;
    private CorridaDAO db;
    private float km = 0;
    private DecimalFormat dc;
    private int minutos = 0;
    private int horas = 0;

    public static Meta newInstance() {
        Meta fragment = new Meta();
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
        View v = inflater.inflate(R.layout.fragment_meta, container, false);

        //btnVoltar = (Button) v.findViewById(R.id.voltar_btn);
        tVKm = (TextView) v.findViewById(R.id.km_tV); //No xml é EditText mais foi referenciado como TextView.
        tVMinutos = (TextView) v.findViewById(R.id.minutos_eT);
        tVHoras= (TextView) v.findViewById(R.id.horas_eT);
        tIETComment = (TextInputEditText) v.findViewById(R.id.comment_tIET);
        btnFCorrer = (FloatingActionButton) v.findViewById(R.id.correr_btnF);
        btnFSalvar = (FloatingActionButton) v.findViewById(R.id.salvar_btnF);
        btnFIncrement = (FloatingActionButton) v.findViewById(R.id.increment_btnF);
        btnFDecrement = (FloatingActionButton) v.findViewById(R.id.decrement_btnF);
        btnIncremMin = (Button) v.findViewById(R.id.incremMin_btn);
        btnDecremMin = (Button) v.findViewById(R.id.decremMin_btn);
        btnIncremHora = (Button) v.findViewById(R.id.incremHora_btn);
        btnDecremHora = (Button) v.findViewById(R.id.decremHora_btn);

        // Seta as informações
        tVHoras.setText("0");
        tVMinutos.setText("0");

        //Controle de Percurso
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
                if(km >= 0.1f) {
                    // Diminui
                    km = (Float) km - 0.1f;
                    tVKm.setText(Float.toString(Float.parseFloat(dc.format(km))));
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
                float km = Float.parseFloat(String.valueOf(tVKm.getText()));
             //   int mi =String.valueOf(tVMinutos.getText());
                String co = String.valueOf(String.valueOf(tIETComment.getText()));

                Corrida c = new Corrida();
                c.setMaxKm(km);
                c.setComment(co);
                c.setMinutos(minutos, horas);

                db.addCorrida(c);
                Toast.makeText(getActivity(), "Meta criada com sucesso!", Toast.LENGTH_SHORT).show();
                MainActivity.Transicao.abrirView(getActivity(), Metas.newInstance());

            }
        });/*
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Transicao.abrirView(getActivity(), Home.newInstance());
            }
        });
*/
        return v;
    }
}
