package br.com.dantesouzaesouza.cadastroclientes.data.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.dantesouzaesouza.cadastroclientes.R;

public class ListaActivity extends AppCompatActivity {

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        criaTela();
    }

    public void criaTela(){
        fab = findViewById(R.id.cadastraFloatingActionButton);
        fab.setOnClickListener((view) -> {
                    Snackbar.make(view, "Cadastrar novo cliente?", Snackbar.LENGTH_LONG)
                            .setAction("Sim!", v -> chamaCadastro()).setActionTextColor(Color.YELLOW)
                            .show();
                }
        );
    }

    public void chamaCadastro(){
        Intent cadastraCliente = new Intent(getApplicationContext(), CadastraActivity.class);
        startActivity(cadastraCliente);
        finishAffinity();
    }

}
