package folderapp.bar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import folderapp.bar.Model.CorridaDAO;

public class Settings extends Fragment {
    private Button btnDropDB;
    private CorridaDAO db = new CorridaDAO(getContext());

    public static Settings newInstance() {
        Settings fragment = new Settings();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);;

        btnDropDB = (Button) v.findViewById(R.id.drop_bd_btn);

        btnDropDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = null;
                if(db.apagarBanco())
                    result = "Banco de Dados deletado com sucesso!";
                else
                    result = "Falha ao deletar Banco de Dados!";

                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
