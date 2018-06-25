package folderapp.bar;



import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import folderapp.bar.Model.Perfil;
import folderapp.bar.Model.PerfilDAO;

/**
 * A login screen that offers login via email/password.
 */
public class MeuPerfil extends AppCompatActivity {
    private AutoCompleteTextView aCTVNome;
    private FloatingActionButton btnConfirm;
    private int img;

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
                p.setFoto(String.valueOf(img));
                pDb.addPerfil(p);

                startActivity(new Intent(MeuPerfil.this, MainActivity.class));
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        RadioButton mark;
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.avatar1:
                if (checked) {
                    this.voltarConfiguração();
                    img = R.drawable.avatar1;
                    mark = (RadioButton) findViewById(R.id.avatar1);
                    mark.setBackgroundResource(R.drawable.ic_about);
                    Toast.makeText(getApplicationContext(), "Avatar 1 escolhido", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.avatar2:
                if (checked) {
                    this.voltarConfiguração();
                    img = R.drawable.avatar2;
                    mark = (RadioButton) findViewById(R.id.avatar2);
                    mark.setBackgroundResource(R.drawable.ic_about);
                    Toast.makeText(getApplicationContext(), "Avatar 2 escolhido", Toast.LENGTH_SHORT).show();
                }
                    break;
            case R.id.avatar3:
                if (checked)
                    this.voltarConfiguração();
                    img = R.drawable.avatar3;
                    mark = (RadioButton) findViewById(R.id.avatar3);
                    mark.setBackgroundResource(R.drawable.ic_about);
                    Toast.makeText(getApplicationContext(), "Avatar 3 escolhido", Toast.LENGTH_SHORT).show();
                break;
            case R.id.avatar4:
                if (checked)
                    this.voltarConfiguração();
                    img = R.drawable.avatar4;
                    mark = (RadioButton) findViewById(R.id.avatar4);
                    mark.setBackgroundResource(R.drawable.ic_about);
                    Toast.makeText(getApplicationContext(), "Avatar 4 escolhido", Toast.LENGTH_SHORT).show();
                break;

        }
    }
    public void voltarConfiguração(){
        RadioButton mark1 = (RadioButton) findViewById(R.id.avatar1);
        RadioButton mark2 = (RadioButton) findViewById(R.id.avatar2);
        RadioButton mark3 = (RadioButton) findViewById(R.id.avatar3);
        RadioButton mark4 = (RadioButton) findViewById(R.id.avatar4);
        mark1.setBackgroundResource(R.drawable.avatar1);
        mark2.setBackgroundResource(R.drawable.avatar2);
        mark3.setBackgroundResource(R.drawable.avatar3);
        mark4.setBackgroundResource(R.drawable.avatar4);
    }
}

