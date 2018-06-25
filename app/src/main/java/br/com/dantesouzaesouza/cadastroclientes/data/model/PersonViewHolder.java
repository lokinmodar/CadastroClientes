package br.com.dantesouzaesouza.cadastroclientes.data.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.dantesouzaesouza.cadastroclientes.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    CardView clienteCard;
    TextView nomeTextView;
    TextView cpfTextView;
    TextView idadeTextView;
    TextView telefoneTextView;
    TextView cidadeTextView;
    TextView dateTextView;

    public PersonViewHolder(View itemView) {
        super(itemView);
        itemView.setOnLongClickListener(this);
        clienteCard = itemView.findViewById(R.id.itemListaCardView);
        nomeTextView = itemView.findViewById(R.id.nomeTextView);
        cpfTextView = itemView.findViewById(R.id.cpfTextView);
        idadeTextView = itemView.findViewById(R.id.idadeTextView);
        telefoneTextView = itemView.findViewById(R.id.telefoneTextView);
        cidadeTextView = itemView.findViewById(R.id.cidadeTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);



    }

    @Override
    public boolean onLongClick(View v) {
        int p = getLayoutPosition();
        System.out.println("Long Click: " + p );
        return false;
    }
}
