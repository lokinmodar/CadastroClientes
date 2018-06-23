package br.com.dantesouzaesouza.cadastroclientes.data.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import br.com.dantesouzaesouza.cadastroclientes.R;
import butterknife.BindView;

public class CadastraActivity extends AppCompatActivity {

    @BindView(R.id.dateTextView) TextView dateTextView;
    @BindView(R.id.nomeEditText) EditText nomeEditText;
    @BindView(R.id.cpfEditText) EditText cpfEditText;
    @BindView(R.id.idadeEditText) EditText idadeEditText;
    @BindView(R.id.telefoneEditText) EditText telefoneEditText;
    @BindView(R.id.cidadeEditText) EditText cidadeEditText;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra);
        criaTela();
    }

    public void criaTela(){
        fab = findViewById(R.id.salvaFloatingActionButton);
        fab.setOnClickListener((view) -> {
                    Snackbar.make(view, "Salvar novo Cliente?", Snackbar.LENGTH_LONG)
                            .setAction("Sim!", v -> salvaDados()).setActionTextColor(Color.GREEN)
                            .show();
                }
        );
    }

    public void salvaDados(){


        Intent verLista = new Intent(getApplicationContext(), ListaActivity.class);
        startActivity(verLista);
        finishAffinity();
    }

    @Override
    public void onBackPressed(){
        Intent mainIntent = new Intent(getApplicationContext(), TelaInicialActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
