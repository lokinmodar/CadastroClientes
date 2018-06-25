package br.com.dantesouzaesouza.cadastroclientes.data.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.concrete.canarinho.formatador.Formatador;
import br.com.concrete.canarinho.formatador.FormatadorCPFCNPJ;
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher;
import br.com.dantesouzaesouza.cadastroclientes.R;
import br.com.dantesouzaesouza.cadastroclientes.data.model.Cliente;
import butterknife.BindView;

public class CadastraActivity extends AppCompatActivity {

    TextView dateTextView;
    EditText nomeEditText;
    EditText cpfEditText;
    EditText idadeEditText;
    EditText telefoneEditText;
    EditText cidadeEditText;
    FloatingActionButton fab;

    SQLiteDatabase meuBanco;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra);
        criaTela();
    }

    public void criaTela(){




        fab = findViewById(R.id.salvaFloatingActionButton);
        fab.setOnClickListener((view) -> Snackbar.make(view, "Salvar novo Cliente?", Snackbar.LENGTH_LONG)
                .setAction("Sim!", v -> salvaDados()).setActionTextColor(Color.GREEN)
                .show()
        );
        dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText(convertTime(System.currentTimeMillis()));
        nomeEditText = findViewById(R.id.nomeEditText);
        cpfEditText = findViewById(R.id.cpfEditText);
        idadeEditText = findViewById(R.id.idadeEditText);
        cidadeEditText = findViewById(R.id.cidadeEditText);
        telefoneEditText = findViewById(R.id.telefoneEditText);

        cpfEditText.addTextChangedListener(new MascaraNumericaTextWatcher("###.###.###-##"));

        telefoneEditText.addTextChangedListener(new MascaraNumericaTextWatcher("(##) #####-####"));
        operacoesBanco();

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

    public void salvaDados(){

         cliente = new Cliente(nomeEditText.getText().toString(),
                Long.parseLong(FormatadorCPFCNPJ.CPF.desformata(cpfEditText.getText().toString())),
                Integer.parseInt(idadeEditText.getText().toString()),
                Long.parseLong(Formatador.TELEFONE.desformata(telefoneEditText.getText().toString())),
                cidadeEditText.getText().toString(),
                desconverteTime(dateTextView.getText().toString())
         );

        salvaNoDb(cliente);

        Intent verLista = new Intent(getApplicationContext(), ListaActivity.class);
        startActivity(verLista);
        finishAffinity();
        Log.v("Salvou?", cliente.toString());
    }

    @Override
    public void onBackPressed(){
        Intent mainIntent = new Intent(getApplicationContext(), TelaInicialActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        return format.format(date);
    }

    public long desconverteTime(String text){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        Date date = null;
        try {
            date = format.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public void salvaNoDb(Cliente n){

        String sql = "INSERT INTO clientes (nome, cpf, idade, telefone, cidade, data) VALUES (?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = meuBanco.compileStatement(sql);
        try {
            statement.bindString(1, n.getNome());
            statement.bindString(2, String.valueOf(n.getCpf()));
            statement.bindString(3, String.valueOf(n.getIdade()));
            statement.bindString(4, String.valueOf(n.getTelefone()));
            statement.bindString(5, n.getCidade());
            statement.bindString(6, String.valueOf(n.getDataCadastro()));

            statement.execute();
        } finally {
            statement.close();
            meuBanco.close();
        }
    }
}
