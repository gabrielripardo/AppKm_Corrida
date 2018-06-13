package folderapp.bar;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;

public class Home extends Fragment{
    Context contexto;
    private ImageButton btnCorrer, btnMeta;
    private TextView tVKm, tVMinutos;
    private CorridaDAO db;
    // private AppCompatActivity activity;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        btnCorrer = (ImageButton) v.findViewById(R.id.correrAgora_btn);
        btnMeta = (ImageButton) v.findViewById(R.id.definirMeta_btn); //No xml é EditText mais foi referenciado como TextView.

        btnCorrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Funcionalidade não implementada!", Toast.LENGTH_SHORT).show();
            }
        });

        btnMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar uma meta
                MainActivity.Transicao.abrirView(getActivity(), Meta.newInstance());
            }
        });

        return v;
    }
}
