package folderapp.bar;

/*
	Data de criação 04/06/2018


 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;

public class CorridaAdapter extends ArrayAdapter<Corrida> {
	private Context context;
	private ArrayList<Corrida> elementos;
	private CorridaDAO db;
	
	public CorridaAdapter(Context context, ArrayList<Corrida> elementos){
		super(context, R.layout.linha, elementos);
		this.context = context;
		this.elementos = elementos;
		db = new CorridaDAO(context);
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.linha, parent, false);


		rowView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Corrida corrida = db.carregarCorrida(elementos.get(position).getId());
				Log.i("&&&&&&&&&&", "Item selecionado! Id: "+String.valueOf(corrida.getId())+" Máximo de Km: "+String.valueOf(corrida.getMaxKm()));
				//Ao clicar no item uma nova view será aberta com as informações detalhadas da corrida
			}
		});


		TextView tVmaxKm = (TextView) rowView.findViewById(R.id.maxKm_tV);
		TextView tVmaxTempo = (TextView) rowView.findViewById(R.id.maxTempo_tV);

		tVmaxKm.setText("Item foi selecionado");

		//ImageView imagem = (ImageView) rowView.findViewById(R.id.imagem);
		final Button btnDeletar = (Button) rowView.findViewById(R.id.deletar_btn);

		tVmaxKm.setText(String.valueOf(elementos.get(position).getMaxKm()));
		tVmaxTempo.setText(elementos.get(position).getMaxTempo());
		//imagem.setImageResource(elementos.get(position).getImagem());

		/*
        final Intent intent0 = new Intent();
        intent0.setClass(context, MainActivity.class);
		final Intent intent1 = new Intent();
		intent1.setClass(context, MetasList.class);
		*/

		btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDeletar.setTextColor(Color.RED);
				//code
				db.apagarCorrida(new Corrida(elementos.get(position).getId(), elementos.get(position).getComment(), elementos.get(position).getMaxKm(), elementos.get(position).getMaxTempo()));

				//Como atualizar a activity sem abrir várias dela?
				//Como fechar uma activity?
				/*
                context.startActivity(intent0);
				context.startActivity(intent1);
				*/
            }
        });
		return rowView;
	}
}