package folderapp.bar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;

import folderapp.bar.Model.CorridaDAO;
import folderapp.bar.Model.PerfilDAO;

public class Settings extends Fragment {
    private Button btnDelCorridas;
    private CorridaDAO dbCorrida;
    private PerfilDAO dbPerfil;
    private int img;
    protected  View v;

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

        dbPerfil.setSong(4);
//        Toast.makeText(getActivity(), dbPerfil.getSong(), Toast.LENGTH_SHORT).show();


        this.v = v;
        return v;
    }
    public void onRadioBtnClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        RadioButton mark;
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.avatar1:
                if (checked) {
                    this.voltarConfiguração();
                    img = R.drawable.avatar1;
                    mark = (RadioButton) v.findViewById(R.id.avatar1);
                    mark.setBackgroundResource(R.drawable.avatar1_selected);
                    Toast.makeText(getContext(), "Avatar 1 escolhido", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.avatar2:
                if (checked) {
                    this.voltarConfiguração();
                    img = R.drawable.avatar2;
                    mark = (RadioButton) v.findViewById(R.id.avatar2);
                    mark.setBackgroundResource(R.drawable.avatar2_selected);
                    Toast.makeText(getContext(), "Avatar 2 escolhido", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.avatar3:
                if (checked)
                    this.voltarConfiguração();
                img = R.drawable.avatar3;
                mark = (RadioButton) v.findViewById(R.id.avatar3);
                mark.setBackgroundResource(R.drawable.avatar3_selected);
                Toast.makeText(getContext(), "Avatar 3 escolhido", Toast.LENGTH_SHORT).show();
                break;
            case R.id.avatar4:
                if (checked)
                    this.voltarConfiguração();
                img = R.drawable.avatar4;
                mark = (RadioButton) v.findViewById(R.id.avatar4);
                mark.setBackgroundResource(R.drawable.avatar4_selected);
                Toast.makeText(getContext(), "Avatar 4 escolhido", Toast.LENGTH_SHORT).show();
                break;

        }
    }
    public void voltarConfiguração(){
        RadioButton mark1 = (RadioButton) v.findViewById(R.id.avatar1);
        RadioButton mark2 = (RadioButton) v.findViewById(R.id.avatar2);
        RadioButton mark3 = (RadioButton) v.findViewById(R.id.avatar3);
        RadioButton mark4 = (RadioButton) v.findViewById(R.id.avatar4);
        mark1.setBackgroundResource(R.drawable.avatar1);
        mark2.setBackgroundResource(R.drawable.avatar2);
        mark3.setBackgroundResource(R.drawable.avatar3);
        mark4.setBackgroundResource(R.drawable.avatar4);
    }
}
