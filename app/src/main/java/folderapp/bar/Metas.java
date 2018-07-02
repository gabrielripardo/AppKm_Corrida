package folderapp.bar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        View v = inflater.inflate(R.layout.fragment_metas, container, false);
        lVCorridas = (ListView) v.findViewById(R.id.corridas_lV);
        carregarLista();

        return v;
    }
    public void carregarLista(){
        FragmentActivity fA = getActivity();
        ArrayList<Corrida> lista = (ArrayList<Corrida>) db.listarTodasCorridas();
        ArrayAdapter adapter = new CorridaAdapter(getActivity(), lista, fA);
        lVCorridas.setAdapter(adapter);
    }

    public class CorridaAdapter extends ArrayAdapter<Corrida> {
        private Context context;
        private ArrayList<Corrida> elementos;
        private CorridaDAO db;
        private FragmentActivity fragActy;

        public CorridaAdapter(Context context, ArrayList<Corrida> elementos, FragmentActivity fA){
            super(context, R.layout.linha, elementos);
            this.context = context;
            this.elementos = elementos;
            this.fragActy = fA;
            db = new CorridaDAO(context);
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.linha, parent, false);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Corrida corrida = db.carregarCorrida(elementos.get(position));
                    MainActivity.Transicao.setCorrida(corrida);
                    MainActivity.Transicao.abrirView(fragActy, MyMeta.newInstance());
                }
            });

            ImageView iVMedalha = (ImageView) rowView.findViewById(R.id.medalha_iV);
            TextView tVmaxKm = (TextView) rowView.findViewById(R.id.maxKm_tV);
            TextView tVmaxTempo = (TextView) rowView.findViewById(R.id.maxTempo_tV);
            ImageView imagem = (ImageView) rowView.findViewById(R.id.status_iV);
            final Button btnDeletar = (Button) rowView.findViewById(R.id.deletar_btn);

            //Recupera o número da imagem da medalha e faz o set no iV
            iVMedalha.setBackgroundResource(elementos.get(position).obterResource());

            //Faz os set da meta
            tVmaxKm.setText(String.valueOf(elementos.get(position).getMaxKm()));
            tVmaxTempo.setText(String.valueOf(elementos.get(position).getMaxTempo()));

            //Através do atributo "finalizada" é gerado uma String com a imagem de referência.
            if(elementos.get(position).isFinalizada()){
                imagem.setImageResource(R.drawable.corrida_on);
            }

            btnDeletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Corrida c = new Corrida();
                c.setId(elementos.get(position).getId());
                db.apagarCorrida(c);
                MainActivity.Transicao.abrirView(fragActy, Metas.newInstance());
                Toast.makeText(getContext(), "Meta deletada com sucesso!", Toast.LENGTH_LONG).show();
                }
            });
            return rowView;
        }
    }
}
