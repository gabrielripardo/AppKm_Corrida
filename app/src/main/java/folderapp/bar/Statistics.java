package folderapp.bar;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

import folderapp.bar.Model.Corrida;
import folderapp.bar.Model.CorridaDAO;


public class Statistics extends Fragment {
    private CorridaDAO db;
    protected int cont;
    private List<Corrida> corridas;
    int x;

    public static Statistics newInstance() {
        Statistics fragment = new Statistics();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new CorridaDAO(getActivity());
        cont=0;
        x = 1;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);

        corridas = db.listarTodasCorridas();

        GraphView graph = (GraphView) v.findViewById(R.id.grafico_gV);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(getDataPoint());

         graph.addSeries(series);

        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        series.setTitle("Corridas");
        series.setSpacing(10);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>(){
            @Override
            public int get(DataPoint dataPoint){
                cont++;
                if (cont == x){
                    x+=2;
                    return Color.BLUE;
                }
                return (Color.GREEN);
            }
        });
        return v;
    }
    private DataPoint[] getDataPoint() {
        DataPoint[] dp = new DataPoint[corridas.size() * 2];
        int iC = 0;
        int p = 0;
        for (int i = 0; i < corridas.size() * 2; i++) {
            dp[i] = new DataPoint(p, corridas.get(iC).getMaxKm());
            i++;p++;
            dp[i] = new DataPoint(p, corridas.get(iC).getKm());
            p+=2;
            iC++;
        }
        return dp;
    }
    /*
    private DataPoint[] getDataPoint() {
        DataPoint[] dp = new DataPoint[corridas.size() * 2];
        int iC = 0;
        for (int i = 0; i < corridas.size() * 2; i++) {
            dp[i] = new DataPoint(i, corridas.get(iC).getMaxKm());
            i++;
            dp[i] = new DataPoint(i, corridas.get(iC).getKm());
            iC++;
        }
        return dp;
    }
    */
}
