package folderapp.bar;

/*
    Data de criação: 02/06/2018
    obs.: Essa classe é intermediária das outras. É nela que ficará o menu, portanto será a primeira e sempre será executada.
    bibliografia: http://www.truiton.com/2017/01/android-bottom-navigation-bar-example/
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import folderapp.bar.Model.Corrida;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // Tela inicial/menu
                    selectedFragment = Home.newInstance();
                    break;

                case R.id.navigation_metas:
                    // Tela de metas
                    selectedFragment = Metas.newInstance();
                    break;

                case R.id.navigation_statistics:
                    selectedFragment = Statistics.newInstance();
                    break;

                case R.id.navigation_settings:
                    // Tela de configurações
                    selectedFragment = Settings.newInstance();
                    break;

                case R.id.navigation_help:
                    // Tela de ajuda/instrunções.
                    selectedFragment = Help.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, Home.newInstance());
        transaction.commit();



    }
    public static class Transicao{
        public static Corrida crdObj;

        public static void abrirView(FragmentActivity fragActy, Fragment selectedFragment){
            FragmentTransaction transaction = fragActy.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
        }
        public static Corrida getCorrida(){
            return crdObj;
        }
        public static void setCorrida(Corrida c){
            crdObj = c;
        }
    }

}
