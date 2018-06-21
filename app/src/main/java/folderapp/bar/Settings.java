package folderapp.bar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;
import folderapp.bar.Model.Perfil;
import folderapp.bar.Model.PerfilDAO;

public class Settings extends Fragment {
    private Button btnDelPerfil, btnDelCorridas;
    private CorridaDAO dbCorrida;
    private PerfilDAO dbPerfil;

    public static Settings newInstance() {
        Settings fragment = new Settings();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbPerfil = new PerfilDAO(getActivity());
        dbCorrida = new CorridaDAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        btnDelPerfil = (Button) v.findViewById(R.id.perfilDel_btn);
        btnDelCorridas = (Button) v.findViewById(R.id.corridasDel_btn);

        btnDelCorridas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code
                String result;
                dbCorrida.deletarRegs();

                if(dbCorrida.isEmpty()) {
                    result = "Corridas apagadas com sucesso!";

                }else
                    result = "Erro ao apagar corridas!\n Clique em Ajuda";
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }
        });

        btnDelPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result;
                dbPerfil.deletarRegistros();

                if(dbPerfil.isEmpty()) {
                    result = "Perfil apagado com sucesso!";
                    startActivity(new Intent(getActivity(), MeuPerfil.class));
                }else
                    result = "Erro ao apagar perfil!\n Clique em Ajuda";
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }
}
