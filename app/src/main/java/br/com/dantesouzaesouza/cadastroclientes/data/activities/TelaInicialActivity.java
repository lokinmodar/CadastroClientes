package br.com.dantesouzaesouza.cadastroclientes.data.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import br.com.dantesouzaesouza.cadastroclientes.R;


public class TelaInicialActivity extends AppCompatActivity {

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        criaTela();
    }

    public void criaTela(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView toolbarImage = findViewById(R.id.toolbarImage);

        Glide.with(this)
                .load("https://source.unsplash.com/random")
                .apply(RequestOptions.centerCropTransform())
                .into(toolbarImage);


        fab = findViewById(R.id.verListaFloatingActionButton);
        fab.setOnClickListener((view) -> Snackbar.make(view, "Ver clientes salvos?", Snackbar.LENGTH_LONG)
                .setAction("Sim!", v -> chamaLista()).setActionTextColor(Color.YELLOW)
                .show()
        );
    }

    public void chamaLista(){
        Intent verLista = new Intent(getApplicationContext(), ListaActivity.class);
        startActivity(verLista);
        finishAffinity();
    }
}