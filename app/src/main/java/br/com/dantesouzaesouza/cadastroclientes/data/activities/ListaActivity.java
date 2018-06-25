package br.com.dantesouzaesouza.cadastroclientes.data.activities;

import android.content.DialogInterface;
import android.content.Intent;
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

public class ListaActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView meuRecyclerView;
    List<Cliente> clientes;
    MeuRecyclerViewAdapter recyclerViewAdapter;

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

    public void criaTela(){
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

        RecycleClick.addTo(meuRecyclerView).setOnItemLongClickListener((recyclerView, position, v) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(ListaActivity.this); //alert for confirm to delete
            builder.setMessage("Deletar cliente?");    //set message

            //when click on DELETE
            //not removing items if cancel is done
            final AlertDialog dialog = builder.setPositiveButton("SIM!", (dialog1, which) -> {
                recyclerViewAdapter.notifyItemRemoved(position);    //item removed from recylcerview
                clientes.remove(position);  //then remove item
            }).setNegativeButton("CANCELAR", (dialog12, which) -> {
                recyclerViewAdapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                recyclerViewAdapter.notifyItemRangeChanged(position, recyclerViewAdapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
            }).create();
            dialog.setOnShowListener(arg0 -> {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
            });
            dialog.show();//shows alert dialog

            return false;
        });



    }

    public void chamaCadastro(){
        Intent cadastraCliente = new Intent(getApplicationContext(), CadastraActivity.class);
        startActivity(cadastraCliente);
        finishAffinity();
    }

    @Override
    public void onBackPressed(){
        Intent mainIntent = new Intent(getApplicationContext(), TelaInicialActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public int contaClientes() {
        return clientes == null ? 0 : clientes.size();
    }


}
