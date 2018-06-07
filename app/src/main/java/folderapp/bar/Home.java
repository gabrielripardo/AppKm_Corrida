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
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;

public class Home extends Fragment{
    Context contexto;
    private Button btnCorrer, btnMeta;
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

        btnCorrer = (Button) v.findViewById(R.id.correrAgora_btn);
        btnMeta = (Button) v.findViewById(R.id.definirMeta_btn); //No xml é EditText mais foi referenciado como TextView.

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
                Fragment selectedFragment = null;
                selectedFragment = Meta.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }
        });

        return v;
    }
}
