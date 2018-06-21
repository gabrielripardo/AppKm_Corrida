package folderapp.bar;



import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import folderapp.bar.Model.Perfil;
import folderapp.bar.Model.PerfilDAO;

/**
 * A login screen that offers login via email/password.
 */
public class MeuPerfil extends AppCompatActivity {
    private AutoCompleteTextView aCTVNome;
    private FloatingActionButton btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);
        // Set up the login form.
        btnConfirm = (FloatingActionButton) findViewById(R.id.confirm_btn);
        aCTVNome = (AutoCompleteTextView) findViewById(R.id.nome_aCTV);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilDAO pDb = new PerfilDAO(getApplicationContext());
                Perfil p = new Perfil();
                p.setNome(String.valueOf(aCTVNome.getText()));
                pDb.addPerfil(p);

                startActivity(new Intent(MeuPerfil.this, MainActivity.class));
            }
        });
    }
}

