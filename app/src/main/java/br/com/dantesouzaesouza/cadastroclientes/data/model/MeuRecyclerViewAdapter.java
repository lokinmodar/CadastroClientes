package br.com.dantesouzaesouza.cadastroclientes.data.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.dantesouzaesouza.cadastroclientes.R;

public class MeuRecyclerViewAdapter extends RecyclerView.Adapter<PersonViewHolder> {
    private List<Cliente> clientes = new ArrayList<>();
    static AdapterView.OnItemLongClickListener itemLongClickListener;

    public void setItemLongClickListener(AdapterView.OnItemLongClickListener itemLongClickListener){
        MeuRecyclerViewAdapter.itemLongClickListener = itemLongClickListener;
    }

    public MeuRecyclerViewAdapter(List<Cliente> clientes){
        this.clientes = clientes;
    }

    public void refreshData(List<Cliente> clientes){
        this.clientes = clientes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_lista, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.nomeTextView.setText(clientes.get(position).getNome());
        holder.cpfTextView.setText(String.valueOf(clientes.get(position).getCpf()));
        holder.idadeTextView.setText(String.valueOf(clientes.get(position).getIdade()));
        holder.telefoneTextView.setText(String.valueOf(clientes.get(position).getTelefone()));
        holder.cidadeTextView.setText(clientes.get(position).getCidade());
        holder.dateTextView.setText("Salvo em: " + convertTime(clientes.get(position).getDataCadastro()));

    }

    @Override
    public int getItemCount() {
        return clientes == null ? 0 : clientes.size();
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        return format.format(date);
    }

    public void removeItem(int position){
        clientes.remove(position);
    }
}
