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

public class Home extends Fragment{
    Context contexto;
    private Button btnVoltar, btnSalvar;
    private FloatingActionButton btnIncrement, btnDecrement, btnIncremMin, btnDecremMin;
    private TextView tVKm, tVMinutos;
    private TextInputEditText tIETComment;
    private CorridaDAO db;
    // private AppCompatActivity activity;
    private float km = 0;
    private DecimalFormat dc;
    private int minutos = 0;

    public static Home newInstance() {
        Home fragment = new Home();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        btnVoltar = (Button) v.findViewById(R.id.voltar_btn);
        tVKm = (TextView) v.findViewById(R.id.km_tV); //No xml é EditText mais foi referenciado como TextView.
        tVMinutos = (TextView) v.findViewById(R.id.minutos_eT);
        tIETComment = (TextInputEditText) v.findViewById(R.id.comment_tIET);
        btnSalvar = (Button) v.findViewById(R.id.salvar_btn);
        btnIncrement = (FloatingActionButton) v.findViewById(R.id.increment_btn);
        btnDecrement = (FloatingActionButton) v.findViewById(R.id.decrement_btn);
        btnIncremMin = (FloatingActionButton) v.findViewById(R.id.incremMin_btn);
        btnDecremMin = (FloatingActionButton) v.findViewById(R.id.decremMin_btn);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tVKm.setText("Run");
            }
        });

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
                tVMinutos.setText(String.valueOf(minutos));
            }
        });

        btnDecremMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minutos = minutos - 1;
                tVMinutos.setText(String.valueOf(minutos));
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float km = Float.parseFloat(String.valueOf(tVKm.getText()));
                String mi = String.valueOf(String.valueOf(tVMinutos.getText()));
                String co = String.valueOf(String.valueOf(tIETComment.getText()));


                db.addCorrida(new Corrida(co, km, mi));
                //Toast.makeText(Home.this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Meta criada com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
