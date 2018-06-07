package folderapp.bar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;

public class Metas extends Fragment {
    private ListView lVCorridas;
    private ArrayList<Corrida> elementos;
    private CorridaDAO db;

    public static Metas newInstance() {
        Metas fragment = new Metas();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new CorridaDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_metas, container, false);
        View v = inflater.inflate(R.layout.fragment_metas, container, false);
        lVCorridas = (ListView) v.findViewById(R.id.corridas_lV);
        carregarLista();

        return v;
    }
    public void carregarLista(){

        ArrayList<Corrida> lista = (ArrayList<Corrida>) db.listarTodasCorridas();
        ArrayAdapter adapter = new CorridaAdapter(getActivity(), lista);
        lVCorridas.setAdapter(adapter);

    }
}
