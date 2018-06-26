package br.com.dantesouzaesouza.cadastroclientes.data.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.concrete.canarinho.formatador.Formatador;
import br.com.concrete.canarinho.formatador.FormatadorCPFCNPJ;
import br.com.concrete.canarinho.validator.Validador;
import br.com.concrete.canarinho.watcher.MascaraNumericaTextWatcher;
import br.com.dantesouzaesouza.cadastroclientes.R;
import br.com.dantesouzaesouza.cadastroclientes.data.model.Cliente;
import br.com.dantesouzaesouza.cadastroclientes.data.model.SampleEventoDeValidacao;
import butterknife.BindView;


//******************************************************

//Instituto Federal de São Paulo - Campus Sertãozinho

//Disciplina......: M4DADM

//Programação de Computadores e Dispositivos Móveis

//Aluno...........: Dante Souza e Souza

//******************************************************



public class CadastraActivity extends AppCompatActivity {

    TextView dateTextView;
    EditText nomeEditText;
    EditText cpfEditText;
    EditText idadeEditText;
    EditText telefoneEditText;
    EditText cidadeEditText;
    FloatingActionButton fab;
    List<Cliente> clientes;
    SQLiteDatabase meuBanco;
    Cliente cliente;
    Boolean carregado;
    TextInputLayout textInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra);
        clientes = new ArrayList<>();
        criaTela();
    }

    public void criaTela(){ //Monta a UI da Tela

        fab = findViewById(R.id.salvaFloatingActionButton); //Configura ação do FloatingActionButton
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
        textInputLayout = findViewById(R.id.edit_input_layout);

        cpfEditText.addTextChangedListener(new MascaraNumericaTextWatcher.Builder()
                .paraMascara("###.###.###-##")
                .comCallbackDeValidacao(new SampleEventoDeValidacao(textInputLayout))
                .comValidador(Validador.CPF)
                .build());

        telefoneEditText.addTextChangedListener(new MascaraNumericaTextWatcher("(##) #####-####"));
        operacoesBanco();

    }

    public void operacoesBanco(){ //abre o banco de dados do SQLite pra operações
        try{
            meuBanco = getApplicationContext().openOrCreateDatabase("Clientes",MODE_PRIVATE, null);
            if (meuBanco.isOpen()) {
                meuBanco.execSQL("CREATE TABLE IF NOT EXISTS clientes (codigo PRIMARY KEY AUTOINCREMENT, nome VARCHAR, cpf BIGINT, idade INTEGER, telefone BIGINT, cidade VARCHAR, data BIGINT)");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void salvaDados(){ //Salva os dados numa instância da classe Cliente
        if (nomeEditText.getText().toString().isEmpty() || cpfEditText.getText().toString().isEmpty() ||
                idadeEditText.getText().toString().isEmpty() || telefoneEditText.getText().toString().isEmpty() ||
                cidadeEditText.getText().toString().isEmpty() || dateTextView.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(CadastraActivity.this);
            builder.setMessage("ERRO! Favor preencher todos os campos para prosseguir!");
            final AlertDialog dialog = builder.setNeutralButton("OK", (dialog1, which) -> {

            }).create();
            dialog.setOnShowListener(arg0 -> dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.BLACK));
            dialog.show();

        } else {

            String selectQuery = "SELECT codigo FROM " + "clientes";
            Cursor cursor = meuBanco.rawQuery(selectQuery, null);
            cursor.moveToLast();
            int codigo = cursor.getCount()-1;
            Log.w("Valor do cursor", Integer.toString(codigo));


            cliente = new Cliente(codigo+1, nomeEditText.getText().toString(),
                    Long.parseLong(FormatadorCPFCNPJ.CPF.desformata(cpfEditText.getText().toString())),
                    Integer.parseInt(idadeEditText.getText().toString()),
                    Long.parseLong(Formatador.TELEFONE.desformata(telefoneEditText.getText().toString())),
                    cidadeEditText.getText().toString(),
                    desconverteTime(dateTextView.getText().toString())
            );
            Log.w("Valor a salvar", cliente.toString());
            salvaNoDb(cliente);
            chamaLista();

        }
    }

    @Override
    public void onBackPressed(){ //volta para a Lista de Itens cadastrados
        chamaLista();
        finish();
    }

    public String convertTime(long time){  //converte data em Millis em Data formatada
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        return format.format(date);
    }

    public long desconverteTime(String text){ //converte data formatada em Data em millis
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        Date date = null;
        try {
            date = format.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public void salvaNoDb(Cliente n){ //Salva os dados no banco de Dados

        String sql = "INSERT INTO clientes (codigo, nome, cpf, idade, telefone, cidade, data) VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = meuBanco.compileStatement(sql);
        try {
            statement.bindString(1, String.valueOf(n.getCodigo()));
            statement.bindString(2, n.getNome());
            statement.bindString(3, String.valueOf(n.getCpf()));
            statement.bindString(4, String.valueOf(n.getIdade()));
            statement.bindString(5, String.valueOf(n.getTelefone()));
            statement.bindString(6, n.getCidade());
            statement.bindString(7, String.valueOf(n.getDataCadastro()));

            statement.execute();
        } finally {
            statement.close();
            meuBanco.close();
        }
    }

    public void chamaLista(){ //chama lista de Itens cadastrados
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

    public void carregaDoBanco() { //Carrega dasos do banco

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
                Cliente item = new Cliente(c.getInt(codigoIndex) ,c.getString(nomeIndex),
                        c.getLong(cpfIndex), c.getInt(idadeIndex), c.getLong(telefoneIndex),
                        c.getString(cidadeIndex), c.getLong(dataIndex));
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

    public int contaClientes() { //Conta quantidade de clientes cadastrados
        return clientes == null ? 0 : clientes.size();
    }
}
