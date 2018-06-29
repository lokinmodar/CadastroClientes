package br.com.dantesouzaesouza.cadastroclientes.data.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chootdev.recycleclick.RecycleClick;

import java.util.ArrayList;
import java.util.List;

import br.com.dantesouzaesouza.cadastroclientes.R;
import br.com.dantesouzaesouza.cadastroclientes.data.model.Cliente;
import br.com.dantesouzaesouza.cadastroclientes.data.model.MeuRecyclerViewAdapter;

//******************************************************

//Instituto Federal de São Paulo - Campus Sertãozinho

//Disciplina......: M4DADM

//Programação de Computadores e Dispositivos Móveis

//Aluno...........: Dante Souza e Souza

//******************************************************



public class ListaActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView meuRecyclerView;
    List<Cliente> clientes;
    MeuRecyclerViewAdapter recyclerViewAdapter;
    SQLiteDatabase meuBanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        if (getIntent().hasExtra("lista")) {
            Log.e("Intent tem extra", getIntent().toString());
            clientes = new ArrayList<>();
            clientes = getIntent().getParcelableArrayListExtra("lista");
            int totalClientes = contaClientes();
            Log.e("Tamanho da lista", String.valueOf(totalClientes));
        }

        criaTela();
    }

    public void criaTela() { //Monta UI
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lista de Clientes");
        ImageView toolbarImage = findViewById(R.id.toolbarImage);

        Glide.with(this)
                .load("https://source.unsplash.com/random")
                .apply(RequestOptions.centerCropTransform())
                .into(toolbarImage);

        fab = findViewById(R.id.cadastraFloatingActionButton);
        fab.setOnClickListener((view) -> Snackbar.make(view, "Cadastrar novo cliente?", Snackbar.LENGTH_LONG)
                .setAction("Sim!", v -> chamaCadastro()).setActionTextColor(Color.YELLOW)
                .show()
        );


        meuRecyclerView = findViewById(R.id.myRView);
        recyclerViewAdapter = new MeuRecyclerViewAdapter(clientes);
        meuRecyclerView.setAdapter(recyclerViewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        meuRecyclerView.setLayoutManager(linearLayoutManager);

        RecycleClick.addTo(meuRecyclerView).setOnItemLongClickListener((recyclerView, position, v) -> { //Permite deletar itens da lista com clique longo
            Log.i("Posicao Clicada:", Integer.toString(position));
            AlertDialog.Builder builder = new AlertDialog.Builder(ListaActivity.this); //alerta pra confirmar deleção
            builder.setMessage("Deletar cliente?");    //seta mensagem

            //quando clicar DELETE
            //não remove o item se "Cancelar" é clicado
            final AlertDialog dialog = builder.setPositiveButton("SIM!", (dialog1, which) -> {
                int posDeletar = clientes.get(position).getCodigo();
                recyclerViewAdapter.removeItem(position);    //item removido do RecyclerView

                operacoesBanco();
                removeDoBanco(posDeletar);
            }).setNegativeButton("CANCELAR", (dialog12, which) -> {
                recyclerViewAdapter.notifyItemRemoved(position + 1);    //notifica o RecyclerView Adapter que o dado foi removido do adapter na posição x".
                recyclerViewAdapter.notifyItemRangeChanged(position, recyclerViewAdapter.getItemCount());   //notifica o RecyclerView Adapter que as posições dos elementos do adapter mudaram (removido item do fim da lista), então atualize a lista.
            }).create();
            dialog.setOnShowListener(arg0 -> {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
            });
            dialog.show();//mostra caixa de alerta

            return false;
        });


    }

    public void chamaCadastro() { //chama tela de Cadastro de Item
        Intent cadastraCliente = new Intent(getApplicationContext(), CadastraActivity.class);
        startActivity(cadastraCliente);
        finishAffinity();
    }

    @Override
    public void onBackPressed() { // volta para a tela inicial
        Intent mainIntent = new Intent(getApplicationContext(), TelaInicialActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public int contaClientes() {
        return clientes == null ? 0 : clientes.size();
    }

    public void operacoesBanco() { //abre banco de dados para operações
        try {
            meuBanco = getApplicationContext().openOrCreateDatabase("Clientes", MODE_PRIVATE, null);
            if (meuBanco.isOpen()) {
                meuBanco.execSQL("CREATE TABLE IF NOT EXISTS clientes (codigo PRIMARY KEY, nome VARCHAR, cpf BIGINT, idade INTEGER, telefone BIGINT, cidade VARCHAR, data BIGINT)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeDoBanco(int posicao) { //Remove item do banco
        Log.w("Código a deletar", Integer.toString(posicao));
        Log.e("tamanho ArrayList", Integer.toString(clientes.size()));
        String sql = "DELETE FROM clientes WHERE codigo='" + posicao + "'";
        Log.e("SQL", sql);
        if (meuBanco.isOpen()) {
            meuBanco.execSQL(sql);
        }
        meuBanco.close(); // fecha o banco de dados
    }
}