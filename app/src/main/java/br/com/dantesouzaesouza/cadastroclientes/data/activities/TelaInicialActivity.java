package br.com.dantesouzaesouza.cadastroclientes.data.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;
import java.util.List;

import br.com.dantesouzaesouza.cadastroclientes.R;
import br.com.dantesouzaesouza.cadastroclientes.data.model.Cliente;


public class TelaInicialActivity extends AppCompatActivity {

    FloatingActionButton fab;
    SQLiteDatabase meuBanco;
    List<Cliente> clientes;
    Boolean carregado;
       


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        clientes = new ArrayList<>();
        carregado = false;
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
        operacoesBanco();
        carregaDoBanco();
        
        Intent verLista = new Intent(getApplicationContext(), ListaActivity.class);
        int tamanhoLista = contaClientes();
        Log.e("Tamanho lista", String.valueOf(tamanhoLista));
        if (tamanhoLista != 0) {
            verLista.putParcelableArrayListExtra("lista", (ArrayList<? extends Parcelable>) clientes);
        }
        startActivity(verLista);
        finishAffinity();
    }

    public void operacoesBanco(){
        try{
            meuBanco = getApplicationContext().openOrCreateDatabase("Clientes",MODE_PRIVATE, null);
            if (meuBanco.isOpen()) {
                meuBanco.execSQL("CREATE TABLE IF NOT EXISTS clientes (codigo PRIMARY KEY, nome VARCHAR, cpf BIGINT, idade INTEGER, telefone BIGINT, cidade VARCHAR, data BIGINT)");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void carregaDoBanco() {

        Cursor c = meuBanco.rawQuery("SELECT * FROM clientes", null);

        int codigoIndex = c.getColumnIndex("codigo");
        int nomeIndex = c.getColumnIndex("nome");
        int cpfIndex = c.getColumnIndex("cpf");
        int idadeIndex = c.getColumnIndex("idade");
        int telefoneIndex = c.getColumnIndex("telefone");
        int cidadeIndex = c.getColumnIndex("cidade");
        int dataIndex = c.getColumnIndex("data");


        c.moveToFirst();

        try {
            while (c.moveToNext()) {
                Cliente item = new Cliente(c.getString(nomeIndex), c.getLong(cpfIndex), c.getInt(idadeIndex), c.getLong(telefoneIndex), c.getString(cidadeIndex), c.getLong(dataIndex));
                clientes.add(item);
                Log.e("Item", item.toString());

            }

        } finally {
            if (c != null) {
                c.close();
                meuBanco.close();
            }
            carregado = true;

        }
    }

    public int contaClientes() {
        return clientes == null ? 0 : clientes.size();
    }
}
