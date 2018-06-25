package folderapp.bar;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import folderapp.bar.Model.Perfil;
import folderapp.bar.Model.PerfilDAO;

public class Home extends Fragment{
    Context contexto;
    private FloatingActionButton btnFMeta;
    private TextView tVNome;
    private TextView tVKm, tVMinutos;
    private CorridaDAO db;
    private PerfilDAO dbPerfil;
    private Button imgBPerfil;
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
        dbPerfil = new PerfilDAO(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        tVNome = (TextView) v.findViewById(R.id.nome_tV);
        btnFMeta = (FloatingActionButton) v.findViewById(R.id.definirMeta_btnF);
        imgBPerfil = (Button) v.findViewById(R.id.perfil_imgB);

        tVNome.setText(dbPerfil.carregarPerfil(1).getNome());
        imgBPerfil.setBackgroundResource(Integer.parseInt(dbPerfil.carregarPerfil(1).getFoto()));

        btnFMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar uma meta
                MainActivity.Transicao.abrirView(getActivity(), Meta.newInstance());
            }
        });

        return v;
    }
}
