package br.com.dantesouzaesouza.cadastroclientes.data.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.dantesouzaesouza.cadastroclientes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    @BindView(R.id.itemListaCardView) CardView clienteCard;
    @BindView(R.id.nomeTextView) TextView nomeTextView;
    @BindView(R.id.cpfTextView) TextView cpfTextView;
    @BindView(R.id.idadeTextView) TextView idadeTextView;
    @BindView(R.id.telefoneTextView) TextView telefoneTextView;
    @BindView(R.id.cidadeTextView) TextView cidadeTextView;
    @BindView(R.id.codigoTextView) TextView codigoTextView;

    public PersonViewHolder(View itemView) {
        super(itemView);
        itemView.setOnLongClickListener(this);
        ButterKnife.bind(this, itemView);


    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
